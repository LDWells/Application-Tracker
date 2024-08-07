package job_tracker.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends BasicAuthenticationFilter
{
    private final JwtConverter converter;

    public JwtRequestFilter(AuthenticationManager authenticationManager, JwtConverter converter) {
        super(authenticationManager); // 1. Must satisfy the super class.
        this.converter = converter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException
    {

        // 2. Read the Authorization value from the request.
        String authorization = request.getHeader("Authorization");
        System.out.println("HERE .5");
        if (authorization != null && authorization.startsWith("Bearer ")) {

            // 3. The value looks okay, confirm it with JwtConverter.
            User user = converter.getUserFromToken(authorization);
            if (user == null) {
                response.setStatus(403); // Forbidden
            }
            else {
                System.out.println("HERE");
                System.out.println(user);
                System.out.println(user.getUsername());
                System.out.println(user.getAuthorities());
                // 4. Confirmed. Set auth for this single request.
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        user.getUsername(), null, user.getAuthorities());
                System.out.println(token);
                SecurityContextHolder.getContext().setAuthentication(token);
            }
        }
        System.out.println("HERE 2");
        // 5. Keep the chain going.
        try
        {
            chain.doFilter(request, response);
        }
        catch (Exception ex)
        {
            System.out.println("ERROR IN CHAIN FILTER");
        }

    }

}
