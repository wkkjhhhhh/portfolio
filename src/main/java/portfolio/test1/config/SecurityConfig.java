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

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
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
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable);
        http
                .authorizeHttpRequests(request->request
                        //.requestMatchers("/home","/","/join","/join_proc", "/login/**",
                               // "/standard","/category/**","/idcheck","/Item/**",
                               // "/css/**","/js/**","/img/**","/outer").permitAll()
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .requestMatchers("/mypage/**","/logout/**","cart/**").hasRole("USER")
                        .anyRequest().permitAll());
        http
                .formLogin(login -> login	// form 방식 로그인 사용
                        .loginPage("/login") //만들어둔 로그인 페이지로
                        .loginProcessingUrl("/login_proc") // 로그인 버튼 클릭시 이동
                        .usernameParameter("userid") // 아이디 받아오기
                        .passwordParameter("pwd") // 비밀번호 받아오기
                        //.successHandler(customAuthenticationSuccessHandler) // 이거 추가함
                        .defaultSuccessUrl("/",true)
                        .permitAll()
                )
                .logout(withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)

        return http.build();
    }

}
