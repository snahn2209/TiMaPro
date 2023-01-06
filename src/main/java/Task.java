import java.util.Date;

public class Task {
    private int id;
    private String name;
    private Date deadline;
    private double timeEstimation; //in hours
    private int priority; //1-10
    private boolean done;
    private Date gotDoneDate;
    private int maxPoints;
    private int responsiblePersonId;
    private int project;


    public Task(int id, String name, Date deadline, double timeEstimation, int priority, boolean done, Date gotDoneDate, int maxPoints, int responsiblePersonId, int project) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.timeEstimation = timeEstimation;
        this.priority = priority;
        this.done = done;
        this.gotDoneDate = gotDoneDate;
        this.maxPoints = maxPoints;
        this.responsiblePersonId = responsiblePersonId;
        this.project = project;
    }

    /*private Task(int id, String name, Date deadline, double timeEstimation, int priority, boolean done, Date gotDoneDate, int maxPoints) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.timeEstimation = timeEstimation;
        this.priority = priority;
        this.done = done;
        this.gotDoneDate = gotDoneDate;
        this.maxPoints = maxPoints;
    }*/

    public Task(double timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public double getTimeEstimation() {
        return timeEstimation;
    }

    public void setTimeEstimation(double timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getGotDoneDate() {
        return gotDoneDate;
    }

    public void setGotDoneDate(Date gotDoneDate) {
        this.gotDoneDate = gotDoneDate;
    }

    public int getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(int maxPoints) {
        this.maxPoints = maxPoints;
    }

    public int getResponsiblePersonId() {
        return responsiblePersonId;
    }

    public void setResponsiblePersonId(int responsiblePersonId) {
        this.responsiblePersonId = responsiblePersonId;
    }

    public int getProject() {
        return project;
    }

    public void setProject(int project) {
        this.project = project;
    }


    public static class TaskBuilder{
        private int id;
        private String name;
        private Date deadline;
        private double timeEstimation; //in hours
        private int priority; //1-10
        private boolean done;
        private Date gotDoneDate;
        private int maxPoints;
        private int responsiblePersonId;
        private int project;

        public TaskBuilder setId (int id ){
            this.id = id;
            return this;
        }

        public TaskBuilder setName (String name){
            this.name = name;
            return this;
        }

        public TaskBuilder setDeadline (Date deadline){
            this.deadline = deadline;
            return this;
        }

        public TaskBuilder setTimeEstimation (double timeEstimation){
            this.timeEstimation = timeEstimation;
            return this;
        }

        public TaskBuilder setPriority (int priority){
            this.priority = priority;
            return this;
        }

        public TaskBuilder setDone (boolean done){
            this.done = done;
            return this;
        }


        public TaskBuilder setMaxPoints (int maxPoints){
            this.maxPoints = maxPoints;
            return this;
        }

        public TaskBuilder setResponsiblePersonId (int responsiblePersonId){
            this.responsiblePersonId = responsiblePersonId;
            return this;
        }

        public TaskBuilder setProject (int project){
            this.project = project;
            return this;
        }

        public Task createTask(){
            return new Task(id, name, deadline,timeEstimation,priority,done,gotDoneDate,maxPoints,responsiblePersonId,project);


        }
    }
    @Override
    public String toString() {return "taskId: " +id+ " | name: " + name + " | deadline: "+deadline; }

    public static int calculateMaxPoints(double timeEstimation){
        //10 min -> 1 point
        int timeEstimationInMin = (int) Math.round(timeEstimation * 60);

        int maxPoints = timeEstimationInMin / 10;

        return maxPoints;
    }


}
