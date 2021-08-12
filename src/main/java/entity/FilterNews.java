/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Ngan Do
 */
public class FilterNews {

    private String query;
    private int id_author;
    private int id_cate;
    private String status;
    private String property;

    public FilterNews() {
    }

    public FilterNews(String query, int id_author, int id_cate, String status, String property) {
        this.query = query;
        this.id_author = id_author;
        this.id_cate = id_cate;
        this.status = status;
        this.property = property;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public int getId_cate() {
        return id_cate;
    }

    public void setId_cate(int id_cate) {
        this.id_cate = id_cate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

}
