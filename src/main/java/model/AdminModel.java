package model;

import client.MysqlClient;
import entity.Admin;
import helper.SecurityHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminModel {

    private static final MysqlClient dbClient = MysqlClient.getMysqlCli();
    private final String NAMETABLE = "admin";
    public static AdminModel INSTANCE = new AdminModel();

    public List<Admin> getAllAdmins() {
        List<Admin> resultListAdmins = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return resultListAdmins;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "`";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId_admin(rs.getInt("id_admin"));
                admin.setPhone(rs.getString("phone"));
                admin.setPassword(rs.getString("password"));

                resultListAdmins.add(admin);
            }

            return resultListAdmins;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return resultListAdmins;
    }

    public boolean checkLogin(String phone, String password) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return false;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "` "
                    + "WHERE `phone`='" + phone + "' "
                    + "AND `password`='" + password + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                if (rs.getString("phone") != null && !rs.getString("phone").trim().isEmpty()) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return false;
    }

    private static final String SECRET_KEY = "ngandethuong";

    public static String genAuthenCookie(String phone) {
        return phone + "." + SecurityHelper.getMD5Hash(phone + SECRET_KEY);
    }

    public static String getAuthenCookie(String cookie) {
        String[] arrCookie = cookie.split("\\.");
        if (arrCookie.length == 2) {
            String phone = arrCookie[0];
            String hash = arrCookie[1];

            if (hash.equals(SecurityHelper.getMD5Hash(phone + SECRET_KEY))) {
                return phone;
            }
        }

        return "";
    }

}
