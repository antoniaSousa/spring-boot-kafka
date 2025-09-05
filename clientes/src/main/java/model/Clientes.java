package model;

import jakarta.persistence.*;
import jdk.jfr.DataAmount;
import lombok.Data;

@Entity
@Table(name= "clientes")
@Data
public class Clientes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    @Column(name = "nome", nullable = false, length = 150)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column (name = "logradouro", length = 100)
    private String logradouro;

    @Column (name = "numero", length = 10 )
    private String numero;

    @Column (name = "bairro", length = 150)
    private String bairro;

    @Column (name = "emai", length = 150)
    private String email;

    @Column (name = "telefone", length = 20)
    private String telefone;
}
