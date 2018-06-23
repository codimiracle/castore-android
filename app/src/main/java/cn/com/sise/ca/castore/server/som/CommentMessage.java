package cn.com.sise.ca.castore.server.som;

import java.util.List;

public class CommentMessage {

    /**
     * type : msg
     * icon : info
     * message : The page is not visible.
     * comments : [{"id":"1","targetId":"2","title":"sfs","content":"sss","contentId":"3","createdTime":"2018-03-08 16:27:19","updatedTime":"2018-03-08 16:27:19","userId":"1"},{"id":"2","targetId":"2","title":"界面不好看","content":"和上一个版本相比，UI没有变啊？？","contentId":"4","createdTime":"2018-03-08 16:27:19","updatedTime":"2018-03-08 16:27:19","userId":"1"},{"id":"3","targetId":"2","title":"2342# #sfDf","content":"sfafa","contentId":"5","createdTime":"2018-03-08 16:27:19","updatedTime":"2018-03-08 16:27:19","userId":"1"},{"id":"4","targetId":"2","title":"Hello World","content":"The first comment.","contentId":"6","createdTime":"2018-03-08 16:27:19","updatedTime":"2018-03-08 16:27:19","userId":"1"},{"id":"5","targetId":"2","title":"UI 不好","content":"我们想要漂亮的UI。","contentId":"7","createdTime":"2018-03-08 16:27:19","updatedTime":"2018-03-08 16:27:19","userId":"1"},{"id":"6","targetId":"2","title":"哦哦哦","content":"oo","contentId":"8","createdTime":"2018-03-08 16:27:19","updatedTime":"2018-03-08 16:27:19","userId":"1"}]
     */

    private List<CommentInfoBean> comments;

    public List<CommentInfoBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentInfoBean> comments) {
        this.comments = comments;
    }
}
