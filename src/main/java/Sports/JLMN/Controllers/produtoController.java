package Sports.JLMN.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import Sports.JLMN.repositories.produtoRepository;

@Controller
public class produtoController {
	
	@Autowired
	private produtoRepository pr;
	
	@GetMapping("/Produtos")
	public String listProdutos() {
		return "/produto/produtos";
	}
	
	@GetMapping("/saveProduto")
	public String saveProduto() {
		return "/produto/saveProduto";
	}
}
