import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Date;
import java.util.*;

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

        //TMProject/CheckOfTask?task="+task.id+"&user="+encodedUsername
        post("TMProject/CheckOfTask", (req, res) -> {

            int taskID = Integer.parseInt(req.queryParams("task"));

            //decode url
            URLDecoder.decode(req.url(), StandardCharsets.UTF_8.toString());

            //get current user form url
            String username = req.queryParams("user");
            String encodedUsername = URLEncoder.encode(username, StandardCharsets.UTF_8.toString());

            Connection con = DBConnection.getConnection();
            //mark task as !done in db
            Task updatedTask = TaskDataProvider.checkOffTask(con, taskID);
            //add points to projectuser table
            //add points to total points of user
            DBConnection.disconnect(con);

            //redirect to project overview
            if(updatedTask!=null){
                res.redirect("/TMProject/Project?id="+updatedTask.getProject()+"&user="+encodedUsername);
            }else{
                res.redirect("/TMProject/Projects?username="+encodedUsername);
            }


            return null;
        });
    }
}
