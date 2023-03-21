package service;

import pojo.Message;
import pojo.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

//提供与消息相关的方法
/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 * 这个类进行文本聊天的发送
 **/
public class MessageClientService {
    /**私聊*/
    public void sendMessageToOneUser(String content,String senderId,String getterId){
        Message message = new Message();
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        //这是一个普通私聊消息
        message.setMesType(MessageType.COMMON_ONE_MESSAGE);
        //编辑消息
        try {
            ObjectOutputStream out = new ObjectOutputStream(ManageClientThread.getThread(senderId).getSocket().getOutputStream());
            out.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**群发*/
    public void sendMessageToAll(String senderId,String content){
        Message message = new Message();
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());
        //这是一个普通群聊消息
        message.setMesType(MessageType.COMMON_ALL_MESSAGE);
        try {
            ObjectOutputStream out = new ObjectOutputStream(ManageClientThread.getThread(senderId).getSocket().getOutputStream());
            out.writeObject(message);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
