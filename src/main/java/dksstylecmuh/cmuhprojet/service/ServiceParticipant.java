package dksstylecmuh.cmuhprojet.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dksstylecmuh.cmuhprojet.modele.Participant;
import dksstylecmuh.cmuhprojet.repositorycmuhtest.RepositoryParticipant;

@Service
public class ServiceParticipant {

    @Autowired
    private RepositoryParticipant repositoryParticipant;

    public boolean deleteParticipantByEmail(String email){
        Participant participantContent = repositoryParticipant.findByEmail(email);
        if(participantContent != null){
            repositoryParticipant.delete(participantContent);
            return true;
        }
        return false;
    }

    //methode pour voir liste des participer
    public List<Participant> listAll(){
        //methode listAll(): appell repo.findAll pour obtenir un Iterable<Participant>
        Iterable<Participant> partiipant = repositoryParticipant.findAll();
        //creer une nouvelle "ArrayList<Participant>"
        List<Participant> listParticipants = new ArrayList<>();
        //parcourt chaque element de l'Itterable<Participants> et l'ajoute à la ArrayList<Participant>
        //en utilisant la methode "forEarch"
        partiipant.forEach(listParticipants::add);
        return listParticipants;
    }

   


    

}
