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
import Sports.JLMN.models.Usuario;
import Sports.JLMN.repositories.artigoRepository;
import Sports.JLMN.repositories.produtoRepository;
import Sports.JLMN.repositories.usuarioRepository;
import jakarta.validation.Valid;

@Controller
public class projectController {

	@Autowired
	private artigoRepository ar;
	@Autowired
	private produtoRepository pr;
	@Autowired
	private usuarioRepository ur;
	

	@GetMapping("/addArtigo")
	public String addArtigo(Artigo artigo) {
		return "artigo/addArtigo";
	}

	@PostMapping("/saveArtigo")
	public String saveArtigo(@Valid Artigo artigo, BindingResult result, RedirectAttributes attribute) {
		if (result.hasErrors()) {
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
		if (opt.isEmpty()) {
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
		if (opt.isEmpty()) {
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
		mv.setViewName("artigo/home");
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

		mv.setViewName("artigo/Artigo");
		Artigo artigo = opt.get();
		mv.addObject("artigo", artigo);

		List<Produto> produtos = pr.findByArtigo(artigo);
		mv.addObject("produtos", produtos);
		return mv;
	}
	
	
	//--------------------------------------------------------------------------------------------------//
	

	@PostMapping("/addProduto/{id}")
	public ModelAndView addProduto(@PathVariable Long id, @Valid Produto produto, BindingResult result, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();
		Optional<Artigo> opt = ar.findById(id);
		if(opt.isEmpty()) {
			mv.setViewName("redirect:/home");
			return mv;
		}
		
		Artigo artigo = opt.get();
		
		if(result.hasErrors()) {
			return artigo(id, produto);
		}
		produto.setArtigo(artigo);
		pr.save(produto);
		attribute.addFlashAttribute("mensagem", "Produto adicionado/editado com sucesso!");
		mv.setViewName("redirect:/Artigo/{id}");
		return mv;
	}
	
	@GetMapping("/Produtos")
	public ModelAndView listProdutos(Produto produto) {
		List<Produto> produtos = pr.findAll();
		ModelAndView mv = new ModelAndView();
		mv.addObject("produtos", produtos);
		mv.setViewName("produto/Produtos");
		return mv;
	}
	
	@GetMapping("/Produto/{id}")
	public ModelAndView Produto(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView();
		Optional<Produto> opt = pr.findById(id);
		if(opt.isEmpty()) {
			mv.setViewName("redirect:/Produto");
			return mv;
		}
		
		mv.setViewName("produto/Produto");
		Produto produto = opt.get();
		mv.addObject("produto", produto);
		return mv;
	}
	
	@GetMapping("/Produto/edit/{idArtigo}/{id}")
	public ModelAndView editProduto(@PathVariable Long id, @PathVariable Long idArtigo) {
		ModelAndView mv = new ModelAndView();
		Optional<Produto> opt = pr.findById(id);
		Optional<Artigo> opt2 = ar.findById(idArtigo);
		if (opt.isEmpty() || opt2.isEmpty()) {
			mv.setViewName("redirect:/home");
			return mv;
		}
		Produto produto = opt.get();
		Artigo artigo = opt2.get();
		mv.addObject("produto", produto);
		mv.addObject("artigo", artigo);
		mv.addObject("produtos", pr.findByArtigo(artigo));
		mv.setViewName("artigo/Artigo");
		return mv;
	}

	@GetMapping("/Produto/delete/{idArtigo}/{id}")
	public String deletarProduto(@PathVariable Long id, @PathVariable Long idArtigo, RedirectAttributes attribute) {
		Optional<Produto> opt = pr.findById(id);
		if (opt.isEmpty()) {
			return "redirect:/home";
		}
		pr.delete(opt.get());
		attribute.addFlashAttribute("mensagem", "Produto deletado com sucesso!");
		return "redirect:/Artigo/{idArtigo}";
	}
	
	@GetMapping("/Produto/add/{idUsuario}/{id}")
	public ModelAndView addUserProduto(@PathVariable Long id, @PathVariable Long idUsuario, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();
		Optional<Produto> opt = pr.findById(id);
		Optional<Usuario> opt2 = ur.findById(idUsuario);
		if (opt.isEmpty() || opt2.isEmpty()) {
			mv.setViewName("redirect:/home");
			return mv;
		}
		Usuario usuario = opt2.get();
		Produto produto = opt.get();
		produto.setUsuario(usuario);
		attribute.addFlashAttribute("mensagem", "Produto adicionado ao carrinho com sucesso!");
		mv.setViewName("redirect:/Usuario/{idUsuario}");
		return mv;
	}

	@GetMapping("/Produto/remove/{idUsuario}/{id}")
	public String removeUserProduto(@PathVariable Long id, @PathVariable Long idUsuario, RedirectAttributes attribute) {
		Optional<Produto> opt = pr.findById(id);
		Optional<Usuario> opt2 = ur.findById(idUsuario);
		if (opt.isEmpty() || opt2.isEmpty()) {
			return "redirect:/home";
		}
		Produto produto = opt.get();
		produto.setUsuario(null);
		attribute.addFlashAttribute("mensagem", "Produto removido do carrinho com sucesso!");
		return "redirect:/Usuario/{idUsuario}";
	}
	
	
	//----------------------------------------------------------------------------------------------------------//
	
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

	public ModelAndView newSenha(String email, String senha, RedirectAttributes attribute) {
		ModelAndView mv = new ModelAndView();
		Optional<Usuario> opt = ur.getByEmail(email);
		if (opt.isEmpty()) {
			mv.setViewName("redirect:/altSenha");
			attribute.addFlashAttribute("mensagem", "Email inválido!");
			return mv;
		}
		if(senha == null) {
			mv.setViewName("redirect:/altSenha");
			attribute.addFlashAttribute("mensagem", "Campo Senha não pode ser vazio!");
			return mv;
		}
		Usuario usuario = opt.get();
		usuario.setSenha(senha);
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
		attributes.addFlashAttribute("mensagem", "O Usuário realizou login com sucesso!");
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
		Usuario usuario = opt.get();
		List<Produto> produtos = pr.findAll();
		List<Produto> userProdutos = pr.findByUsuario(usuario);
		mv.addObject("produtos", produtos);
		mv.addObject("userProdutos", userProdutos);
		mv.addObject("usuario", usuario);
		mv.setViewName("usuario/detalhes");
		return mv;
	}

	@GetMapping("/Usuario/{idProduto}/{id}")
	public ModelAndView userProduto(@PathVariable Long id, @PathVariable Long idProduto) {
		ModelAndView mv = new ModelAndView();
		Optional<Usuario> opt = ur.findById(id);
		Optional<Produto> opt2 = pr.findById(idProduto);
		if (opt.isEmpty() || opt2.isEmpty()) {
			mv.setViewName("redirect:/usuarios");
			return mv;
		}
		Produto produto = opt2.get();
		Usuario usuario = opt.get();
		produto.setUsuario(usuario);
		pr.save(produto);
		mv.setViewName("redirect:/Usuario/{id}");
		return mv;
	}

	@GetMapping("/Usuario/remove/{idProduto}/{id}")
	public ModelAndView removeUserProduto(@PathVariable Long id, @PathVariable Long idProduto) {
		ModelAndView mv = new ModelAndView();
		Optional<Usuario> opt = ur.findById(id);
		Optional<Produto> opt2 = pr.findById(idProduto);
		if (opt.isEmpty() || opt2.isEmpty()) {
			mv.setViewName("redirect:/usuarios");
			return mv;
		}
		Produto produto = opt2.get();
		produto.setUsuario(null);
		pr.save(produto);
		mv.setViewName("redirect:/Usuario/{id}");
		return mv;
	}
	
	@GetMapping("/pay")
	public String Payment() {
		return "pay";
	}
}
