package labs.webtech.controller;

import labs.webtech.DAO.ExerciseDAO;
import labs.webtech.DAO.GroupDAO;

import labs.webtech.DAO.impl.ExerciseDAOImpl;
import labs.webtech.DAO.impl.GroupDAOImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExerciseController {

    @Autowired
    private final ExerciseDAO exerciseDAO = new ExerciseDAOImpl();
    @Autowired
    private final GroupDAO groupDAO = new GroupDAOImpl();

    @GetMapping("/exercises")
    public String exerciseListPage(Model model) {
        model.addAttribute("exerciseService", exerciseDAO);
        model.addAttribute("groupService", groupDAO);
        return "exercises";
    }
}
