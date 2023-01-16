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

            DBConnection.disconnect(con);

            model.put("task", selectedTask);

            ModelAndView modelAndView = new ModelAndView(model, "TaskOverview");
            return modelAndView;
        }, new JadeTemplateEngine());

        // addTask form
        //http://localhost:4567/TMProject/AddTask?project=1&user=Pia
        get("/TMProject/AddTask", (req, res) -> {
            // get ProjectID from url
            int projectID = Integer.parseInt(req.queryParams("project"));

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

        post("/TMProject/AddTAsk", (req, res) -> {
            // decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            // get current project form url
            int projectid = Integer.parseInt(req.queryParams("id"));
            String username = req.queryParams("user");
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());

            // get values from form
            String taskName = req.queryParams("name");
            Date deadline = Date.valueOf(req.queryParams("deadline"));
            double timeEstimation = Double.parseDouble(req.queryParams("timeEstimation"));
            int prio = Integer.parseInt(req.queryParams("prio"));
            int responsibleUserID = Integer.parseInt(req.queryParams("responsibleuserid"));

            // insertTask
            Connection con = DBConnection.getConnection();
            Task newTask = TaskDataProvider.insertTask(con, taskName, deadline, timeEstimation, prio, responsibleUserID,
                    projectid);

            DBConnection.disconnect(con);

            res.redirect("/TMProject/Project?id=" + projectid + "&user=" + encodedUsername);

            return null;

        });

        // TMProject/CheckOfTask?task="+task.id+"&user="+encodedUsername
        post("TMProject/CheckOfTask", (req, res) -> {

            int taskID = Integer.parseInt(req.queryParams("task"));

            // decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());
            // get current user form url
            String username = req.queryParams("user");
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());

            Connection con = DBConnection.getConnection();

            UserAccount currentUser = UserDataProvider.selectUser(con, username);
            int currentUserID = currentUser.getID();

            // mark task as !done in db
            Task updatedTask = TaskDataProvider.checkOffTask(con, taskID);

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
            DBConnection.disconnect(con);

            // redirect to project overview
            if (updatedTask != null) {
                res.redirect("/TMProject/Project?id=" + updatedTask.getProject() + "&user=" + encodedUsername);
            } else {
                res.redirect("/TMProject/Projects?username=" + encodedUsername);
            }
            return null;
        });
    }
}
