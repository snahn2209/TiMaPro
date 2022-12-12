import java.util.Date;

public class Project {

    private int ID;
    private String name;
    private Date deadline;

    public Project(int ID, String name, Date deadline) {
        this.ID = ID;
        this.name = name;
        this.deadline = deadline;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    @Override
    public String toString(){
        return "ProjectID: "+ ID +" | Name: "+ name + " | deadline: "+ deadline;
    }
}
