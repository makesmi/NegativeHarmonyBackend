
package harmony.services;

import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.stereotype.Component;


@Component
public class TokenFilter extends AbstractPreAuthenticatedProcessingFilter{
    public static final String TOKEN_HEADER = "negativeharmony_token";
    
    @Autowired
    private FacebookTokenService tokens;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(TOKEN_HEADER))
                .flatMap(tokens::getUser)
                .orElse(null);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "N/A";
    }
    
    @PostConstruct
    private void createAuthenticationManager(){
        setAuthenticationManager(authentication -> {
            authentication.setAuthenticated(authentication.getPrincipal() != null);
            return authentication;
        });
    }
}
