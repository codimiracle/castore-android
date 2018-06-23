package cn.com.sise.ca.castore.server.som;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfoBean {

    /**
     * username : Codimiracle
     * nickname : Codimiracle
     * uid : 1
     * gender : 1
     * permission : ["anonymous","user","content","console"]
     */

    @SerializedName("username")
    private String name;
    private String nickname;
    @SerializedName("uid")
    private String id;
    private String gender;
    private List<String> permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getPermission() {
        return permission;
    }

    public void setPermission(List<String> permission) {
        this.permission = permission;
    }
}
