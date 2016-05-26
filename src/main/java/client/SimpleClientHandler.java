package client;

import avro.models.User;

public class SimpleClientHandler {
    public static void main(String[] args) {
        TcpClient client = new TcpClient("localhost", 8085);
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
        client.connect(user);
        client.close();
    }
}
