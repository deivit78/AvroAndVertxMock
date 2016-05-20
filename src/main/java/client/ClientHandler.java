package client;

import avro.models.User;

/**
 * Created by dave on 17/05/16.
 */
public class ClientHandler {
    public static void main(String[] args) {
        TcpClient client = new TcpClient("localhost", 8085);
        String msg = "ExplotameExplotameExplo!";
        int times = 1;
        try{
            client.start();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        User user = new User();
        user.setName("Chelo");
        user.setFavoriteColor("red");
        user.setFavoriteNumber(2);
        client.connect(user,1);
/*
        client.connect(result -> {
            NetSocket socket = result.result();
            for (int i = 0; i < times; i++) {
                socket.write(msg);
            }
            socket.handler(buffer -> {
                System.out.println("Received data: " + buffer.length());
                System.out.println(buffer.getString(0, buffer.length()));
            });
        });*/

        client.close();
        return;
    }

}
