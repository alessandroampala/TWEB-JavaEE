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

    public static void insertTeacher(String name, String surname)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO docente (nome, cognome) values(?, ?);";
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
                    if(st != null)
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
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM docente WHERE id = ?;";
            st = conn.prepareStatement(sql);
            st.setInt(1, teacher.getId());
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
                    if(st != null)
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

    public void insertCourse(String name)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO corso (nome) values(?);";
            st = conn.prepareStatement(sql);
            st.setString(1, name);
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
                    if(st != null)
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
    public void deleteCourse(String course)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM corso WHERE nome = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
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
                    if(st != null)
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

    public void insertLesson(String courseName, int teacherId)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO lezione (corsoID, docenteID) values(?, ?);";
            st = conn.prepareStatement(sql);
            st.setString(1, courseName);
            st.setInt(2, teacherId);
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
                    if(st != null)
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
    public void deleteLesson(String courseName, Teacher teacher)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM lezione WHERE corsoId = ? AND docenteId = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, courseName);
            st.setInt(2, teacher.getId());
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
                    if(st != null)
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

    public void insertBooking(String username, int teacherId, String course, int lessonSlot)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO prenotazione (corsoID, docenteID, utenteId, lessonDate) values(?, ?, ?, ?);";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
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
                    if(st != null)
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

    //same as deleteBooking
    public void cancelBooking(String username, int teacherId, String course, int lessonSlot)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM prenotazione" +
                    " WHERE corsoID = ? AND docenteID = ? AND utenteID = ? AND lessonDate = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
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
                    if(st != null)
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

    //mark booking as done
    public void markBooking(String username, int teacherId, String course, int lessonSlot)
    {
        Connection conn = null;
        PreparedStatement st = null;
        try
        {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "UPDATE prenotazione SET status = 'done'" +
                    " WHERE corsoID = ? AND docenteID = ? AND utenteID = ? AND lessonDate = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
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
                    if(st != null)
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

    /*public List<Booking> getBookings(String username)
    {

    }*/

    //ADMIN
    /*public List<Booking> getAllBookings()
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
    Status status;

    public Booking(String username, int teacherId, String course, int lessonSlot, Status status)
    {
        this.username = username;
        this.teacherId = teacherId;
        this.course = course;
        this.lessonSlot = lessonSlot;
        this.status = status;
    }
}

enum Status
{
    ACTIVE,
    DONE,
    CANCELED,
}