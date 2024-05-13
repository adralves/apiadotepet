package br.com.adopet.api.service;

import br.com.adopet.api.dto.AprovacaoAdocaoDto;
import br.com.adopet.api.dto.ReprovacaoAdocaoDto;
import br.com.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.adopet.api.repository.AdocaoRepository;
import br.com.adopet.api.repository.PetRepository;
import br.com.adopet.api.repository.TutorRepository;
import br.com.adopet.api.model.Adocao;
import br.com.adopet.api.model.Pet;
import br.com.adopet.api.model.StatusAdocao;
import br.com.adopet.api.model.Tutor;
import br.com.adopet.api.validacoes.ValidacaoSolicitacaoAdocao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdocaoService {
    @Autowired
    private AdocaoRepository adocaoRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private TutorRepository tutorRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private List<ValidacaoSolicitacaoAdocao> validadoes;

    public void solicitar(SolicitacaoAdocaoDto dto){

        Pet pet = petRepository.getReferenceById(dto.idPet());
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());

        /*
         *Chama as validações(o spring fica responsavel por criar todas os objetos e fazer as validações necessarias dentro de cada classe)
         *essa validação esta usando o padrão Chain of Responsibility
         */
        validadoes.forEach(v -> v.validar(dto));

        Adocao adocao = new Adocao(tutor,pet, dto.motivo());

        adocaoRepository.save(adocao);

        String mensagem = "Olá " +adocao.getPet().getAbrigo().getNome() +"!\n\nUma solicitação de adoção foi registrada hoje para o pet: " +adocao.getPet().getNome() +". \nFavor avaliar para aprovação ou reprovação.";
        emailService.enviarEmail(adocao.getPet().getAbrigo().getEmail(),"Solicitação de Adoção",mensagem);

    }

    public void aprovar(AprovacaoAdocaoDto dto){
        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.MarcarComoAprovado();

        String mensagem = "Parabéns " +adocao.getTutor().getNome() +"!\n\nSua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi aprovada.\nFavor entrar em contato com o abrigo " +adocao.getPet().getAbrigo().getNome() +" para agendar a busca do seu pet.";
        emailService.enviarEmail(adocao.getTutor().getEmail(),"Adoção aprovada",mensagem);

    }

    public void reprovar(ReprovacaoAdocaoDto dto){
        Adocao adocao = adocaoRepository.getReferenceById(dto.idAdocao());
        adocao.marcarComoReprovado(dto.justificativa());

        String mensagem = "Olá " +adocao.getTutor().getNome() +"!\n\nInfelizmente sua adoção do pet " +adocao.getPet().getNome() +", solicitada em " +adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) +", foi reprovada pelo abrigo " +adocao.getPet().getAbrigo().getNome() +" com a seguinte justificativa: " +adocao.getJustificativaStatus();
        emailService.enviarEmail(adocao.getTutor().getEmail(),"Adoção reprovada",mensagem);
    }


}
