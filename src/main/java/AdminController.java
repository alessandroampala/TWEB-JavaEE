import com.google.gson.Gson;
import javax.servlet.ServletConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {
    // Checks if Dao has been initialized, if not it is initialized
    public void init(ServletConfig conf) {
        if (Dao.isNotInitialized())
            Dao.initialize();
    }

    // Post requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    // Get requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    // Manages all the requests
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Gson gson = new Gson();

        HttpSession session = request.getSession();

        if(!Controller.isLoggedIn(session))
        {
            PrintWriter out = response.getWriter();
            response.setContentType("application/json;charset=UTF-8");
            out.print(gson.toJson(new jsonMessage<Object>("Not logged in", null)));
            out.flush();
            out.close();
            return;
        }

        String action = request.getParameter("action");
        if (Controller.isLoggedIn(session) && Controller.isAdmin(session) && action != null) {
            switch (action) {
                case "adminInsertTeacher": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String name = request.getParameter("teacherName");
                    String surname = request.getParameter("teacherSurname");

                    jsonMessage<Object> result = Dao.insertTeacher(name, surname);
                    System.out.println(result.getMessage());
                    out.print(gson.toJson(result));
                    out.flush();
                    out.close();
                    return;
                }
                case "adminDeleteCourse": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");

                    out.print(gson.toJson(Dao.deleteCourse(course)));
                    out.flush();
                    out.close();
                    return;
                }
                case "adminAddCourse": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");

                    out.print(gson.toJson(Dao.insertCourse(course)));
                    out.flush();
                    out.close();
                    return;
                }
                case "adminDeleteTeacher": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String teacherId = request.getParameter("teacherId");

                    out.print(gson.toJson(Dao.deleteTeacher(Integer.parseInt(teacherId))));
                    out.flush();
                    out.close();
                    return;
                }
                case "adminAddLesson": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");
                    String teacherId = request.getParameter("teacherId");

                    out.print(gson.toJson(Dao.insertLesson(course, Integer.parseInt(teacherId))));
                    out.flush();
                    out.close();
                    return;
                }
                case "adminDeleteLesson": {
                    PrintWriter out = response.getWriter();
                    response.setContentType("application/json;charset=UTF-8");
                    String course = request.getParameter("course");
                    String teacherId = request.getParameter("teacherId");

                    out.print(gson.toJson(Dao.deleteLesson(course, Integer.parseInt(teacherId))));
                    out.flush();
                    out.close();
                }
            }
        }
    }
}
