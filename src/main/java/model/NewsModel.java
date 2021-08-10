package model;

import client.MysqlClient;
import common.ErrorCode;
import entity.Author;
import entity.FilterNews;
import entity.News;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsModel {

    private static final MysqlClient dbClient = MysqlClient.getMysqlCli();
    private final String NAMETABLE = "news";
    public static NewsModel INSTANCE = new NewsModel();

    public News getNewsByID(int id) {
        News result = new News();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return result;
            }
            String sql = "SELECT * FROM `" + NAMETABLE + "` WHERE `id_news`='" + id + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                result.setId_news(rs.getInt("id_news"));
                result.setTitle(rs.getString("title"));
                result.setImage(rs.getString("image"));
                result.setContent(rs.getString("content"));
                result.setDescription(rs.getString("description"));

                long currentTimeMillis = rs.getLong("post_date");
                Date date = new Date(currentTimeMillis);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateString = sdf.format(date);
                result.setPostDate(dateString);

                result.setStatus(rs.getString("status"));
                result.setId_author(rs.getInt("id_author"));
                result.setId_category(rs.getInt("id_cate"));

            }

            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }
        return result;
    }

    public List<News> getSlice(FilterNews filter, int limit, int offset) {
        List<News> resultListNews = new ArrayList<>();
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return resultListNews;
            }
            String sql = "SELECT news.*, category.name AS `cate_name`, "
                    + "author.name AS `author_name` "
                    + "FROM news "
                    + "INNER JOIN category ON news.id_cate = category.id_cate "
                    + "INNER JOIN author ON news.id_author=author.id_author WHERE 1=1 ";

            if (filter.getQuery() != null && !"".equals(filter.getQuery())) {
                sql = sql + " AND news.title LIKE '%" + filter.getQuery() + "%' ";

                //LIKE '%or%'
            }
            if (filter.getId_cate() > 0) {
                sql = sql + " AND news.id_cate = " + filter.getId_cate();
            }
            if (filter.getId_author() > 0) {
                sql = sql + " AND news.id_author = " + filter.getId_author();
            }
            if (filter.getStatus() != null && !"".equals(filter.getStatus())) {
                sql = sql + " AND news.status = '" + filter.getStatus() + "' ";
            }
            sql = sql + " LIMIT " + limit + " OFFSET " + offset;

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                News news = new News();
                news.setId_news(rs.getInt("id_news"));
                news.setTitle(rs.getString("title"));
                news.setImage(rs.getString("image"));
                news.setContent(rs.getString("content"));
                news.setDescription(rs.getString("description"));

                long currentTimeMillis = rs.getLong("post_date");
                Date date = new Date(currentTimeMillis);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateString = sdf.format(date);
                news.setPostDate(dateString);

                news.setStatus(rs.getString("status"));
                news.setId_author(rs.getInt("id_author"));
                news.setId_category(rs.getInt("id_cate"));
                news.setCate_name(rs.getString("cate_name"));
                news.setAuthor_name(rs.getString("author_name"));

                resultListNews.add(news);
            }

            return resultListNews;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return resultListNews;
    }

    public int getAllNews(FilterNews filter) {
        int total = 0;
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return total;
            }
            String sql = "SELECT COUNT(id_news) AS total FROM news WHERE 1=1 ";
            if (filter.getQuery() != null && !"".equals(filter.getQuery())) {
                sql = sql + " AND news.title = '" + filter.getQuery() + "' ";
            }
            if (filter.getId_cate() > 0) {
                sql = sql + " AND news.id_cate = " + filter.getId_cate();
            }
            if (filter.getId_author() > 0) {
                sql = sql + " AND news.id_author = " + filter.getId_author();
            }

            if (filter.getStatus() != null && !"".equals(filter.getStatus())) {
                sql = sql + " AND news.status = '" + filter.getStatus() + "' ";
            }

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            dbClient.releaseDbConnection(conn);
        }

        return total;
    }

    public int addNews(String title, String image, String content,
            String description, String status, int id_author, int id_category) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }
            String sql = "INSERT INTO `" + NAMETABLE + "`"
                    + "(`title`, `image`, `content`, `description`, `post_date`, `status`, `id_author`, `id_cate`) "
                    + "VALUES "
                    + "('" + title + "', '" + image + "','" + content + "', '" + description + "', '"
                    + System.currentTimeMillis() + "', '" + status + "','" + id_author + "', '" + id_category + "')";

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

    public int editNews(int id, String title, String image, String content,
            String description, String status, int id_author, int id_category) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            String sql = "UPDATE `" + NAMETABLE + "` SET `title`='" + title + "', "
                    + "`image`='" + image + "', `content`='" + content + "', "
                    + "`description`='" + description + "', `status`='" + status + "', "
                    + "`id_author`='" + id_author + "', `id_cate`='" + id_category + "' "
                    + " WHERE `id_news`='" + id + "'";

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

    public int deleteNews(int id) {
        Connection conn = null;
        try {
            conn = dbClient.getDbConnection();
            if (null == conn) {
                return ErrorCode.CONNECTION_FAIL.getValue();
            }

            News newsByID = getNewsByID(id);
            if (newsByID.getId_news() == 0) {
                return ErrorCode.NOT_EXIST.getValue();
            }
            String sql = "DELETE FROM `" + NAMETABLE + "` WHERE `id_news`='" + id + "'";

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
