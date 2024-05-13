package br.com.adopet.api.controller;

import br.com.adopet.api.dto.DadosDetalhesPet;
import br.com.adopet.api.repository.PetRepository;
import br.com.adopet.api.model.Pet;
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
    private PetRepository petRepository;

    @GetMapping
    public ResponseEntity<List<DadosDetalhesPet>> listarTodosDisponiveis() {
        List<Pet> pets = petRepository.findAll();
        List<DadosDetalhesPet> disponiveis = new ArrayList<>();

        for (Pet pet : pets) {
            if (pet.getAdotado() == false) {
                disponiveis.add(new DadosDetalhesPet(pet));
            }
        }
        return ResponseEntity.ok(disponiveis);
    }

}
