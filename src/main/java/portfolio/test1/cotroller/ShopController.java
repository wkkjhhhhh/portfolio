package portfolio.test1.cotroller;


import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import portfolio.test1.DTO.*;
import portfolio.test1.DTO.OAuth2.CustomOAuth2User;
import portfolio.test1.DTO.Pay.OrderDTO;
import portfolio.test1.DTO.Pay.DeliveryDTO;
import portfolio.test1.Repositiry.CartRepository;
import portfolio.test1.Repositiry.CategoryRepository;
import portfolio.test1.Repositiry.ItemRepository;
import portfolio.test1.Repositiry.OrderRepository;
import portfolio.test1.Service.ItemService;
import portfolio.test1.Service.MemberService;
import portfolio.test1.Service.PaySerivce;
import portfolio.test1.config.CustomUserDetails;
import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.CategoryEntity;
import portfolio.test1.entity.ItemEntity;
import portfolio.test1.entity.Pay.OrderEntity;
import portfolio.test1.entity.Pay.OrderItemEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Controller
@ControllerAdvice
@Log4j2
public class ShopController {

    private final MemberService memberService;
    private final CategoryRepository categoryRepository;
    private final ItemService itemService;
    private final CartRepository cartRepository;
    private final PaySerivce paySerivce;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private CustomOAuth2User customUserDetails;


    public ShopController(MemberService memberService, CategoryRepository categoryRepository, ItemService itemService, CartRepository cartRepository, PaySerivce paySerivce, OrderRepository orderRepository, ItemRepository itemRepository) {
        this.memberService = memberService;
        this.categoryRepository = categoryRepository;
        this.itemService = itemService;
        this.cartRepository = cartRepository;
        this.paySerivce = paySerivce;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
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
    public String list() {
        //String username = customUserDetails.getUsername(); //세션 username


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
                       @AuthenticationPrincipal CustomOAuth2User customOAuth2User,
                       @RequestParam(name = "item_idx" ,required = false)Long idx ,
                       @RequestParam(name = "quantity",required = false)Integer quantity,
                       Model model) {
        String username = null;
        if(customUserDetails.getUsername() != null) {
             username = customUserDetails.getUsername();
        }else {
            username = customOAuth2User.getUsername();
        }


        if(idx != null && quantity != null && idx != 0 && quantity != 0) {
            itemService.saveCart(username, idx, quantity);
        }
        List<CartDTO> cart = itemService.findByUsername(username);

        model.addAttribute("dto", cart);

        return "cart";
    }

    /**
     * 장바구니 수량 바꾸기
     * @param idx 카트에 idx
     * @param quantity 새로 넣을 아이템 갯수
     */
    @PostMapping("/updateCart")
    @ResponseBody
    public void updateCart(@RequestParam("idx")Long idx,
                             @RequestParam("quantity")int quantity) {
    itemService.update(idx,quantity);
    }

    /**
     * 장바구니 물품 삭제
     * @param idx 카트 idx
     */
    @GetMapping("/deleteCart")
    public String deleteCart(@RequestParam("idx")Long idx) {

        CartEntity cartEntity = cartRepository.findById(idx).orElse(null);
        cartRepository.delete(Objects.requireNonNull(cartEntity));

        return "redirect:/cart";
    }

    /**
     *  네 페이지
     * @param customUserDetails 내 아이디 확인
     * @param model 회원 정보, 결제 내역
     * @return 내정보창
     */
    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                         @AuthenticationPrincipal CustomOAuth2User customOAuth2User,

                         Model model) {
        String username = null;
        if(customUserDetails != null) {
            username = customUserDetails.getUsername();
        }else {
             username = customOAuth2User.getUsername();
        }


       MyUserDto dto = memberService.findUserid(username);


        model.addAttribute("member", dto);
        return "mypage";
    }

    /**
     * @param idx 구매할 장바구니
     * @param totalPay 총 결제 금액
     * @param principal 로그인한 회원
     * @return 결제 창
     */
    @PostMapping("/buy")
    public String Buy(@RequestParam("check") List<Long> idx,
                      @RequestParam("totalPay")String totalPay,
                      Model model,
                      //@AuthenticationPrincipal CustomUserDetails customUserDetails
                      Principal principal) {
        List<CartDTO> cartDTOs = new ArrayList<>();
        for(int i=0; i< idx.size(); i++) {
            CartEntity cartEntity = cartRepository.findById(idx.get(i)).orElse(null);
            if(cartEntity != null) {
                CartDTO cartDTO = CartDTO.toChange(cartEntity);
                cartDTOs.add(cartDTO);
            }
        }

        String username = principal.getName();
        MyUserDto user = memberService.findUserid(username);
        //회원 정보
        model.addAttribute("user",user);
        //체크한 아이템들 리스트 뽑아오기
        model.addAttribute("dtos",cartDTOs);
        //총 결제 해야할 금액 표시
        model.addAttribute("total",totalPay);
        return "/Buy";
    }

    @GetMapping("/buy")
    public String buyIt(@RequestParam("idx")Long idx,
                        @RequestParam("quantity") int quantity,
                        Model model,
                        @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        String username = customUserDetails.getUsername();

        //아이템 보내주기
        ItemEntity item = itemRepository.findById(idx).orElse(null);
        List<CartDTO> cartDTOs = new ArrayList<>();
        CartDTO dto = new CartDTO();
        dto.setItem(item);
        dto.setQuantity(quantity);
        dto.setUsername(username);
        cartDTOs.add(dto);
        model.addAttribute("dtos",cartDTOs);

        //회원정보
        MyUserDto user = memberService.findUserid(username);
        model.addAttribute("user",user);


        //총 결제 해야할 금액 표시
        int totalPay = quantity * item.getPrice();
        model.addAttribute("total",totalPay);

        return "/Buy";
    }

    /**
     * 주문 저장
     * @param deliveryDTO 배송지 정보
     *   장바구니 x 바로구매가능
     */
    @PostMapping("/buy-item")
    public String delivery(@ModelAttribute DeliveryDTO deliveryDTO,
                         @AuthenticationPrincipal CustomUserDetails customUserDetails,
                         @RequestParam(name = "CartIdx")List<Long> idx,
                          @RequestParam(name="ItemIdx") Long itemIdx,
                           @RequestParam(name = "itemQuantity")int quantity) {

            String username = customUserDetails.getUsername();
            //배송지 저장
            paySerivce.save(deliveryDTO, idx, username,itemIdx,quantity);
            
        return "/home";
    }

}
