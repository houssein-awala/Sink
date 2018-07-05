/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sink;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sink.Sink.Requests;

/**
 *
 * @author mostafa slim
 */
public class connectionWithRouterForRequest extends Thread{

    public SinkRequest request;
    public performRequest perRequest;
    
    
    public connectionWithRouterForRequest(SinkRequest request,performRequest pRequest) {
        this.request = request;
        this.perRequest=pRequest;
    }
    
    
    
    @Override
    public void run() {
        try {
            Socket socket =  new Socket("router",9999);
            ObjectOutputStream out =  new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in =  new ObjectInputStream(socket.getInputStream());
            out.writeObject(this.request);
            boolean readed =in.readBoolean();
            if(readed){
             connectionWithRouterForGetRequestResult cForGetRequestResult   = new connectionWithRouterForGetRequestResult(in,out);
             cForGetRequestResult.start();
            }
            out.writeBoolean(true);
        } catch (IOException ex) {
            Logger.getLogger(connectionWithRouterForRequest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(connectionWithRouterForRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SinkRequest getRequest() {
        return request;
    }
    
    
}
