package servlets.author;

import com.google.gson.Gson;
import common.Config;
import entity.Author;
import entity.Category;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AuthorModel;
import templater.PageGenerator;

public class EditAuthorServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("static_domain", Config.STATIC_DOMAIN);
        pageVariables.put("app_domain", Config.APP_DOMAIN);
        pageVariables.put("message", "hello word");
        

        int idEdit = Integer.parseInt(request.getParameter("id"));
        Author authorByID = AuthorModel.INSTANCE.getAuthorByID(idEdit);
        pageVariables.put("author", authorByID);
        
        
        
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("author/edit-author.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
