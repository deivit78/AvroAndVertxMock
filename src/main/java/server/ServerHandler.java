package server;

/**
 * Created by dave on 17/05/16.
 */
public class ServerHandler {
    public static void main(String[] args){
        TcpServer server = new TcpServer("localhost",8085);
        try{
            server.start();
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

}
