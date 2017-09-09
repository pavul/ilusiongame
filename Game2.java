/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;


import com.ilusion2.gamemanager.GameManager;
import javax.swing.JFrame;

/**
 * this class implement the other constructor of GameManager
 * @author pavulzavala
 */
public class Game2 
{
    
     static JFrame frame;
    
    
    
    public static void main(String []args)
    {
  
        try 
        {
            
            SpashScreen spash =
            new SpashScreen(400, 400, 400, 400 );
            
            //se crea el room, este contiene a todos los niveles
            //this creates the room container that will have all levels and
            //will set the screen to full screen
            GameManager room = new GameManager( spash , !true );
        
            room.setFocusable( true );
            //room.setIgnoreRepaint( true );
        
        frame = new JFrame("game xample");
        
        
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated( false ); //to quit window bar
        frame.add( room );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        
        //frame.setSize( 480, 320 );

        //this resize the frame to the correct scaled width and heigth 
        //of the of teh scaled view port
        frame.setSize( room.getGameSize() );
        
        frame.setLocationRelativeTo( null ); //for center
        frame.setVisible(true);
        frame.setResizable(false);
        

        //este va al final, porque se debe de renderizar a lo ultim
        room.setFps( 30 );
        room.gameStart( );  
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
        

        
    }//main
    
    
}//
