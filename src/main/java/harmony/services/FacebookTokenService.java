
package harmony.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FacebookTokenService {
    private final Map<String, String> tokens = new HashMap<>();
    private final Map<String, LocalDateTime> lastValidation = new HashMap<>();
    
    private static final int EXPIRATION_HOURS = 2;
    private static final int OLD_HOURS = 24 * 3;
    private static final String DEVELOPMENT_USER = "defaultuser";

    @Autowired
    private FacebookUserService userService;
    @Autowired
    private RunEnvironment environment;
    
    public Optional<String> getUser(String token){
        return Optional.of(token)
                .filter(tokens::containsKey)
                .filter(t -> !hasExpired(t) || setAndValidate(token))
                .map(tokens::get);
    }
    
    public boolean setAndValidate(String token){
        removeOldTokens();
        var userResponse = environment.production() ?
                userService.getLoggedInUser(token) : Optional.of(DEVELOPMENT_USER);
        
        userResponse.ifPresent(user -> {
            tokens.put(token, user);
            lastValidation.put(token, LocalDateTime.now());
        });
        
        return userResponse.isPresent();
    }
    
    public boolean removeToken(String token){
        if(tokens.containsKey(token)){
            tokens.remove(token);
            lastValidation.remove(token);
            return true;
        }else{
            return false;
        }
    }
    
    private void removeOldTokens(){
        var oldTokens = tokens.keySet().stream()
                .filter(this::isOld)
                .collect(toList());
        oldTokens.forEach(this::removeToken);
    }
    
    private boolean hasExpired(String token){
        return ageInHours(token) >= EXPIRATION_HOURS;
    }
    
    private boolean isOld(String token){
        return ageInHours(token) >= OLD_HOURS;
    }
    
    private long ageInHours(String token){
        if(!lastValidation.containsKey(token)){ return 0; }
        return ChronoUnit.HOURS.between(lastValidation.get(token), LocalDateTime.now());
    }
}
