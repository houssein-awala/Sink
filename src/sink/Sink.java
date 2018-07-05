/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sink;

import java.awt.Point;
import java.util.HashMap;

/**
 *
 * @author Mostafa slim
 */
public class Sink {

public static HashMap<Integer,SinkRequest> Requests= new HashMap<Integer,SinkRequest>();


public Sink() {
        
    }


public void getData(Point position,int TypeOfService){
    
    SinkRequest request= new SinkRequest(TypeOfService,position);
   
    performRequest pRequest=new performRequest(request);
    pRequest.start();
}

}
