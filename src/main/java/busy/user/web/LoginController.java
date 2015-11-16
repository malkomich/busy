package busy.user.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {
		return "login";
	}
	
	@RequestMapping(value="/", method=RequestMethod.POST)
	public String login(Model model) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
