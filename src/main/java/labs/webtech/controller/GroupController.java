package labs.webtech.controller;

import labs.webtech.table.Group;

import labs.webtech.DAO.GroupDAO;
import labs.webtech.DAO.StudentDAO;

import labs.webtech.DAO.impl.GroupDAOImpl;
import labs.webtech.DAO.impl.StudentDAOImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GroupController {

    @Autowired
    private final GroupDAO groupDAO = new GroupDAOImpl();

    @Autowired
    private final StudentDAO studentDAO = new StudentDAOImpl();

    @GetMapping("/group")
    public String groupPage(@RequestParam(name = "id") Long id, Model model) {
        Group group = groupDAO.getById(id);

        if (group == null) {
            model.addAttribute("error_msg", "There is no group with this id = " + id);
            return "errorPage";
        }

        model.addAttribute("group", group);
        model.addAttribute("groupService", groupDAO);
        model.addAttribute("students", studentDAO.getByGroup(group));
        return "group";
    }

    @GetMapping("/stream")
    public String streamPage(@RequestParam(name = "stream") Integer stream, Model model) {
        model.addAttribute("stream", stream);
        model.addAttribute("studentService", studentDAO);
        return "stream";
    }

    @GetMapping("/groups")
    public String groupListPage(Model model) {
        List<Group> groups = (List<Group>) groupDAO.getAll();
        model.addAttribute("groups", groups);
        model.addAttribute("groupService", groupDAO);
        return "groups";
    }
}
