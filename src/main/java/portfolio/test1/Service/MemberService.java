package portfolio.test1.Service;


import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import portfolio.test1.DTO.MemberDTO;
import portfolio.test1.DTO.MyUserDto;
import portfolio.test1.Repositiry.CartRepository;
import portfolio.test1.Repositiry.MemberRepository;
import portfolio.test1.Repositiry.OAuthUserRepository;
import portfolio.test1.Repositiry.OrderRepository;
import portfolio.test1.entity.CartEntity;
import portfolio.test1.entity.MemberEntity;
import portfolio.test1.entity.OAuthUserEntity;
import portfolio.test1.entity.Pay.OrderEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final OAuthUserRepository oAuthUserRepository;


    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, CartRepository cartRepository, OrderRepository orderRepository, OAuthUserRepository oAuthUserRepository) {


        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.oAuthUserRepository = oAuthUserRepository;
    }

    ///////회원가입
    public void save(MemberDTO dto) {
        Optional<MemberEntity> byId = memberRepository.findByUserid(dto.getUserid());

        if(byId.isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(dto);
            //패스워드 인코더
            memberEntity.setPwd(passwordEncoder.encode(memberEntity.getPwd()));
            memberRepository.save(memberEntity);
        }
    }
    //아이디 체크
    public boolean finduserid(String userid) {
       return memberRepository.existsByUserid(userid);
    }

    // view (my page)
    public MyUserDto findUserid(String username) {
        OAuthUserEntity oAuthUSer = oAuthUserRepository.findByUsername(username);
        Optional<MemberEntity> member = memberRepository.findByUserid(username);

        if(member.isPresent()) {
            MemberEntity entity = member.get();

            return  MyUserDto.builder()
                    .userid(entity.getUserid())
                    .name(entity.getName())
                    .phone(entity.getPhone())
                    .email(entity.getEmail())
                    .deposit(entity.getDeposit())
                    .build();
       }else {

        return MyUserDto.builder()
                .userid(oAuthUSer.getUsername().substring(0,5))
                .email(oAuthUSer.getEmail())
                .build();
       }
    }
    //list
    public List<MemberDTO> findAll() {
        List<MemberEntity> list = memberRepository.findAll(Sort.by(Sort.Order.desc("idx")));
        List<MemberDTO> memberDTOS = new ArrayList<>();

        for(MemberEntity memberEntity : list) {
            memberDTOS.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return  memberDTOS;
    }

    public void delete(Long idx) {
        String userid = memberRepository.findById(idx).orElse(null).getUserid();

        List<CartEntity> byUsername = cartRepository.findByUsername(userid);
        List<OrderEntity> orderEntityList = orderRepository.findByUsername(userid);


        for (CartEntity cartEntity : byUsername) {
            cartRepository.delete(cartEntity);
        }
        for (OrderEntity orderEntity : orderEntityList) {
            orderRepository.delete(orderEntity);
        }

        memberRepository.deleteById(idx);
    }
}
