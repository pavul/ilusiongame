/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.control.GpioGameControl;
import com.ilusion2.control.GpioGameControlListener;
import com.ilusion2.level.GameLevel;
import com.ilusion2.room.GameState;
import com.ilusion2.room.ImageBackground;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author pavulzavala
 */
public class Level1 extends GameLevel 
{
    String msg = "";
    
     public Level1(int roomWidth, int roomHeight, int viewWidth, int viewHeight,
           ArrayList<ImageBackground> imgbg)
     {
     super( roomWidth, roomHeight,viewWidth, viewHeight, imgbg );
      
     msg = "push any button";     
     
     //se activa el control de GPIO

     try
     {
     this.initGpioGameControl();
     this.
     gpioGameControl.setGpioListener( this );
     }
     catch( Exception e )
     {e.printStackTrace();}
     
    
     
     
     
     }//const1
    
    
    @Override
    public void update() 
    {
         switch( gameState )
               {
                   case LOADING:
                       //process to show loading screen
                       break;
                   case PLAYING:
                       //put gameplay code here
                       break;
                   case GAMEOVER:
                       //put game over code here
                       break;
                   case COMPLETED:
                       //put code when a game is completed here
                       break;
                   case PAUSED:
                       
                       //show here the PAUSE message or screen
                       
                       break;
                   case STOPPED:
                       //put code when level is stopped here
                       break;
                   case DIALOGUING:
                       //put code when characters are dialoguing here
                       break;
               }//such
        
    
        
    }//update

    @Override
    public boolean initSprite() 
    {
        return true;
    }

    @Override
    public boolean initBg() {
        return true;
    }

    @Override
    public boolean initHud() {
        return true;
    }

    @Override
    public boolean initSound() {
        return true;
    }

    @Override
    public boolean initData() {
        return true;
    }

    @Override
    public void renderBackground(Graphics g) 
    {
        g.setColor( Color.WHITE );
        g.fillRect( 0, 0,roomWidth, roomHeight );
        
    }

    @Override
    public void renderForeground(Graphics g) {
    }

    @Override
    public void renderHUD(Graphics g) 
    {
        
        g.setColor( Color.BLACK );
        g.drawString( msg, 20, 20 );
        
    }

    @Override
    public void updateControl() {
    }

    @Override
    public void manageNetworkData() throws IOException {
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) 
    {
    
         //buttons pressed
        if( event.getState().equals( gpioGameControl.getBtnStateLow() ) )
        {
        
            if( event.getPin().equals( gpioGameControl.getLeftPad() ) )
            {
             msg = "left pad";
            }
            if( event.getPin().equals( gpioGameControl.getRigthPad()) )
            {msg = "rigth pad";
            }
            if( event.getPin().equals( gpioGameControl.getUpPad()) )
            {msg = "up pad";
            }
            if( event.getPin().equals( gpioGameControl.getDownPad()) )
            {msg = "down pad";
            }
            if( event.getPin().equals( gpioGameControl.getGoBtn()) )
            {msg = "go button";
            }
            if( event.getPin().equals( gpioGameControl.getRedBtn()) )
            {msg = "red button";
            }
            if( event.getPin().equals( gpioGameControl.getGreenBtn()) )
            {msg = " green button";
            }
            if( event.getPin().equals( gpioGameControl.getBlueBtn()) )
            {msg = "blue button";
            }
            if( event.getPin().equals( gpioGameControl.getAlphaBtn()) )
            {msg = "alpha button";
            }
            
        }
        else //buttons released
        {
        
        }
        
    }//
 
}//class
