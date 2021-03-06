package servlets.api;

import com.google.gson.Gson;
import common.APIResult;
import entity.FilterNews;
import entity.ListNews;
import entity.News;
import helper.HttpHelper;
import helper.ServletUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.NewsModel;
import org.json.JSONObject;

public class ApiNewsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        int pageIndex = 1;
        int limit = 10;
        int offset = (pageIndex - 1) * limit;

        FilterNews filter = new FilterNews();
        List<News> listNews = NewsModel.INSTANCE.getSlice(filter, limit, offset);
        int allNews = NewsModel.INSTANCE.getAllNews(filter);

        ListNews result = new ListNews();
        result.setTotal(allNews);
        result.setListNews(listNews);
        result.setItemPerPage(limit);

        ServletUtil.printJson(request, response, gson.toJson(result));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        APIResult result = new APIResult(0, "Success");

        String action = request.getParameter("action");
        switch (action) {
            case "add": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                String title = jData.optString("title");
                String image = jData.optString("image");
                String content = jData.optString("content");
                String description = jData.optString("description");
                String status = jData.optString("status");
                String property = jData.optString("property");
                int id_cate = jData.optInt("idCategory"); //trung ten o ajax
                int id_author = jData.optInt("idAuthor");

                int addNews = NewsModel.INSTANCE.addNews(title, image, content, description, status, property, id_author, id_cate);
                if (addNews >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Th??m news th??nh c??ng!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Th??m news th???t b???i!");
                }
                break;
            }

            case "edit": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                int id = jData.optInt("id");
                String title = jData.optString("title");
                String image = jData.optString("image");
                String content = jData.optString("content");
                String description = jData.optString("description");
                String status = jData.optString("status");
                String property = jData.optString("property");
                int id_cate = jData.optInt("idCategory"); //trung ten o ajax
                int id_author = jData.optInt("idAuthor");

                News newsByID = NewsModel.INSTANCE.getNewsByID(id);
                if (newsByID.getId_news() == 0) {
                    result.setErrorCode(-1);
                    result.setMessage("S???a news th???t b???i!");
                    return;
                }

                int editNews = NewsModel.INSTANCE.editNews(id, title, image, content, description, status, property, id_author, id_cate);
                if (editNews >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("S???a news th??nh c??ng!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("S???a news th???t b???i!");
                }
                break;
            }
            case "delete": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                int id = jData.optInt("id");
                int deleteNews = NewsModel.INSTANCE.deleteNews(id);
                if (deleteNews >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("X??a news th??nh c??ng!");
                } else {
                    result.setErrorCode(-2);
                    result.setMessage("X??a news th???t b???i!");
                }
                break;
            }
            case "search": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                String valueSearch = jData.optString("valueSearch");
                int id_cate = jData.optInt("idCate");
                String status = jData.optString("status");
                String property = jData.optString("property");
                int id_author = jData.optInt("author");
                int pageIndex = jData.optInt("pageIndex");

                int limit = 10;
                int offset = (pageIndex - 1) * limit;

                FilterNews filter = new FilterNews();
                filter.setQuery(valueSearch);
                filter.setId_cate(id_cate);
                filter.setStatus(status);
                filter.setId_author(id_author);
                filter.setProperty(property);

                List<News> listSlice = NewsModel.INSTANCE.getSlice(filter, limit, offset);
                int allNews = NewsModel.INSTANCE.getAllNews(filter);

                ListNews listNews = new ListNews();
                listNews.setTotal(allNews);
                listNews.setListNews(listSlice);
                listNews.setItemPerPage(limit);
                result.setData(listNews);
                break;
            }
            default:
                throw new AssertionError();
        }

        ServletUtil.printJson(request, response, gson.toJson(result));
    }
}
