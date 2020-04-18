
package harmony.services;

import static java.util.Arrays.stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunEnvironment {
    private static final String DEV_PROFILE = "dev";
    
    @Autowired
    private org.springframework.core.env.Environment environment;   
    
    public boolean development(){
        return !production();
    } 
    
    public boolean production(){
        return stream(environment.getActiveProfiles())
                .noneMatch(s -> s.equals(DEV_PROFILE));
    }
}
