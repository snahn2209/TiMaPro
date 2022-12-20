import spark.ModelAndView;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark . Spark .*;
import spark.template.jade.JadeTemplateEngine;

public class Main {
    public static void main(String[] args) throws SQLIntegrityConstraintViolationException {
        /*
        UserDataProvider.insertUser("Pia");

        UserAccount user = UserDataProvider.selectUser("Pia");
        System.out.println(user.toString());
        Connection con = DBConnection.getConnection();

        List<UserAccount> users = UserDataProvider.selectAllUsers(con);
        System.out.println(users.toString());

        DBConnection.disconnect(con);*/

        /*Connection con = DBConnection.getConnection();
        Project project7 = ProjectDataProvider.insertProject(con, "Project7", Date.valueOf("2025-07-13"));
        System.out.println(project7.toString());
        DBConnection.disconnect(con);*/


        //http://localhost:4567/
        get("/", (req, res) -> {return "<b>Welcome to TMProject</b>";} );
        //http://localhost:4567/TMProject
        get("/TMProject", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ModelAndView modelAndView = new ModelAndView(model, "TMProject");
            return modelAndView;
        }, new JadeTemplateEngine());

    }}
