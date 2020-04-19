
package harmony.controllers;

import harmony.domain.Song;
import harmony.repository.SongRepository;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HarmonyController {
    @Autowired
    private SongRepository repository;
    
    @RequestMapping(value = "/songs/{user}", method = GET)
    @ResponseBody
    public List<Song> get(@PathVariable String user, HttpServletRequest request){
        return repository.findByUserId(user);
    }
    
    @RequestMapping(value = "/songs", method = POST)
    @ResponseBody
    public ResponseEntity<String> set(@RequestBody Song harmonia){
        repository.save(harmonia);
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
