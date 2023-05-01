package labs.webtech.controller;

import labs.webtech.DAO.AudienceDAO;

import labs.webtech.DAO.impl.AudienceDAOImpl;

import labs.webtech.table.Audience;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AudienceController {

    @Autowired
    private final AudienceDAO audienceDAO = new AudienceDAOImpl();

    @GetMapping("/audience")
    public String audiencePage(@RequestParam(name = "id") Long id, Model model) {
        Audience audience = audienceDAO.getById(id);

        if (audience == null) {
            model.addAttribute("error_msg", "There is no audience with this id = " + id);
            return "errorPage";
        }

        model.addAttribute("audience", audience);
        model.addAttribute("audienceService", audienceDAO);
        return "audience";
    }

    @GetMapping("/editAudience")
    public String editAudiencePage(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("audience", new Audience());
            model.addAttribute("audienceService", audienceDAO);
            return "editAudience";
        }

        Audience audience = audienceDAO.getById(id);

        if (audience == null) {
            model.addAttribute("error_msg", "There is no audience with this id =" + id);
            return "errorPage";
        }

        model.addAttribute("audience", audience);
        model.addAttribute("audienceService", audienceDAO);
        return "editAudience";
    }

    @PostMapping("/saveAudience")
    public String saveAudiencePage(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "number") String number,
                                   @RequestParam(name = "capacity") Integer capacity,
                                   Model model) {
        Audience audience = audienceDAO.getById(id);

        if (audience != null) {
            audience.setNumber(number);
            audience.setCapacity(capacity);
        } /*else {
            audience = new Audience(id, surname, name, patronymic, course);
        }*/

        model.addAttribute("error_msg", "Data not saved");
        return "errorPage";
    }

    @PostMapping("/removeAudience")
    public String removeAudiencePage(@RequestParam(name = "id") Long id) {
        audienceDAO.deleteById(id);
        return "redirect:/audiences";
    }

    @GetMapping("/audiences")
    public String audienceListPage(Model model) {
        List<Audience> audiences = (List<Audience>) audienceDAO.getAll();
        model.addAttribute("audiences", audiences);
        model.addAttribute("audienceService", audienceDAO);
        return "audiences";
    }
}
