package Sports.JLMN.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class sportsController {
	
	@RequestMapping("/home")
	public String site() {
		return "home";
	}
	
	@GetMapping("/")
	public String login() {
		return "login";
	}
}
