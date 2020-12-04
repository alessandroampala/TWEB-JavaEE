import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    private static final String url = "jdbc:mysql://localhost:3306/ripetizioni";
    private static final String user = "root";
    private static final String password = "";
    private static boolean initialized = false;

    public static void initialize() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            initialized = true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static boolean isNotInitialized() {
        return !initialized;
    }

    public static jsonMessage<Object> insertTeacher(String name, String surname) {
        Connection conn = null;
        PreparedStatement st = null;
        jsonMessage<Object> message = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO docente (nome, cognome) values(?, ?);";
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, surname);
            st.executeUpdate();
            message = new jsonMessage<>("OK", null);
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
        if (message == null)
            return new jsonMessage<>("DB error", null);
        return message;
    }

    //ADMIN
    public static jsonMessage<Object> deleteTeacher(int teacherId) {
        Connection conn = null;
        PreparedStatement st = null;
        jsonMessage<Object> message = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM docente WHERE id = ?;";
            st = conn.prepareStatement(sql);
            st.setInt(1, teacherId);
            st.executeUpdate();
            message = new jsonMessage<>("OK", null);
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
        if (message == null)
            return new jsonMessage<>("DB error", null);
        return message;
    }

    public static jsonMessage<List<Teacher>> getTeachers() {
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
            return new jsonMessage<>("OK", out);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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

    public static jsonMessage<Object> insertCourse(String name) {
        Connection conn = null;
        PreparedStatement st = null;
        jsonMessage<Object> message = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO corso (nome) values(?);";
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            st.executeUpdate();
            message = new jsonMessage<>("OK", null);
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
        if (message == null)
            return new jsonMessage<>("DB error", null);
        return message;
    }

    //ADMIN
    public static jsonMessage<Object> deleteCourse(String course) {
        Connection conn = null;
        PreparedStatement st = null;
        jsonMessage<Object> message = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM corso WHERE nome = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.executeUpdate();
            message = new jsonMessage<>("OK", null);
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
        if (message == null)
            return new jsonMessage<>("DB error", null);
        return message;
    }

    public static jsonMessage<List<Course>> getCourses() {
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
            return new jsonMessage<>("OK", out);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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

    public static jsonMessage<Object> insertLesson(String courseName, int teacherId) {
        Connection conn = null;
        PreparedStatement st = null;
        jsonMessage<Object> message = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO lezione (corsoID, docenteID) values(?, ?);";
            st = conn.prepareStatement(sql);
            st.setString(1, courseName);
            st.setInt(2, teacherId);
            st.executeUpdate();
            message = new jsonMessage<>("OK", null);
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
        if (message == null)
            return new jsonMessage<>("Errore, il docente insegna già la materia?", null);
        return message;
    }

    //ADMIN
    public static jsonMessage<Object> deleteLesson(String courseName, int teacherId) {
        Connection conn = null;
        PreparedStatement st = null;
        jsonMessage<Object> message;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "DELETE FROM lezione WHERE corsoId = ? AND docenteId = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, courseName);
            st.setInt(2, teacherId);
            st.executeUpdate();
            message = new jsonMessage<>("OK", null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", null);
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
        if (message == null)
            return new jsonMessage<>("DB error", null);
        return message;
    }

    public static jsonMessage<List<Lesson>> getLessons(String course, String teacherId) {
        Connection conn = null;
        PreparedStatement st = null;
        List<Lesson> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM lezione";

            if (!course.equals("") && !teacherId.equals("")) {
                sql += " WHERE corsoId = ? AND docenteId = ?;";
                st = conn.prepareStatement(sql);
                st.setString(1, course);
                st.setInt(2, Integer.parseInt(teacherId));
            } else if (!course.equals("")) {
                sql += " WHERE corsoId = ?;";
                st = conn.prepareStatement(sql);
                st.setString(1, course);
            } else if (!teacherId.equals("")) {
                sql += " WHERE docenteId = ?;";
                st = conn.prepareStatement(sql);
                st.setInt(1, Integer.parseInt(teacherId));
            } else {
                sql += ";";
                st = conn.prepareStatement(sql);
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                out.add(new Lesson(getTeacher(rs.getInt("docenteID")), new Course(rs.getString("corsoID"))));
            }
            return new jsonMessage<>("OK", out);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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

    public static jsonMessage<Object> insertBookings(int[] lessonSlots, String username, int teacherId, String course) {
        Connection conn = null;
        PreparedStatement st = null;
        jsonMessage<Object> message;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO prenotazione (corsoID, docenteID, utenteId, lessonDate) values(?, ?, ?, ?);";
            conn.setAutoCommit(false);

            for (int lessonSlot : lessonSlots) {
                st = conn.prepareStatement(sql);
                st.setString(1, course);
                st.setInt(2, teacherId);
                st.setString(3, username);
                st.setInt(4, lessonSlot);
                st.executeUpdate();
            }
            conn.commit();
            message = new jsonMessage<>("OK", null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            message = new jsonMessage<>("Lezione già prenotata/non sei disponibile in questo orario/il docente non è disponibile in questo orario", null);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    if (st != null)
                        st.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }

        if (message == null)
            return new jsonMessage<>("DB error", null);
        return message;
    }

    //same as deleteBooking
    public static jsonMessage<Object> cancelBooking(String username, int teacherId, String course, int lessonSlot) {
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
            System.out.println("cancelBooking rows: " + st.getUpdateCount());

            if (st.getUpdateCount() == 0)
                return new jsonMessage<>("Ripetizione non trovata, l'hai già effettuata/disdetta?", null);
            return new jsonMessage<>("OK", null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("DB error", null);
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
    public static jsonMessage<Object> markBooking(String username, int teacherId, String course, int lessonSlot) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = " UPDATE prenotazione" +
                    " SET status = 'done'" +
                    " WHERE corsoID = ? AND docenteID = ? AND utenteID = ? AND lessonDate = ?;";
            conn.setAutoCommit(false);
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
            st.executeUpdate();

            if (st.getUpdateCount() == 0)
                return new jsonMessage<>("Ripetizione non trovata, l'hai già effettuata/disdetta?", null);


            sql = " DELETE FROM prenotazione where corsoID = ? AND docenteID = ? AND utenteID = ? AND lessonDate = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            st.setString(3, username);
            st.setInt(4, lessonSlot);
            st.executeUpdate();
            System.out.println("deleted row: " + st.getUpdateCount());

            conn.commit();
            return new jsonMessage<>("OK", null);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return new jsonMessage<>("DB error", null);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
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

    public static jsonMessage<List<Booking>> getTeacherBookings(int teacherId) {
        Connection conn = null;
        PreparedStatement st = null;
        List<Booking> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM prenotazione WHERE docenteID = ?;";
            st = conn.prepareStatement(sql);
            st.setInt(1, teacherId);
            ResultSet rs = st.executeQuery();
            while (rs.next())
                out.add(new Booking(rs.getString("utenteID"), rs.getInt("docenteID"),
                        rs.getString("corsoID"), rs.getInt("lessonDate"),
                        Status.fromString(rs.getString("status"))));
            return new jsonMessage<>("OK", out);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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

    public static jsonMessage<List<Booking>> getTeacherCourseBookings(String course, int teacherId) {
        Connection conn = null;
        PreparedStatement st = null;
        List<Booking> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM prenotazione WHERE corsoID = ? AND docenteID = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, course);
            st.setInt(2, teacherId);
            ResultSet rs = st.executeQuery();
            while (rs.next())
                out.add(new Booking(rs.getString("utenteID"), rs.getInt("docenteID"),
                        rs.getString("corsoID"), rs.getInt("lessonDate"),
                        Status.fromString(rs.getString("status"))));
            return new jsonMessage<>("OK", out);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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

    public static jsonMessage<List<Booking>> getUserBookings(String username, boolean isAndroid) {
        Connection conn = null;
        PreparedStatement st = null;
        List<Booking> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM prenotazione WHERE utenteID = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (isAndroid) {
                while (rs.next())
                    out.add(new Booking(rs.getString("utenteID"), getTeacher(rs.getInt("docenteID")),
                            rs.getString("corsoID"), rs.getInt("lessonDate"),
                            Status.fromString(rs.getString("status"))));
            } else {
                while (rs.next())
                    out.add(new Booking(rs.getString("utenteID"), rs.getInt("docenteID"),
                            rs.getString("corsoID"), rs.getInt("lessonDate"),
                            Status.fromString(rs.getString("status"))));
            }
            return new jsonMessage<>("OK", out);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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

    public static jsonMessage<List<Booking>> getOldUserBookings(String username) {
        Connection conn = null;
        PreparedStatement st = null;
        List<Booking> out = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM archivioPrenotazione WHERE utenteID = ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();

            while (rs.next())
                out.add(new Booking(rs.getString("utenteID"), new Teacher(-1, rs.getString("nome"), rs.getString("cognome")),
                        rs.getString("corsoID"), rs.getInt("lessonDate"),
                        Status.fromString(rs.getString("status"))));
            return new jsonMessage<>("OK", out);
        } catch (
                SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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
    public static jsonMessage<BothBookings> getAllBookings() {
        Connection conn = null;
        Statement st = null;
        List<Booking> out = new ArrayList<>();
        List<Booking> out2 = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT * FROM prenotazione;";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                out.add(new Booking(rs.getString("utenteID"), rs.getInt("docenteID"),
                        rs.getString("corsoID"), rs.getInt("lessonDate"),
                        Status.fromString(rs.getString("status"))));
            }

            sql = "SELECT * FROM archivioPrenotazione";
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                out2.add(new Booking(rs.getString("utenteID"), new Teacher(-1, rs.getString("nome"), rs.getString("cognome")),
                        rs.getString("corsoID"), rs.getInt("lessonDate"),
                        Status.fromString(rs.getString("status"))));
            }

            return new jsonMessage<>("OK", new BothBookings(out2, out));
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", null);
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

    public static jsonMessage<User> getUser(String username, String userPass) {
        Connection conn = null;
        PreparedStatement st = null;
        User out = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            String sql = "SELECT admin FROM utente WHERE username = BINARY ? and password = BINARY ?;";
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, userPass);
            ResultSet rs = st.executeQuery();
            while (rs.next())
                out = new User(username, rs.getBoolean("admin"));
            if (out == null)
                return new jsonMessage<>("Username e/o Password sbagliati", out);
            return new jsonMessage<>("OK", out);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return new jsonMessage<>("ERROR", out);
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
}

class User {
    private final String username;
    private final boolean admin;

    public User(String username, boolean admin) {
        this.username = username;
        this.admin = admin;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean getAdmin() {
        return this.admin;
    }
}

class Course {
    private final String name;

    public Course(String name) {
        this.name = name;
    }

}

class Teacher {
    private final int id;
    private final String name;
    private final String surname;

    public Teacher(int id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

}

class Lesson {
    private final Teacher teacher;
    private final Course course;

    public Lesson(Teacher teacher, Course course) {
        this.teacher = teacher;
        this.course = course;
    }

}

class Booking {
    private String username;
    private int teacherId;
    private Teacher teacher = null;
    private final String course;
    private final int lessonSlot;
    Status status;

    public Booking(String username, int teacherId, String course, int lessonSlot, Status status) {
        this.username = username;
        this.teacherId = teacherId;
        this.course = course;
        this.lessonSlot = lessonSlot;
        this.status = status;
    }

    public Booking(String username, Teacher teacher, String course, int lessonSlot, Status status) {
        this.username = username;
        this.teacher = teacher;
        this.course = course;
        this.lessonSlot = lessonSlot;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

class BothBookings {
    List<Booking> oldBookings;
    List<Booking> newBookings;

    public BothBookings(List<Booking> oldBookings, List<Booking> newBookings) {
        this.oldBookings = oldBookings;
        this.newBookings = newBookings;
    }
}

class jsonMessage<T> {
    private final String message;
    private final T data;

    public jsonMessage(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
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