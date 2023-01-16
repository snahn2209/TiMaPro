import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.*;

import java.sql.Date;

import static spark.Spark.*;

public class TaskController {
    public TaskController(){

        //http://localhost:4567/TMProject/Task?id=1
        get("/TMProject/Task", (req, res) -> {
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            int taskId = Integer.parseInt(req.queryParams("id"));

            Map<String, Object> model = new HashMap<>();

            Connection con = DBConnection.getConnection();
            Task selectedTask = TaskDataProvider.selectTask(con, taskId);

            DBConnection.disconnect(con);

            model.put("task", selectedTask);

            ModelAndView modelAndView = new ModelAndView(model, "TaskOverview");
            return modelAndView;
        }, new JadeTemplateEngine());

        //addTask form
        get("/TMProject/AddTask", (req, res) -> {
            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            //get ProjectID from url ?
            int projectid = Integer.parseInt(req.queryParams("id"));

            Map<String, Object> model = new HashMap<>();
            model.put("id", projectid);

            ModelAndView modelAndView = new ModelAndView(model, "AddTaskForm");
            return modelAndView;

        }, new JadeTemplateEngine());

        post("/TMProject/AddTAsk", (req, res) ->  {
            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            //get current project form url
            int projectid = Integer.parseInt(req.queryParams("id"));
            String username = req.queryParams("user");
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());


            //get values from form
            String taskName = req.queryParams("name");
            Date deadline = Date.valueOf(req.queryParams("deadline"));
            double timeEstimation = Double.parseDouble(req.queryParams("timeEstimation"));
            int prio = Integer.parseInt(req.queryParams("prio"));
            int responsibleUserID = Integer.parseInt(req.queryParams("responsibleuserid"));

            //insertTask
            Connection con = DBConnection.getConnection();
            Task newTask = TaskDataProvider.insertTask(con, taskName, deadline, timeEstimation, prio, responsibleUserID, projectid);

            DBConnection.disconnect(con);

            res.redirect("/TMProject/Project?id="+projectid+"&user="+encodedUsername);


            return null;


            });
        }
    }



