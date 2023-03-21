package pojo;

import java.io.Serializable;
/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 *  用户类
 **/
public class User implements Serializable {
    private static final long serialVersionUID = -8195407799013398049L;
    private String userId;
    private String userPw;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userPw='" + userPw + '\'' +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public User() {
    }

    public User(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }
}
