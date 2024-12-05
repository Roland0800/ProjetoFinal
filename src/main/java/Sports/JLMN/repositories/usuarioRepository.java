package Sports.JLMN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import Sports.JLMN.models.Usuario;


public interface usuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select i from Usuario i where i.nome = :nome and i.senha = :senha and i.email = :email and i.tipo = :tipo")
	public Usuario login(String nome, String senha, String email, String tipo);
	
	public Optional<Usuario> getByEmail(String email);
	
}
