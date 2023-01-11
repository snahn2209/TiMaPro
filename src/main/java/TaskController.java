import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
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
    }
}
