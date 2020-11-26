import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    private static final String url = "jdbc:mysql://localhost:3306/ripetizioni";
    private static final String user = "root";
    private static final String password = "";

    public static void initialize() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void insertTeacher(String name, String surname) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO docente (nome, cognome) values(?, ?);";
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, surname);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //ADMIN
    public static void deleteTeacher(Teacher teacher) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM docente WHERE id = ?;";
            st = conn.prepareStatement(sql);
            st.setInt(1, teacher.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static List<Teacher> getTeachers() {
        Connection conn = null;
        Statement st = null;
        List<Teacher> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM docente;";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                out.add(new Teacher(rs.getInt("id"), rs.getString("nome"), rs.getString("Cognome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return out;
    }

    private static Teacher getTeacher(int docenteID) {
        Connection conn = null;
        PreparedStatement st = null;
        Teacher out = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT nome, cognome FROM docente WHERE id = ?;";
            st = conn.prepareStatement(sql);
            st.setInt(1, docenteID);
            ResultSet rs = st.executeQuery();
            while (rs.next())
                out = new Teacher(docenteID, rs.getString("nome"), rs.getString("cognome"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return out;
    }

    public static void insertCourse(String name) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO corso (nome) values(?);";
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //ADMIN
    public static void deleteCourse(String course) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM corso WHERE nome = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static List<Course> getCourses() {
        Connection conn = null;
        Statement st = null;
        List<Course> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM corso;";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                out.add(new Course(rs.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return out;
    }

    public static void insertLesson(String courseName, int teacherId) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO lezione (corsoID, docenteID) values(?, ?);";
            st = conn.prepareStatement(sql);
            st.setString(1, courseName);
            st.setInt(2, teacherId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //ADMIN
    public static void deleteLesson(String courseName, Teacher teacher) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM lezione WHERE corsoId = ? AND docenteId = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, courseName);
            st.setInt(2, teacher.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static List<Lesson> getLessons() {
        Connection conn = null;
        Statement st = null;
        List<Lesson> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM lezione;";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                out.add(new Lesson(getTeacher(rs.getInt("docenteID")), new Course(rs.getString("corsoID"))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return out;
    }

    public static void insertBooking(String username, int teacherId, String course, int lessonSlot) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO prenotazione (corsoID, docenteID, utenteId, lessonDate) values(?, ?, ?, ?);";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //same as deleteBooking
    public static void cancelBooking(String username, int teacherId, String course, int lessonSlot) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM prenotazione" +
                    " WHERE corsoID = ? AND docenteID = ? AND utenteID = ? AND lessonDate = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    //mark booking as done
    public static void markBooking(String username, int teacherId, String course, int lessonSlot) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "START TRANSACTION;" +
                    " UPDATE prenotazione" +
                    " SET status = 'done'" +
                    " WHERE corsoID = ? AND docenteID = ? AND utenteID = ? AND lessonDate = ?;" +
                    " DELETE FROM prenotazione where corsoID = ? AND docenteID = ? AND utenteID = ? AND lessonDate = ?;" +
                    " COMMIT;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
            st.setString(5, course);
            st.setInt(6, teacherId);
            st.setString(7, username);
            st.setInt(8, lessonSlot);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static List<Booking> getBookings(String username) {
        Connection conn = null;
        PreparedStatement st = null;
        List<Booking> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM prenotazione WHERE utenteID = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            while (rs.next())
                out.add(new Booking(rs.getString("utenteID"), rs.getInt("docenteID"),
                        rs.getString("corsoID"), rs.getInt("lessonDate"),
                        Status.fromString(rs.getString("status"))));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return out;
    }

    //ADMIN
    public static List<Booking> getAllBookings() {
        Connection conn = null;
        Statement st = null;
        List<Booking> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM prenotazione;";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next())
                out.add(new Booking(rs.getString("utenteID"), rs.getInt("docenteID"),
                        rs.getString("corsoID"), rs.getInt("lessonDate"),
                        Status.fromString(rs.getString("status"))));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        return out;
    }

    public static jsonMessage getUser(String username, String userPass) {
        Connection conn = null;
        PreparedStatement st = null;
        User out = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT admin FROM utente WHERE username = ? and BINARY password = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, userPass);
            ResultSet rs = st.executeQuery();
            while (rs.next())
                out = new User(username, userPass, rs.getBoolean("admin"));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }
        if(out == null)
            return new jsonMessage<User>("Username e/o Password sbagliati", out);
        return new jsonMessage<User>("Ok", out);
    }
}

class User {
    private String username;
    private String password;
    private boolean admin;

    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean getAdmin() {
        return this.admin;
    }

    public void setPassword(String password){
        this.password=User.this.password;
    }
}

class Course{
    private String name;

    public Course(String name){
        this.name=name;
    }

    public String getName(){
        return this.name;
    }
}

class Teacher {
    private int id;
    private String name;
    private String surname;

    public Teacher(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}

class Lesson {
    private Teacher teacher;
    private Course course;

    public Lesson(Teacher teacher, Course course) {
        this.teacher = teacher;
        this.course = course;
    }

    public Teacher getTeacher() {
        return this.teacher;
    }

    public Course getCourse() {
        return this.course;
    }
}

class Booking {
    public String username;
    int teacherId;
    String course;
    int lessonSlot;
    Status status;

    public Booking(String username, int teacherId, String course, int lessonSlot, Status status) {
        this.username = username;
        this.teacherId = teacherId;
        this.course = course;
        this.lessonSlot = lessonSlot;
        this.status = status;
    }
}

class jsonMessage<T>{
    private String message;
    private T data;

    public jsonMessage(String message, T data){
        this.message=message;
        this.data = data;
    }

    public String getMessage(){
        return this.message;
    }

    public T getData(){
        return this.data;
    }
}

enum Status {
    ACTIVE,
    DONE,
    CANCELED;

    public static Status fromString(String status) {
        switch (status) {
            case "active":
                return Status.ACTIVE;
            case "done":
                return Status.DONE;
            case "canceled":
                return Status.CANCELED;
        }
        return null;
    }
}