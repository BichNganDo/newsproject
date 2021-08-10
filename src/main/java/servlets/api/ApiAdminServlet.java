package servlets.api;

import com.google.gson.Gson;
import common.APIResult;
import entity.Admin;
import helper.HttpHelper;
import helper.SecurityHelper;
import helper.ServletUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AdminModel;
import org.json.JSONObject;

public class ApiAdminServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        List<Admin> listAdmins = AdminModel.INSTANCE.getAllAdmins();
        ServletUtil.printJson(request, response, gson.toJson(listAdmins));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        APIResult result = new APIResult(0, "Success");

        String action = request.getParameter("action");
        switch (action) {
            case "login": {
                String bodyData = HttpHelper.getBodyData(request);
                JSONObject jData = new JSONObject(bodyData);
                String phone = jData.getString("phone");
                String password = SecurityHelper.getMD5Hash(jData.getString("password"));
                boolean checkLogin = AdminModel.INSTANCE.checkLogin(phone, password);
                if (checkLogin) {
                    result.setErrorCode(0);
                    result.setMessage("Đăng nhập thành công!");
                    String genAuthenCookie = AdminModel.INSTANCE.genAuthenCookie(phone);
                    HttpHelper.setCookie(response, "authen", genAuthenCookie, 185000);

                } else {
                    result.setErrorCode(-3);
                    result.setMessage("Tên đăng nhập hoặc mật khẩu không đúng");
                }
                break;
            }

            default:
                throw new AssertionError();
        }

        ServletUtil.printJson(request, response, gson.toJson(result));
    }
}
