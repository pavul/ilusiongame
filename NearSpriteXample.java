/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.level.GameLevel;
import com.ilusion2.gamemanager.ImageBackground;
import com.ilusion2.sprite.Sprite;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author nigthmare
 */
public class NearSpriteXample extends GameLevel
{
    
    
    List<Sprite> spritePool = new ArrayList<>();
    
        Sprite player;
        
        Sprite t1, t2, t3, t4;
        
        Sprite near = null;
        
          public NearSpriteXample(
             int roomWidth, 
             int roomHeight, 
             int viewWidth, 
             int viewHeight,
             ArrayList<ImageBackground> imgbg)
     {
         super( roomWidth, roomHeight,viewWidth, viewHeight);
     }//
        
        
    @Override
    public void update()
    {
     
        player.spriteNearest(spritePool, this ).moveSpeedX( 3 );
        
        System.out.println("instance near: "+player.spriteNearest(spritePool, this ).getLabel());
        
        
        
        if( mouseControl.isReleased() )
        {
           
            t1.setPosition(50, 50);

            t2.setPosition(100, 100);

            t3.setPosition(200, 200);

            t4.setPosition(40, 40);
            
            
            mouseControl.setReleased(false);
            
            gm.loadLvl( "mazeXample" );
        }//
        
        
        
    }//

    @Override
    public boolean initSprite()
    {
        
        
        try
         {
             
             
        player = new Sprite( 24, 32, ImageIO.read( this.getClass().getResource( "/char1.png" ) )  );
        player.setPosition(20, 20);
        player.setVisible(true);
        
        
        t1 = new Sprite( 32, 32, ImageIO.read( this.getClass().getResource( "/level2/turret.png" ) ) );
        t1.setPosition(50, 50);
        t1.setVisible(true);
        t1.setLabel( "turret 1" );
        spritePool.add(t1);
        
        t2 = new Sprite( 32, 32, ImageIO.read( this.getClass().getResource( "/level2/turret.png" ) ) );
        t2.setPosition(100, 100);
        t2.setVisible(true);
        t2.setLabel( "turret 2" );
        spritePool.add(t2);
        
        
        t3 = new Sprite( 32, 32, ImageIO.read( this.getClass().getResource( "/level2/turret.png" ) ) );
        t3.setPosition(200, 200);
        t3.setVisible(true);
        t3.setLabel( "turret 3" );
        spritePool.add(t3);
        
        
        t4 = new Sprite( 32, 32, ImageIO.read( this.getClass().getResource( "/level2/turret.png" ) ) );
        t4.setPosition(40, 40);
        t4.setVisible(true);
        t4.setLabel( "turret 4" );
        spritePool.add(t4);     

             
            
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
    public void renderBackground(Graphics2D g2) 
    {
    drawBgColor( g2 , Color.CYAN );
    }

    @Override
    public void renderForeground(Graphics2D g2) 
    {
     //draw sprite
        player.draw( g2 );
        
        
        //draw turrets
        for( Sprite spr: spritePool)
            spr.draw( g2 );
    }

    @Override
    public void renderHUD(Graphics2D g2) 
    {
    
        ( g2 ).setColor( Color.BLACK );
        ( g2 ).drawString( "click to reset turret position " , 50, 20);
        
    }//

    @Override
    public void updateControl() {
    
    }

    @Override
    public void manageNetworkData() throws IOException {
    
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
    
    }

    @Override
    public boolean resetLevel() {return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
    
}//class
