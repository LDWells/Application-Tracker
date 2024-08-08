package job_tracker.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter)
    {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //Not included in lessons, but included in example
        http.cors();
//        This will require attention
        http.authorizeRequests()
                // TODO add antMatchers here to configure access to specific API endpoints
                .antMatchers("/api/user/authenticate", "/api/user/register").permitAll()
                .antMatchers(HttpMethod.GET, "api/jobs/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/applications").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/application/details", "/api/details/application/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/community").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/community").hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/community").permitAll()
                .antMatchers(HttpMethod.POST, "/api/comments").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/comments/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("*");
            }
        };
    }
}
