package com.el.rest.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	private String usersQuery = "select email, password, enabled from elect_line.user where email=?";

	private String rolesQuery = "select email, role from elect_line.user where email=?";

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
		jdbcAuthentication()
		.usersByUsernameQuery(usersQuery)
		.authoritiesByUsernameQuery(rolesQuery)
		.dataSource(dataSource)
		.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		/*authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/manage/**").permitAll()/*hasAuthority("ADMIN")*
		.anyRequest()
		.authenticated().and().formLogin()
		.loginPage("/login").failureUrl("/login?error=true")
		.defaultSuccessUrl("/home")
		.usernameParameter("email")
		.passwordParameter("password")
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/").and().exceptionHandling()
		.accessDeniedPage("/access-denied");.and().csrf().disable();*/
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web
		.ignoring()
		.antMatchers("/asserts/**","/resources/**", "/static/**", "/asserts/css/**", "/asserts/js/**/**", "/asserts/images/**");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
}