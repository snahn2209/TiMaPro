import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;


public class ProjectController {
    public ProjectController() {

        //http://localhost:4567/TMProject/Projects?username=Pia
        get("/TMProject/Projects", (req, res) -> {
            String userName = req.queryParams("username");

            Map<String, Object> model = new HashMap<>();

            if(userName!=null){
                Connection con = DBConnection.getConnection();

                UserAccount currentUser = UserDataProvider.selectUser(con, userName);
                DBConnection.disconnect(con);

                model.put("name", currentUser.getName());
                model.put("totalPoints", currentUser.getTotalPoints());

                //TODO: select alle projects of user
                //TODO: error handling wenn user nicht existiert oder username null ist
            }

            ModelAndView modelAndView = new ModelAndView(model, "ProjectsDashboard");
            return modelAndView;
        }, new JadeTemplateEngine());

    }
}
