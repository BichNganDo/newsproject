package servlets.api;

import com.google.gson.Gson;
import common.APIResult;
import entity.Author;
import entity.Category;
import helper.HttpHelper;
import helper.ServletUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AuthorModel;
import org.json.JSONObject;

public class ApiAuthorServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        List<Author> allAuthors = AuthorModel.INSTANCE.getAllAuthors();
        ServletUtil.printJson(request, response, gson.toJson(allAuthors));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        APIResult result = new APIResult(0, "Success");

        String action = request.getParameter("action");
        switch (action) {
            case "add": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                String name = jData.optString("name");

                int addAuthor = AuthorModel.INSTANCE.addAuthor(name);
                if (addAuthor >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Thêm author thành công!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Thêm author thất bại!");
                }
                break;
            }

            case "edit": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                int id = jData.optInt("id");
                String name = jData.optString("name");

                Author authorByID = AuthorModel.INSTANCE.getAuthorByID(id);
                if (authorByID.getId_author()== 0) {
                    result.setErrorCode(-1);
                    result.setMessage("Sửa author thất bại!");
                    return;
                }

            int editAuthor = AuthorModel.INSTANCE.editAuthor(id, name);
                if (editAuthor >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Sửa author thành công!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Sửa author thất bại!");
                }
                break;
            }
            case "delete": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                int id = jData.optInt("id");
                int deleteAuthor = AuthorModel.INSTANCE.deleteAuthor(id);
                if (deleteAuthor >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Xóa author thành công!");
                } else {
                    result.setErrorCode(-2);
                    result.setMessage("Xóa author thất bại!");
                }
                break;
            }
            default:
                throw new AssertionError();
        }

        ServletUtil.printJson(request, response, gson.toJson(result));
    }
}
