package client;

import avro.models.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


class TcpClient extends AbstractVerticle{
    private String serverName;
    private int serverPort;
    private NetClient client;
    private Vertx vertx;

    TcpClient(String serverName, int port) {
        this.serverName = serverName;
        this.serverPort = port;
        this.vertx = Vertx.vertx();
    }

    @Override
    public void start() throws Exception{
        if (this.client != null)
        {
            this.close();
        }
        this.client = this.vertx.createNetClient();
    }

    public void connect(String msg,int times){
        this.client.connect(this.serverPort,this.serverName,result -> {
            NetSocket socket = result.result();
            for (int i = 0; i < times; i++) {
                socket.write("GET / HTTP/1.1\n" + msg + "\n" + msg + "\n");
            }
        });
    }
    void connect(User user){

        this.client.connect(this.serverPort, this.serverName, result -> {
            NetSocket socket = result.result();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
            DatumWriter<User> writer = new SpecificDatumWriter<>(User.getClassSchema());
            try {
                writer.write(user, encoder);
                System.out.println("Id" + user.getName());
                encoder.flush();
                out.close();
                socket.write(Buffer.buffer(out.toByteArray())).end();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void connect(Handler<AsyncResult<NetSocket>> connectionHandler){
        this.client.connect(this.serverPort,this.serverName,connectionHandler);
    }

    void close(){
        this.client.close();
        System.out.println("Client closed successfully");
    }


}
