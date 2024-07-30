package dksstylecmuh.cmuhprojet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
// import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import dksstylecmuh.cmuhprojet.modele.Participant;
import dksstylecmuh.cmuhprojet.repositorycmuhtest.RepositoryParticipant;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class TestCmuh {

   @Autowired
    private RepositoryParticipant repositoryParticipant;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreatUser(){
        Participant participant = new Participant();

        participant.setNom("Jean");
        participant.setPrenom("Maykenslo");
        participant.setEmail("testnn@gmail.com");
        participant.setMode_de_participation("finance");
        participant.setMontant(100);
        // participant.setCreated_At(date);

        Participant saveParticipant = repositoryParticipant.save(participant);

        //// Récupération de l'utilisateur sauvegardé depuis la base de données
        Participant existParticipant = entityManager.find(Participant.class, saveParticipant.getId());

      // Vérification que l'email de l'utilisateur récupéré est égal à l'email de l'utilisateur original
        assertThat(existParticipant.getEmail()).isEqualTo(participant.getEmail());
        // assertThat(existParticipant.getCreatedAt()).isNotNull();
        // assertThat(existParticipant.getUpdatedAt()).isNotNull();

         assertNotNull(participant.getCreatedAt(), "La date de création ne doit pas être nulle après l'enregistrement.");
        assertNotNull(participant.getUpdatedAt(),  "La date de mise à jour ne doit pas être nulle après l'enregistrement.");


         LocalDateTime createdAt = participant.getCreatedAt();
        LocalDateTime updatedAt = participant.getUpdatedAt();
        // assertEquals(createdAt, updatedAt, "Les dates de création et de mise à jour doivent être identiques après l'enregistrement.");
        //     assertTrue(Duration.between(participant.getCreatedAt(), participant.getUpdatedAt()).getSeconds() < 1, "Les dates de création et de mise à jour doivent être presque identiques après l'enregistrement.");

    }
    
}
