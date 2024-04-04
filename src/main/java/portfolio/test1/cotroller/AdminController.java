package portfolio.test1.cotroller;


import jakarta.persistence.criteria.From;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio.test1.DTO.*;
import portfolio.test1.Repositiry.CategoryRepository;
import portfolio.test1.Repositiry.ItemFileRepository;
import portfolio.test1.Repositiry.ItemRepository;
import portfolio.test1.Service.CategoryItemService;
import portfolio.test1.Service.ItemModifyService;
import portfolio.test1.Service.ItemService;
import portfolio.test1.Service.MemberService;
import portfolio.test1.entity.CategoryEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.ItemFileEntity;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/Admin")
@Log4j2
public class AdminController {

    private final ItemService itemService;
    private final MemberService memberService;
    private final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    private final ItemFileRepository itemFileRepository;
    private final ItemModifyService itemModifyService;
    private final ItemRepository itemRepository;
    private final CategoryItemService categoryItemService;
    private final CategoryRepository categoryRepository;

    @Value("${save.path}")
    private String path;

    @GetMapping("/admin")
    public String admin() {
        return "/Admin/admin";
    }

    @GetMapping("/write") // <- 상품등록 ? 이라면
    public String write(Model model) {
        LOGGER.info("[상품 정보] 상품등록 페이지 입니다.");
        LOGGER.info("[환경 변수] 현재 등록된 환경 변수: "+path);

        List<CategoryEntity> all = categoryRepository.findFirst();
        model.addAttribute("category",all);

        return "/Admin/write";
    }
    @PostMapping("/save")
    public String write_proc(@ModelAttribute ItemCreateDTO dto) throws IOException {
    //24-03-27

        itemService.save(dto);
    return "/Admin/admin";
    }
    //////////////////////썸네일 해야됨
    @GetMapping("/memberList")
    public String list(Model model){
        List<MemberDTO> memberDTOS = memberService.findAll();

        model.addAttribute("member",memberDTOS);

        return "/Admin/memberList";
    }
    @GetMapping("/itemlist")
    public String items(Model model) {
        List<ItemListDTO> dtos = itemService.findAll();



        model.addAttribute("dtos",dtos);
        return "/Admin/itemlist";
    }

    @GetMapping("/modify/{idx}")
    public String modify(@PathVariable("idx") Long idx, Model model) {
        List<ItemFileEntity> list = itemFileRepository.findByItemEntity_Idx(idx);

        //itemRepository.findByCategory_Idx(idx);
        List<Long> childIdx = new ArrayList<>();
        for(int i=0; i< list.size(); i++) {
            childIdx.add(list.get(i).getIdx());
        }

         ItemDTO dto = itemService.findById(idx);
        dto.setC_Idx(childIdx);

        String first = dto.getCategory().getFirst();
        String second = dto.getCategory().getSecond();

        model.addAttribute("item" ,dto);

        List<CategoryEntity> categoryEntityList = categoryRepository.findFirst();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntityList){
            categoryDTOList.add(CategoryDTO.toCategoryDTO(categoryEntity));
        }
        model.addAttribute("category1", categoryDTOList);

        List<CategoryEntity> categoryEntityList1 = categoryRepository.findSecond(first);
        List<CategoryDTO> categoryDTOList1 = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntityList1){
            categoryDTOList1.add(CategoryDTO.toCategoryDTO(categoryEntity));
        }
        model.addAttribute("category2", categoryDTOList1);

        List<CategoryEntity> categoryEntityList2 = categoryRepository.findThird(first, second);
        List<CategoryDTO> categoryDTOList2 = new ArrayList<>();
        for(CategoryEntity categoryEntity : categoryEntityList2){
            categoryDTOList2.add(CategoryDTO.toCategoryDTO(categoryEntity));
        }
        model.addAttribute("category3", categoryDTOList2);


         return "/Admin/modify";
    }
    @PostMapping("/modify_proc")
    public String modify_proc(@ModelAttribute ItemCreateDTO dto,
                              @RequestParam(name = "check1")List<String> check) throws  Exception{

        itemModifyService.update(dto,check);
            //24-03-27
        return "/Admin/admin";
    }
    @GetMapping("/delete/{idx}")
    public String delete(@PathVariable("idx") Long idx) {

        itemService.delete(idx);

        return "/Admin/admin";
    }
    @GetMapping("/CategoryCreate")
    public String Category(Model model) {
        String value = "1";


        List<CategoryEntity> all = categoryRepository.findFirst();
        Long firstIdxidx = categoryRepository.findFirstIdx();
        if(firstIdxidx != null) {
            firstIdxidx++;
            value = firstIdxidx.toString();
        }
        model.addAttribute("FirstValue", value);
        model.addAttribute("category",all);

        return "/Admin/CategoryCreate";
    }
    /* 카테고리에서 select 값 불러오기*/
    @PostMapping("/select")
    public String category(@ModelAttribute CategoryDTO dto) {

        CategoryEntity categoryEntity = CategoryEntity.toSave(dto);
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<String> categoryName= new ArrayList<>();
        List<String> first= new ArrayList<>();

        for(int i=0; i<categoryEntityList.size(); i++) {
            categoryName.add(categoryEntityList.get(i).getCategoryName());
            first.add(categoryEntityList.get(i).getFirst());
        }
        if(dto.getSecond().equals("0") && dto.getThird().equals("0")) {
            if(!categoryName.contains(dto.getName())){
                categoryRepository.save(categoryEntity);
            }
        }else {
            categoryRepository.save(categoryEntity);
        }

        return "redirect:/Admin/admin";
    }
    @PostMapping("/first")
    @ResponseBody
    public List<SecondCategoryDTO> firstCheck(@RequestParam("first") String first,
                                              @RequestParam("second") String second) {

        List<SecondCategoryDTO> dtos = categoryItemService.select(first,second);

       return dtos;
    }
    @GetMapping("/MemberDelete/{idx}")
    public String MemberUpdate(@PathVariable("idx") Long idx, Model model) {
        log.info(idx);
        memberService.delete(idx);


        return "redirect:/Admin/memberList";
    }


}

