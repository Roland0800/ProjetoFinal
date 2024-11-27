package Sports.JLMN.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Sports.JLMN.models.Usuario;

public interface usuarioRepository extends JpaRepository<Usuario, Long>{
	
	 List<Usuario> findByNome (Usuario usuario);

}
