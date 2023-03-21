package service;

import gui.LogIn;
/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 * 这个类为我们客户端的入口，调用LogIn方法可以进入登录UI
 **/
public class ClientMain {
    public static void main(String[] args) {
        new LogIn();
    }
}
