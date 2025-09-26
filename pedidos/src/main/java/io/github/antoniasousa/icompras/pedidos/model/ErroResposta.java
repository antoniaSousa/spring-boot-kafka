package io.github.antoniasousa.icompras.pedidos.model;

import java.util.SplittableRandom;

public record ErroResposta(String mensagem, String campo, String erro) {
}
