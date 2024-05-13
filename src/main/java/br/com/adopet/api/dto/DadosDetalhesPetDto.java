package br.com.adopet.api.dto;

import br.com.adopet.api.model.Pet;
import br.com.adopet.api.model.TipoPet;

public record DadosDetalhesPetDto(Long id, TipoPet tipoPet, String nome, String raca, Integer idade) {
    public DadosDetalhesPetDto(Pet pet){
        this(pet.getId(), pet.getTipo(), pet.getNome(), pet.getRaca(), pet.getIdade());
    }
}
