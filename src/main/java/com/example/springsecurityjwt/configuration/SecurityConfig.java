package com.example.springsecurityjwt.configuration;

import com.example.springsecurityjwt.handler.CustomAccessDeniedHandler;
import com.example.springsecurityjwt.handler.CustomAuthenticationEntryPoint;
import com.example.springsecurityjwt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    /* 보안 필터 체인을 구현한다. 디폴트로 UsernameAndPasswordAuthenticationFilter 로 설정되어 있다.
     * 스프링 시큐리티를 설정하는 것인데, 대부분 HttpSecurity 를 통해 진행한다.
     * 매개 변수로 전달받은 HttpSecurity 의 다양한 상태를 설정해준다. */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable() // 기본 값인 UI 사용을 하지 않도록 설정합니다.
                .csrf().disable() // REST API 에서는 브라우저 사용 환경이 아니므로 csrf 보안이 필요치 않다. 비활성합니다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않고 JWT 인증 방식이므로 stateless 입니다.
                .and()
                .authorizeRequests() // 사용 권한을 확인한다는 의미입니다.
                .antMatchers("api/v1/jwt/login").permitAll() // 어떤 URL 의 요청을 허용할 것인지를 명시합니다.
                .antMatchers(HttpMethod.POST).authenticated() // POST 요청들은 모두 인증을 거쳐야 함을 명시합니다.
                .anyRequest().hasRole("ADMIN") // 명시되지 않은 기타 요청은 역할을 통해 권한을 확인합니다.
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) // 접근이 거부될 때는 접근 거부 예외로 처리합니다.
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 권한 문제가 아닌 다른 인증 중 에러 발생 시 예외로 처리합니다.
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
