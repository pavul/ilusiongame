
package demo1.xpace;

import com.ilusion2.gamemanager.GameState;
import com.ilusion2.level.GameLevel;
import com.ilusion2.physics.Collision;
import com.ilusion2.sprite.Sprite;
import com.ilusion2.util.Util;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javazoom.jl.decoder.JavaLayerException;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;

/**
 * this class is used to create levels for a game, if your
 * game will have 8 levels, 1 splash screen, 1 title and 1 menu
 * then you will have to create 11 GameLevels
 * @author pavulzavala
 * 
 * 400 width x 600 height
 */
public class XpaceGame extends GameLevel
{
    //to generate random numbers
    Random rand;
    
    //HUD variables for games
    String shieldLbl = "Shield: ";
    String missileLbl = "Missile: ";
    String scoreLbl = "Score: ";
    
    //initial speed of the ship
    float shipSpeed = 2;
    
    //those variables are to handle animation of the ship
    byte animCounter = 0;
    byte curFrame = 2;
    byte shipMovement = -1;
    
    
    //some values for the HUD
    byte shield = 100;
    byte missiles = 5;
    int  score = 0;
    
    //weapon selected initiali plasma
    byte selectedWpn = 3;

    //sounter to span enemies certain number of frames
    byte spawnEnemyCounter = 0;
    
    
    
    Font font;
    
    /**
     * spacial ship of the game (player)
     */
    Sprite ship;
    
    /**
     * enemies of the game
     */
    Sprite ene1, ene2, ene3, ene4, ene5, ene6;
    
    /**
     * bullet and misiles
     */
    Sprite plasmaBullet, laserBullet, waveBullet, missile;
    
    /**
     * sprites for power ups
     */
    Sprite missilePowerUp, plasmaPowerUp, shieldPowerUp, speedPowerUp, wavePowerUp, laserPowerUp;
    
    /**
     * bottom bar to show hud
     */
    Sprite bottomHUD;
    
    //pools de enemies bullets and power ups
    List<Sprite> enemyList;
    List<Sprite> bulletList;
    List<Sprite> powerUpList;
    
    Image[] enemyImgArray;
    
    
//    final URL resource = getClass().getResource( "/demo1/xpace/lasershoot.wav" );
//    AudioClip sound = new AudioClip( resource.toString() ) ;
    
    
    Image[] explosion;
    
    Image[] ene1Sprite;
    Image[] ene2Sprite;
    Image[] ene3Sprite;
    Image[] ene4Sprite;
    Image[] ene5Sprite;
    Image[] ene6Sprite;
     
    
    
    //variables to show stars
    byte smallStarRadius = 2;
    byte bigStarRadius = 4;
    
    Point[ ] smalStarList;
    Point[ ] bigStarList;
    byte smallStarSpeed = 1;
    byte bigStarSpeed = 4;
    
    
    Sound explosionSFX;
    Sound shootSFX;
    
    
    /**
     * constructor
     * @param roomWidth
     * @param roomHeight
     * @param viewWidth
     * @param viewHeight 
     */
    public XpaceGame( int roomWidth,  int roomHeight,  int viewWidth,  int viewHeight )
    {
    super(roomWidth, roomHeight, viewWidth, viewHeight);
    
        TinySound.init();
    
     //comment this if u want to run in PC, 
     //enable to run in raspberry and to use GPIO pins as control
//     try
//     {
//     this.initGpioGameControl();
//     gpioGameControl.setGpioListener( this );
//     }
//     catch( Exception e )
//     {e.printStackTrace();}
//    
    
    }//
    
    
    
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
       
        
        
        switch( gameState )
        {
            
            case PAUSED:
                updateControlInPause();
                
                break;
            case PLAYING:
                
        updateControl();
        
        
           for( Sprite ene: enemyList )
            {
                if( ene.isVisible() )
                {
                    ene.move();
                    ene.updateAnimation( Sprite.ANIM_FOWARD );
                    
                    if( ene.getFrames().length > 1 && ene.isAnimationEnd() )
                    {
                    ene.setVisible( false );
                    
                    ene.setPosition( 0, -30 );
                    
                    
                        if( ene1.equals( ene ) )
                        {
                            ene.setFrames( ene1Sprite );
                            ene.setSpeedY( 2 );
                            increaseScore( 5 );
                        }
                        else if( ene2.equals( ene ) )
                        {
                            ene.setFrames( ene2Sprite );
                            ene.setSpeedY( 2 );
                            increaseScore( 10 );
                        } 
                        else if( ene3.equals( ene ) )
                        {
                            ene.setFrames( ene3Sprite );
                            ene.setSpeedY( 3 );
                            increaseScore( 15 );
                        } 
                        else if( ene4.equals( ene ) )
                        {
                            ene.setFrames( ene4Sprite );
                            ene.setSpeedY( 3 );
                            increaseScore( 20 );
                        } 
                        else if( ene5.equals( ene ) )
                        {
                            ene.setFrames( ene5Sprite );
                            ene.setSpeedY( 4 );
                            increaseScore( 25 );
                        } 
                        else if( ene6.equals( ene ) )
                        {
                            ene.setFrames( ene6Sprite );
                            ene.setSpeedY( 4 );
                            increaseScore( 30 );
                        } 
                    
                    }
                    
                    //check collision between ship
                    if( Collision.getInstance()
                    .rectangleColision( ene , ship ) )
                    {
                        ene.setPosition( 0, -30 );
                        ene.setVisible( false );
                        
                        shield -=10;
                        if( shield <= 0 )shield = 0;
                    }
                    
                     //check collision between bullets
                    for( Sprite bullet:  bulletList )
                    {
                        if( Collision.getInstance().rectangleColision( bullet , ene ) && bullet.isVisible() )
                        {
                             //@TODO points up, 
                            
                            explosionSFX.play( .5f );
//                             soundPlayer.play( "/demo1/xpace/misiexplosion.wav" );
                             //check if it must spawn a power up
                            spawnPowerUp( ene.getX(), ene.getY() );
                            
                            ene.setFrames( explosion );
                            ene.setSpeedY( 0 );
                            //to check whether the sprite animation endend 
                            ene.setAnimationEnd( false );
                            
                            bullet.setVisible( false );
                            bullet.setPosition(0, 600);
                            
                        }
                    
                    }
                    
                }//
                  
            checkEnemyOutside( ene );
                
            }//
           
           
           //moving bullets if exist
           for( Sprite bullet : bulletList )
           {

               if( bullet.isVisible() )
               {
                   bullet.move();
               }
               
               checkBulletOutside(bullet );
           }
           
           
           //
           for( Sprite powerUp: powerUpList )
           {
                if( powerUp.isVisible() )
               {
                   powerUp.move();
                   
                   if( Collision.getInstance()
                           .rectangleColision( powerUp, ship ))
                   {
                   
                       //check which powerUp touch the ship
                       powerUp.setVisible( false );
                       powerUp.setPosition( 0 , -30 );

                       if( powerUp.equals( missilePowerUp ) )
                       {
                           missiles +=1;
                           if( missiles >= 10 )missiles = 10;
                       }
                       else if( powerUp.equals( plasmaPowerUp ) )
                       {
                           selectedWpn = 1;
                       }
                       else if( powerUp.equals( shieldPowerUp ) )
                       {
                           shield += 10;
                           if( shield >= 100 ) shield = 100;
                       }
                       else if( powerUp.equals( speedPowerUp ) )
                       {
                       shipSpeed =4;
                       }
                       else if( powerUp.equals( wavePowerUp ) )
                       {
                           selectedWpn = 3;
                       }
                       else if( powerUp.equals( laserPowerUp ) )
                       {
                           selectedWpn = 2;
                       }
                       
                   }
                       
               }
               
               checkEnemyOutside( powerUp );
           }
           
           
           //animate stars here
           for( int i = 0; i < 4 ;i++)
           {
           
               smalStarList[ i ].setLocation( smalStarList[ i ].x, smalStarList[ i ].y + smallStarSpeed );
               bigStarList[ i ].setLocation( bigStarList[ i ].x, bigStarList[ i ].y + bigStarSpeed );
               
               
               if( smalStarList[ i ].y > 600 )
               {
                   smalStarList[ i ].setLocation(  rand.nextInt( 395 ), -10 );
               }
              
               rand.setSeed( rand.nextInt() );
               
                if( bigStarList[ i ].y > 600 )
               {
                    bigStarList[ i ].setLocation( rand.nextInt( 395 ), -10 );
               }
               
           }//
           
           
        
            spawnEnemyCounter++;
        if( spawnEnemyCounter >= 100 )
        {
            spawnEnemyCounter = 0;
            spawnenemy();
        }//
        
                
                break;
        }//
        
    }//

    
     /**
     * init all your sprites here
     * @return 
     */
    @Override
    public boolean initSprite() 
    {
        
        enemyList = new ArrayList<>();
        bulletList= new ArrayList<>();
        powerUpList = new ArrayList<>();  
        
        enemyImgArray = new Image[ 6 ];
        
        
        smalStarList = new Point[ 4 ];
        bigStarList = new Point[ 4 ];

        smalStarList[ 0 ] =new  Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );
        smalStarList[ 1 ]= new  Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );
        smalStarList[ 2 ]= new  Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );
        smalStarList[ 3 ]= new  Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );

        rand.setSeed( rand.nextInt() );
        
        
        bigStarList[ 0 ]= new Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );
        bigStarList[ 1 ]= new Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );
        bigStarList[ 2 ]= new Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );
        bigStarList[ 3 ]= new Point( rand.nextInt( 395 ), rand.nextInt( 600 ) );
    
    
        try 
        {
          
          ship = new Sprite( 5, 24,24, "/demo1/xpace/xpaceship.png" );
          ship.setPosition( roomWidth/2 - ship.getW()/2 , 300  );
          ship.setVisible( true );
          
          //setthing frame to show at the beginning
          ship.setCurrentFrame( 2 );
          
          //borders delimit player not to go beyond the limits of the room
          ship.setRoomBoundLeft( 0 );
          ship.setRoomBoundTop( 0 );
          ship.setRoomBoundRight( roomWidth );
          ship.setRoomBoundBottom( 500 );
          
            
          
            //creation of enemies and put outside
            ene1 = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/ene1.png" ) ) );
            ene1.setPosition( 0, -50 );
            ene1.setLabel( "enemy" );
            ene1.setSpeedY( 2 );
            ene1.setVisible( true );
            ene1.setAnimationSpeedLimit( 20 );
            ene1Sprite = ene1.getFrames();
            enemyList.add(ene1);
            
            
            ene2 = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/ene2.png" ) ) );
            ene2.setPosition( 0, -50 );
            ene2.setLabel( "enemy" );
            ene2.setSpeedY( 2 );
            ene2.setAnimationSpeedLimit( 20 );
            ene2Sprite = ene2.getFrames();
            enemyList.add(ene2);
            
            ene3 = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/ene3.png" ) ) );
            ene3.setPosition( 0, -50 );
            ene3.setLabel( "enemy" );
            ene3.setSpeedY( 3 );
            ene3.setAnimationSpeedLimit( 20 );
            ene3Sprite = ene3.getFrames();
            enemyList.add(ene3);
            
            
            ene4 = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/ene4.png" ) ) );
            ene4.setPosition( 0, -50 );
            ene4.setLabel( "enemy" );
            ene4.setSpeedY( 3 );
            ene4.setAnimationSpeedLimit( 20 );
            ene4Sprite = ene4.getFrames();
            enemyList.add(ene4);
            
            
            ene5 = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/ene5.png" ) ) );
            ene5.setPosition( 0, -50 );
            ene5.setLabel( "enemy" );
            ene5.setSpeedY( 4 );
            ene5.setAnimationSpeedLimit( 20 );
            ene5Sprite = ene5.getFrames();
            enemyList.add(ene5);
            
            
            ene6 = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/ene6.png" ) ) );
            ene6.setPosition( 0, -50 );
            ene6.setLabel( "enemy" );
            ene6.setSpeedY( 4 );
            ene6.setAnimationSpeedLimit( 20 );
            ene6Sprite = ene6.getFrames();
            enemyList.add(ene6);
            
            
            //create bullets and misiles for ship
            plasmaBullet = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/bullet.png" ) ) );
            plasmaBullet.setPosition(0 , 600);
            plasmaBullet.setSpeedY( -5 );
            plasmaBullet.setLabel( "plasmaBullet" );
            bulletList.add( plasmaBullet );
            
            
            laserBullet = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/laser.png" ) ) );
            laserBullet.setPosition(0 , 600);
            laserBullet.setSpeedY( -5 );
            laserBullet.setLabel( "laserBullet" );
            bulletList.add( laserBullet );
            
            waveBullet = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/wave.png" ) ) ); 
            waveBullet.setPosition(0 , 600);
            waveBullet.setSpeedY( -4 );
            waveBullet.setLabel( "waveBullet" );
            bulletList.add( waveBullet );
            
            missile = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/missile.png" ) ) );
            missile.setPosition(0 , 600);
            missile.setSpeedY( -4 );
            missile.setLabel( "missileBullet" );
            bulletList.add( missile );
            
            
            Image[] im = Util.getArrayFrames( 6, 16, 16, "/demo1/xpace/powerups.png" );
            
            explosion = Util.getArrayFrames( 5 , 24, 22,  "/demo1/xpace/explosion.png" );
            
            
            missilePowerUp = new Sprite( im[ 0 ] );
            missilePowerUp.setPosition(0 , -50);
            missilePowerUp.setSpeedY( 2 );
            powerUpList.add( missilePowerUp );
            
            
            shieldPowerUp = new Sprite( im[ 1 ] );
            shieldPowerUp.setSpeedY( 2 );
            shieldPowerUp.setPosition(0 , -50);
            powerUpList.add( shieldPowerUp );
            
            speedPowerUp = new Sprite( im[ 2 ] );
            speedPowerUp.setPosition(0 , -50);
            speedPowerUp.setSpeedY( 2 );
            powerUpList.add( speedPowerUp );
            
            
            plasmaPowerUp = new Sprite( im[ 3 ] );
            plasmaPowerUp.setPosition(0 , -50);
            plasmaPowerUp.setSpeedY( 2 );
            powerUpList.add( plasmaPowerUp );
            
            wavePowerUp = new Sprite( im[ 4 ] );
            wavePowerUp.setPosition(0 , -50);
            wavePowerUp.setSpeedY( 2 );
            powerUpList.add( wavePowerUp );
            
            laserPowerUp = new Sprite( im[ 5 ] );
            laserPowerUp.setPosition(0 , -50);
            laserPowerUp.setSpeedY( 2 );
            powerUpList.add( laserPowerUp );
            
            
            bottomHUD = new Sprite( ImageIO.read(this.getClass().getResource( "/demo1/xpace/bottombar.png" ) ) );
            bottomHUD.setPosition(0 , 500);
            bottomHUD.setVisible( true );
            
            
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
        System.out.println("::: entrando initSound");
          
        
        explosionSFX = TinySound.loadSound( "/demo1/xpace/misiexplosion.wav"  );
        shootSFX = TinySound.loadSound( "/demo1/xpace/lasershoot.wav" );
        
        
        //to repeat the bg song indefinided
        musicPlayer.setRepeat( true );
        
        try {
//            System.out.println("::: "+this.getClass() );
            musicPlayer.play( "/demo1/xpace/planetEarth.mp3" );
        } catch (JavaLayerException ex) {
            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
        rand = new Random();
        
        try 
        {
            font = Util.getFont( this.getClass() , "/demo1/PressStart2P.ttf", 12 );
        }
        catch (IOException |FontFormatException ex) 
        {
            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
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
       
        drawBgColor( g , Color.black );

        g.setColor(Color.white);
       
           
           //
         switch( gameState )
        {
            
             //starts have to be shown even if the game is
             //paused or not
            case PAUSED:
            case PLAYING:
           //draw stars here
           //animate stars here
           for( int i = 0; i < 4 ;i++)
           {               
               g.fillOval( smalStarList[ i ].x, smalStarList[ i ].y , smallStarRadius, smallStarRadius );
               g.fillOval( bigStarList[ i ].x, bigStarList[ i ].y , bigStarRadius, bigStarRadius );
           }//
                break;
                
        }//switch
           
        
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
        
         //
         switch( gameState )
        {
            
            case PAUSED:
                break;
            case PLAYING:
        
        ship.draw( g );
        
        for( Sprite spr: enemyList)  //spritePool)
        {
        spr.draw( g );
        }//
        
        
        for( Sprite spr : bulletList )
        {
        spr.draw( g );
        }
        
        
        for( Sprite spr : powerUpList )
        {
        spr.draw( g );
        }
        
                break;
                
        }//switch
        
        
        
        
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
        
        g.setColor( Color.white );
        g.setFont( font );
        
        bottomHUD.draw( g );
        
         switch( gameState )
        {
            
            case PAUSED:
            g.drawString( "Game Paused Bitch!" , 140, 200 );
            case PLAYING:
            g.drawString( shieldLbl+shield , 20, 530);
            g.drawString( missileLbl+missiles , 20, 550);
            g.drawString( scoreLbl+score , 200, 530);
            break;
        
        
         }
        
    }//

    /**
     * this method is used insice update,but this may contain
     * all the logic to control your player
     */
    @Override
    public void updateControl() 
    {
        
        
         if(keyControl.isKeyDown(KeyEvent.VK_RIGHT))
            {
                
                ship.move( shipSpeed, 0 );
            }
             if(keyControl.isKeyDown(KeyEvent.VK_LEFT))
            {
                ship.move( -shipSpeed, 0 );
            } 
             if(keyControl.isKeyDown(KeyEvent.VK_UP))
            {
                ship.move( 0, -shipSpeed );
            } 
             if(keyControl.isKeyDown(KeyEvent.VK_DOWN))
            {
                ship.move( 0, shipSpeed );
            } 
            if( keyControl.isKeyDown( KeyEvent.VK_X ) )
            {
                //shoot bullet
                switch( selectedWpn )
                {
                    case 1:
                        //shoot plasma
                        
                        if( !plasmaBullet.isVisible() )
                        {
//                            /.,
                            
                          
//                            sound.playSound( "uno" );
                            
                            shootSFX.play( .2f);
                            
//                        soundPlayer.play( "/demo1/xpace/lasershoot.wav" );
//                        sound.play( 0.1 );
                        plasmaBullet.setPosition( ship.getCenterX()+plasmaBullet.getCenterX(),ship.getY() );
                        plasmaBullet.setVisible( true );
                        }
                        
                        break;
                    case 2:
                        //shoot laser
                        if( !laserBullet.isVisible() )
                        {
                            
                            
                        laserBullet.setPosition( ship.getCenterX() + laserBullet.getCenterX(),ship.getY() );
                        laserBullet.setVisible( true );
                        }
                        break;
                    case 3:
                        //shoot wave
                        if( !waveBullet.isVisible() )
                        {
                           
                        waveBullet.setPosition( ship.getCenterX()+waveBullet.getCenterX(),ship.getY() );
                        waveBullet.setVisible( true );
                        }
                        break;
                    
                }
                
            } //
            if( keyControl.isKeyDown( KeyEvent.VK_Z ) )
            {//shoot missile
                
                if( missiles > 0 && !missile.isVisible())
                {
                missiles --;
                
                missile.setVisible( true );
                missile.setPosition( ship.getCenterX()+missile.getCenterX(),ship.getY() );
                
                }//
                
            } 
            
            if( keyControl.isKeyPress( KeyEvent.VK_ENTER ) )
            {
                gameState =  GameState.PAUSED;   
                musicPlayer.pause();
            }//
            
    }//

    /**
     * if your game will have connection via socket or througth
     * network use this method to handle all of that
     * @throws IOException 
     */
    @Override
    public void manageNetworkData() throws IOException 
    {
        
    }//

    /**
     * handle GPIO buttons of rpi 3B+, zero ( with 40 Pins ) here...
     * @param gpdsce 
     */
    @Override
    public void handleGpioPinDigitalStateChangeEvent( GpioPinDigitalStateChangeEvent gpdsce ) 
    {
        
        if( gpdsce.getState().equals( gpioGameControl.getBtnStateLow() ) )
        {
        
            //if we now that a button was pressed, then we have to look
            //which is
            if( gpdsce.getPin().equals( gpioGameControl.getLeftPad() ) )
            {
             ship.move( -shipSpeed, 0 );
            }
            if( gpdsce.getPin().equals( gpioGameControl.getRigthPad()) )
            {
                ship.move( shipSpeed, 0 );
            }
            if( gpdsce.getPin().equals( gpioGameControl.getUpPad()) )
            {
               ship.move( 0, -shipSpeed );
            }
            if( gpdsce.getPin().equals( gpioGameControl.getDownPad()) )
            {
                ship.move( 0, shipSpeed );
            }
            
        }
        else //buttons released
        {
         
         //pause, shoot, missile
          if( gpdsce.getPin().equals( gpioGameControl.getRedBtn()) )
            {
                
                switch( selectedWpn )
                {
                    case 1:
                        //shoot plasma
                        
                        if( !plasmaBullet.isVisible() )
                        {
//                            /.,
//                            sound.playSound( "uno" );
//                            shootSFX.play( .5f );
//                        soundPlayer.play( "/demo1/xpace/lasershoot.wav" );
//                        sound.play( 0.1 );
                        plasmaBullet.setPosition( ship.getCenterX()+plasmaBullet.getCenterX(),ship.getY() );
                        plasmaBullet.setVisible( true );
                        }
                        
                        break;
                    case 2:
                        //shoot laser
                        if( !laserBullet.isVisible() )
                        {
                            
                            
                        laserBullet.setPosition( ship.getCenterX() + laserBullet.getCenterX(),ship.getY() );
                        laserBullet.setVisible( true );
                        }
                        break;
                    case 3:
                        //shoot wave
                        if( !waveBullet.isVisible() )
                        {
                           
                        waveBullet.setPosition( ship.getCenterX()+waveBullet.getCenterX(),ship.getY() );
                        waveBullet.setVisible( true );
                        }
                        break;
                    
                }//suich
                
                
                
            }
            if( gpdsce.getPin().equals( gpioGameControl.getGreenBtn()) )
            {
                if( missiles > 0 && !missile.isVisible())
                {
                missiles --;
                missile.setVisible( true );
                missile.setPosition( ship.getCenterX()+missile.getCenterX(),ship.getY() );
                }//
                
            } 
            
            if( gpdsce.getPin().equals( gpioGameControl.getGoBtn()) )
            {
                 if( keyControl.isKeyPress( KeyEvent.VK_ENTER ) )
                {
                    
                    if( gameState == GameState.PAUSED )
                    {
                    gameState =  GameState.PLAYING;   

                        try 
                        {
                            musicPlayer.resume();
                        } catch (JavaLayerException ex) {
                            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (URISyntaxException ex) {
                            Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                    else
                    {
                    gameState =  GameState.PAUSED;   
                    musicPlayer.pause();    
                    }
                    
                    
                    
                    
                }//
            }
            
             
             
         }
        
    }//
    
    
    
    
    public void checkBulletOutside( Sprite spr )
    {
    
        switch( spr.getLabel() )
        {
        
            case "plasmaBullet":
            case "laserBuller":
            case "waveBullet":
                
                if( spr.getY() <= -50 )
                {
                spr.setVisible( false );
                spr.setPosition( 0 , 600);
                }
                
                
            break;
            
        }
        
    }//
    
    
    public void checkEnemyOutside( Sprite spr )
    {
        
        if( spr.getY() >= 650 )
        {
        
            spr.setVisible( false );
            spr.setPosition( 0 , -30 );
         
//            System.out.println("elomiado: " + spr.getLabel());
        }
            
    }//
    
    public void animateShip( Sprite spr )
    {
        
//        switch( shipMovement )
//        {
//            case -1:
//                
//                if( animCounter !=0)
//                {
//                
//                    if( animCounter <0 )
//                    {
//                    animCounter++;
//                    }
//                
//                
//                }
//                
//                
//                break;
//            case 1:
//                animCounter--;
//                break;
//            case 2:
//                animCounter++;
//                break;
//            
//        }
    
    }//
    
    
    
    public void spawnenemy()
    {
    
        
       int n = rand.nextInt( 6 );
       
//        System.out.println("N: "+n);
       
       int xx = rand.nextInt( 370 ); //-30 due enemy width
//       System.out.println("XX: "+xx);
       
       switch( n )
       {
           case 0:
//                System.out.println("  ene1 v "+ene1.isVisible());
               if( !ene1.isVisible() )
               {
//                   System.out.println(" entra a ene1");
                       
               ene1.setPosition( xx, -30 );
               ene1.setVisible( true );
               }
               break;
           case 1:
//               System.out.println("  ene1 v "+ene2.isVisible());
               if( !ene2.isVisible() )
               {
//               System.out.println(" entra a ene2");
               ene2.setPosition( xx, -30 );
               ene2.setVisible( true );
               }
               break;
           case 2:
//               System.out.println("  ene1 v "+ene3.isVisible());
               if( !ene3.isVisible() )
               {
//               System.out.println(" entra a ene3");
               ene3.setPosition( xx, -30 );
               ene3.setVisible( true );
               }
               break;
           case 3:
               if( !ene4.isVisible() )
               {
               
               ene4.setPosition( xx, -30 );
               ene4.setVisible( true );
               }
               break;
           case 4:
               if( !ene5.isVisible() )
               {
               
               ene5.setPosition( xx, -30 );
               ene5.setVisible( true );
               }
               break;
           case 5:
               if( !ene6.isVisible() )
               {
               
               ene6.setPosition( xx, -30 );
               ene6.setVisible( true );
               }
               break;
           
       }//
           
    }//
    
    
    
    public void spawnPowerUp( float x, float y )
    {
    
        if( rand.nextInt( 100 ) <= 50 )
        {
                
            byte n = (byte)rand.nextInt( 5 );
            
            if( !powerUpList.get( n ).isVisible() )
            {
                powerUpList.get( n ).setVisible( true );
                powerUpList.get( n ).setPosition( x, y );
            }
            
        }
        
        
    }//
    

    public void increaseScore(int score)
    {
    this.score += score;
    }
    
    public void updateControlInPause()
    {
    
        if( keyControl.isKeyPress(KeyEvent.VK_ENTER ) )
          {
                gameState =  GameState.PLAYING;
                
                try 
                {
                musicPlayer.resume();
                } 
                catch (JavaLayerException ex) 
                {
                    Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (IOException ex)
                {
                    Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                catch (URISyntaxException ex)
                {
                    Logger.getLogger(XpaceGame.class.getName()).log(Level.SEVERE, null, ex);
                }
                 
          }
        
        
    }//

    @Override
    public boolean resetLevel() 
    {
        return false;
    }
    
}//class
