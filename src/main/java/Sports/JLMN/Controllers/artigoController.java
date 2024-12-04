package Sports.JLMN.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Sports.JLMN.models.Artigo;
import Sports.JLMN.repositories.artigoRepository;
import jakarta.validation.Valid;

@Controller
public class artigoController {

	@Autowired
	private artigoRepository ar;
	
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
		attribute.addFlashAttribute("mensagem", "Artigo adicionado com sucesso!");
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
	
}
