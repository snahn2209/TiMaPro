import java.sql.Timestamp;
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
        private UserAccount responsiblePerson;
        private Project project;

    public Task(int id, String name, Date deadline, double timeEstimation, int priority, boolean done, Date gotDoneDate, int maxPoints, UserAccount responsiblePerson, Project project) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.timeEstimation = timeEstimation;
        this.priority = priority;
        this.done = done;
        this.gotDoneDate = gotDoneDate;
        this.maxPoints = maxPoints;
        this.responsiblePerson = responsiblePerson;
        this.project = project;
    }

    public Task(int id, String name, Date deadline, double timeEstimation, int priority, boolean done, Date gotDoneDate, int maxPoints) {
        this.id = id;
        this.name = name;
        this.deadline = deadline;
        this.timeEstimation = timeEstimation;
        this.priority = priority;
        this.done = done;
        this.gotDoneDate = gotDoneDate;
        this.maxPoints = maxPoints;
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

    public UserAccount getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(UserAccount responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {return "taskId: " +id+ " | name: " + name + " | deadline: "+deadline; }
}
