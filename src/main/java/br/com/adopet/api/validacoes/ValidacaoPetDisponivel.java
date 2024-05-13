package br.com.adopet.api.validacoes;

import br.com.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.adopet.api.exception.ValidacaoException;
import br.com.adopet.api.model.Pet;
import br.com.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacaoPetDisponivel implements ValidacaoSolicitacaoAdocao{

    @Autowired
    private PetRepository petRepository;
    public void validar(SolicitacaoAdocaoDto dto) {

        //cria uma variavel pet que será buscada atraves do id que esta vindo pelo dto
        Pet pet = petRepository.getReferenceById(dto.idPet());

        if (pet.getAdotado()) {
            throw new ValidacaoException("Pet já foi adotado!");
        }
    }

}
