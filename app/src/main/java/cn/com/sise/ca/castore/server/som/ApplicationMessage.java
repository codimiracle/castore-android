package cn.com.sise.ca.castore.server.som;

import java.util.List;

public class ApplicationMessage {

    /**
     * appInfo : {"id":"10","contentId":"21","title":"ICONApp","name":"ICONApp","description":"sfae.43","package":"com.tencent.timm","platform":"android-aarch32","developer":"test","version":"2.3","createdTime":"2018-05-12 15:04:28","updatedTime":"2018-05-12 15:04:28"}
     * appIcon : {"id":"17","targetId":"21","field":"app_icon","path":"static/files/4959f6a51996a2528496c3ef94108164.jpg","size":"156307","mimeType":"image/jpeg","uploadedTime":"2018-05-12 15:04:28","updatedTime":"2018-05-12 15:04:28"}
     * appPowerpoints : [{"id":"18","targetId":"21","field":"app_powerpoint","path":"static/files/9bd565a63224a7c944fc566692d03e69.jpg","size":"164984","mimeType":"image/jpeg","uploadedTime":"2018-05-12 15:04:28","updatedTime":"2018-05-12 15:04:28"}]
     * appComments : [{"id":"10","targetId":"21","title":"Hello","content":"a good comment","contentId":"25","createdTime":"2018-05-13 13:02:40","userId":"1"}]
     */

    private ApplicationInfoBean appInfo;
    private ResourceInfoBean appIcon;
    private List<ResourceInfoBean> appPowerpoints;
    private List<CommentInfoBean> appComments;
    private ResourceInfoBean appPackage;

    public ApplicationInfoBean getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(ApplicationInfoBean appInfo) {
        this.appInfo = appInfo;
    }

    public ResourceInfoBean getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(ResourceInfoBean appIcon) {
        this.appIcon = appIcon;
    }

    public List<ResourceInfoBean> getAppPowerpoints() {
        return appPowerpoints;
    }

    public void setAppPowerpoints(List<ResourceInfoBean> appPowerpoints) {
        this.appPowerpoints = appPowerpoints;
    }

    public List<CommentInfoBean> getAppComments() {
        return appComments;
    }

    public void setAppComments(List<CommentInfoBean> appComments) {
        this.appComments = appComments;
    }


    public ResourceInfoBean getAppPackage() {
        return appPackage;
    }

    public void setPackageInfo(ResourceInfoBean appPackage) {
        this.appPackage = appPackage;
    }
}
