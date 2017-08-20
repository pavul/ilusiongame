/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.config.Config;
import com.ilusion2.gamemanager.Camera;
import com.ilusion2.level.GameLevel;
import com.ilusion2.physics.Collision;
import com.ilusion2.sprite.Sprite;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author pavulzavala
 */
public class Level1 extends GameLevel 
{
    String msg = "";
    String sideColision = "";
 
    Sprite player;
    
    float potency; 
    int degrees;
    
    float moveSpeed =1.0f;
  
    
    /**
     * tile settings
     */
     int columns = 60;
     int rows = 20;
     int tileWidth = 16;
     int tileHeigth = 16;
     
     
     
     BufferedImage bufBackground = null;// this.getClass().getResource( "" );
     
     Sprite backgroundTile = null;  //new Sprite( 16,16, bufBackground );
     
    
   
     
     /**
      * background for this level is a tile map
      */
     int [] tileMap = {
     
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
   
     
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,11,11,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
    
     52,52,52,52,52,52,52,52,42,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,42,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,42,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,42,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,42,10,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,42,10,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11, 11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,  0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0    
     };
     
     
     /**
      * this will sspecify which tiles are solid, or whhose of those
      * tiles will colide with player/enemies
      */
     int [] tileColisionMap = {
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    
     0,0,0,0,0,0,0,0,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,2,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1, 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0 
     };
     
    
     int [] slopeArray = {
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,
     0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,
     0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,
     
     0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,
     0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,1,
     0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,1,
     0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,1,
     
     0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,
     0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,1,
     0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,
     0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,
     
     0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,
     0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,1,
     0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,1,
     1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
     };
     
     
     public Level1(int roomWidth, int roomHeight, int viewWidth, int viewHeight)
     {
         super( roomWidth, roomHeight,viewWidth, viewHeight );
      
     msg = "push any button";     
     
     //se activa el control de GPIO
     
     //comment this if u want to run in PC, 
     //enable to run in raspberry and to use GPIO pins as control
//     try
//     {
//     this.initGpioGameControl();
//     gpioGameControl.setGpioListener( this );
//     }
//     catch( Exception e )
//     {e.printStackTrace();}
     
    //creation of camera
     cam = new Camera( roomWidth, roomHeight, viewWidth, viewHeight);
     
     
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
//                       g.translate(cam.getCamx(), cam.getCamy());
                       
                       
                       
                        //acepts key events
                        updateControl();
                    
                        
                       //this will always processing the gravity for player
                        player.processJump();
                      
                          
//                        if player is not jumping we will substract jumpvalue
//                        because processJump is always adding gravity to player
                        if( !player.isJump() )
                        {
                        //remove jump value if player is not jumping
                        player.setY( player.getY( )  - player.getJumpValue() );
                        }
                        
                        
                       //this check tile colision against player
                       //basically is puting the solid tiles of the whole level
                       //enemies, usable objects and players may check this colisions
                       if( Collision.getInstance().checkColsionTile(
                       player,
                       tileColisionMap, 
                       columns,
                       rows, 
                       tileWidth,
                       tileHeigth ).equals( Config.COLISION_BOTTOM ) )
                       {
                             player.setJump( false );
//                             player.setJumpValue( 0 );
//                             player.setY( player.getY( ) - player.getJumpValue() );
                             System.out.println("::: inside collision "+player.getY() );
                       }//
                       
                       
                       if(
                       !Collision.getInstance().
                       checkColsionFree 
                        (
                        player, 2,
                        tileColisionMap, 
                        columns,
                        rows, 
                        tileWidth,
                        tileHeigth
                        ) && !player.isJump() )
                       {
                       player.setJump( true );
                       //player.setJumpValue( 1 );
                       }
//                      
                         
                        

                      
                            
                       
                       
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
            player = new Sprite( 24, 32, ImageIO.read( this.getClass().getResource( "/char1.png" ) )  );
//            player = new Sprite( 16,16, ImageIO.read( this.getClass().getResource( "/char1.png" ) )  );
            
            player.setPosition( 20, 320 - 32 - player.getH() );
            player.setVisible( true );
            
            player.setJumpForce( 5 );
            
//            player.setAnchor(new Point( ( int )player.getHalfWidth(), ( int )player.getH() ));
//            player.setAnchor(new Point( 10 , ( int )player.getH() ));
            
            //player.setJumpValue( 1 );
            
        bufBackground =  ImageIO.read( this.getClass().getResource( "/tiles1.png" ) );
        
        }
        catch( IOException ioe )
        {
            System.out.println("OUT ERROR:");
            ioe.printStackTrace();
        }
        
        backgroundTile = new Sprite( 16, 16, bufBackground );
        
        
        
        
        //set cammera bounds here!
        player.setRoomBoundLeft( 0 );
        player.setRoomBoundRight( roomWidth );
        player.setRoomBoundTop( 0 );
        player.setRoomBoundBottom( roomHeight );
        
        
        cam.setMarginLeft( viewWidth / 3 );
        cam.setMarginRight( (viewWidth / 3) * 2 );
        
        
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
    public void renderBackground(Graphics2D g2) 
    {
        /**
         * this code is very importan!
         * this is making to move the camera when
         * the player is moving.
         */
        g2.translate( cam.getCamx(), cam.getCamy() );
        
        
        //draw blue sky
        drawBgColor( g2, Color.BLACK );
        

        //draw tiles
        drawBgTile( g2 , 
             backgroundTile.getFrames() ,tileMap, columns, rows, tileWidth, tileHeigth );
        ( g2 ).setColor(Color.GREEN);

/**
 * este sirve para mostrar el area donde se puede presionar a la izquierda, toma los valores sin
 * escalar porque los escala la matriz, sin embargo para que puedan ser entendidos por el programa
 * es decir, en el evento del mouse, ahi si se tienen que escalar.
 */
         ( g2 ).  drawRect(0, 0, 240, 320);
//        0, 0, )
    }

    @Override
    public void renderForeground(Graphics2D g2) 
    {
        
        player.draw( g2 );
        
        
        g2.setColor( Color.red );
//        g2.drawString("[]", player.getX() + player.getAnchor().x, player.getY( ) + player.getAnchor().y );
//        player//.drawBalisticTrayectory((Graphics2D)g );
//        .drawBalisticTrayectory( g2 ,
//            potency, 
//            degrees,
//            20);
        //player.drawRotate( (Graphics2D)g , playerdegrees );
        
        
    }

    @Override
    public void renderHUD(Graphics2D g2 )
    {
        
        g2.setColor( Color.WHITE );
        g2.drawString( "deg: "+degrees+" pot: "+potency , 20, 20 );
        
    }//

    @Override
    public void updateControl() 
    { 
     
        
//          else if(keyControl.isKeyDown(KeyEvent.VK_UP))
//            {
//                player.setSubanimation(AnimationState.MOVEUP);
//            player.move(0, -3);
//                 if(player.getY() < cam.getOffsetY() + cam.getMarginTop())
//                { cam.moveY(3); }  
//            } 
//            else if(keyControl.isKeyDown(KeyEvent.VK_DOWN))
//            {
//                if(player.getY()+player.getH() > cam.getOffsetY()  +cam.getMarginBottom())
//                { cam.moveY(- 3); }  
//            } 
        
        
        
        //keyboard control
            if(keyControl.isKeyDown( KeyEvent.VK_RIGHT ))
            {
                player.moveSpeedX( moveSpeed );
                
                if( player.getX() + player.getW() > cam.getOffsetX() + cam.getMarginRight() )
                { cam.moveX( - 3 ); } 
                System.out.println("--> "+cam.getOffsetX());
            }
            if(keyControl.isKeyDown( KeyEvent.VK_LEFT ))
            {
                player.moveSpeedX( -moveSpeed );
                
                 if( player.getX() < cam.getOffsetX() + cam.getMarginLeft() )
                { cam.moveX( 3 ); } 
                
            }
            //this is ISKEYPRESS because it only executes once when
            //the key is presed
            if(keyControl.isKeyPress( KeyEvent.VK_SPACE ) )
            {
                System.out.println("jumping");
                player.setJump( true );
                player.setJumpValue( 5 );
            } 
            
            
            if(keyControl.isKeyPress( KeyEvent.VK_1 ) )
            {
                potency +=1;
            }
            
            if(keyControl.isKeyPress( KeyEvent.VK_2 ) )
            {
                degrees +=1; if( degrees >=360 )degrees = 1;
            }
            
            if(keyControl.isKeyPress( KeyEvent.VK_3 ) )
            {
                potency -=1;
            }
            
            if(keyControl.isKeyPress( KeyEvent.VK_4 ) )
            {
                degrees -=1; if( degrees <= 0 )degrees = 360;
            }
            
            
            
/**
 * mouse control 
 */
 
//area values to check if are pressed on a certain point, must be multiplied by
//the scale, because it changes completely the pixels on screen 
if( mouseControl.areaPresed(0, 0, (int)(240.0 * xScale)  , (int)(320.0 * yScale) ) )
{
    System.out.println(" ::: pressed mid area ");
}



if( mouseControl.spritePressed( player ) )
{
    System.out.println(" ::: pressed player "+mouseControl.getPointPressed().getX()
    +" - "+mouseControl.getPointPressed().getY() );
}//


/**
 * this is the functionality to create swipes in this case in X axis
 */
if( mouseControl.isReleased() )
{
    //every time mouse is pressed then, isReleased = true
    mouseControl.setReleased( false );
    
    if( mouseControl.swipeRigth( 100 )  )
        {
            
            System.out.println("::: swipe Righ done! ");
        }
    else if( mouseControl.swipeLeft( 100 )  ) 
        {
        //swipe = MouseControl.SWIPE_LEFT;
        System.out.println("::: swipe left done! ");
        
        }
        
    
    
  getGameManager().loadLvl( "rotationXample" );
    
}//if mouse control released



if(mouseControl.isPressed())
{

//    turret.calculateAngle(
//            (int)mouseControl.getPointPressed().getX(), 
//            (int)mouseControl.getPointPressed().getY() );
    
}//is mouse button is pressed


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

    @Override
    public boolean resetLevel() {return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

 
}//class
