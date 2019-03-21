package mobile.myfitplan.myfitplan;

import java.util.Date;

public class AccUser {
    public int ID;
    public String Email;
    public String DateOfBirth;
    public String Name;
    public int Purpose;
    public String Gender;
    public int TrainingLevel;

    public AccUser() {
    }

    public AccUser(int ID, String email, String dateOfBirth, String name, int purpose, String gender, int trainingLevel) {
        this.ID = ID;
        Email = email;
        DateOfBirth = dateOfBirth;
        Name = name;
        Purpose = purpose;
        Gender = gender;
        TrainingLevel = trainingLevel;
    }
}
