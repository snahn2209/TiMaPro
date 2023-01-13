import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.*;

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

        //http://localhost:4567/TMProject/Project?id=1
        get("/TMProject/Project", (req, res) -> {

            int projectID = Integer.parseInt(req.queryParams("id"));

            Map<String, Object> model = new HashMap<>();

            Connection con = DBConnection.getConnection();
            Project sectedProject = ProjectDataProvider.selectProject(con, projectID);
            List<Task> tasks = TaskDataProvider.selectAllTasksOfProject(con, projectID);
            List<UserAccount> members = UserDataProvider.selectAllUsersOfProject(con, projectID);
            //sort memebers by points
            members.sort(new Comparator<UserAccount>() {
                @Override
                public int compare(UserAccount o1, UserAccount o2) {
                    if(o1.getTotalPoints() == o2.getTotalPoints()){
                        return 0; //equal
                    }else if(o1.getTotalPoints() > o2.getTotalPoints()){
                        return -1;
                    }else{
                        return 1;
                    }
                }
            });
            DBConnection.disconnect(con);

            model.put("project", sectedProject);
            model.put("tasks", tasks);
            model.put("members", members);

            ModelAndView modelAndView = new ModelAndView(model, "ProjectOverview");
            return modelAndView;
        }, new JadeTemplateEngine());

    }
}
