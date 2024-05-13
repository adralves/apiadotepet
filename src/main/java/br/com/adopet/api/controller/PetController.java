package br.com.adopet.api.controller;

import br.com.adopet.api.dto.DadosDetalhesPetDto;
import br.com.adopet.api.repository.PetRepository;
import br.com.adopet.api.model.Pet;
import br.com.adopet.api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesPetDto>> listarTodosDisponiveis() {
        List<DadosDetalhesPetDto> pets = petService.listarPetsDisponiveis();

        return ResponseEntity.ok(pets);
    }

}
