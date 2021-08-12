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

public class NewsInCategory extends HttpServlet {
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("app_domain", Config.APP_DOMAIN);
        pageVariables.put("static_domain", Config.STATIC_DOMAIN);
        
        List<Category> allCategories = CategoryModel.INSTANCE.getAllCategories();
        pageVariables.put("all_categories", allCategories);
        
        FilterNews idFilter = new FilterNews();
        int id_cate = Integer.parseInt(request.getParameter("id"));
        idFilter.setId_cate(id_cate);
        List<News> listNews = NewsModel.INSTANCE.getSlice(idFilter, 10, 0);
        pageVariables.put("list_news", listNews);
        
        FilterNews filterProperty2 = new FilterNews();
        filterProperty2.setProperty("Popular");
        List<News> listNewsPopular = NewsModel.INSTANCE.getSlice(filterProperty2, 1, 0);
        pageVariables.put("list_news_popular", listNewsPopular.get(0));
        
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().println(PageGenerator.instance().getPage("client/news-in-category.html", pageVariables));
        
        response.setStatus(HttpServletResponse.SC_OK);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
