package portfolio.test1.Service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import portfolio.test1.DTO.OAuth2.CustomOAuth2User;
import portfolio.test1.DTO.OAuth2.GoogleResponse;
import portfolio.test1.DTO.OAuth2.NaverResponse;
import portfolio.test1.DTO.OAuth2.OAuth2Response;
import portfolio.test1.Repositiry.OAuthUserRepository;
import portfolio.test1.entity.OAuthUserEntity;

@Service
@Log4j2
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final OAuthUserRepository oAuthUserRepository;

    public CustomOAuth2UserService(OAuthUserRepository oAuthUserRepository) {
        this.oAuthUserRepository = oAuthUserRepository;
    }


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info(oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        if(registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());

        }else if(registrationId.equals("google")) {

            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());

        }else {

            return null;
        }

        String username = oAuth2Response.getProvider()+" "+oAuth2Response.getProviderId();
        OAuthUserEntity existData = oAuthUserRepository.findByUsername(username);
        String role = null;

        if(existData == null) {
            OAuthUserEntity oAuthUserEntity = new OAuthUserEntity();
            oAuthUserEntity.setUsername(username);
            oAuthUserEntity.setEmail(oAuth2Response.getEmail());
            oAuthUserEntity.setRole("ROLE_USER");

            oAuthUserRepository.save(oAuthUserEntity);

        }else {
            role = existData.getRole();

            existData.setEmail(oAuth2Response.getEmail());

            oAuthUserRepository.save(existData);
        }

        return new CustomOAuth2User(oAuth2Response,role);
    }
}
