/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.level.GameLevel;
import com.ilusion2.room.Room;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author pavulzavala
 */
public class Game 
{
    
     static JFrame frame;
    
    
    
    public static void main(String []args)
    {
  

        //se agrega el nivel al stack de niveles
        Map<String, GameLevel> levelStack =  new HashMap<>();
        levelStack.put("first", new Level1( 480, 320, 480, 320, null));
        
        try 
        {
          //        se crea el room, este contiene a todos los niveles
        Room room = new Room( "first", levelStack );
        
        room.setFocusable(true);
        room.setIgnoreRepaint(true);
        frame= new JFrame("game xample");
        frame.add( room );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize( 480, 320 );
        frame.setVisible(true);
        frame.setResizable(false);

//        //este va al final, porque se debe de renderizar a lo ultim
room.setFps( 30 );
room.gameStart( );  
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        

        
    }//main
    
    
}//
