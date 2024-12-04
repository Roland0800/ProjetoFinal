package Sports.JLMN.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Sports.JLMN.models.Artigo;
import Sports.JLMN.models.Produto;
import Sports.JLMN.models.Usuario;
import Sports.JLMN.repositories.artigoRepository;
import Sports.JLMN.repositories.produtoRepository;
import Sports.JLMN.repositories.usuarioRepository;
import jakarta.validation.Valid;

@Controller
public class usuarioController {
	
	@Autowired
	private usuarioRepository ur;

	@GetMapping("/criarUsuario")
	public String criarUsuario(Usuario usuario) {
		return "criarUsuario";
	}

	@PostMapping("/saveUsuario")
	public String saveUsuario(Usuario usuario) {
		System.out.println(usuario.toString());
		ur.save(usuario);
		return "redirect:/home";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/loginUsuario")
	public ModelAndView loginUsuario(String nome, String senha, String tipo) {
		Usuario login = ur.login(nome, senha, tipo);

		ModelAndView mv = new ModelAndView();
		if (login == null) {
			mv.setViewName("redirect:/login");
			return mv;
		}
		System.out.println(login);
		mv.setViewName("redirect:/home");
		return mv;
	}
}
