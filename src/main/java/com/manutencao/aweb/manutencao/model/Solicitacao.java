package com.manutencao.aweb.manutencao.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDateTime;

@Data 
@Entity 
public class Solicitacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do solicitante é obrigatório.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private String nomeSolicitante;

    @NotBlank(message = "A descrição do problema é obrigatória.")
    @Column(length = 1000) 
    private String descricaoProblema;

    private LocalDateTime dataHoraSolicitacao;
    private LocalDateTime dataHoraEncerramento;

    @PrePersist
    public void antesDeSalvar() {
        this.dataHoraSolicitacao = LocalDateTime.now(); 
    }
   
}
