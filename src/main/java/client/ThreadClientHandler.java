package client;

import avro.models.User;

public class ThreadClientHandler {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        for (int i=0;i<1000;i++){
            Thread thread = new Thread(){
                public void run(){
                    User user = new User();
                    TcpClient client = new TcpClient("localhost", 8085);
                    try{
                        client.start();
                    }
                    catch(Exception ex) {
                        ex.printStackTrace();
                    }

                    user.setName("Chelo"+ Math.random());
                    user.setFavoriteColor("red");
                    user.setFavoriteNumber((int) Math.random());
                    client.connect(user);
                    client.close();
                }
            };
            thread.start();
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);

        System.out.println("Duration in t is: " + duration);
    }
}
