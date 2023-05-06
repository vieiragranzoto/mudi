package br.com.alura.mvc.mudi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.mvc.mudi.model.Acesso;

@Repository
public interface AcessoRepository extends CrudRepository<Acesso, Long> {

	public List<Acesso> findAll();
}
