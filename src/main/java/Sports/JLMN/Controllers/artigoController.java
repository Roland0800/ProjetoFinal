package Sports.JLMN.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Sports.JLMN.models.Artigo;
import Sports.JLMN.models.Produto;
import Sports.JLMN.repositories.artigoRepository;
import Sports.JLMN.repositories.produtoRepository;
import jakarta.validation.Valid;

@Controller
public class artigoController {

	@Autowired
	private artigoRepository ar;

	@Autowired
	private produtoRepository pr;

	@GetMapping("/addArtigo")
	public String addArtigo(Artigo artigo) {
		return "/addArtigo";
	}

	@PostMapping("/saveArtigo")
	public String saveArtigo(@Valid Artigo artigo, BindingResult result, RedirectAttributes attribute) {
		if(result.hasErrors()) {
			return addArtigo(artigo);
		}
		System.out.println(artigo.toString());
		ar.save(artigo);
		attribute.addFlashAttribute("mensagem", "Artigo adicionado/editado com sucesso!");
		return "redirect:/home";
	}
	
	@GetMapping("/Artigo/edit/{id}")
	public ModelAndView editArtigo(@PathVariable Long id, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();		
		Optional<Artigo> opt = ar.findById(id);
		if(opt.isEmpty()) {
			mv.setViewName("redirect:/home");
			return mv;
		}
		Artigo artigo = opt.get();
		mv.addObject(artigo);
		mv.setViewName("redirect:/addArtigo");
		return mv;
	}
	
	@GetMapping("/Artigo/delete/{id}")
	public String deletarArtigo(@PathVariable Long id, RedirectAttributes attribute) {
		Optional<Artigo> opt = ar.findById(id);
		if(opt.isEmpty()) {
			return "redirect:/home";
		}
		ar.delete(opt.get());
		attribute.addFlashAttribute("mensagem", "Artigo deletado com sucesso!");
		return "redirect:/home";
	}

	@GetMapping("/home")
	public ModelAndView listaArtigo(Artigo artigo) {
		List<Artigo> artigos = ar.findAll();
		ModelAndView mv = new ModelAndView();

		mv.addObject("artigos", artigos);
		mv.setViewName("/home");
		return mv;
	}

	@GetMapping("/Artigo/{id}")
	public ModelAndView artigo(@PathVariable Long id, Produto produto) {
		ModelAndView mv = new ModelAndView();
		Optional<Artigo> opt = ar.findById(id);
		if (opt.isEmpty()) {
			mv.setViewName("redirect:/home");
			return mv;
		}

		mv.setViewName("Artigo");
		Artigo artigo = opt.get();
		mv.addObject("artigo", artigo);

		List<Produto> produtos = pr.findByArtigo(artigo);
		mv.addObject("produtos", produtos);
		return mv;
	}
}
