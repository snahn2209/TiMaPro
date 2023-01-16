import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;

import java.sql.Date;

import static spark.Spark.*;

public class TaskController {
    public TaskController() {
        
        //Task Overview
        // http://localhost:4567/TMProject/Task?id=1&user=Pia
        get("/TMProject/Task", (req, res) -> {
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            int taskId = Integer.parseInt(req.queryParams("id"));

            // decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());
            // get current user form url
            String username = req.queryParams("user");

            Map<String, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("encodedUsername", URLEncoder.encode(username, StandardCharsets.UTF_8.toString()));

            Connection con = DBConnection.getConnection();
            Task selectedTask = TaskDataProvider.selectTask(con, taskId);
            int responsiblePersonID = selectedTask.getResponsiblePersonId();
            UserAccount responsibleUser = UserDataProvider.selectUserByID(con, responsiblePersonID);
            DBConnection.disconnect(con);

            model.put("task", selectedTask);
            if(responsibleUser!=null){
                model.put("responsiblePerson", responsibleUser.getName());
            }else{
                model.put("responsiblePerson", "-");
            }

            ModelAndView modelAndView = new ModelAndView(model, "TaskOverview");
            return modelAndView;
        }, new JadeTemplateEngine());

        // addTask form
        //http://localhost:4567/TMProject/AddTask?id=1&user=Pia
        get("/TMProject/AddTask", (req, res) -> {
            // get ProjectID from url
            int projectID = Integer.parseInt(req.queryParams("id"));

            // decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());
            String username = req.queryParams("user");

            Map<String, Object> model = new HashMap<>();
            model.put("projectID", projectID);
            model.put("username", username);
            model.put("encodedUsername", URLEncoder.encode(username, StandardCharsets.UTF_8.toString()));


            ModelAndView modelAndView = new ModelAndView(model, "AddTaskForm");
            return modelAndView;

        }, new JadeTemplateEngine());

        post("/TMProject/AddTask", (req, res) -> {
            // get params from url
            int projectID = Integer.parseInt(req.queryParams("id"));

            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());
            String username = req.queryParams("user");
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());

            // get values from form
            String taskName = req.queryParams("name");
            Date deadline = Date.valueOf(req.queryParams("deadline"));
            double timeEstimation = Double.parseDouble(req.queryParams("time"));
            int prio = Integer.parseInt(req.queryParams("prio"));
            String responsibleUser = req.queryParams("responsiblePerson");

            // insertTask
            Connection con = DBConnection.getConnection();
            // check if responsible person is part of project
            boolean responsiblePersonIsPartOfProject=false;
            if(!responsibleUser.equals("")){
                UserAccount user = UserDataProvider.selectUser(con, responsibleUser);
                if(user!=null){
                    List<UserAccount> team = UserDataProvider.selectAllUsersOfProject(con, projectID);
                    for (UserAccount member : team) {
                        if(member.getID() == user.getID()){
                            responsiblePersonIsPartOfProject=true;
                            break;
                        }
                    }
                }
            }

            if(responsiblePersonIsPartOfProject){
                //resposible user assigned
                UserAccount user = UserDataProvider.selectUser(con, responsibleUser);
                Task newTask = TaskDataProvider.insertTask(con, taskName, deadline, timeEstimation, prio, user.getID(),projectID);
            }else{
                //no resposible uer assigned
                Task newTask = TaskDataProvider.insertTask(con, taskName, deadline, timeEstimation, prio,null,projectID);
            }

            DBConnection.disconnect(con);

            res.redirect("/TMProject/Project?id="+projectID+"&user="+encodedUsername);
            return null;
        });

        post("/TMProject/CheckOfTask", (req, res) -> {
            int taskID = Integer.parseInt(req.queryParams("task"));

            // decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());
            // get current user form url
            String username = req.queryParams("user");
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());

            Connection con = DBConnection.getConnection();

            UserAccount currentUser = UserDataProvider.selectUser(con, username);
            int currentUserID = currentUser.getID();

            //check if user is responsible for this task
            Task task = TaskDataProvider.selectTask(con,taskID);

            boolean userIsResponsible = true; //if no user is responsible -> everybody is responsible
            if(task.getResponsiblePersonId() != 0){
                userIsResponsible=false; //only one user is responsible
                if(task.getResponsiblePersonId()==currentUserID){
                    userIsResponsible = true;//current user is responsible
                }
            }

            Task updatedTask = TaskDataProvider.selectTask(con,taskID);
            if(userIsResponsible){
                // mark task as !done in db
                updatedTask = TaskDataProvider.checkOffTask(con, taskID);

                if (updatedTask != null && updatedTask.isDone()) {
                    // earning points
                    // add points to projectuser table
                    ProjectDataProvider.earnProjectPoints(con, currentUserID, updatedTask.getProject(),
                            updatedTask.getMaxPoints());
                    // add points to total points of user
                    UserDataProvider.earnPoints(con, currentUserID, updatedTask.getMaxPoints());
                } else if (updatedTask != null && !updatedTask.isDone()) {
                    // loosing points
                    // removing points from projectuser table
                    ProjectDataProvider.earnProjectPoints(con, currentUserID, updatedTask.getProject(),
                            -updatedTask.getMaxPoints());
                    // removing points from users total points
                    UserDataProvider.earnPoints(con, currentUserID, -updatedTask.getMaxPoints());
                }
            }

            DBConnection.disconnect(con);

            // redirect to project overview
            if (updatedTask != null) {
                res.redirect("/TMProject/Project?id=" + updatedTask.getProject() + "&user=" + encodedUsername);
            }
            return null;
        });
    }
}
