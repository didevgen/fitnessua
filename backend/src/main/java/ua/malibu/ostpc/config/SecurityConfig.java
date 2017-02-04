package ua.malibu.ostpc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import ua.malibu.ostpc.filters.*;
import ua.malibu.ostpc.utils.auth.BaseAuthenticationProvider;
import ua.malibu.ostpc.utils.auth.SuccessHandler;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthFilter authFilter;
    @Autowired
    private BaseAuthenticationProvider authProv;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(new RegexRequestMatcher("/blah", "POST"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProv);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/blah");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .successHandler(new SuccessHandler()).permitAll()
        .and().authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable()
                .addFilterBefore(authFilter, BasicAuthenticationFilter.class);
    }
}
