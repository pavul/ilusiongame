/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.config.Config;
import com.ilusion2.control.GpioGameControl;
import com.ilusion2.control.GpioGameControlListener;
import com.ilusion2.level.GameLevel;
import com.ilusion2.physics.Collision;
import com.ilusion2.room.GameState;
import com.ilusion2.room.ImageBackground;
import com.ilusion2.sprite.Sprite;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author pavulzavala
 */
public class Level1 extends GameLevel 
{
    String msg = "";
 
    Sprite player;
    
    
     int columns = 30;
     int rows = 20;
     int tileWidth = 16;
     int tileHeigth = 16;
     
     
     
     BufferedImage bufBackground = null;// this.getClass().getResource( "" );
     
     Sprite backgroundTile = null;  //new Sprite( 16,16, bufBackground );
                                    
     
     
     int [] tileMap = {
     
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,11,11,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
    
     52,52,52,52,52,52,52,52,52,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,10,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0   
     };
     
     
     int [] tileColisionMap = {
      0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    
     0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0   
     };
     
    
    
    
    
    
     public Level1(int roomWidth, int roomHeight, int viewWidth, int viewHeight,
           ArrayList<ImageBackground> imgbg)
     {
     
         super( roomWidth, roomHeight,viewWidth, viewHeight, imgbg );
      
     msg = "push any button";     
     
     
     
     
     
     //se activa el control de GPIO
     
     //comment this if u want to run in PC, enable to run in raspberry
//     try
//     {
//     this.initGpioGameControl();
//     gpioGameControl.setGpioListener( this );
//     }
//     catch( Exception e )
//     {e.printStackTrace();}
     
    
     
     
     
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
                       
                       
                       //acepts key events
                       updateControl();
                       
                       
                       //when jump = true for player object
                       //this function will process all the jump
                       player.processJump();
                       
                       //this check tile colision against player
                       //basically is puting the solid tiles of the whole level
                       //enemies, usable objects and players may check this colisions
                       if( !Collision.getInstance().checkColsionTile(
                               player,
                               tileColisionMap, 
                               columns,
                               rows, 
                               tileWidth,
                               tileHeigth ).equals( Config.COLISION_NONE ) )
                       {
                           player.setJump( false );
                       }//
                       
                       
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
        
        try
        {
            player = new Sprite( 21, 32, ImageIO.read( this.getClass().getResource( "/char1.png" ) )  );
            
            player.setPosition( 20, 320 - 32 - player.getH() );
            player.setVisible( true );
            
            player.setJumpForce( 5 );
            player.setJumpValue( 1 );
            
        bufBackground =  ImageIO.read( this.getClass().getResource( "/tiles1.png" ) );
        }
        catch( IOException ioe )
        {
            System.out.println("OUT ERROR:");
            ioe.printStackTrace();
        }
        
     backgroundTile = new Sprite( 16, 16, bufBackground );
        
        
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
        
        //draw blue sky
        drawBgColor( ( Graphics2D )g , Color.BLACK );
        

        //draw tiles
        drawBgTile( ( Graphics2D )g , 
             backgroundTile.getFrames() ,tileMap, columns, rows, tileWidth, tileHeigth );
        
    }

    @Override
    public void renderForeground(Graphics g) 
    {
        
        player.draw( (Graphics2D)g );
        
    }

    @Override
    public void renderHUD(Graphics g) 
    {
        
        g.setColor( Color.BLACK );
        g.drawString( msg, 20, 20 );
        
    }

    @Override
    public void updateControl() 
    {
     
        //keyboard control
            if(keyControl.isKeyDown( KeyEvent.VK_RIGHT ))
            {
                player.moveSpeedX( 3 );
            }
            else if(keyControl.isKeyDown( KeyEvent.VK_LEFT ))
            {
                player.moveSpeedX( -3 );
            }
            //this is ISKEYPRESS because it only executes once when
            //the key is presed
            else if(keyControl.isKeyPress( KeyEvent.VK_SPACE ) )
            {
                System.out.println("jumping");
                player.setJump( true );
            } 
            
            
         //mouse control
//        mouseControl.mouseReleased( MouseEvent.MOUSE_CLICKED );
         
         
    }//

    @Override
    public void manageNetworkData() throws IOException {
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) 
    {
    
         //buttons pressed
        //if there is BTNSTATELOW it means at least one button was pressed
        if( event.getState().equals( gpioGameControl.getBtnStateLow() ) )
        {
        
            //if we now that a button was pressed, then we have to look
            //which is
            if( event.getPin().equals( gpioGameControl.getLeftPad() ) )
            {
             msg = "left pad";
             player.moveSpeedX( -3 );
            }
            if( event.getPin().equals( gpioGameControl.getRigthPad()) )
            {
                msg = "rigth pad";
                player.moveSpeedX( 3 );
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
        {}
        
    }//
 
}//class
