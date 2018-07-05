/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sink;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JALAL
 */
public class performRequest extends Thread{

    public SinkRequest request;
    
    
  public performRequest(SinkRequest request)
  {
  this.request=request;
  }
  
    @Override
    public void run(){
        try {
            connectionWithSupervisorForRequest cWithSupervisorForRequest =  new connectionWithSupervisorForRequest(request,this);
            cWithSupervisorForRequest.start();
            this.wait();
           //connect with the supervisor and wait 
            this.request=cWithSupervisorForRequest.getRequest();
            
            // take the configuration from the supervisor
            
            connectionWithRouterForRequest cWithRouterForRequest = new connectionWithRouterForRequest(request, this);
            cWithRouterForRequest.start();
            
            // connect to the sensor and wait it 
            this.request=cWithRouterForRequest.getRequest();
            // this is the final request returned from the sensor and contain the data replying from sensor you can  display it 
        } catch (InterruptedException ex) {
            Logger.getLogger(performRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
  
}
