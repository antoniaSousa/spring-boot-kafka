package io.github.antoniasousa.icompras.pedidos.publisher.representation;

public record DadosClientesRepresentations(
        Long codigo,
        String nome,
        String cpf,
        String logradouro,
        String numero,
        String bairro,
        String email,
        String telefone
) {
}
