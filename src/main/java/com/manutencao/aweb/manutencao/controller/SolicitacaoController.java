package com.manutencao.aweb.manutencao.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.manutencao.aweb.manutencao.repository.SolicitacaoRepository;
import com.manutencao.aweb.manutencao.model.Solicitacao;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/solicitacoes") 
public class SolicitacaoController {

    @Autowired 
    private SolicitacaoRepository solicitacaoRepository;

    @GetMapping
    public String listarSolicitacoes(Model model) {
        model.addAttribute("solicitacoes", solicitacaoRepository.findAll());
        return "lista-solicitacoes"; 
    }

    @GetMapping("/nova")
    public String novaSolicitacaoForm(Model model) {
        model.addAttribute("solicitacao", new Solicitacao());
        return "form-solicitacao"; 
    }

    @PostMapping
    public String salvarSolicitacao(@Valid @ModelAttribute("solicitacao") Solicitacao solicitacao, BindingResult result) {
        if (result.hasErrors()) {
            return "form-solicitacao"; 
        }
        solicitacaoRepository.save(solicitacao);
        return "redirect:/solicitacoes"; 
    }

    @PostMapping("/{id}/finalizar")
    public String finalizarSolicitacao(@PathVariable Long id) {
        Optional<Solicitacao> solicitacaoOpt = solicitacaoRepository.findById(id);
        if (solicitacaoOpt.isPresent()) {
            Solicitacao solicitacao = solicitacaoOpt.get();
            solicitacao.setDataHoraEncerramento(LocalDateTime.now());
            solicitacaoRepository.save(solicitacao);
        }
        return "redirect:/solicitacoes";
    }
    @GetMapping("/editar/{id}")
    public String editarSolicitacaoForm(@PathVariable Long id, Model model) {
        
        Optional<Solicitacao> solicitacaoOpt = solicitacaoRepository.findById(id);

        if (!solicitacaoOpt.isPresent()) {
            return "redirect:/solicitacoes";
        }

        model.addAttribute("solicitacao", solicitacaoOpt.get());

        return "form-solicitacao";
    }

    @PostMapping("/editar/{id}")
    public String atualizarSolicitacao(@PathVariable Long id, 
                                     @Valid @ModelAttribute("solicitacao") Solicitacao solicitacao, 
                                     BindingResult result) {
                                    
        if (result.hasErrors()) {
            return "form-solicitacao";
        }

        
        Optional<Solicitacao> solicitacaoOriginalOpt = solicitacaoRepository.findById(id);
        if (!solicitacaoOriginalOpt.isPresent()) {
            return "redirect:/solicitacoes";
        }

        Solicitacao solicitacaoOriginal = solicitacaoOriginalOpt.get();

       
        solicitacaoOriginal.setNomeSolicitante(solicitacao.getNomeSolicitante());
        solicitacaoOriginal.setDescricaoProblema(solicitacao.getDescricaoProblema());

        solicitacaoRepository.save(solicitacaoOriginal);

        return "redirect:/solicitacoes";
    }
}