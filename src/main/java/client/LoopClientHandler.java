package client;

import avro.models.User;

public class LoopClientHandler {
    public static void main(String[] args) {

        TcpClient client = new TcpClient("localhost", 8085);
        try{
            client.start();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        User user = new User();
        for (int u=0;u<100;u++) {
            user.setName("Chelo"+u);
            user.setFavoriteColor("red");
            user.setFavoriteNumber(u);
            client.connect(user);
        }
        client.close();
    }
}
