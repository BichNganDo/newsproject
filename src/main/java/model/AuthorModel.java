package model;

import client.MysqlClient;
import common.ErrorCode;
import entity.Author;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AuthorModel {
    private static final MysqlClient dbClient = MysqlClient.getMysqlCli();
    private final String NAMETABLE = "author";
    public static AuthorModel INSTANCE = new AuthorModel();

    public Author getAuthorByID(int id) {
        Author result = new Author();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return result;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "` WHERE `id_author`='" + id + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                result.setId_author(rs.getInt("id_author"));
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

    public List<Author> getSlice(int limit, int offset) {
        List<Author> resultListAuthors = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return resultListAuthors;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "` LIMIT " + limit + " OFFSET " + offset;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setId_author(rs.getInt("id_author"));
                author.setName(rs.getString("name"));

                resultListAuthors.add(author);
            }

            return resultListAuthors;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return resultListAuthors;
    }

    public List<Author> getAllAuthors() {
        List<Author> resultListAuthors = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return resultListAuthors;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "`";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setId_author(rs.getInt("id_author"));
                author.setName(rs.getString("name"));

                resultListAuthors.add(author);
            }

            return resultListAuthors;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return resultListAuthors;
    }

    public int addAuthor(String name) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }
            String sql = "INSERT INTO `author`"
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

    public int editAuthor(int id, String name) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            String sql = "UPDATE `author` SET `name`='" + name + "'WHERE `id_author`='" + id + "'";

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

    public int deleteAuthor(int id) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            Author authorByID = getAuthorByID(id);
            if (authorByID.getId_author()== 0) {
                return ErrorCode.NOT_EXIST.getValue();
            }
            String sql = "DELETE FROM `author` WHERE `id_author`='" + id + "'";

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
