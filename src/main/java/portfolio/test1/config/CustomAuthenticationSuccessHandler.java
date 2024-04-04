/*
package portfolio.test1.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 사용자 아이디 가져오기
        String username = authentication.getName();

        // 세션에 사용자 아이디 저장
        HttpSession session = request.getSession();
        session.setAttribute("userid", username);

        // "/" 페이지로 이동
        response.sendRedirect("/");
    }
}
/////////////////////// 사용하지 않음*/
