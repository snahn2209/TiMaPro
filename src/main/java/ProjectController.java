import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;


public class ProjectController {
    public ProjectController() {

        //http://localhost:4567/TMProject/Projects?username=Pia
        get("/TMProject/Projects", (req, res) -> {
            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            //get user name from url
            String userName = req.queryParams("username");

            Map<String, Object> model = new HashMap<>();

            if(userName!=null){
                Connection con = DBConnection.getConnection();
                UserAccount currentUser = UserDataProvider.selectUser(con, userName);
                DBConnection.disconnect(con);

                if(currentUser!=null){
                    //user exists
                    model.put("name", currentUser.getName());
                    model.put("totalPoints", currentUser.getTotalPoints());

                    //select alle projects of user
                    con = DBConnection.getConnection();
                    List<Project> listOfProjects = ProjectDataProvider.selectAllProjectsOfUser(con, userName);
                    DBConnection.disconnect(con);
                    model.put("projects", listOfProjects);

                }else{
                    //user doesn't exist -> login
                    res.redirect("/TMProject/login");
                    return null;
                }
            }else{
                //username is null -> login
                res.redirect("/TMProject/login");
                return null;
            }

            ModelAndView modelAndView = new ModelAndView(model, "ProjectsDashboard");
            return modelAndView;
        }, new JadeTemplateEngine());

    }
}
