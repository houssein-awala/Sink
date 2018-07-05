/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sink;

import java.io.ObjectInputStream;

/**
 *
 * @author mostafa slim
 *
 */
public class connectionWithRouterForGetRequestResult extends Thread
{
    ObjectInputStream reader ;

    public connectionWithRouterForGetRequestResult(ObjectInputStream reader) {
        this.reader = reader;
    }



    
    
    
    @Override
    public void run(){
    
    
    }
}
