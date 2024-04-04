package portfolio.test1.config;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import portfolio.test1.Repositiry.MemberRepository;
import portfolio.test1.entity.MemberEntity;


import java.util.Optional;

@Component
public class MyUserDetailsSerivce implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MyUserDetailsSerivce(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> userid = memberRepository.findByUserid(username);

        MemberEntity member = userid.orElseThrow(() ->new UsernameNotFoundException("없는 회원입니다."));

        if(member != null){
            return new CustomUserDetails(member);
        }else{
            return null;
        }
    }
}
