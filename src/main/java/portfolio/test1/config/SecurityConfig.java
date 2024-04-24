package portfolio.test1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import portfolio.test1.Service.CustomOAuth2UserService;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }
    /*private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }*/

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {

        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return hierarchy;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       /* http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);

        http
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService))));

        http
                .authorizeHttpRequests(request->request
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .requestMatchers("/mypage/**","/logout/**","cart/**").hasRole("USER")
                        .anyRequest().permitAll());
        http
                .formLogin(login -> login	// form 방식 로그인 사용
                        .loginPage("/login") //만들어둔 로그인 페이지로
                        .loginProcessingUrl("/login_proc") // 로그인 버튼 클릭시 이동
                        .usernameParameter("userid") // 아이디 받아오기
                        .passwordParameter("pwd") // 비밀번호 받아오기
                        //.successHandler(customAuthenticationSuccessHandler) // 이거 추가함 현재 사용x
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )

                .logout(withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)


*/
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)

                .oauth2Login(oauth -> oauth
                        .loginPage("/login")
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .requestMatchers("/mypage/**", "/logout/**", "/cart/**").hasRole("USER")
                        .anyRequest().permitAll())

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/login_proc")
                        .usernameParameter("userid")
                        .passwordParameter("pwd")
                        .defaultSuccessUrl("/", true)
                        .permitAll())


                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .permitAll());


        return http.build();
    }

}
