package br.com.adopet.api.service;

import br.com.adopet.api.dto.DadosDetalhesPetDto;
import br.com.adopet.api.model.Abrigo;
import br.com.adopet.api.model.Pet;
import br.com.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;
    public List<DadosDetalhesPetDto> listarPetsDisponiveis(){
        return petRepository
                .findAllByAdotadoFalse()
                .stream()
                .map(DadosDetalhesPetDto::new)
                .toList();
    }

    /*
    public void cadastrarPet(Abrigo abrigo, CadastroPetDto dto){
        petRepository.save(new Pet(dto,abrigo));
    }

     */
}
