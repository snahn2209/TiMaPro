import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.*;

import java.sql.Date;

import static spark.Spark.*;


public class ProjectController {
    public ProjectController() {

        //Projects Dashboard
        //http://localhost:4567/TMProject/Projects?username=Pia
        get("/TMProject/Projects", (req, res) -> {
            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            //get user name from url
            String userName = req.queryParams("username");

            Map<String, Object> model = new HashMap<>();
            model.put("encodedUsername", URLEncoder.encode(userName, StandardCharsets.UTF_8.toString()));

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


        //Project Overview
        //http://localhost:4567/TMProject/Project?id=1&user=Pia
        get("/TMProject/Project", (req, res) -> {

            int projectID = Integer.parseInt(req.queryParams("id"));
            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());
            //get user name from url
            String userName = req.queryParams("user");

            Map<String, Object> model = new HashMap<>();
            model.put("username", userName);
            model.put("encodedUsername", URLEncoder.encode(userName, StandardCharsets.UTF_8.toString()));

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


        //Add Project Form
        //http://localhost:4567/TMProject/AddProject?user=Pia
        get("/TMProject/AddProject", (req, res) -> {
            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            //get user name from url
            String username = req.queryParams("user");

            Map<String, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("encodedUsername", URLEncoder.encode(username, StandardCharsets.UTF_8.toString()));

            ModelAndView modelAndView = new ModelAndView(model, "AddProjectForm");
            return modelAndView;
        }, new JadeTemplateEngine());

        post("/TMProject/AddProject", (req, res) -> {
            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            //get current user form url
            String username = req.queryParams("user");
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());

            //get values from form
            String projectName = req.queryParams("name");
            Date deadline = Date.valueOf(req.queryParams("deadline"));

            //get list of memberIDs
            List<String> memberNames = new ArrayList<>();
            memberNames.add(username);
            for (int i=1; i<5; i++){
                if(req.queryParams("member"+i)!=null && !req.queryParams("member" + i).equals("")){
                    memberNames.add(req.queryParams("member"+i));
                }
            }
            Connection con = DBConnection.getConnection();
            int[] memberIDs = new int[memberNames.size()];
            for(int i=0; i<memberIDs.length; i++){
                UserAccount user = UserDataProvider.selectUser(con, memberNames.get(i));
                memberIDs[i]=user.getID();
            }

            //insert Project into db
            Project newProject = ProjectDataProvider.insertProject(con, projectName,deadline, memberIDs);

            DBConnection.disconnect(con);

            res.redirect("/TMProject/Projects?username="+encodedUsername);
            return null;
        });

    }
}
