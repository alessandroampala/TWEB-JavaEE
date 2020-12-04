import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
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
    public void init(ServletConfig conf) {
        if (Dao.isNotInitialized())
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
        /*String u = (String) session.getAttribute("username");
        if (u != null)
            System.out.println("L'username in sessione è: " + u);*/

        String action = request.getParameter("action");
        if (action != null) {

            if (action.contains("admin")) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("AdminController");
                dispatcher.forward(request, response);
            }

            switch (action) {
                case "lessons": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");
                    String teacherId = request.getParameter("teacherId");
                    out.print(gson.toJson(Dao.getLessons(course, teacherId)));
                    out.flush();
                    out.close();
                    return;
                }
                case "materie": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    jsonMessage<List<Course>> courses = Dao.getCourses();
                    courses.getData().add(0, new Course("Seleziona Materia"));
                    out.print(gson.toJson(courses));
                    out.flush();
                    out.close();
                    return;
                }
                case "docenti": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    jsonMessage<List<Teacher>> teachers = Dao.getTeachers();
                    teachers.getData().add(0, new Teacher(0, "Seleziona", "Docente"));
                    out.print(gson.toJson(teachers));
                    out.flush();
                    out.close();
                    return;
                }
                case "login": {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");

                    jsonMessage<User> jsonData = Dao.getUser(username, password);
                    if (jsonData.getMessage().equals("OK")) //user exists
                    {
                        session.setAttribute("username", jsonData.getData().getUsername());
                        session.setAttribute("isAdmin", jsonData.getData().getAdmin());
                    }
                    out.print(gson.toJson(jsonData));
                    out.flush();
                    out.close();
                    return;
                }
                case "getSessionLogin": {
                    response.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    jsonMessage<User> jsonData;
                    if (!session.isNew() && session.getAttribute("username") != null) {
                        jsonData = new jsonMessage<>("Sessione valida", new User((String) session.getAttribute("username"), (boolean) session.getAttribute("isAdmin")));
                        out.print(gson.toJson(jsonData));
                    } else {
                        session.invalidate();
                        jsonData = new jsonMessage<>("Sessione invalida", null);
                        out.print(gson.toJson(jsonData));
                    }
                    out.close();
                    return;
                }
                case "logout":
                    session.invalidate();
                    break;
                case "prenotazioniDocente": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");
                    String teacherId = request.getParameter("teacherId");
                    jsonMessage<List<Booking>> bookings = Dao.getTeacherCourseBookings(course, Integer.parseInt(teacherId));
                    String username = (String) session.getAttribute("username");
                    for (Booking b : bookings.getData()) {
                        if (!b.getUsername().equals(username))
                            b.setUsername(null);
                    }
                    out.print(gson.toJson(bookings));
                    out.flush();
                    out.close();
                    return;
                }
                case "teacherBooking": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String teacherId = request.getParameter("teacherId");
                    jsonMessage<List<Booking>> bookings = Dao.getTeacherBookings(Integer.parseInt(teacherId));

                    for (Booking b : bookings.getData()) {
                        b.setUsername(null);
                    }
                    out.print(gson.toJson(bookings));
                    out.flush();
                    out.close();
                    return;
                }
                case "userBooking": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String username = (String) session.getAttribute("username");
                    String isAndroid = request.getParameter("isAndroid");
                    jsonMessage<List<Booking>> bookings;
                    if (isAndroid != null)
                        bookings = Dao.getUserBookings(username, true);
                    else
                        bookings = Dao.getUserBookings(username, false);

                    out.print(gson.toJson(bookings));
                    out.flush();
                    out.close();
                    return;
                }
                case "oldUserBookings": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String username = (String) session.getAttribute("username");
                    jsonMessage<List<Booking>> bookings;

                    bookings = Dao.getOldUserBookings(username);

                    /*for (Booking b : bookings.getData()) {
                        b.username = null;
                    }*/
                    out.print(gson.toJson(bookings));
                    out.flush();
                    out.close();
                    return;
                }
                case "allBookings": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");

                    if(isAdmin(session))
                    {
                        jsonMessage<BothBookings> bookings;
                        bookings = Dao.getAllBookings();
                        out.print(gson.toJson(bookings));
                    }

                    out.flush();
                    out.close();
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
                        out.print(gson.toJson(new jsonMessage<Object>("Not logged in", null)));
                    }
                    out.close();
                    return;
                }
                case "disdici": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    if (isLoggedIn(session)) {
                        String username = (String) session.getAttribute("username");
                        String teacherID = (String) request.getParameter("teacherId");
                        String course = (String) request.getParameter("course");
                        String lessonSlot = (String) request.getParameter("lessonSlot");
                        out.print(gson.toJson(Dao.cancelBooking(username, Integer.parseInt(teacherID), course, Integer.parseInt(lessonSlot))));
                    } else {
                        out.print(gson.toJson(new jsonMessage<Object>("Not logged in", null)));
                    }
                    out.close();
                    return;
                }
                case "effettuata": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    if (isLoggedIn(session)) {
                        String username = (String) session.getAttribute("username");
                        String teacherID = (String) request.getParameter("teacherId");
                        String course = (String) request.getParameter("course");
                        String lessonSlot = (String) request.getParameter("lessonSlot");
                        out.print(gson.toJson(Dao.markBooking(username, Integer.parseInt(teacherID), course, Integer.parseInt(lessonSlot))));
                    } else {
                        out.print(gson.toJson(new jsonMessage<Object>("Not logged in", null)));
                    }
                    out.close();
                    return;
                }
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        dispatcher.forward(request, response);
    }

    public static boolean isLoggedIn(HttpSession session) {
        return !session.isNew() && session.getAttribute("username") != null;
    }

    public static boolean isAdmin(HttpSession session) {
        return (boolean) session.getAttribute("isAdmin");
    }

}
