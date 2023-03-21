package service;

import java.util.HashMap;

//该类用来管理客户端的多个线程
/**
 * @author Pink starfish
 * @date 2023/2/25
 * @apiNote
 * 这个类用来管理用户线程，其实在这里并没有什么必要，但是为了可扩展性，我加上了他
 **/
public class ManageClientThread {
    private static HashMap<String,ClientThread> hashMap=new HashMap<>();
    public static void addThread(String userId,ClientThread clientThread){
        //调用该方法将线程放入Map
        hashMap.put(userId,clientThread);

    }
    public static ClientThread getThread(String userId){
        return hashMap.get(userId);
    }
}
