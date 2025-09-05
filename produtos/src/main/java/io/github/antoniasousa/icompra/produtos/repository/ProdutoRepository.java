package io.github.antoniasousa.icompra.produtos.repository;

import io.github.antoniasousa.icompra.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository  extends JpaRepository<Produto, Long> {
}
