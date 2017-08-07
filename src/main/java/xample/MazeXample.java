/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xample;

import com.ilusion2.level.GameLevel;
import com.ilusion2.physics.Collision;
import com.ilusion2.gamemanager.ImageBackground;
import com.ilusion2.sprite.AnimationState;
import com.ilusion2.sprite.Sprite;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author nigthmare
 */
public class MazeXample extends GameLevel
{

    
        ImageBackground bg;
    
    Sprite player;
    Sprite orb;
    
    
  int []colmap =
  {
    0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,  
    0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,0,0, 0,0,0,0,0,0,0,0,0,0,
    1,1,1,1,1,1,1,0,1,1, 1,1,0,1,1,1,1,1,1,1,
    1,1,1,1,1,1,1,0,1,0, 0,1,0,1,1,1,1,1,1,1,
    
    0,0,0,0,0,0,1,0,1,0, 0,1,0,1,0,0,0,0,0,0,
    0,0,0,0,0,0,1,0,1,0, 0,1,0,1,0,0,0,0,0,0,
    0,0,0,0,0,0,1,0,1,0, 0,1,0,1,0,0,0,0,0,0,
    0,0,0,0,0,0,0,0,1,0, 0,1,0,0,0,0,0,0,0,0,
    0,0,0,0,0,0,1,0,1,0, 0,1,0,1,0,0,0,0,0,0,
    
    0,0,0,0,0,0,1,0,0,0, 0,0,0,1,0,0,0,0,0,0,
    0,0,0,0,0,0,1,0,1,0, 0,1,0,1,0,0,0,0,0,0,
    0,0,0,0,0,0,1,0,0,0, 0,0,0,1,0,0,0,0,0,0,
    0,0,0,0,0,0,1,0,0,1, 1,0,0,1,0,0,0,0,0,0,
    0,0,0,1,1,1,1,1,1,0, 0,1,1,1,1,1,1,0,0,0
  };//
    
    /**
     * constructor 1
     * @param roomWidth
     * @param roomHeight
     * @param viewWidth
     * @param viewHeight
     * @param imgbg 
     */
       public MazeXample(
             int roomWidth, 
             int roomHeight, 
             int viewWidth, 
             int viewHeight,
             ArrayList<ImageBackground> imgbg)
     {
         super( roomWidth, roomHeight,viewWidth, viewHeight );
         
//         this.colisionTileMaps.add( colmap );
//         room.setFps( 30 );
         this.setPersistent(true);
     }//
    
    
    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public synchronized void update()
    {
    
        
        // will move the player with the controls
        updateControl();
        
        
        //this check wheter the player has been colided 
        //with some solid block
       Collision.getInstance()
         .checkColsionTile(
             player,
                 colmap, 20, 15, 16,16);
       
        
    }//

    @Override
    public boolean initSprite() 
    {
        
        
        //player and orb sprites creation
//            try 
//            {
                
        //pass tiles to the player
        player = new Sprite( 12, 16,16, "/mazegame/mazegametiles.png" );
        player.setPosition(0, 0);
        player.setVisible( true );

            ///save the animations ( each frame ) on diferent arrays
            int[] right = {9,10,11,10};
            int[] left = {0,1,2,1};
            int[] up = {3,4,5,4};
            int[] down = {6,7,8,7};
            
            //then each animation will be mapped to an ANIMATIONSTATE
            //if player press left key on keyboard, AnimationState
            //will be MOVELEFT and for the player left animation will be shown
            HashMap<AnimationState,int[]> subAnimationStack = new HashMap<>();
            subAnimationStack.put(AnimationState.MOVERIGHT, right);
            subAnimationStack.put(AnimationState.MOVELEFT, left);
            subAnimationStack.put(AnimationState.MOVEUP, up);
            subAnimationStack.put(AnimationState.MOVEDOWN, down);
            
            player.setAnimationSpeed( 0 );
           player.setAnimationSpeedLimit( 40 );
            
            //finalmente se agrega el stack de animaciones al sprite
            player.setSubAnimationStack(subAnimationStack);
            
            //by default animation state is right
            player.setSubanimation(AnimationState.MOVERIGHT);
                    

        
        
        return false;
    }

    @Override
    public boolean initBg() 
    {
        
            try 
            {
                bg = new ImageBackground(
                        ImageIO.read( 
                                this.getClass().getResource( "/mazegame/mazemap.png" ) ) , 0, 0 );
           
            }
            catch (IOException ex)
            {
                Logger.getLogger(MazeXample.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
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
//        Color aa = new Color(roomWidth, roomWidth, roomWidth, roomWidth);
        drawBgImage( g2, bg.getImg()  , bg.getX(), bg.getY() );
        
    }//

    @Override
    public void renderForeground(Graphics2D g2) 
    {
    
//        player.draw( (Graphics2D )g );
        
        //this will make the player update the current
        //frames of animation state
        player.drawSubanimation( g2 ,  true );
        
    }//

    @Override
    public void renderHUD(Graphics2D g2)
    {
    
    }

    @Override
    public void updateControl() 
    {
        
        if( mouseControl.isReleased() )
        {
            
            //System.out.println("xample.MazeXample.updateControl()");
            mouseControl.setReleased(false);
            
            gm.loadLvl( "first" );
        }//
              
    
        if(keyControl.isKeyDown(KeyEvent.VK_RIGHT))
            {
                
                if( player.getCurrentAnimationState()!= AnimationState.MOVERIGHT )
                player.setSubanimation(AnimationState.MOVERIGHT);
                
                player.move(2,0);
            }
            else if(keyControl.isKeyDown(KeyEvent.VK_LEFT))
            {
                if( player.getCurrentAnimationState()!= AnimationState.MOVELEFT )
                player.setSubanimation(AnimationState.MOVELEFT);
                player.move(-2, 0);
            } 
            else if(keyControl.isKeyDown(KeyEvent.VK_UP))
            {
                if( player.getCurrentAnimationState()!= AnimationState.MOVEUP )
                player.setSubanimation(AnimationState.MOVEUP);
                player.move(0, -2);
//                 if(player.getY() < cam.getOffsetY()  +cam.getMarginTop())
//                { cam.moveY(3); }  
            } 
            else if(keyControl.isKeyDown(KeyEvent.VK_DOWN))
            {
                if( player.getCurrentAnimationState()!= AnimationState.MOVEDOWN )
                player.setSubanimation(AnimationState.MOVEDOWN);
                player.move(0, 2);
//                if(player.getY()+player.getH() > cam.getOffsetY()  +cam.getMarginBottom())
//                { cam.moveY(- 3); }  
            } 
        
        
   
        
        
        
    }//

    @Override
    public void manageNetworkData() throws IOException {
    
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
    
    }

    @Override
    public boolean resetLevel() 
    {return false;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}//class
