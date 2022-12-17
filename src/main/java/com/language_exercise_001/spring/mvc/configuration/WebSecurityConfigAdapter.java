package com.language_exercise_001.spring.mvc.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


    @Configuration
    @EnableWebSecurity
    public class WebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {


//        @Bean
//        public PasswordEncoder passwordEncoder() {
//            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        }


        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
            authenticationMgr.inMemoryAuthentication()
                    .withUser("admin")
                    .password("{noop}password")
                    .authorities("ROLE_ADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/homePage").access("hasRole('ROLE_ADMIN')")
                    .and()
                    .formLogin().loginPage("/loginPage")
                    .defaultSuccessUrl("/homePage")
                    .failureUrl("/loginPage?error")
                    .usernameParameter("username").passwordParameter("password")
                    .and()
                    .logout().logoutSuccessUrl("/loginPage?logout");

        }

}
