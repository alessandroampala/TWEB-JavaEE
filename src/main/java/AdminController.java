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

@WebServlet(name = "AdminController", urlPatterns = {"/AdminController"})
public class AdminController extends HttpServlet {
    public void init(ServletConfig conf) throws ServletException {
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
        String u = (String) session.getAttribute("username");

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
                    return;
                }
            }
        }
    }
}
