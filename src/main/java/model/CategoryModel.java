package model;

import client.MysqlClient;
import common.ErrorCode;
import entity.Author;
import entity.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryModel {
    private static final MysqlClient dbClient = MysqlClient.getMysqlCli();
    private final String NAMETABLE = "category";
    public static CategoryModel INSTANCE = new CategoryModel();

    public Category getCategoryByID(int id) {
        Category result = new Category();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return result;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "` WHERE `id_cate`='" + id + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                result.setId_category(rs.getInt("id_cate"));
                result.setName(rs.getString("name"));
            }

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }
        return result;
    }

    public List<Category> getSlice(int limit, int offset) {
        List<Category> resultListCategories = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return resultListCategories;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "` LIMIT " + limit + " OFFSET " + offset;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setId_category(rs.getInt("id_cate"));
                category.setName(rs.getString("name"));

                resultListCategories.add(category);
            }

            return resultListCategories;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return resultListCategories;
    }

    public List<Category> getAllCategories() {
        List<Category> resultListCategories = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return resultListCategories;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "`";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setId_category(rs.getInt("id_cate"));
                category.setName(rs.getString("name"));

                resultListCategories.add(category);
            }

            return resultListCategories;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return resultListCategories;
    }

    public int addCategory(String name) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }
            String sql = "INSERT INTO `" + NAMETABLE + "`"
                    + "(`name`) VALUES "
                    + "('" + name + "')";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int rs = preparedStatement.executeUpdate();

            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return ErrorCode.FAIL.getValue();
    }

    public int editCategory(int id, String name) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            String sql = "UPDATE `" + NAMETABLE + "` SET `name`='" + name + "'WHERE `id_cate`='" + id + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int rs = preparedStatement.executeUpdate();

            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }
        return ErrorCode.FAIL.getValue();
    }

    public int deleteCategory(int id) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            Category categoryByID = getCategoryByID(id);
            if (categoryByID.getId_category()== 0) {
                return ErrorCode.NOT_EXIST.getValue();
            }
            String sql = "DELETE FROM `" + NAMETABLE + "` WHERE `id_cate`='" + id + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            int rs = preparedStatement.executeUpdate();

            return rs;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }
        return ErrorCode.FAIL.getValue();
    }
}
