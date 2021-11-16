package shop.sss.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override // 모든 http 요청에 대해 인증 절차를 Pass 시키도록 상속
    protected void configure(HttpSecurity http) throws Exception {
    }

    @Bean // 비밀번호 암호화 객체
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
