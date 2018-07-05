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
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import static sink.Sink.Requests;

/**
 *
 * @author mostafa slim 
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
            Frame frame = (Frame)in.readObject();
            byte[] pubkey = frame.data;
            RC4Security sec1 =  new RC4Security();
            String masterkey  = sec1.gnerateKey();
            X509EncodedKeySpec ks = new X509EncodedKeySpec(pubkey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(ks);
            byte[] encryotedmasterkey =  security.encrypt(publicKey,masterkey);
            this.request.setMasterKey(encryotedmasterkey);
            this.request=(SinkRequest)in.readObject();
            Requests.put(this.request.requestID,masterkey);
            this.pRequest.notify();
    } catch (IOException ex) {
        Logger.getLogger(connectionWithSupervisorForRequest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(connectionWithSupervisorForRequest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (NoSuchAlgorithmException ex) {
        Logger.getLogger(connectionWithSupervisorForRequest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidKeySpecException ex) {
        Logger.getLogger(connectionWithSupervisorForRequest.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
        Logger.getLogger(connectionWithSupervisorForRequest.class.getName()).log(Level.SEVERE, null, ex);
    }
}

    public SinkRequest getRequest() {
        return request;
    }


}
