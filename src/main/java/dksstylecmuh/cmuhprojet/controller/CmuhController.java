package dksstylecmuh.cmuhprojet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dksstylecmuh.cmuhprojet.modele.Participant;
import dksstylecmuh.cmuhprojet.repositorycmuhtest.RepositoryParticipant;
import dksstylecmuh.cmuhprojet.service.EmailService;
import dksstylecmuh.cmuhprojet.service.ServiceParticipant;


@Controller
// @RequestMapping("/cmuhprojet")
public class CmuhController {

    @Autowired
    private RepositoryParticipant repositoryParticipant;

    @Autowired
    private ServiceParticipant participantContent;

    @Autowired
    private EmailService emailService;

    @GetMapping("/")
    public String homePage() {
        return "index_presentation_page1";
    }

    @GetMapping("/validation")
    // model : C'est un objet de type Model ou ModelMap fourni par Spring MVC. Il
    // permet de passer des données depuis le contrôleur vers la vue (comme une page
    // HTML).

    public String formvalidation(Model model) {
        model.addAttribute("participant", new Participant());

        return "form_page2";
    }
    //test
    // @Autowired
    // private TestEntityManager entityManager;

    @PostMapping("/add_element")
    public String registertverification(@RequestParam("montant") String montant, Participant participant, Model model,
            RedirectAttributes ra) { // RedirectAttributes: non utiliser
        if (repositoryParticipant.findByEmail(participant.getEmail()) != null) {
            model.addAttribute("email", participant.getEmail());
            model.addAttribute("errorMessage",
                    "Une participation avec cet email a déjà été validée récemment. Pour continuer, vous pouvez supprimer l'ancienne participation et retourner à la page d'accueil pour une nouvelle soumission.");
            return "delete_proposition";
           
        }
        // // kit funny
        // Participant foundParticipant = repositoryParticipant.findByPrenom(participant.getPrenom());

        //  if (foundParticipant != null && foundParticipant.getPrenom().equals("Nelson")) {
        //     model.addAttribute("prenom", participant.getPrenom());
        //     return "kitNelson";
        // }

        // validation montant
        try {
            double montantDouble = Double.parseDouble(montant);
            if (montantDouble < 0) {
                model.addAttribute("errorMontantNombre", "Veulliez entrer un montant valide en gourdes");
            }
        } catch (NumberFormatException e) {
            model.addAttribute("errorMontant", "Veuillez entrer un montant valide en gourdes (un nombre)");
            return "form_page2";
        }

        repositoryParticipant.save(participant);
        
        model.addAttribute("prenom", participant.getPrenom());

        // Envoyer un email

        try {
        String subject = "Rendez-Vous Kay Phara";
        String body = "";
        emailService.sendHtmlEmail(participant.getEmail(), subject, body);

        } catch (Exception e) {
        model.addAttribute("emailError", "Il y a eu un probleme lors de l'emvoie de l'email");
        e.printStackTrace();
        return "success_process_page3";

        }
        return "success_process_page3";
    }

    // @RequestParam: Annotation indiquant qu'un paramètre de méthode doit être lié
    // à un paramètre de requête Web.

    @PostMapping("/delete")
    public String deleteParticipant(@RequestParam("email") String email, Model model) {
        boolean isDeleted = participantContent.deleteParticipantByEmail(email);
        if (isDeleted) {
            model.addAttribute("message", "Utilisateur supprimé avec succèss.");
        } else {
            model.addAttribute("message", "Utilisateur non trouvé");
        }

        return "index_presentation_page1";
    }

    @GetMapping("/show_list_participants")
    public String participant_list(Model model) {
        List<Participant> listParticipants = participantContent.listAll();
        model.addAttribute("listParticipants", listParticipants);
        return "list_participants";
    }

}
