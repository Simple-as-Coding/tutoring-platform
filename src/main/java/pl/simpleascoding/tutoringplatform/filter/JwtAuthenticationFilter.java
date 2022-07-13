package pl.simpleascoding.tutoringplatform.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.simpleascoding.tutoringplatform.common.SecurityFinals;
import pl.simpleascoding.tutoringplatform.exception.InvalidTokenException;
import pl.simpleascoding.tutoringplatform.service.jwt.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static pl.simpleascoding.tutoringplatform.common.SecurityFinals.*;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader(AUTHORIZATION);

        if (auth == null || !auth.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            DecodedJWT jwt = jwtService.verifyAndReturnDecodedToken(auth.substring(TOKEN_PREFIX.length()));

            if (!SecurityFinals.ACCESS.equals(jwt.getClaim(TYPE).asString()))
                throw new InvalidTokenException();

            UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getSubject());

            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()));
            filterChain.doFilter(request, response);
        } catch (RuntimeException ex) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(ex.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        List<String> excludedUrls = new ArrayList<>();
        excludedUrls.add("/api/v1/login");
        excludedUrls.add("/api/v1/refresh");
        excludedUrls.add("/api/v1/users");

        return excludedUrls.contains(request.getServletPath());
    }
}
