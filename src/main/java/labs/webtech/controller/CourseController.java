package labs.webtech.controller;

import labs.webtech.DAO.CourseDAO;

import labs.webtech.DAO.impl.CourseDAOImpl;

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
public class CourseController {

    @Autowired
    private final CourseDAO courseDAO = new CourseDAOImpl();

    @GetMapping("/course")
    public String coursePage(@RequestParam(name = "id") Long id, Model model) {
        Course course = courseDAO.getById(id);
        List<Lecturer> lecturers = courseDAO.getLecturerList(course);

        if (course == null) {
            model.addAttribute("error_msg", "There is no course with this id = " + id);
            return "errorPage";
        }

        model.addAttribute("course", course);
        model.addAttribute("courseService", courseDAO);
        model.addAttribute("lecturers", lecturers);
        return "course";
    }

    @GetMapping("/editCourse")
    public String editCoursePage(@RequestParam(name = "id", required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("course", new Course());
            model.addAttribute("courseService", courseDAO);
            //model.addAttribute("lecturerService", lecturerDAO);
            return "editCourse";
        }

        Course course = courseDAO.getById(id);

        if (course == null) {
            model.addAttribute("error_msg", "There is no course with this id =" + id);
            return "errorPage";
        }

        model.addAttribute("course", course);
        model.addAttribute("courseService", courseDAO);
        //model.addAttribute("lecturerService", lecturerDAO);
        return "editCourse";
    }

    @PostMapping("/saveCourse")
    public String saveCoursePage(@RequestParam(name = "id") Long id,
                                  @RequestParam(name = "name") String name,
                                  @RequestParam(name = "coverage") Course.Coverage coverage,
                                 @RequestParam(name = "depth") Integer depth,
                                 @RequestParam(name = "year") Integer year,
                                  Model model) {
        Course course = courseDAO.getById(id);

        if (course != null) {
            course.setName(name);
            course.setCoverage(coverage);
            course.setDepth(depth);
            course.setYear(year);
        } /*else {
            course = new Course(id, surname, name, patronymic, lecturer);
        }*/

        model.addAttribute("error_msg", "Data not saved");
        return "errorPage";
    }

    @PostMapping("/removeCourse")
    public String removeCoursePage(@RequestParam(name = "id") Long id) {
        courseDAO.deleteById(id);
        return "redirect:/courses";
    }

    @GetMapping("/courses")
    public String courseListPage(Model model) {
        model.addAttribute("courseService", courseDAO);
        return "courses";
    }
}
