package io.github.antoniasousa.icompra.clientes.service;

import io.github.antoniasousa.icompra.clientes.model.Cliente;
import io.github.antoniasousa.icompra.clientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {

        return repository.save(cliente);
    }
    public Optional<Cliente> obterPorCodigo(Long codigo) {

        return repository.findById(codigo);
    }
}
