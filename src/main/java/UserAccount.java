public class UserAccount {
    private int ID;
    private String name;
    private int totalPoints;

    public UserAccount(int ID, String name, int totalPoints) {
        this.ID = ID;
        this.name = name;
        this.totalPoints = totalPoints;
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

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString(){
        return "UserID: "+ ID +" | UserName: "+ name + " | totalPoints: "+ totalPoints;
    }
}
