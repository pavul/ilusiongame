/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demo1;

import com.ilusion2.level.GameLevel;
import com.ilusion2.util.Util;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class is used to create levels for a game, if your
 * game will have 8 levels, 1 splash screen, 1 title and 1 menu
 * then you will have to create 11 GameLevels
 * @author pavulzavala
 */
public class Title extends GameLevel
{

    
    Font font;
    
    
    
     public Title(int roomWidth, int roomHeight, int viewWidth, int viewHeight )
     {
         super( roomWidth, roomHeight,viewWidth, viewHeight );
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
            font = Util.getFont( this.getClass() , "PressStart2p.ttf", 12 );
            
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
        
        
        g.setColor( Color.RED );
        g.setFont( font );
        
        g.drawString( "Ilusion2 Game Library", 40, 100);
        
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
        
    }

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

}//class
