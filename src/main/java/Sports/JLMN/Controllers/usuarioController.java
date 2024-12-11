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
import Sports.JLMN.models.Usuario;
import Sports.JLMN.repositories.usuarioRepository;
import jakarta.validation.Valid;

@Controller
public class usuarioController {

	@Autowired
	private usuarioRepository ur;

	@GetMapping("/")
	public String criarUsuario(Usuario usuario) {
		return ("usuario/criarUsuario");
	}

	@PostMapping("/saveUsuario")
	public String saveUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes attribute) {
		
		if(result.hasErrors()) {
			return criarUsuario(usuario);
		}
		
		Optional<Usuario> opt = ur.getByEmail(usuario.getEmail());
		
		if(opt.isEmpty() || usuario.getId() != null) {
			System.out.println(usuario.toString());
			ur.save(usuario);
			attribute.addFlashAttribute("mensagem", "Usuário adicionado/editado com sucesso!");
			return "redirect:/home";
		}
		attribute.addFlashAttribute("mensagem", "Email já presente no sistema!");
		return "redirect:/";
	}
	
	@GetMapping("/Usuario/edit/{id}")
	public ModelAndView editUsuario(@PathVariable Long id, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();		
		Optional<Usuario> opt = ur.findById(id);
		if(opt.isEmpty()) {
			mv.setViewName("redirect:/Usuarios");
			return mv;
		}
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
	public ModelAndView newSenha(@Valid Usuario usuario, BindingResult result, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();
		Optional<Usuario> opt = ur.getByEmail(usuario.getEmail());
		if (result.hasErrors()) {
			mv.setViewName("/usuario/altSenha");
			mv.addObject(usuario);
			return mv;
		}
		if (opt.isEmpty()) {
			mv.setViewName("redirect:usuario/altSenha");
			attribute.addFlashAttribute("mensagem", "Email inválido!");
			return mv;
		}
		Usuario u = opt.get();
		u.setSenha(usuario.getSenha());
		ur.save(usuario);
		attribute.addFlashAttribute("mensagem", "Senha alterada com sucesso!");
		mv.setViewName("redirect:/login");
		return mv;
	}

	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}

	@PostMapping("/loginUsuario")
	public ModelAndView loginUsuario(String email, String senha, String tipo, RedirectAttributes attributes) {
		ModelAndView mv = new ModelAndView();
		Usuario usuario = ur.login(email, senha, tipo);
		
		if (usuario == null) {
			mv.setViewName("redirect:/login");
			attributes.addFlashAttribute("mensagem", "O campo Email/Senha/Tipo está incorreto!");
			return mv;
		}
		System.out.println(usuario);
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
