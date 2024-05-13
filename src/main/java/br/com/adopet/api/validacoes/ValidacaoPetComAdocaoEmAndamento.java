package br.com.adopet.api.validacoes;

import br.com.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.adopet.api.exception.ValidacaoException;
import br.com.adopet.api.model.Adocao;
import br.com.adopet.api.model.Pet;
import br.com.adopet.api.model.StatusAdocao;
import br.com.adopet.api.repository.AdocaoRepository;
import br.com.adopet.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoPetComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao{

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private PetRepository petRepository;
    public void validar(SolicitacaoAdocaoDto dto){
        /* O codigo abaixo foi refatorado, pensando em melhoria de performance, pois o JPA irá fazer uma consulta melhorada no banco de dados usando o Spring
            List<Adocao> adocoes = adocaoRepository.findAll();
            Pet pet = petRepository.getReferenceById(dto.idPet());

            for (Adocao a : adocoes){
                if (a.getPet() == pet && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                    throw new ValidacaoException("Pet já esta aguardando avaliação para ser adotado!");
                }
            }
         */

        boolean petTemAdocaoEmAndamento = adocaoRepository.existsByPetIdAndStatus(dto.idPet(),StatusAdocao.AGUARDANDO_AVALIACAO);

        if (petTemAdocaoEmAndamento) {
            throw new ValidacaoException("Pet já esta aguardando avaliação para ser adotado!");
        }

    }

}
