package Sports.JLMN.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import Sports.JLMN.models.Usuario;


public interface usuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select i from Usuario i where i.email = :email and i.senha = :senha and i.tipo = :tipo")
	public Usuario login(String email, String senha, String tipo);
	
	public Optional<Usuario> getByEmail(String email);
	
}
