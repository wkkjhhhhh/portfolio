package portfolio.test1.cotroller;


import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio.test1.DTO.*;
import portfolio.test1.Repositiry.CartRepository;
import portfolio.test1.Repositiry.CategoryRepository;
import portfolio.test1.Service.ItemService;
import portfolio.test1.Service.MemberService;
import portfolio.test1.config.CustomUserDetails;
import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@ControllerAdvice
@Log4j2
public class ShopController {

    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final ItemService itemService;
    private final CartRepository cartRepository;

    public ShopController(MemberService memberService, CategoryRepository categoryRepository, ItemService itemService, CartRepository cartRepository) {
        this.memberService = memberService;
        this.categoryRepository = categoryRepository;
        this.itemService = itemService;
        this.cartRepository = cartRepository;
    }

    @ModelAttribute
    public void globalModel(Model model) {
        // 모든 핸들러 메소드에서 공통으로 사용할 모델 속성 추가
        List<CategoryEntity> first = categoryRepository.findAll();
        List<CategoryEntity> second = categoryRepository.findAll();
        List<CategoryEntity> third = categoryRepository.findAll();


        model.addAttribute("first", first);
        model.addAttribute("second", second);
        model.addAttribute("third", third);



    }
    @GetMapping("/")
    public String list(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername(); //세션 username


        return "home";
    }
    @GetMapping("/home")
    public String home(Model model) {


        return "home";
    }
    @GetMapping("/login")
    public String login() {


        return "login";
    }
    @GetMapping("/join")
    public String join() {

        return "join";
    }

    @PostMapping("/idcheck")
    public @ResponseBody int idCheck(@RequestBody JoinMemberDto dto) {
        boolean result = memberService.finduserid(dto.getUserid());
        if(result){
            return 1; // 중복
        }else {
            return 0; // 사용가능
        }
    }
    @PostMapping("/join_proc")
    public String join_proc(MemberDTO dto) {
        memberService.save(dto);

        return "home";
    }
    @GetMapping("/cart")
    public String cart(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                       @RequestParam(name = "item_idx" ,required = false)Long idx ,
                       @RequestParam(name = "quantity",required = false)Integer quantity,
                       Model model) {

        String username = customUserDetails.getUsername();
        if(idx != null && quantity != null && idx != 0 && quantity != 0) {
            itemService.saveCart(username, idx, quantity);
        }
        List<CartDTO> cart = itemService.findByUsername(username);

        model.addAttribute("dto", cart);

        return "cart";
    }



    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails,Model model) {
        String username = customUserDetails.getUsername();

       MyPageDto dto = memberService.findUserid(username);


        model.addAttribute("member", dto);
        return "mypage";
    }

}
