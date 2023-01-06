import java.util.Date;

public class Project {

    private int ID;
    private String name;
    private Date deadline;

    private Project(int ID, String name, Date deadline) {
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

    public static class ProjectBuilder{
        private int ID;
        private String name;
        private Date deadline;


        public ProjectBuilder setID (int ID) {
            this.ID = ID;
            return this;
        };

        public ProjectBuilder setName (String name) {
            this.name = name;
            return this;
        };

        public ProjectBuilder setDeadline (Date deadline) {
            this.deadline = deadline;
            return this;
        };

        public Project createProject(){
            return new Project(ID, name, deadline);
        }
    }

    @Override
    public String toString(){
        return "ProjectID: "+ ID +" | name: "+ name + " | deadline: "+ deadline;
    }
}
