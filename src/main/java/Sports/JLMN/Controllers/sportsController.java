package Sports.JLMN.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import Sports.JLMN.models.Artigo;
import Sports.JLMN.models.Produto;
import Sports.JLMN.repositories.artigoRepository;
import Sports.JLMN.repositories.produtoRepository;

@Controller
public class sportsController {

	@Autowired
	private produtoRepository pr;
	@Autowired
	private artigoRepository ar;

	@GetMapping("/addArtigo")
	public String addArtigo(Artigo artigo) {
		return "/addArtigo";
	}

	@PostMapping("/saveArtigo")
	public String saveArtigo(Artigo artigo) {
		System.out.println(artigo.toString());
		ar.save(artigo);
		return "redirect:/home";
	}

	@GetMapping("/home")
	public ModelAndView listaArtigo(Artigo artigo) {
		List<Artigo> artigos = ar.findAll();
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("artigos", artigos);
		return mv;
	}

	@GetMapping("/Artigo/{id}")
	public ModelAndView artigo(@PathVariable Long id, Produto produto) {
		ModelAndView mv = new ModelAndView();
		Optional<Artigo> opt = ar.findById(id);
		if (opt.isEmpty()) {
			mv.setViewName("home");
			return mv;
		}
		mv.setViewName("Artigo");
		Artigo artigo = opt.get();
		mv.addObject("artigo", artigo);
		List<Produto> produtos = pr.findByArtigo(artigo);
		mv.addObject("produtos", produtos);
		return mv;
	}

	@GetMapping("/addProduto")
	public String addProduto(Produto produto) {
		return "/addProduto";
	}

	@PostMapping("/Artigo/{idArtigo}")
	public ModelAndView saveProduto(@PathVariable Long idArtigo, Produto produto) {

		Optional<Artigo> opt = ar.findById(idArtigo);
		ModelAndView mv = new ModelAndView();

		if (opt.isEmpty()) {
			mv.setViewName("home");
			return mv;
		}

		Artigo artigo = opt.get();
		produto.setArtigo(artigo);
		pr.save(produto);
		System.out.println(produto.toString());
		mv.setViewName("redirect:/Artigo/{idArtigo}");
		return mv;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
