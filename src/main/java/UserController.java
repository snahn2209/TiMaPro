import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.net.URLEncoder;

import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

public class UserController {
    public UserController() {

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
            //get name from register form
            String userName = req.queryParams("name");

            Connection con = DBConnection.getConnection();
            try{
                // insert user into db
                UserAccount insertedUser = UserDataProvider.insertUser(con,userName);
                if(insertedUser!=null){
                    //redirect to ProjectsDashboard
                    //encode ursername
                    String encodedUserName = URLEncoder.encode(insertedUser.getName(), StandardCharsets.UTF_8.toString());
                    res.redirect("/TMProject/Projects?username="+encodedUserName);
                }else {
                    //if user already exists -> redirect to login
                    res.redirect("/TMProject/login");
                }

            }catch (SQLIntegrityConstraintViolationException e){
                res.status(500);
            }finally {
                DBConnection.disconnect(con);
            }

            return null;
        });

        post("/TMProject/login", (req, res) -> {
            //get name from login form
            String userName = req.queryParams("name");

            //check if user exists
            Connection con = DBConnection.getConnection();
            UserAccount existingUser = UserDataProvider.selectUser(con, userName);
            DBConnection.disconnect(con);

            if(existingUser!=null){
                String encodedUserName = URLEncoder.encode(userName, StandardCharsets.UTF_8.toString());;
                res.redirect("/TMProject/Projects?username="+encodedUserName);
            }else {
                //user doesn't already exist
                res.redirect("/TMProject/register");
            }
            return null;
        });


    }
}
