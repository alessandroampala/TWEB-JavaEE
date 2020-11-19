import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao
{
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "";
    private static Connection connection = null;

    public static void initialize()
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(url, user, password);
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
    }

    public void insertUser(User user)
    {
        //hash password

    }

    public void insertTeacher(Teacher teacher)
    {

    }

    public void insertCourse(String name)
    {

    }

    public void insertLesson(String courseName, Teacher teacher)
    {

    }

    public void insertBooking(String username, int teacherId, String course, int lessonSlot)
    {

    }

    public void dismissBooking(String username, int teacherId, String course, int lessonSlot)
    {

    }

    public void deleteUser(User user)
    {

    }

    public void deleteTeacher(Teacher teacher)
    {

    }

    public void deleteCourse(String course)
    {

    }


}

class User
{
    public String username;
    public String password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}

class Teacher
{
    public int id;
    public String name;
    public String surname;

    public Teacher(int id, String name, String surname)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}

class Booking
{
    public String username;
    int teacherId;
    String course;
    int lessonSlot;

    public Booking(String username, int teacherId, String course, int lessonSlot)
    {
        this.username = username;
        this.teacherId = teacherId;
        this.course = course;
        this.lessonSlot = lessonSlot;
    }
}