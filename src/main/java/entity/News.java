package entity;

public class News {

    private int id_news;
    private String title;
    private String image;
    private String content;
    private String description;
    private String postDate;
    private String status;
    private String property;
    private int id_author;
    private int id_category;
    private String cate_name;
    private String author_name;

    public News() {
    }

    public News(int id_news, String title, String image, String content, String description, String postDate, String status, String property, int id_author, int id_category, String cate_name, String author_name) {
        this.id_news = id_news;
        this.title = title;
        this.image = image;
        this.content = content;
        this.description = description;
        this.postDate = postDate;
        this.status = status;
        this.property = property;
        this.id_author = id_author;
        this.id_category = id_category;
        this.cate_name = cate_name;
        this.author_name = author_name;
    }

    public int getId_news() {
        return id_news;
    }

    public void setId_news(int id_news) {
        this.id_news = id_news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public int getId_author() {
        return id_author;
    }

    public void setId_author(int id_author) {
        this.id_author = id_author;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
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

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

}
