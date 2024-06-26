package br.com.adopet.api.repository;

import br.com.adopet.api.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorRepository extends JpaRepository<Tutor, Long> {

    boolean existsByTelefone(String telefone);

    boolean existsByEmail(String email);

}
