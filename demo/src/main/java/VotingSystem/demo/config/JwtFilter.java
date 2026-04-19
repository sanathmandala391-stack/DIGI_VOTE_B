package VotingSystem.demo.config;

import VotingSystem.demo.Repository.UserRepository;
import VotingSystem.demo.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String token = null;
        Long userId = null;
        String role = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            if (jwtUtil.validateToken(token)) {
                userId = jwtUtil.extractUserId(token);
                // Read role directly from token — no extra DB call needed
                role = jwtUtil.extractRole(token);
            }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findById(userId).orElse(null);

            if (user != null) {
                // Use role from token. Fall back to DB role if token role is null.
                String roleToUse = (role != null) ? role :
                        (user.getRole() != null ? user.getRole().name() : "VOTER");

                String authority = "ROLE_" + roleToUse;

                System.out.println("JWT >>> " + user.getEmail()
                        + " | tokenRole=" + role
                        + " | authority=" + authority
                        + " | path=" + request.getRequestURI());

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                List.of(new SimpleGrantedAuthority(authority))
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}