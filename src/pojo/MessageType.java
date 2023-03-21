package pojo;

/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 * 这个枚举类用来设置我们消息的类型，是服务器与客户端判断的基础
 **/
public enum MessageType {
    //登录成功
    LOGIN_SUCCESS,
    //登录失败
    LOGIN_FAIL,
    //私聊普通消息
    COMMON_ONE_MESSAGE,
    //私聊文件消息
    COMMON_FILE_ONE_MESSAGE,
    //群聊普通消息
    COMMON_ALL_MESSAGE,
    //要求拉取用户信息
    GET_ONLINE_USER_MESSAGE,
    //返回的用户信息
    RETURN_ONLINE_USER_MESSAGE,
    //客户端退出请求
    EXIT_MESSAGE,

}
