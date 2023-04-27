package labs.webtech.controller;

import labs.webtech.DAO.GroupDAO;
import labs.webtech.DAO.StudentDAO;

import labs.webtech.DAO.impl.GroupDAOImpl;
import labs.webtech.DAO.impl.StudentDAOImpl;

import labs.webtech.table.Group;
import labs.webtech.table.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StudentController {

    @Autowired
    private final StudentDAO studentDAO = new StudentDAOImpl();

    @Autowired
    private final GroupDAO groupDAO = new GroupDAOImpl();

    @GetMapping("/student")
    public String studentPage(@RequestParam(name = "id") Long id, Model model) {
        Student student = studentDAO.getById(id);

        if (student == null) {
            model.addAttribute("error_msg", "There is no student with this id = " + id);
            return "errorPage";
        }

        model.addAttribute("student", student);
        model.addAttribute("studentService", studentDAO);
        model.addAttribute("groupService", groupDAO);
        return "student";
    }

    @GetMapping("/editStudent")
    public String editStudentPage(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("student", new Student());
            model.addAttribute("studentService", studentDAO);
            model.addAttribute("groupService", groupDAO);
            return "editStudent";
        }

        Student student = studentDAO.getById(id);

        if (student == null) {
            model.addAttribute("error_msg", "There is no student with this id =" + id);
            return "errorPage";
        }

        model.addAttribute("student", student);
        model.addAttribute("studentService", studentDAO);
        model.addAttribute("groupService", groupDAO);
        return "editStudent";
    }

    @PostMapping("/saveStudent")
    public String saveStudentPage(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "surname") String surname,
                                  @RequestParam(name = "name") String name,
                                  @RequestParam(name = "patronymic") String patronymic,
                                  @RequestParam(name = "group") Group group,
                                  Model model) {
        Student student = studentDAO.getById(id);

        if (student != null) {
            student.setSurname(surname);
            student.setName(name);
            student.setPatronymic(patronymic);
            student.setGroup(group);
        } /*else {
            student = new Student(id, surname, name, patronymic, group);
        }*/

        model.addAttribute("error_msg", "Data not saved");
        return "errorPage";
    }

    @PostMapping("/removeStudent")
    public String removeStudentPage(@RequestParam(name = "id") Long id) {
        studentDAO.deleteById(id);
        return "redirect:/students";
    }
}
