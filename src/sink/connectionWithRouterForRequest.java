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
 * @author JALAL
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
            RC4Security security =  new RC4Security();
            String masterkey  = security.gnerateKey();
            security sec1 =  new security();
            sec1.encrypt(sec1.pubKey,masterkey);
            this.request.setMasterKey(masterkey.getBytes());
            out.writeObject(this.request);
            boolean readed =in.readBoolean();
            if(readed){
            Requests.put(this.request.requestID,this.request);
             connectionWithRouterForGetRequestResult cForGetRequestResult   = new connectionWithRouterForGetRequestResult(in);
             cForGetRequestResult.start();
            }
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
