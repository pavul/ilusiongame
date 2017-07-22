/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.level.GameLevel;
import com.ilusion2.room.ImageBackground;
import com.ilusion2.sprite.Sprite;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *this class contains an example how to rotate sprites
 * and to move one sprite to a certain poit ( when mouse is clicked )
 * @author nigthmare
 */
public class RotationXample extends GameLevel
{

    
     Sprite turret = null;
     Sprite turret2 = null;
     Sprite turret3 = null;
     Sprite turretbullet = null;
    
     int degrees;
    
    
     public RotationXample(
             int roomWidth, 
             int roomHeight, 
             int viewWidth, 
             int viewHeight,
             ArrayList<ImageBackground> imgbg)
     {
         super( roomWidth, roomHeight,viewWidth, viewHeight, imgbg );
     }
    
     
     
    @Override
    public void update() 
    {
        
        
          turret.move();
                       
           degrees += 1;
           if( degrees>= 360 )degrees = 0;

           turret.setDegrees( degrees );
        
           //turret3.calculateAngle(turret);
           turret3.setDegrees( degrees );
           
           //System.out.println("1= "+turret.getSpriteId()+"-"+turret.getX()+"-"+turret.getY()  );
           System.out.println("2= "+turret2.getSpriteId()+"-"+turret2.getX()+"-"+turret2.getY() );
           //System.out.println("3= "+turret3.getSpriteId()+"-"+turret3.getX()+"-"+turret3.getY() );
           
           
           //turret 3 orbit around turret3
//           turret2.orbit( turret3 , 20 );
           turret2.orbit(
                   turret3.getX(), 
                   turret3.getY(),
                   degrees, 
                   60);
           
           
           
      
           if( mouseControl.isPressed() )
           {
           room.loadLvl( "nearSpriteXample" );
           }
           
           
        //update control must be called in update 
        updateControl();
        
    }//

    
    
    /**
     * init sprites here
     * @return 
     */
    @Override
    public boolean initSprite() 
    {
    
    
         try
         {
             turret = new Sprite( 32, 32, ImageIO.read( this.getClass().getResource( "/level2/turret.png" ) ) );
             turret.setPosition(200, 200);
             turret.setVisible(true);
             
             
             turret2 = new Sprite( 32, 32, ImageIO.read( this.getClass().getResource( "/level2/turretleft.png" ) ) );
             turret2.setPosition(0 ,0);
             turret2.setVisible(true);
             
             
             turret3 = new Sprite( 32, 32, ImageIO.read( this.getClass().getResource( "/level2/turret.png" ) ) );
             turret3.setPosition(240, 160);
             turret3.setVisible(true);
             
             
             turretbullet = new Sprite( 8, 4, ImageIO.read( this.getClass().getResource( "/level2/turretbullet.png" ) ) );
             turretbullet.setPosition(-100, 0);
             
            
         } 
         catch (IOException ex) 
         {
             Logger.getLogger(RotationXample.class.getName()).log(Level.SEVERE, null, ex);
         }
         
         return false;
    }

    @Override
    public boolean initBg() {
       return false;
    }

    @Override
    public boolean initHud() {
        return false;
    }

    @Override
    public boolean initSound() {
        return false;
    }

    @Override
    public boolean initData() {
        return false;
    }

    @Override
    public void renderBackground(Graphics g) 
    {
        drawBgColor( ( Graphics2D )g , Color.CYAN );
    }

    @Override
    public void renderForeground(Graphics g) 
    {
   
        System.out.println(" T3 deg: "+turret3.getDegrees());
        
        
        
    //this code rotate turret3 
    turret3.drawRotate( (Graphics2D)g  );
    //turret3.drawRotate( (Graphics2D)g , degrees );
    
    

    //this draw turret2 but is obiting 
    turret2.draw((Graphics2D)g );
    
     
    turret.drawRotate( (Graphics2D)g );
    
    }

    @Override
    public void renderHUD(Graphics g) 
    {
     
    ((Graphics2D)g).setColor( Color.BLACK );
    ((Graphics2D)g).drawString( "Rotation Degrees "+degrees , 20, 20);
        
    }//

    @Override
    public void updateControl() 
    {
    
          //set new angle of turret every time mouse button is released
    turret.calculateAngle(
             (int)(mouseControl.getPointReleased().getX() ) ,
             (int)(mouseControl.getPointReleased().getY() ) );
    
    

  

    
    if( mouseControl.isReleased() )
    {

    //to move turret toward mouse point
    //this just set the direction and rotation, to move this
    //have to be done in update method with 'move' fuction
    turret.moveTo(
        (int)mouseControl.getPointReleased().getX(), 
        (int)mouseControl.getPointReleased().getY(), 3 );
    
    }//if mouse control released    
    
    
    
    }//

    @Override
    public void manageNetworkData() throws IOException {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
        //To change body of generated methods, choose Tools | Templates.
    }


    
    
    
    
}//class

