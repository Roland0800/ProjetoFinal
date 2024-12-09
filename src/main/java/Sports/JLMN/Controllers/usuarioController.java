package Sports.JLMN.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import Sports.JLMN.models.Usuario;
import Sports.JLMN.repositories.usuarioRepository;

@Controller
public class usuarioController {

	@Autowired
	private usuarioRepository ur;

	@GetMapping("/")
	public String criarUsuario(Usuario usuario) {
		return ("usuario/criarUsuario");
	}

	@PostMapping("/saveUsuario")
	public String saveUsuario(Usuario usuario) {
		System.out.println(usuario.toString());
		ur.save(usuario);
		return "redirect:/home";
	}
	
	@GetMapping("/Usuario/edit/{id}")
	public ModelAndView editUsuario(@PathVariable Long id, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();		
		Optional<Usuario> opt = ur.findById(id);
		if(opt.isEmpty()) {
			mv.setViewName("redirect:/Usuarios");
			return mv;
		}
		attribute.addFlashAttribute("mensagemUsuario", "Usuário editado com sucesso!");
		Usuario usuario = opt.get();
		mv.addObject(usuario);
		mv.setViewName("redirect:/");
		return mv;
	}
	
	@GetMapping("/Usuario/delete/{id}")
	public String deletarUsuario(@PathVariable Long id, RedirectAttributes attribute) {
		Optional<Usuario> opt = ur.findById(id);
		if(opt.isEmpty()) {
			return "redirect:/Usuarios";
		}
		ur.delete(opt.get());
		attribute.addFlashAttribute("mensagem", "Usuário deletado com sucesso!");
		return "redirect:/Usuarios";
	}

	@GetMapping("/altSenha")
	public String altSenha(Usuario usuario) {
		return "usuario/altSenha";
	}

	@PostMapping("/newSenha")
	public ModelAndView newSenha(String email, String senha, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();
		Optional<Usuario> opt = ur.getByEmail(email);
		if (opt.isEmpty()) {
			attribute.addFlashAttribute("mensagem", "Insira senhas Iguais!");
			mv.setViewName("redirect:usuario/altSenha");
			return mv;
		}
		Usuario usuario = opt.get();
		usuario.setSenha(senha);
		ur.save(usuario);
		mv.setViewName("redirect:/home");
		return mv;
	}

	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}

	@PostMapping("/loginUsuario")
	public ModelAndView loginUsuario(String nome, String senha, String email, String tipo) {
		Usuario login = ur.login(nome, senha, email, tipo);

		ModelAndView mv = new ModelAndView();
		if (login == null) {
			mv.setViewName("redirect:/login");
			return mv;
		}
		System.out.println(login);
		mv.setViewName("redirect:/home");
		return mv;
	}

	@GetMapping("/Usuarios")
	public ModelAndView listaUsuarios(Usuario usuario) {
		List<Usuario> usuarios = ur.findAll();
		ModelAndView mv = new ModelAndView();

		mv.addObject("usuarios", usuarios);
		mv.setViewName("usuario/usuarios");
		return mv;
	}

	@GetMapping("/Usuario/{id}")
	public ModelAndView detalhes(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView();
		Optional<Usuario> opt = ur.findById(id);
		if (opt.isEmpty()) {
			mv.setViewName("redirect:/home");
			return mv;
		}

		mv.setViewName("usuario/detalhes");
		Usuario usuario = opt.get();
		mv.addObject("usuario", usuario);
		return mv;
	}
}
