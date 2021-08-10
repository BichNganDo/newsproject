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
import model.CategoryModel;
import org.json.JSONObject;

public class ApiCategoryServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        List<Category> allCategories = CategoryModel.INSTANCE.getAllCategories();
        ServletUtil.printJson(request, response, gson.toJson(allCategories));
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

                int addCategory = CategoryModel.INSTANCE.addCategory(name);
                if (addCategory >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Thêm category thành công!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Thêm category thất bại!");
                }
                break;
            }

            case "edit": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                int id = jData.optInt("id");
                String name = jData.optString("name");

                Category categoryByID = CategoryModel.INSTANCE.getCategoryByID(id);
                if (categoryByID.getId_category()== 0) {
                    result.setErrorCode(-1);
                    result.setMessage("Sửa category thất bại!");
                    return;
                }

            int editCategory = CategoryModel.INSTANCE.editCategory(id, name);
                if (editCategory >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Sửa category thành công!");
                } else {
                    result.setErrorCode(-1);
                    result.setMessage("Sửa category thất bại!");
                }
                break;
            }
            case "delete": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                int id = jData.optInt("id");
                int deleteCategory = CategoryModel.INSTANCE.deleteCategory(id);
                if (deleteCategory >= 0) {
                    result.setErrorCode(0);
                    result.setMessage("Xóa category thành công!");
                } else {
                    result.setErrorCode(-2);
                    result.setMessage("Xóa category thất bại!");
                }
                break;
            }
            default:
                throw new AssertionError();
        }

        ServletUtil.printJson(request, response, gson.toJson(result));
    }
}
