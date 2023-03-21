package controllrt;

import gui.Menu;
import service.FileClientService;
import service.MessageClientService;
import service.UserClintService;

/**
 * @author Pink starfish
 * @date 2023/2/26
 * @apiNote
 **/
public class ClientController {

    private static int loop=0;
    private UserClintService userClintService=new UserClintService();
//
    private MessageClientService userMessageService=new MessageClientService();
    //    这个服务类进行文件的交流
    private FileClientService userFileMessageService=new FileClientService();
    public void logIn(String userId,String userPw){
//        该方法的作用是检查并跳转到菜单页面
        while (loop<3){

            boolean checkUser = userClintService.checkUser(userId, userPw);
//向服务器发送请求查看是否有该用户
            if (checkUser){
                new Menu(userId);
                break;
            }
            loop++;

        }


    }
}
