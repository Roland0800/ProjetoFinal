package Sports.JLMN.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Sports.JLMN.models.Produto;
import Sports.JLMN.repositories.produtoRepository;

@Controller
public class sportsController {
	
	@Autowired
	private produtoRepository pr;
	
	@GetMapping("/addProduto")
	public String addProduto(Produto produto) {
		return "/addProduto";
	}
	
	@PostMapping("/saveProduto")
	public String saveProduto(Produto produto) {
		System.out.println(produto.toString());
		pr.save(produto);
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public ModelAndView home() {
		List<Produto> produtos = pr.findAll();
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
