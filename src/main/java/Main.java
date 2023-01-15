import spark.ModelAndView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.*;
import spark.template.jade.JadeTemplateEngine;

public class Main {
    public static void main(String[] args) throws SQLIntegrityConstraintViolationException {

        UserController userController = new UserController();
        ProjectController projectController = new ProjectController();
        TaskController taskController = new TaskController();

    }
}
