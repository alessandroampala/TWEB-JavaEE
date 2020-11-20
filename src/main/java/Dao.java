import java.awt.print.Book;
import java.sql.*;
import java.util.List;

public class Dao
{
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "";

    public static void initialize()
    {
        try
        {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void insertUser(User user)
    {
        //hash password

    }

    public void deleteUser(User user)
    {

    }

    public static void insertTeacher(String name, String surname)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO docente (nome, cognome) values(?, ?)";
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, surname);
            st.executeUpdate();
        }catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        finally
        {
            if(conn != null)
            {
                try
                {
                    st.close();
                    conn.close();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //ADMIN
    public void deleteTeacher(Teacher teacher)
    {

    }

    public void insertCourse(String name)
    {

    }

    //ADMIN
    public void deleteCourse(String course)
    {

    }

    public void insertLesson(String courseName, Teacher teacher)
    {

    }

    //ADMIN
    public void deleteLesson(String courseName, Teacher teacher)
    {

    }

    public void insertBooking(String username, int teacherId, String course, int lessonSlot)
    {

    }

    public void cancelBooking(String username, int teacherId, String course, int lessonSlot)
    {

    }

    //mark booking as done
    public void markBooking(String username, int teacherId, String course, int lessonSlot)
    {

    }

    /*public List<Booking> getBookings(String username)
    {

    }

    //ADMIN
    public List<Booking> getAllBookings()
    {

    }*/

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
    private int id;
    private String name;
    private String surname;

    public Teacher(int id, String name, String surname)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getSurname()
    {
        return surname;
    }
}

class Booking
{
    public String username;
    int teacherId;
    String course;
    int lessonSlot;
    //Enum active/done/dismissed/

    public Booking(String username, int teacherId, String course, int lessonSlot)
    {
        this.username = username;
        this.teacherId = teacherId;
        this.course = course;
        this.lessonSlot = lessonSlot;
    }
}