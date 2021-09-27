package com.study.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/css/**", "/js/**", "/img/**","/upload/**").permitAll()
//				.antMatchers("/navereditor/**").hasAnyRole("NORMAL", "ADMIN")
				.antMatchers("/guest/**","/android/**").permitAll()
				.antMatchers("/member/**").hasAnyRole("NORMAL", "ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/security/**").permitAll()
				.anyRequest().authenticated();		
		
		http.formLogin()
				.loginPage("/loginForm") // default : /login
				.loginProcessingUrl("/j_spring_security_check")
				.failureUrl("/loginForm?error")			// default : /login?error
				.failureHandler(authenticationFailureHandler)
				.usernameParameter("j_username") // default : j_username
				.passwordParameter("j_password") // default : j_password
				.defaultSuccessUrl("/")
//			.usernameParameter(id)
				.permitAll();
		
		http.logout().logoutUrl("/logout") // default
				.logoutSuccessUrl("/").permitAll();
		
		http.csrf().disable();

	}

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			
			auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery("select c_id as userName, c_pw as password, enabled from customer_list where c_id = ?")
				.authoritiesByUsernameQuery("select c_id as userName, authority from customer_list where c_id = ?")
				.passwordEncoder(new StandardPasswordEncoder());
			
		// 쿼리문에 사용되는 userName, password, enabled, authority는 Spring에서 지정된 컬럼명입니다.
		// 만일 테이블 컬럼과 다를 경우 별칭(Alias)을 줘서 변경하도록 합니다.
		// enabled 의 값이 0이면 비활성, 1이면 활성
	}

	// passwordEncoder() 추가
	@Bean
	public StandardPasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}
}
