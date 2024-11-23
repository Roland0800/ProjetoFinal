package Sports.JLMN.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Sports.JLMN.models.Produto;
import Sports.JLMN.repositories.produtoRepository;

@Controller
@RequestMapping("/JLMN")
public class sportsController {
	
	@Autowired
	private produtoRepository pr;
	
	@GetMapping("/home")
	public String home() {
		return "home";
	}
	
	@GetMapping("/addProduto")
	public String addProduto(Produto produto) {
		return "/addProduto";
	}
	
	@PostMapping("/saveProduto")
	public String saveProduto(Produto produto) {
		System.out.println(produto.toString());
		pr.save(produto);
		return "redirect:/JLMN/home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
