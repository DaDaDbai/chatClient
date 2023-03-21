package service;

import pojo.Message;
import pojo.MessageType;

import java.io.*;
/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 * 这个service专门处理与文件相关的服务
 **/
public class FileClientService {
    public void sendFile(String src,String senderId,String getterId){
        Message message = new Message();
        message.setMesType(MessageType.COMMON_FILE_ONE_MESSAGE);
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setSrc(src);
        //先读取源文件
        FileInputStream in=null;
        byte[] fileByte = new byte[(int) new File(src).length()];
        try {
            in= new FileInputStream(src);
            in.read(fileByte);
            message.setFile(fileByte);
        //创建文件读取流，读取文件数据
            ObjectOutputStream socketOut = new ObjectOutputStream(ManageClientThread.getThread(senderId).getSocket().getOutputStream());
            socketOut.writeObject(message);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }
}
