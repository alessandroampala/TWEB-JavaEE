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
            if (action.equals("lessons")) {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");
                out.print(gson.toJson(Dao.getLessons()));
                out.flush();
                return;
            } else if (action.equals("materie")) {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");
                List<Course> courses = Dao.getCourses();
                courses.add(0, new Course("Seleziona Materia"));
                out.print(gson.toJson(courses));
                out.flush();
                return;
            } else if (action.equals("docenti")) {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");
                List<Teacher> teachers = Dao.getTeachers();
                teachers.add(0, new Teacher(0, "Seleziona", "Docente"));
                out.print(gson.toJson(teachers));
                out.flush();
                return;
            } else if (action.equals("login")) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");

                User user = Dao.getUser(username, password);
                if (user != null) //user exists
                {
                    user.password = null;
                    session.setAttribute("username", user.username);
                    session.setAttribute("isAdmin", user.getAdmin());
                    out.print(gson.toJson(user));
                    out.flush();
                }
                return;
            } else if (action.equals("getSessionLogin")) {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter out = response.getWriter();
                if (!session.isNew() && session.getAttribute("username") != null)
                    out.print(gson.toJson(new User((String) session.getAttribute("username"), null, (boolean) session.getAttribute("isAdmin"))));
                else {
                    session.invalidate();
                    out.print(gson.toJson(null));
                }
                return;
            } else if (action.equals("logout")) {
                session.invalidate();
                //response.setContentType("text/html;charset=UTF-8");
                //request.getRequestDispatcher("index.html").forward(request, response);
            }
        }
        //RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        //dispatcher.forward(request, response);
        /*response.setContentType("text/html;charset=UTF-8");
        Dao.insertTeacher("gatto", "matto");*/
    }
}
