package shop.sss.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import shop.sss.member.service.MemberService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override // 모든 http 요청에 대해 인증 절차를 Pass 시키도록 상속
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                        .mvcMatchers("/","/member/**","/item/**","/images/**").permitAll()
                        .mvcMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated();

        // 권한에 맞지 않은 사용자가 접근할 때 수행되는 핸들러
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

        // hhtp.formLogin() -> http를 통해 들어오는 form 기반 request를 이용하여 로그인 처리
        http.formLogin()
                .loginPage("/member/login") // 로그인 페이지 커스터마이징
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/member/login/fail")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");
    }

    @Override
    public void configure(WebSecurity web) throws Exception{
        //아래 파일들은 인증 무시
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(passwordEncoder());
    }

    @Bean // 비밀번호 암호화 객체
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
