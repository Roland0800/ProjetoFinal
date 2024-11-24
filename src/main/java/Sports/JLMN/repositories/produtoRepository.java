package Sports.JLMN.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Sports.JLMN.models.Artigo;
import Sports.JLMN.models.Produto;

public interface produtoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findByArtigo(Artigo artigo);

}
