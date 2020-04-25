
package harmony.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    public String getUser(){
        return String.valueOf(
            SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
        );
    }
}
