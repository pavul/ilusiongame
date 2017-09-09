/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1;

import com.ilusion2.gamemanager.GameManager;
import javax.swing.JFrame;

/**
 * this class is the official DEMO 1 using ilusion2 library
 * this will have 4 games
 * 
 * 1.- XPACE
 * 2.- MAZE
 * 3.- TOWER DEFENCE
 * 4.- PLATFORM
 * @author nigthmare
 */
public class Demo1
{
    
    static JFrame frame;
    
     public static void main( String []args )
    {
        
        try
        {
        
            //create initial level here...
            Title title = new Title( 400, 600, 400, 600  );
            
            //create GameManager here ( this is )
             GameManager gm = new GameManager( title , true );
             gm.setFocusable( true );
//             gm.setIgnoreRepaint( true );
//             frame.setIconImage(new BufferedImage( getClass().getClassLoader().getResource("PATH/TO/YourImage.png") ) );
             
            //instanciate frame here
            frame = new JFrame("Demo1");
            frame.setExtendedState( JFrame.MAXIMIZED_BOTH ); 
            frame.setUndecorated( true ); //to quit window bar
            frame.add( gm );
            frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
//            frame.pack();
            
            frame.setSize( gm.getGameSize() );
            frame.setLocationRelativeTo( null ); //for center
            frame.setVisible( true );
            frame.setResizable( false );
            frame.pack();
            
            gm.setgameContainer( frame ) ;
            
            //finally set Game Manager settings and start the game
            gm.setFps( 30 );
            gm.gameStart( );  


            
        }//
        catch( Exception e )
        {
        
        }//
        
        
    }//main
    
    
}//class
