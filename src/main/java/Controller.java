import com.google.gson.Gson;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {
    public void init(ServletConfig conf) throws ServletException {
        Dao.initialize();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new Gson();

        HttpSession session = request.getSession();
        String u = (String) session.getAttribute("username");
        if (u != null)
            System.out.println("L'username in sessione è: " + u);

        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "lessons": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");
                    String teacherId = request.getParameter("teacherId");
                    out.print(gson.toJson(Dao.getLessons(course, teacherId)));
                    out.flush();
                    return;
                }
                case "materie": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    jsonMessage<List<Course>> courses = Dao.getCourses();
                    courses.getData().add(0, new Course("Seleziona Materia"));
                    out.print(gson.toJson(courses));
                    out.flush();
                    return;
                }
                case "docenti": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    jsonMessage<List<Teacher>> teachers = Dao.getTeachers();
                    teachers.getData().add(0, new Teacher(0, "Seleziona", "Docente"));
                    out.print(gson.toJson(teachers));
                    out.flush();
                    return;
                }
                case "login": {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");

                    jsonMessage<User> jsonData = Dao.getUser(username, password);
                    if (jsonData.getMessage().equals("Ok")) //user exists
                    {
                        jsonData.getData().setPassword(null);
                        session.setAttribute("username", jsonData.getData().getUsername());
                        session.setAttribute("isAdmin", jsonData.getData().getAdmin());
                    }
                    out.print(gson.toJson(jsonData));
                    out.flush();
                    return;
                }
                case "getSessionLogin": {
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    jsonMessage<User> jsonData;
                    if (!session.isNew() && session.getAttribute("username") != null) {
                        jsonData = new jsonMessage<>("Sessione valida", new User((String) session.getAttribute("username"), null, (boolean) session.getAttribute("isAdmin")));
                        out.print(gson.toJson(jsonData));
                    } else {
                        session.invalidate();
                        jsonData = new jsonMessage<>("Sessione invalida", null);
                        out.print(gson.toJson(jsonData));
                    }
                    return;
                }
                case "logout":
                    session.invalidate();
                    break;
                case "prenotazioniDocente": { //TODO: mostrare nella tabella rosse anche le celle NON di quella materia MA di quel professore
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");
                    String teacherId = request.getParameter("teacherId");
                    jsonMessage<List<Booking>> bookings = Dao.getTeacherCourseBookings(course, Integer.parseInt(teacherId));
                    String username = (String) session.getAttribute("username");
                    for (Booking b : bookings.getData()) {
                        if (!b.username.equals(username))
                            b.username = null;
                    }
                    out.print(gson.toJson(bookings));
                    out.flush();
                    return;
                }
                case "teacherBooking": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String teacherId = request.getParameter("teacherId");
                    jsonMessage<List<Booking>> bookings = Dao.getTeacherBookings(Integer.parseInt(teacherId));

                    System.out.println("teacherBookings");

                    for (Booking b : bookings.getData()) {
                        b.username = null;
                    }
                    out.print(gson.toJson(bookings));
                    out.flush();
                    return;
                }
                case "userBooking": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String username = (String) session.getAttribute("username");
                    jsonMessage<List<Booking>> bookings = Dao.getUserBookings(username);

                    System.out.println("teacherBookings");

                    /*for (Booking b : bookings.getData()) {
                        b.username = null;
                    }*/
                    out.print(gson.toJson(bookings));
                    out.flush();
                    return;
                }
                case "prenotaLezioni": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    if (isLoggedIn(session)) {
                        String username = (String) session.getAttribute("username");
                        String teacherID = (String) request.getParameter("teacherId");
                        String course = (String) request.getParameter("course");
                        int[] lessonSlots = gson.fromJson((String) request.getParameter("lessonSlots"), int[].class);
                        out.print(gson.toJson(Dao.insertBookings(lessonSlots, username, Integer.parseInt(teacherID), course)));
                    } else {
                        out.print("Not logged in");
                    }
                }
            }
        }
        //RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        //dispatcher.forward(request, response);
        /*response.setContentType("text/html;charset=UTF-8");
        Dao.insertTeacher("gatto", "matto");*/
    }

    private boolean isLoggedIn(HttpSession session) {
        return !session.isNew() && session.getAttribute("username") != null;
    }

}
