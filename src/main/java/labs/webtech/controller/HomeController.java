package labs.webtech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value = { "/", "/index"})
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/courses")
    public String courses() {
        return "courses";
    }

    @RequestMapping(value = "/lecturers")
    public String lecturers() {
        return "lecturers";
    }

    @RequestMapping(value = "/groups")
    public String groups() {
        return "groups";
    }
    @RequestMapping(value = "/audiences")
    public String audiences() {
        return "audiences";
    }
}
