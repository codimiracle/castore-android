package cn.com.sise.ca.castore.datasource.database.entities;

public class SearchHistory implements Entity{

    private long id;
    private String keyword;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
