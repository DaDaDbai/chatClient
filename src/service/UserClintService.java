package service;

import pojo.Message;
import pojo.MessageType;
import pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;


/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 * 用来完成用户登录验证和用户注册
 **/
public class UserClintService {
    private boolean flag=false;
    private static User user=new User();
    private Socket socket;
    //登录验证
    //这个集合用来存放我们的好友
    private static LinkedList<String> friends=new LinkedList<>();

    public static LinkedList<String> getFriends() {
        return friends;
    }

    public static void setFriends(LinkedList<String> friends) {
        UserClintService.friends = friends;
    }

    public boolean checkUser(String UserId, String UserPw) {
        user.setUserId(UserId);
        user.setUserPw(UserPw);

        try {
            socket = new Socket(InetAddress.getByName("192.168.142.139"), 9999);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //创建输出流来进行数据的传输
            out.writeObject(user);
            //我们需要将这个User对象发给服务端来判定其身份的正确性

            //服务端会对这个User对象进行判断，进而回送Message对象
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            //获取到输入流
            Message message = (Message) in.readObject();
            //通过输入流获取到服务器端发过来的Message对象
            if (message.getMesType()==MessageType.LOGIN_SUCCESS){

            //如果有这个User则返回的消息类型提示登录成功
            //因为成功了，故我们需要开启一个线程来进行该用户的通讯
                ClientThread clientThread = new ClientThread(socket,user.getUserId());
                clientThread.start();
            // 这里我发现客户端也可能会有多个线程，为了利于管理，以及未来的扩展性，这里准备一个集合来管理线程
                ManageClientThread.addThread(UserId,clientThread);
            //  调用方法获取到好友列表
                getFriendsList();
                //返回值设置
                flag=true;
            }else {
            //如果登录失败了，我们就不需要开启线程了
            //我们需要关闭前面开启的socket
                socket.close();
            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**请求拉取在线用户列表*/
    public void getFriendsList(){
        Message message = new Message();
        message.setMesType(MessageType.GET_ONLINE_USER_MESSAGE);
        message.setSender(user.getUserId());
        //发送获取用户列表的请求
        try {
            ObjectOutputStream out = new ObjectOutputStream(ManageClientThread.getThread(user.getUserId()).getSocket().getOutputStream());
        //通过线程管理类来获取到线程对象，通过线程对象获取到其Socket对象，在通过这个Socket对象获取到与服务器连接的输出流
            out.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**写一个退出的方法，并发送信息给服务端*/
    public void logOut(){
        Message message = new Message();
        message.setMesType(MessageType.EXIT_MESSAGE);
        //给服务端发送下线通知
        message.setSender(user.getUserId());
        // 告诉服务端下线线程
        try {

            ClientThread OUTThread = ManageClientThread.getThread(user.getUserId());
            ObjectOutputStream out = new ObjectOutputStream(OUTThread.getSocket().getOutputStream());
        //通过管理线程的集合来获取到线程，然后获取到Socket再获取到输出流
            out.writeObject(message);
            System.exit(0);
        //结束进程
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

