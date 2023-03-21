package service;

import gui.ChatThread;
import gui.ManageWindows;
import pojo.Message;
import pojo.MessageType;
import pojo.User;

import java.io.EOFException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 **/
public class ClientThread extends Thread{
    private Socket socket;

    private String userId;
    ObjectInputStream in =null;
    private boolean loop=true;
    @Override
    public void run() {
        //循环接收服务端发送的消息
        while (loop) {

            try {
                try {
                    in = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    System.out.println("客户端下线");
                    break;
                }

                /*在这个地方我们发现，如果我们使用前面的Input专门的try块来进行Object流的关闭，
                那么，就会导致，下面这些消息由于Object Heard的问题而无法多次发送
                现在我们选择反在这里，能够发送问题，但是之后的无异常退出会是一个问题*/
                Message message = (Message) in.readObject();
                /*获取输入流，以获取服务器传输的数据
                这里readObject如果没有读取到数据，那么它就会堵塞*/

                if(message.getMesType()== MessageType.RETURN_ONLINE_USER_MESSAGE){
                    //获取到好友列表
                    String[] split = message.getContent().split(" ");
                    //我们规范使用空格来分割
                    LinkedList<String> friends = new LinkedList<>();
                    for (int i = 0; i < split.length; i++) {
                        friends.add(split[i]);
                    }
                    UserClintService.setFriends(friends);
                }else if(message.getMesType()==MessageType.COMMON_ONE_MESSAGE){
                    //如果我们通过服务器转发得到了一个私人的聊天消息
                    //当我们得到了一个私人的聊天消息，那么我们就要创建或者是显示窗口
                    ChatThread window = ManageWindows.getWindow(message.getGetter(), message.getSender());
                    //我们先开启线程或显示窗口，然后返回Runnable对象，这就是我们的窗口对象，我们可以给这个对象存放消息内容
                    window.showMessage(message);
                }else if (message.getMesType()==MessageType.COMMON_ALL_MESSAGE){
                    //这是一个群发消息、打开群聊窗口
                    ChatThread window = ManageWindows.getWindow(userId, "ALL");
                    window.showMessage(message);
                }else if(message.getMesType()==MessageType.COMMON_FILE_ONE_MESSAGE){
                    //这是文件私聊
                    System.out.println(message.getSender()+"给你发送了一个文件");
                    // 这里需要一个窗口，来进行是否接收的判断，以及文件路径的获取
                    ChatThread window;
                    if (message.getGetter().equals("ALL")){
                         window = ManageWindows.getWindow( message.getSender(),message.getGetter());
                    }else {
                         window = ManageWindows.getWindow(message.getGetter(), message.getSender());
                    }
                    /*这里有一个很大的问题，由于我们进入getFile获取文件的时候，由于getFile方法中文件夹路径的选择是放在监听器中的，
                    而监听器需要我们点击才会对文件进行改变，那么一进去不会触发事件，就会导致默认值null被返回，
                    那么要想办法在文件路径操作完后再接下来运行，我这里考量循环*/
                    String fileDest=window.getFile(message.getSender(),message.getSrc());;
                    while (fileDest==null){
                        fileDest=window.getFileGetterPath();
                        System.out.printf("");
                    /*我在这里发现了一个问题，如果在一个循环中调用一个普通的get方法，
                    那么由于是一直获取，java其实是不会循环调用他的（或许是java觉得他没有意义），
                    但是如果我们在这个循环中添加打印语句，那么java就会执行他*/
                    }
                    //复制文件
                    if (!fileDest.equals("UN_SUCCESS")){
                    //返回一个不可能的路径进行路径的判断
                        FileOutputStream out = new FileOutputStream(fileDest);
                        out.write(message.getFile());
                        out.close();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public ClientThread(Socket socket ,String userId){
        this.socket=socket;
        this.userId=userId;
        //我们希望每一个线程对应一个用户，故我们需要将用户的socket拿到

    }


}
