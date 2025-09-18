package io.github.antoniasousa.icompra.clientes.repository;

import io.github.antoniasousa.icompra.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
