package ufrn.imd.jv.springboard;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@FeignClient("SPRINGUSER")
public interface UserServiceInterface {
    @RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    ResponseEntity<Map<String, String>> getUser(@PathVariable Long id);
}
