package dksstylecmuh.cmuhprojet.repositorycmuhtest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dksstylecmuh.cmuhprojet.modele.Participant;

@Repository
public interface RepositoryParticipant extends JpaRepository<Participant, Long> {
    Participant findByEmail(String email);
    Participant findByPrenom(String prenom);


}
