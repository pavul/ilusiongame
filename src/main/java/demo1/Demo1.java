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
            Title title = new Title( 640,480 ,640 ,480 );
            
            //create GameManager here ( this is )
             GameManager gm = new GameManager( title , false );//change null for your level
             gm.setFocusable( true );
            
            //instanciate frame here
            frame = new JFrame("Demo1");
            //frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            frame.setUndecorated( false ); //to quit window bar
            frame.add( gm );
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            
            System.out.println( "-> "+gm.getScaledWindowSize().getSize()
                    + "");
            
            frame.setSize( gm.getScaledWindowSize() );
            frame.setLocationRelativeTo( null ); //for center
            frame.setVisible( true );
            frame.setResizable( false );
            
            //finally set Game Manager settings and start the game
            gm.setFps( 30 );
            gm.gameStart( );  


            
        }//
        catch( Exception e )
        {
        
        }//
        
        
    }//main
    
    
}//class
