package gabriel.yuppiewall.ws;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/ping")
public class Ping {

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity ping() {

		return new ResponseEntity<Object>(HttpStatus.OK);
	}
}
