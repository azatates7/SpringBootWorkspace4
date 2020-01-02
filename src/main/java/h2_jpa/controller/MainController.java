package h2_jpa.controller;

import h2_jpa.dao.PersonDAO;
import h2_jpa.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController implements ErrorController {

    @Autowired
    private PersonDAO personDAO;

    @ResponseBody
    @RequestMapping("/h2_jpa")
    public String index() {
        Iterable<Person> all = personDAO.findAll();
        StringBuilder sb = new StringBuilder();
        all.forEach(p -> sb.append(p.getFullName() + "<br>"));
        return sb.toString();
    }

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }


}