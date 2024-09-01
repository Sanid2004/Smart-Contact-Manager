package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// This is the last step
// Since, I am using the version 3.3.2 in which WebSecurityConfigurerAdapter is deprecated , so no need to implement it
// Firstly we create methods which return the objects of UserDetailService class and BCryptPasswordEncoder
// Now we set the UserDetailService as the object returned by other methods and also the PasswordEncoder
// Now this daoauthenticationprovider object is passed to the AuthenticationProvider
// Finally using SecurityFilterChain we determine which types of patterns of url's requires Login
// Here I use the spring's default Login page
// But in future I may customize it
@Configuration
@EnableWebSecurity
public class MyConfig  {

    @Bean
    public UserDetailsService getUserDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider AuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(PasswordEncoder());
        return daoAuthenticationProvider;
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(AuthenticationProvider());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .requestMatchers("/user/**")
                        .hasRole("USER")
                        .requestMatchers("/**")
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .loginProcessingUrl("/dologin")
                        .defaultSuccessUrl("/user/index")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF protection

        return http.build();
    }

}

// loginPage() = the custom login page
// loginProcessingUrl() = the url to submit the username and password to
// defaultSuccessUrl() = the landing page after successful login
// failureUrl() = the landing page after an unsuccessful login
