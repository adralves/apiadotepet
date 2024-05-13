package br.com.adopet.api.validacoes;

import br.com.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.adopet.api.exception.ValidacaoException;
import br.com.adopet.api.model.Adocao;
import br.com.adopet.api.model.StatusAdocao;
import br.com.adopet.api.model.Tutor;
import br.com.adopet.api.repository.AdocaoRepository;
import br.com.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocao{

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private TutorRepository tutorRepository;
    public void validar(SolicitacaoAdocaoDto dto){
        List<Adocao> adocoes = adocaoRepository.findAll();
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        for (Adocao a : adocoes){
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Tutor já possui outra adoção em andamento!");
            }
        }
    }

}
