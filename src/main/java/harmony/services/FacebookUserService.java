
package harmony.services;

import harmony.domain.FacebookTokenInformation;
import harmony.domain.FacebookTokenInformation.Content;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class FacebookUserService {
    private static final String APP_ACCESS_TOKEN_FILE = "d2.dat";
    private static final String TOKEN_CHECK_URL = "https://graph.facebook.com/debug_token";
    
    @Autowired
    private RestTemplate restTemplate;
    
    private String appAccessToken;
    
    public Optional<String> getLoggedInUser(String token){
        var url = UriComponentsBuilder.fromHttpUrl(TOKEN_CHECK_URL)
                .queryParam("input_token", token)
                .queryParam("access_token", appAccessToken)
                .toUriString();

        try{
            return Optional.ofNullable(
                restTemplate.getForObject(url, FacebookTokenInformation.class))
                    .map(FacebookTokenInformation::getData)
                    .filter(Content::isIs_valid)
                    .map(Content::getUser_id);
        }catch(RestClientException exception){
            return Optional.empty();
        }
    }
    
    @PostConstruct
    private void loadAccessToken(){
        try {
            appAccessToken = Files.readString(Paths.get(APP_ACCESS_TOKEN_FILE)).trim();
        } catch (IOException ex) {
            Logger.getLogger(FacebookUserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
