import com.google.gson.Gson;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet
{
    public void init(ServletConfig conf) throws ServletException
    {
        Dao.initialize();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String action = request.getParameter("action");
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.html");
        if(action!=null){
            if(action.equals("lessons")){
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");
                Gson gson = new Gson();
                out.print( gson.toJson(Dao.getLessons()));
                out.flush();
                return;
            }
            else if(action.equals("materie")){
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");
                List<Course> courses= Dao.getCourses();
                courses.add(0, new Course("Seleziona Materia"));
                Gson gson = new Gson();
                out.print( gson.toJson(courses));
                out.flush();
                return;
            }
            else if(action.equals("docenti")){
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");
                List<Teacher> teachers= Dao.getTeachers();
                teachers.add(0, new Teacher(0, "Seleziona", "Docente"));
                Gson gson = new Gson();
                out.print( gson.toJson(teachers));
                out.flush();
                return;
            }
            else if(action.equals("login")){
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                PrintWriter out = response.getWriter();
                response.setContentType("application/json;charset=UTF-8");
                Gson gson = new Gson();
                out.print( gson.toJson(Dao.getUser(username, password)));
                out.flush();
                return;
            }
        }
        dispatcher.forward(request, response);
        /*response.setContentType("text/html;charset=UTF-8");
        Dao.insertTeacher("gatto", "matto");*/
    }
}
