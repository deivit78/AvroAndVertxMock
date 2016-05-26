package client;

import avro.models.User;

public class Loop2ClientHandler {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        TcpClient client = new TcpClient("localhost", 8085);
        User user = new User();
        for (int u=0;u<100;u++) {

            try{
                client.start();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }

            user.setName("Chelo"+u);
            user.setFavoriteColor("red");
            user.setFavoriteNumber(u);
            client.connect(user);
            client.close();
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Duration in loop2 is: " + duration);
    }
}
