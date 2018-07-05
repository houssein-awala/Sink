/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sink;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mostafa slim
 *
 */
public class connectionWithRouterForGetRequestResult extends Thread
{
    ObjectInputStream reader ;
    ObjectOutputStream out;
    public connectionWithRouterForGetRequestResult(ObjectInputStream reader,ObjectOutputStream out) {
        this.reader = reader;
        this.out=out;
    }



    
    
    
    @Override
    public void run(){
        try {
            SinkRequest request= (SinkRequest)reader.readObject();
            int requestid=request.requestID;
            if(Sink.Requests.containsKey(requestid))                    
            {
                RC4Security sec = new RC4Security();
               String result =  sec.decrypt(Sink.Requests.get(requestid),"RC4",request.result);
              System.out.println(result);
              Sink.Requests.remove(requestid);            }
                    } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(connectionWithRouterForGetRequestResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
