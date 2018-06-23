package cn.com.sise.ca.castore.server.som;

import java.util.List;

public class ApplicationQueryMessage {

    private List<ApplicationInfoBean> results;

    public List<ApplicationInfoBean> getResults() {
        return results;
    }

    public void setResults(List<ApplicationInfoBean> results) {
        this.results = results;
    }
}
