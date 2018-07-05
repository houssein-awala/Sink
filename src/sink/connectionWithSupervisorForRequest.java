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

/**
 *
 * @author JALAL
 */
public class connectionWithSupervisorForRequest extends Thread{

public SinkRequest request;
public performRequest pRequest;




public connectionWithSupervisorForRequest(SinkRequest request,performRequest peRequest) {
        this.request = request;
        this.pRequest=peRequest;
    }


@Override
public void run()
{try {
        Socket socket = new Socket("localhost",8888);
            ObjectInputStream in  = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(request);
            this.request=(SinkRequest)in.readObject();
            this.pRequest.notify();
    } catch (IOException ex) {
        Logger.getLogger(connectionWithSupervisorForRequest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(connectionWithSupervisorForRequest.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public SinkRequest getRequest() {
        return request;
    }


}
