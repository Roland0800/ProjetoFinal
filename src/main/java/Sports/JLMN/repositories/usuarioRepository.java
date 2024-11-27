package Sports.JLMN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Sports.JLMN.models.Usuario;

public interface usuarioRepository extends JpaRepository<Usuario, Long>{
	
	@Query("select i from Usuario i where i.nome = :nome")
	public Usuario findByNome (String nome);
	
	@Query("select j from Usuario j where j.senha = :senha")
	public Usuario findBySenha (String senha);

}
