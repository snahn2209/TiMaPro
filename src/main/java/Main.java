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

        //http://localhost:4567/TMProject/login
        get("/TMProject/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ModelAndView modelAndView = new ModelAndView(model, "LoginForm");
            return modelAndView;
        }, new JadeTemplateEngine());

        //http://localhost:4567/TMProject/register
        get("/TMProject/register", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            ModelAndView modelAndView = new ModelAndView(model, "RegisterForm");
            return modelAndView;
        }, new JadeTemplateEngine());

        post("/TMProject/register", (req, res) -> {
            // Get name from register form
            String userName = req.queryParams("name");

            //method calls
            Connection con = DBConnection.getConnection();
            UserAccount insertedUser = UserDataProvider.insertUser(con,userName);
            DBConnection.disconnect(con);

            //navigate to next page
            Map<String, Object> model = new HashMap<>();
            ModelAndView modelAndView = new ModelAndView(model, "ProjectsView");
            return modelAndView;
        }, new JadeTemplateEngine());

    }}
