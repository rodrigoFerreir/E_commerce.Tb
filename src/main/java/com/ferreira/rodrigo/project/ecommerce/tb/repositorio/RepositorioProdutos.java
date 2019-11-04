package com.ferreira.rodrigo.project.ecommerce.tb.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ferreira.rodrigo.project.ecommerce.tb.model.Categoria;
import com.ferreira.rodrigo.project.ecommerce.tb.model.Produto;

@Repository
public interface RepositorioProdutos extends JpaRepository<Produto, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> buscar(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias,
			Pageable pageRequest);
}// consulta para busca de produtos.
