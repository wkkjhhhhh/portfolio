package portfolio.test1.config;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import portfolio.test1.DTO.OAuth2.CustomOAuth2User;
import portfolio.test1.DTO.OAuth2.OAuth2Response;
import portfolio.test1.Repositiry.MemberRepository;
import portfolio.test1.Repositiry.OAuthUserRepository;
import portfolio.test1.entity.MemberEntity;
import portfolio.test1.entity.OAuthUserEntity;


import java.util.Optional;

@Component
public class MyUserDetailsSerivce implements UserDetailsService {

    private final MemberRepository memberRepository;


    public MyUserDetailsSerivce(MemberRepository memberRepository, OAuthUserRepository oAuthUserRepository, OAuthUserRepository oAuthUserRepository1) {
        this.memberRepository = memberRepository;


    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberEntity> userid = memberRepository.findByUserid(username);

        OAuth2Response oAuth2Response = null;
        String role = null;
        MemberEntity member = userid.orElseThrow(() ->new UsernameNotFoundException("없는 회원입니다."));

        if(member != null){
            return new CustomUserDetails(member);
        }else {
            return null;
        }
    }


}
