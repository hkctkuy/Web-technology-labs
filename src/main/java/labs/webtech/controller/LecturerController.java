package labs.webtech.controller;

import labs.webtech.DAO.LecturerDAO;

import labs.webtech.DAO.impl.LecturerDAOImpl;

import labs.webtech.table.Course;
import labs.webtech.table.Lecturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LecturerController {

    @Autowired
    private final LecturerDAO lecturerDAO = new LecturerDAOImpl();

    @GetMapping("/lecturer")
    public String lecturerPage(@RequestParam(name = "id") Long id, Model model) {
        Lecturer lecturer = lecturerDAO.getById(id);
        List<Course> courses = lecturerDAO.getCourseList(lecturer);

        if (lecturer == null) {
            model.addAttribute("error_msg", "There is no lecturer with this id = " + id);
            return "errorPage";
        }

        model.addAttribute("lecturer", lecturer);
        model.addAttribute("lecturerService", lecturerDAO);
        model.addAttribute("courses", courses);
        return "lecturer";
    }

    @GetMapping("/editLecturer")
    public String editLecturerPage(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("lecturer", new Lecturer());
            model.addAttribute("lecturerService", lecturerDAO);
            //model.addAttribute("courseService", courseDAO);
            return "editLecturer";
        }

        Lecturer lecturer = lecturerDAO.getById(id);

        if (lecturer == null) {
            model.addAttribute("error_msg", "There is no lecturer with this id =" + id);
            return "errorPage";
        }

        model.addAttribute("lecturer", lecturer);
        model.addAttribute("lecturerService", lecturerDAO);
        //model.addAttribute("courseService", courseDAO);
        return "editLecturer";
    }

    @PostMapping("/saveLecturer")
    public String saveLecturerPage(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "surname") String surname,
                                  @RequestParam(name = "name") String name,
                                  @RequestParam(name = "patronymic") String patronymic,
                                  Model model) {
        Lecturer lecturer = lecturerDAO.getById(id);

        if (lecturer != null) {
            lecturer.setSurname(surname);
            lecturer.setName(name);
            lecturer.setPatronymic(patronymic);
        } /*else {
            lecturer = new Lecturer(id, surname, name, patronymic, course);
        }*/

        model.addAttribute("error_msg", "Data not saved");
        return "errorPage";
    }

    @PostMapping("/removeLecturer")
    public String removeLecturerPage(@RequestParam(name = "id") Long id) {
        lecturerDAO.deleteById(id);
        return "redirect:/lecturers";
    }

    @GetMapping("/lecturers")
    public String lecturerListPage(Model model) {
        List<Lecturer> lecturers = (List<Lecturer>) lecturerDAO.getAll();
        model.addAttribute("lecturers", lecturers);
        model.addAttribute("lecturerService", lecturerDAO);
        return "lecturers";
    }
}
