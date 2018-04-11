/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package xample;

import demo1.mazexample.MazeXample;
import demo1.platform.Level1;
import com.ilusion2.gamemanager.ImageBackground;
import com.ilusion2.level.GameLevel;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * this class is used to create levels for a game, if your
 * game will have 8 levels, 1 splash screen, 1 title and 1 menu
 * then you will have to create 11 GameLevels
 * @author pavulzavala
 */
public class SpashScreen extends GameLevel
{


    ImageBackground bgSplash;
    
    int step;
    
    
    public SpashScreen(int roomWidth, int roomHeight, int viewWidth, int viewHeight)
    {
        super( roomWidth, roomHeight,viewWidth, viewHeight);
        
    }//
    
    
    
    
    /**
     * this is the main method to update the game, this framework 
     * will be in charge to run the loop and execute
     * oreder of execution
     * update
     * render()
     */
    @Override
    public void update( double delta  ) 
    {
        
        updateControl();
        
        step ++;
        if( step >= 20 )
        {
        step = 0;

        System.out.println("alpha value: "+alpha);
        
        this.alpha += 0.05f;      
        if( alpha >= 1f )alpha = 1f; 
        
        }//
        
   
        
        

        
    }//update

    
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
        
         try 
            {
                bgSplash = new ImageBackground(
                        ImageIO.read( 
//                                this.getClass().getResource( "/splashxample/dukelogo.gif" ) ) , 0, 0 );
                                this.getClass().getResource( "/char1.png" ) ) , 0, 0 );
           
            }
            catch (IOException ex)
            {
                Logger.getLogger(MazeXample.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
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
//        alpha = 0.8f;
        
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
    public void renderBackground(Graphics2D g2) 
    {
     
        
         drawBgColor( g2, Color.GREEN );
       
        
        //alpha = 1f;
        
        this.alphaComposite =
                AlphaComposite.getInstance( 
                        AlphaComposite.SRC_OVER,
//                        AlphaComposite.SRC_OUT,
                        this.alpha );
        
     
        
        ( g2 ).setComposite( alphaComposite );
        
       drawBgColor( g2, Color.YELLOW,0, 0, 200,200 );
        
        
        drawBgImage( 
                g2,
                bgSplash.getImg(),
                100,
                100);
        
        
        
        
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
    public void renderForeground(Graphics2D g2) 
    {
        
    }

    /**
     * use this method to render all HUD stuff
     * render order:
     * background
     * foreground
     * HUD
     * @param g2
     */
    @Override
    public void renderHUD(Graphics2D g2) 
    {
        
    }

    /**
     * this method is used insice update,but this may contain
     * all the logic to control your player
     */
    @Override
    public void updateControl() 
    {
        
       if( mouseControl.isReleased() )
       {
           Level1 lvl = new Level1( 480, 320, 480, 320 );
           gm.loadLvl( lvl );
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
        
    }

    @Override
    public boolean resetLevel() {return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}//class
