package portfolio.test1.cotroller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import portfolio.test1.DTO.ItemDTO;
import portfolio.test1.DTO.ItemListDTO;
import portfolio.test1.Service.CategoryItemService;
import portfolio.test1.Service.ItemService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
@Log4j2
public class categoryController {

    private final CategoryItemService categoryItemService;
    private final ItemService itemService;


    @GetMapping("/about")
    public String about() {

        return "/category/about";
    }
    @GetMapping("/community")
    public String community() {

        return "/category/community";
    }
    @GetMapping("/list/{idx}")
    public  String aaa(@PathVariable("idx")Long idx, Model model, @PageableDefault(page = 1) Pageable pageable){

        String CategoryName = categoryItemService.findById(idx);
        Page<ItemListDTO> dtos = itemService.paging(idx,pageable);



        int blockLimit = 3;  //페이지 번호의 개수
        //첫번째 페이지 번호
        int startPage = ((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit)) -1) * blockLimit + 1;

        int endPage = 1;
        int lastPage = startPage + blockLimit  -1;
        int totalPage = dtos.getTotalPages();

        if((lastPage< dtos.getTotalPages())) {
            endPage =lastPage;
        }else {
            if(!dtos.isEmpty()) {

                endPage = totalPage;
            }
        }
        //줄일수 있다.
        model.addAttribute("CategoryName" ,CategoryName);
        model.addAttribute("Category_idx",idx);


        model.addAttribute("dtos",dtos);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "/category/list";
    }
    @GetMapping("/view/{idx}")
    public String view(@PathVariable("idx")Long idx,Model model){
        ItemDTO dto = itemService.findById(idx);
        String[] dtos = dto.getContents().split("- ");
        model.addAttribute("contents" ,dtos);
        model.addAttribute("dto",dto);

        return "/category/view";
    }
}
