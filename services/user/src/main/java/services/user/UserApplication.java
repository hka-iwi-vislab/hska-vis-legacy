package services.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableAutoConfiguration
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

}

@RestController
class HelloWorld {
 
    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    ResponseEntity<String> helloWorld() {
        return new ResponseEntity<String>("Hello World!", HttpStatus.OK);
    }
}
