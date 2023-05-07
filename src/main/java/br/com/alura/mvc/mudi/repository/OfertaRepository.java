package br.com.alura.mvc.mudi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.mvc.mudi.model.Oferta;

@Repository
public interface OfertaRepository extends CrudRepository<Oferta, Long>{

}
