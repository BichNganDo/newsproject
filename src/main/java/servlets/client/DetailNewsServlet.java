package servlets.client;

import common.Config;
import entity.Category;
import entity.FilterNews;
import entity.News;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.CategoryModel;
import model.NewsModel;
import templater.PageGenerator;

public class DetailNewsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("app_domain", Config.APP_DOMAIN);
        pageVariables.put("static_domain", Config.STATIC_DOMAIN);

        List<Category> allCategories = CategoryModel.INSTANCE.getAllCategories();
        pageVariables.put("all_categories", allCategories);

        int id_news = Integer.parseInt(request.getParameter("id"));
        News newsByID = NewsModel.INSTANCE.getNewsByID(id_news);
        pageVariables.put("detail_news", newsByID);

        FilterNews filterProperty = new FilterNews();
        filterProperty.setProperty("Recent");
        List<News> listNewsRecent = NewsModel.INSTANCE.getSlice(filterProperty, 10, 0);
        pageVariables.put("list_news_recent", listNewsRecent);

        FilterNews filterProperty2 = new FilterNews();
        filterProperty2.setProperty("Popular");
        List<News> listNewsPopular = NewsModel.INSTANCE.getSlice(filterProperty2, 10, 0);
        pageVariables.put("list_news_popular", listNewsPopular);

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(PageGenerator.instance().getPage("client/single.html", pageVariables));

        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
