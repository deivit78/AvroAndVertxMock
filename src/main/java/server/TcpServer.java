package server;
import avro.models.User;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;

public class TcpServer  extends AbstractVerticle{

    private String serverName;
    private int serverPort;
    private NetServer server;
    private Vertx vertx;


    public TcpServer(String serverName,int serverPort){
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.vertx = Vertx.vertx();
    }

    @Override
    public void start() throws Exception{
        this.server = vertx.createNetServer();

        this.server.connectHandler(netSocket -> {
            System.out.println("Incoming connection!");
            netSocket.handler(buffer -> {
               SpecificDatumReader<User> reader = new SpecificDatumReader<User>(User.getClassSchema());
                Decoder decoder = DecoderFactory.get().binaryDecoder(buffer.getBytes(),null);
                User user = null;
                try{
                    user = reader.read(null,decoder);
                }catch(Exception ex)
                {
                    ex.printStackTrace();
                }
                System.out.println("Tu nombre es: " +user.getName());

            });
        });

        this.server.listen(this.serverPort,this.serverName,res-> {
            if (res.succeeded()) {
                System.out.println("Server listening.");
            }
            else {
                System.out.println("Error in server binding");
            }
        });

     }

    public void close(){
        this.server.close(result -> {
            if(result.succeeded()){
               System.out.println("Server closed correctly");
            }
            else {
                System.out.println("Server closing error");
            }
        });
    }
}

