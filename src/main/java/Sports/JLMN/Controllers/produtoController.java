package Sports.JLMN.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import Sports.JLMN.models.Produto;
import Sports.JLMN.repositories.produtoRepository;

@Controller
public class produtoController {
	
	@Autowired
	private produtoRepository pr;
	
	@GetMapping("/Produtos")
	public String listProdutos() {
		return "/produto/produtos";
	}
	
	@GetMapping("/addProduto")
	public String addProduto() {
		return "/produto/saveProduto";
	}
	
	@PostMapping("/saveProduto")
	public String saveProduto(Produto produto) {
		System.out.println(produto);
		pr.save(produto);
		return "redirect:/Produtos";
	}
}
