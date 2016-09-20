package com.academicwork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
public class DemoController {

    Logger logger = new Logger();

    public DemoController() throws SQLException {
    }

    @RequestMapping(method = RequestMethod.GET, path = "/" )
    public  ModelAndView init() {
        ModelAndView modelAndView = new ModelAndView("init");
        List<String> listOfUsers = logger.getAllUsers();
        modelAndView.addObject("listOfUsers", listOfUsers);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/time")
    public ModelAndView time() {
        ModelAndView modelAndView = new ModelAndView("time");
        modelAndView.addObject("currentTime", LocalDateTime.now());
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/hello")
    public ModelAndView hello(@RequestParam String name) {
        ModelAndView modelAndView = new ModelAndView("hello");
        logger.addUser(name);
        modelAndView.addObject("name", name);
        return modelAndView;
    }
    @RequestMapping(method = RequestMethod.POST, path = "/logg")
    public ModelAndView logg(@RequestParam String username, @RequestParam(required = false) String input) {//, @RequestParam String username) {
        if (input != null) logger.logPost(username, input);
        ModelAndView modelAndView = new ModelAndView("logg");
        // modelAndView.addObject("loggPost", logger.generateLog());
        modelAndView.addObject("loggPost", logger.getAllFromDB(username));
        modelAndView.addObject("username", username);
        return modelAndView;
    }

}
