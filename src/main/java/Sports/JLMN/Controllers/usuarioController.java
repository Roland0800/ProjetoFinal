package Sports.JLMN.Controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Sports.JLMN.models.Usuario;
import Sports.JLMN.repositories.usuarioRepository;

@Controller
public class usuarioController {

	@Autowired
	private usuarioRepository ur;

	@GetMapping("/criarUsuario")
	public String criarUsuario(Usuario usuario) {
		return "usuario/criarUsuario";
	}

	@PostMapping("/saveUsuario")
	public String saveUsuario(Usuario usuario) {
		System.out.println(usuario.toString());
		ur.save(usuario);
		return "redirect:/home";
	}

	@GetMapping("/usuario/altSenha")
	public String altSenha() {
		return "usuario/altSenha";
	}

	@PostMapping("/usuario/newSenha")
	public String newSenha(Long id, String senha, String altSenha, BindingResult result, RedirectAttributes attribute) {
		Optional<Usuario> opt = ur.findById(id);
		if (result.hasErrors()) {
			return "usuario/altSenha";
		}
		if (opt.isEmpty()) {
			return "usuario/altSenha";
		}
		if (senha != altSenha) {
			attribute.addFlashAttribute("mensagem", "Insira senhas Iguais!");
			return "usuario/altSenha";
		}
		ur.newSenha(senha,id);
		return "home";
	}

	@GetMapping("/login")
	public String login() {
		return "usuario/login";
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
