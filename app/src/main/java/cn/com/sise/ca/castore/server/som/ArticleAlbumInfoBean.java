package cn.com.sise.ca.castore.server.som;

public class ArticleAlbumInfoBean {

    private ApplicationInfoBean applicationInfoBean;
    private String title;
    private String summary;
    private String author;

    public ApplicationInfoBean getApplicationInfoBean() {
        return applicationInfoBean;
    }

    public void setApplicationInfoBean(ApplicationInfoBean applicationInfoBean) {
        this.applicationInfoBean = applicationInfoBean;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummaryShort() {
        return summary;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
