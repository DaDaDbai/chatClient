package pojo;

import java.io.Serializable;
/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 * 该类作为pojo类存在，是我们发送与接收消息的载体
 **/
public class Message implements Serializable {
    //serialVersionUID
    private static final long serialVersionUID = 3835716060779299256L;
    //发送者
    private String sender;
    //接收者
    private String getter;
    //消息内容
    private String content;
    //发送时间
    private String sendTime;
    //消息类型
    private MessageType mesType;
    //文件消息
    private byte[] file;
    //文件长度
    private int fileLength=0;
    //文件传输地址
    private String dest;
    //源文件路径
    private String src;

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public int getFileLength() {
        return fileLength;
    }

    public void setFileLength(int fileLength) {
        this.fileLength = fileLength;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", getter='" + getter + '\'' +
                ", content='" + content + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", mesType='" + mesType + '\'' +
                '}';
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public MessageType getMesType() {
        return mesType;
    }

    public void setMesType(MessageType mesType) {
        this.mesType = mesType;
    }

    public Message() {
    }

    public Message(String sender, String getter, String content, String sendTime, MessageType mesType) {
        this.sender = sender;
        this.getter = getter;
        this.content = content;
        this.sendTime = sendTime;
        this.mesType = mesType;
    }
}
