package Sports.JLMN.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class sportsController {
	
	@RequestMapping("/")
	public String site() {
		return "home";
	}
}
