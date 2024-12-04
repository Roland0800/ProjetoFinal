package Sports.JLMN.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Sports.JLMN.models.Usuario;

public interface usuarioRepository extends JpaRepository<Usuario, Long> {

	@Query("select i from Usuario i where i.nome = :nome and i.senha = :senha and i.tipo = :tipo")
	public Usuario login(String nome, String senha, String tipo);
	
	@Query("update i Usuario i set i.senha i where i.id = :id")
	public Usuario newSenha(String senha, Long id);
}
