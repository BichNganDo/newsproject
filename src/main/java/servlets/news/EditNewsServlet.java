package servlets.news;

import com.google.gson.Gson;
import common.Config;
import entity.Category;
import entity.News;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CategoryModel;
import model.NewsModel;
import templater.PageGenerator;

public class EditNewsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("static_domain", Config.STATIC_DOMAIN);
        pageVariables.put("app_domain", Config.APP_DOMAIN);
        pageVariables.put("message", "hello word");

        int idEdit = Integer.parseInt(request.getParameter("id"));
        News newsByID = NewsModel.INSTANCE.getNewsByID(idEdit);
        pageVariables.put("news", newsByID);

        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(PageGenerator.instance().getPage("news/edit-news.html", pageVariables));
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
