/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.config.Config;
import com.ilusion2.gamemanager.Camera;
import com.ilusion2.gamemanager.GameState;
import com.ilusion2.inventory.Inventory;
import com.ilusion2.inventory.Item;
import com.ilusion2.level.GameLevel;
import com.ilusion2.physics.Collision;
import com.ilusion2.sprite.Sprite;
import com.ilusion2.util.Util;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import tile.Tile;

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
     
     
     boolean moveLeft, moveRight, moveUp, moveDown;
     
     
     BufferedImage bufBackground = null;// this.getClass().getResource( "" );
     
     Sprite backgroundTile = null;  //new Sprite( 16,16, bufBackground );
     
     
     Sprite[] coinArray;
    
     /**
      * list to save non transparent tiles to show as background
      */
     List<Tile> bgTileList;
     
     /**
      * tile list to store all solid tiles
      */
     List<Tile> colTileList;
   
     
     
     List<Item> itemList;
     Inventory inventory;
     
     
     
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
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,11,11,11,11,11,11,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,11,11,11,11,11,11,11,11,11,11,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,11,11,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
   
     
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 52,52,52,11,11,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52, 11,11,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,52,52,52,52,11,11,52,52,52,52,52,52,52,52,52,52,52,52,52,52,11, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,52,52,11,11,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,11,52, 52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
    
     52,52,52,52,52,52,52,52,42,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,11,52,52, 52,52,52,52,52,52,52,52,42,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,52,42,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,11,52,52,52, 52,52,52,52,52,52,52,42,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
     52,52,52,52,52,52,42,10,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,11,52,52,52,52, 52,52,52,52,52,52,42,10,10,10,10,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,52,
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
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, 1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
    
     0,0,0,0,0,0,0,0,2,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,0,2,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     0,0,0,0,0,0,2,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
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
                       
                       
                       
                       
                       //to move with gpio pins buttons
                       if( moveLeft )
                       {
                       player.move( -3, 0 );
                       }
                       if( moveRight )
                       {
                       player.move( 3, 0 );
                       }
                       
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
//                       if( Collision.getInstance().checkColsionTile(
//                       player,
//                       tileColisionMap, 
//                       columns,
//                       rows, 
//                       tileWidth,
//                       tileHeigth ).equals( Config.COLISION_BOTTOM ) )
//                       {
//                             player.setJump( false );
//                       }//
                       
                      
                        //@NEW APPROACH
                        if ( Collision.getInstance().checkColsionTile(player, colTileList)
                                .equals( Config.COLISION_BOTTOM ) )
                        {
                            player.setJump( false );
                        }//
                        
                        
                        
                        //@OLD APROACH
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
                       }
                         
                        

                      //@NEW APPROACH
//                       if(  )
                            
                       
                       
                       //check collision with coins
                       for( int ii = 0; ii < coinArray.length; ii++ )
                       {
                           
                           //if there is a collision with a coin
                           //and the coin is visible, add that to
                           //item qty
                            if( Collision.getInstance()
                               .rectangleColision(player, coinArray[ ii ]  )
                                    && coinArray[ ii ].isVisible() )
                            {
                                
                                itemList.get( 1 ).setCurrentQty(
                                itemList.get( 1 ).getCurrentQty() + 1
                                );
                                
                                coinArray[ ii ].setVisible( false );
                                
                            }
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
                       updatePrivateControl();
                       
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
        
        
        
        coinArray = new Sprite[ 10 ];
        
        for( int i = 0; i < coinArray.length; i++ )
        {
            
            try 
            {
                coinArray[ i ] = new Sprite( Util.getImage( "/level1/coin.png" ) );
                coinArray[ i ].setVisible( true );
            }
            catch (IOException ex)
            {
                Logger.getLogger(Level1.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        }//
        
        coinArray[ 0 ].setPosition( 10*16, 13*16 );
        coinArray[ 1 ].setPosition( 11*16, 17*16 );
        coinArray[ 2 ].setPosition( 14*16, 12*16 );
        coinArray[ 3 ].setPosition( 30*16, 11*16 );
        coinArray[ 4 ].setPosition( 34*16, 10*16 );
        coinArray[ 5 ].setPosition( 37*16, 8*16 );
        coinArray[ 6 ].setPosition( 48*16, 7*16 );
        coinArray[ 7 ].setPosition( 49*16, 5*16 );
        coinArray[ 8 ].setPosition( 55*16, 5*16 );
        coinArray[ 9 ].setPosition( 59*16, 17*16 );
        
        
        
        return true;
    }//

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
    public boolean initData() 
    {
        
        //retrieving bg tiles to show in screen
        bgTileList = Util.getTileList(
                tileMap,
                tileWidth, 
                tileHeigth, 
                columns, 
                rows,
                new int[]{0,10,11}
        );
        
        
        //retrieving solid tiles to avoid
        //process with all tiles on array
        colTileList =  Util.getTileList(
                tileColisionMap, 
                tileWidth,
                tileHeigth, 
                columns, 
                rows, 
                1);
        
//        System.out.println(" bg: "+bgTileList.size() +" - col: " + colTileList.size());
        
        try
        {
            itemList = new ArrayList<>();        
            itemList.add( new Item(0,3,0,10, (BufferedImage) Util.getImage("/level1/headlife.png")  ,"lives","","") );
            itemList.add( new Item(0,0,0,10, (BufferedImage) Util.getImage("/level1/coin.png")  ,"coin","","") );
        
        }
        catch (IOException ex)
        {
            Logger.getLogger(Level1.class.getName()).log(Level.SEVERE, null, ex);
        }

        inventory = new Inventory(itemList, 0, 0);
        
        
        return true;
    }

    @Override
    public void renderBackground(Graphics2D g2) 
    {
        
        
//        System.out.println("camera :"+cam.getCamx() +" : "+cam.getCamy());
        /**
         * this code is very importan!
         * this is making to move the camera when
         * the player is moving.
         */
        g2.translate( cam.getCamx(), cam.getCamy() );
        
        
        //draw blue sky
        drawBgColor( g2, Color.cyan );
        
        //@OLD WAY
        //draw tiles
//        drawBgTile( g2 , 
//             backgroundTile.getFrames() ,tileMap, columns, rows, tileWidth, tileHeigth );
       
        

        //@NEW WAY
        bgTileList.forEach( ( tile )->
        {
            g2.drawImage( backgroundTile.getFrames()[ tile.getValue() ] , tile.getX(), tile.getY(), null);
        });

        
        
        
//        ( g2 ).setColor(Color.GREEN);
//
///**
// * este sirve para mostrar el area donde se puede presionar a la izquierda, toma los valores sin
// * escalar porque los escala la matriz, sin embargo para que puedan ser entendidos por el programa
// * es decir, en el evento del mouse, ahi si se tienen que escalar.
// */
//         ( g2 ).  drawRect(0, 0, 240, 320);
////        0, 0, )
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
        
       
         for( int i = 0; i < coinArray.length; i++ )
         {
                coinArray[ i ].draw(g2);
         }//
        
        
        
    }//

    @Override
    public void renderHUD(Graphics2D g2 )
    {
        g2.setColor( Color.black );
        
        switch( gameState )
        {
        
            case PAUSED:
                
                g2.drawString( "Game Paused" , cam.getOffsetX() + 200, cam.getOffsetY() + 100 );
                
            case PLAYING:
                //those setters can be changed to when button left and rigth are pressed
                inventory.setX( cam.getOffsetX() + 20 );
                inventory.setY( cam.getOffsetY() + 20 );

                inventory.drawLine( g2, true );

                break;
            
        }
        
        
        
        //g2.drawString( "deg: "+degrees+" pot: "+potency , 20, 20 );
        

        
    }//

    @Override
    public void updateControl() 
    { 
     
        //keyboard control
            if(keyControl.isKeyDown( KeyEvent.VK_RIGHT ))
            {
                player.moveSpeedX( moveSpeed );
                
                if( player.getX() + player.getW() > cam.getOffsetX() + cam.getMarginRight() )
                { cam.moveX( - 3 ); } 
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
            
         
            /**
             * for other example
             */
//            if(keyControl.isKeyPress( KeyEvent.VK_1 ) )
//            {
//                potency +=1;
//            }
//            
//            if(keyControl.isKeyPress( KeyEvent.VK_2 ) )
//            {
//                degrees +=1; if( degrees >=360 )degrees = 1;
//            }
//            
//            if(keyControl.isKeyPress( KeyEvent.VK_3 ) )
//            {
//                potency -=1;
//            }
//            
//            if(keyControl.isKeyPress( KeyEvent.VK_4 ) )
//            {
//                degrees -=1; if( degrees <= 0 )degrees = 360;
//            }
            
            
             if( keyControl.isKeyPress( KeyEvent.VK_ENTER ) )
            {
                gameState =  GameState.PAUSED;   
//                musicPlayer.pause();
            }//
            
            
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
//             player.moveSpeedX( -3 );
            moveLeft = true;
            }
            if( event.getPin().equals( gpioGameControl.getRigthPad()) )
            {
                msg = "rigth pad";
//                player.moveSpeedX( 3 );
            moveRight = true;
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
            System.out.println("buttons released");
            
            moveLeft = false;
            moveRight = false;
            
        
        }
        
    }//

    @Override
    public boolean resetLevel() {return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    private void updatePrivateControl()
    {
        if( keyControl.isKeyPress( KeyEvent.VK_ENTER ) )
            {
                gameState =  GameState.PLAYING;   
//                musicPlayer.pause();
            }//
        
    }//
    
    
 
}//class
