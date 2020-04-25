
package harmony.controllers;

import harmony.services.FacebookTokenService;
import harmony.services.TokenFilter;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {
    
    @Autowired
    private FacebookTokenService tokens;
    
    @RequestMapping(value = "/login", method = POST)
    @ResponseBody
    public ResponseEntity<Object> login(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader(TokenFilter.TOKEN_HEADER))
                .filter(tokens::setAndValidate)
                .map(token -> new ResponseEntity<>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
    
    @RequestMapping(value = "/logout", method = POST)
    @ResponseBody
    public ResponseEntity<Object> logout(HttpServletRequest request){
        return Optional.ofNullable(request.getHeader(TokenFilter.TOKEN_HEADER))
                .filter(tokens::removeToken)
                .map(token -> new ResponseEntity<>(HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
    
    
}
