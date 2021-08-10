package entity;

import java.util.List;

public class ListNews {
    private List<News> listNews;
    private int total;
    private int itemPerPage;

    public ListNews() {
    }

    public ListNews(List<News> listNews, int total, int itemPerPage) {
        this.listNews = listNews;
        this.total = total;
        this.itemPerPage = itemPerPage;
    }

    public List<News> getListNews() {
        return listNews;
    }

    public void setListNews(List<News> listNews) {
        this.listNews = listNews;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getItemPerPage() {
        return itemPerPage;
    }

    public void setItemPerPage(int itemPerPage) {
        this.itemPerPage = itemPerPage;
    }
    
}
