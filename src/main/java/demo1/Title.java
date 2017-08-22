
package demo1;

import com.ilusion2.level.GameLevel;
import com.ilusion2.util.Util;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import demo1.xpace.XpaceGame;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import xample.Level1;
import xample.MazeXample;

/**
 * this class is used to create levels for a game, if your
 * game will have 8 levels, 1 splash screen, 1 title and 1 menu
 * then you will have to create 11 GameLevels
 * @author pavulzavala
 */
public class Title extends GameLevel
{

    
    Font font;
    
    final int X_CURSOR = 80;
          int yCursor = 100;
    
    byte selector = 1;
    
     public Title(int roomWidth, int roomHeight, int viewWidth, int viewHeight )
     {
         super( roomWidth, roomHeight,viewWidth, viewHeight );
         
         
     //comment this if u want to run in PC, 
     //enable to run in raspberry and to use GPIO pins as control
//     try
//     {
//     this.initGpioGameControl();
//     gpioGameControl.setGpioListener( this );
//     }
//     catch( Exception e )
//     {e.printStackTrace();}
    
         
     }
    
    /**
     * this is the main method to update the game, this framework 
     * will be in charge to run the loop and execute
     * oreder of execution
     * update
     * render()
     */
    @Override
    public void update() 
    {
        //
        updateControl();
        
        
    }//

    
     /**
     * init all your sprites here
     * @return 
     */
    @Override
    public boolean initSprite() {
        return false;
    }

     /**
     * init all backgrounds resources here
     * @return 
     */
    @Override
    public boolean initBg() 
    {
        return false;
    }

    
     /**
     * init all HUD resources here
     * @return 
     */
    @Override
    public boolean initHud() 
    {
        return false;
    }

    /**
     * init all sound resources here
     * @return 
     */
    @Override
    public boolean initSound() 
    {
        return false;
    }

    /**
     * this function is used to init all data to use for
     * this game, arrays, get property/text files, make
     * db connections, etc.
     * @return 
     */
    @Override
    public boolean initData() 
    {
        
        try 
        {
            font = Util.getFont( this.getClass() , "/demo1/PressStart2P.ttf", 12 );
            
        } 
        catch ( IOException | FontFormatException ex )
        {
            Logger.getLogger( Title.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    /**
     * use this method to render background images, tiles, 
     * parallax, etc
     * render order:
     * background
     * foreground
     * HUD
     * @param g 
     */
    @Override
    public void renderBackground(Graphics2D g) 
    {
       
        drawBgColor( g, Color.black );
  
        
    }//

    
    /**
     * use this method to render player, enemies, interactive
     * objects, etc
     * render order:
     * background
     * foreground
     * HUD
     * @param g 
     */
    @Override
    public void renderForeground(Graphics2D g) 
    {
        
//        System.out.println("font: "+font.getName() );
        
        g.setColor( Color.white );
        g.setFont( font );
        
        g.drawString( "Ilusion2 Game Library", 40, 20);
        
        
//        g.setColor( Color.RED );
//        g.setFont( font );
//        
//        g.drawString( "Ilusion2 Game Library", 40, 100);
        
        g.fillOval(X_CURSOR, yCursor -10, 10, 10 );

        g.drawString( "1.- Xpace", 100, 100);
        g.drawString( "2.- Maze", 100, 130);
        g.drawString( "3.- Tower Defence", 100, 160);
        g.drawString( "4.- Platform", 100, 190);
        
        
    }//

    /**
     * use this method to render all HUD stuff
     * render order:
     * background
     * foreground
     * HUD
     * @param g 
     */
    @Override
    public void renderHUD(Graphics2D g) 
    {
        
    }

    /**
     * this method is used insice update,but this may contain
     * all the logic to control your player
     */
    @Override
    public void updateControl() 
    {
       
        if( keyControl.isKeyPress( KeyEvent.VK_DOWN ) )
        {
            yCursor +=30;
            if( yCursor >=190 )yCursor = 190;
            
            selector++;
            if(selector>=4)selector=4;
        }
        else if( keyControl.isKeyPress( KeyEvent.VK_UP ) )
        {
            yCursor -=30;
            if( yCursor <=100 )yCursor = 100;
            
            selector--;
            if(selector<=1)selector=1;
            
        } 
        else if( keyControl.isKeyPress(KeyEvent.VK_ENTER) )
        {
        chooseLvl();
        }
        
    }//

    /**
     * if your game will have connection via socket or througth
     * network use this method to handle all of that
     * @throws IOException 
     */
    @Override
    public void manageNetworkData() throws IOException 
    {
        
    }

    /**
     * handle GPIO buttons of rpi 3B+, zero ( with 40 Pins ) here...
     * @param gpdsce 
     */
    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) 
    {
       
        if( gpdsce.getState().equals( gpioGameControl.getBtnStateLow() ) )
        {
        
              if( gpdsce.getPin().equals( gpioGameControl.getGoBtn()) )
            {
                    chooseLvl();
            }
              if( gpdsce.getPin().equals( gpioGameControl.getDownPad()) )
            {
                  selector++;
                  if(selector >= 4)selector = 4; 
            }
              if( gpdsce.getPin().equals( gpioGameControl.getUpPad()) )
            {
                   selector--;
                   if(selector <= 1)selector = 1;
            
            }
            
        
        }//
        
        
        
    }//

    @Override
    public boolean resetLevel() {return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    private void chooseLvl()
    {
        System.out.println( "::: entrar a "+selector );
            
            switch( selector )
            {
                case 1:
                    gm.loadLvl( new XpaceGame( 400, 600, 400, 600 ) );
                    break;
                case 2:
                    gm.loadLvl( new MazeXample( 320,240, 320,240 ) );
                    break;
                case 3:
                    ///gm.loadLvl( new MazeXample( 320,240, 320,240 ) );
                    break;
                case 4:
                    gm.loadLvl( new Level1( 960, 320, 480, 320 ) );
                    break;
            }
    }//
    
}//class
