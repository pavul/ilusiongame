/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1.towerdefence;

import com.ilusion2.level.GameLevel;
import com.ilusion2.sprite.Sprite;
import com.ilusion2.util.Util;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tile.Tile;

/**
 *
 * @author pavulzavala
 */
public class TowerDefence extends GameLevel
{

    
    
    List<TurretSprite> turretList;
    
    
    List<Sprite> eneList;

    List<Sprite> bulletList;
    
    
    int shootCounter = 0;
    
    
    
    
    
    public TowerDefence(int roomWidth, int roomHeight, int viewWidth, int viewHeight )
    {
        super(roomWidth, roomHeight, viewWidth, viewHeight );
    }
    
    
    
    
    
    @Override
    public void update( double delta  ) 
    {
        
        switch( gameState )
        {
        
            case PLAYING:
                
//                shootCounter ++;
//                if( shootCounter >= 100 )
//                {
//                shootCounter = 0;
//                
//                Sprite ss = bulletList.stream().findFirst().get();
//                    if( !ss.isVisible() )
//                    {
//                        
//                    ss.setPosition(turretList.get( 0 ).getX(),
//                            turretList.get( 0 ).getY() );
//                    ss.setVisible( true );
//                    
//                     ss.moveTo( eneList.get( 0 ) , 5 );
//                    
//                    }
//                
//                }
                
                eneList.get( 0 ).move();
                
                
                turretList.forEach( turret ->
                {
                turret.calculateAngle( eneList.get( 0 ) );
                
                
                //check if there are enemies nearby
                turret.seekAndDestroy( eneList, this );
                
                });
                
                
                
                bulletList.forEach( b ->
                {
                    if( b.isVisible() )
                    {
                    b.move();
                    }
                } );
                
                
                break;
                
            case PAUSED:
                
                
                break;
            
        }
        
        
        
    }//

    @Override
    public boolean resetLevel() 
    {
        
        return true;
    }

    @Override
    public boolean initSprite()
    {
        
        
        turretList = new ArrayList<>();
        
        eneList = new ArrayList<>();
        
        Image img = null;
        try 
        {
            img = Util.getImage( "/level2/turret.png" );
        }
        catch (IOException ex) 
        {
            Logger.getLogger(TowerDefence.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        for( int i = 0; i < 4 ; i++ )
        {
            
                TurretSprite s = new TurretSprite( img );
                s.setVisible( true );
        
                Sprite t = new Sprite( 0, 0, 32, 32 );
                t.setVisible( true );
                
                
                turretList.add( s );
                eneList.add( t );
            
                //System.out.println("suze: "+turretList.size());
        
        }
        
        turretList.get( 0 ).setPosition( 20,  200);
        turretList.get( 1 ).setPosition( 200, 200);
        turretList.get( 2 ).setPosition( 20,  280);
        turretList.get( 3 ).setPosition( 200, 280);
        
        
        
        
        eneList.get( 0 ).setSpeedX( -1 );
        eneList.get( 1 ).setSpeedX( 2 );
        eneList.get( 2 ).setSpeedX( 1 );
        eneList.get( 3 ).setSpeedX( 5 );
        
        eneList.get( 0 ).setPosition( roomWidth, 240 );
        
        
        Image bImg = null;
        try 
        {
            bImg = Util.getImage( "/level2/turretbullet.png" );
        }
        catch (IOException ex) 
        {
            Logger.getLogger(TowerDefence.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        bulletList = new ArrayList<>();
        
        for(int i=0; i < 4 ; i++)
        {
            Sprite s = new Sprite( bImg );
            s.setPosition( 0,0 );
            s.setVisible( true );
            
            bulletList.add( s );
            
            
        //setting bullets
        turretList.get( i ).setBullet( bulletList.get( i ) );
            
            
        }
        
        
        
        
    return true;
    }

    @Override
    public boolean initBg() {
    return true;}

    @Override
    public boolean initHud() 
    {
        
        eneList.forEach( i -> System.out.println("--> "+ i.getSpriteId() ) );
        
    return true;
    }

    @Override
    public boolean initSound() {
    return true;}

    @Override
    public boolean initData() {
    return true;}

    @Override
    public void renderBackground(Graphics2D g) 
    {
        
          switch( gameState )
        {
        
            case PLAYING:
                
                drawBgColor(g, Color.gray);
                
                g.setColor(Color.darkGray);
                g.fillRect(0, 230, 640, 40);
                
                break;
                
            case PAUSED:
                
                
                break;
            
        }
        
    }

    @Override
    public void renderForeground(Graphics2D g) 
    {
          switch( gameState )
        {
        
            case PLAYING:
                
                //draw turrets
                turretList.forEach( s ->
                {
                s.drawRotate(g);
                });
                
                //draw enemies
                eneList.forEach( ene ->
                {
//                    ene.draw(g);
                    if( ene.isVisible() )
                    {
                    g.setColor( Color.white );
                    g.fillRect(
                    (int)ene.getX(),
                    (int)ene.getY(),
                    (int)ene.getW(), 
                    (int)ene.getH()
                    );
                    }
                    
                } );
                
                
                
                bulletList.forEach( bull ->
                {
                    bull.drawRotate( g );  
                } );
                
                break;
                
            case PAUSED:
                
                
                break;
            
        }
    }

    @Override
    public void renderHUD(Graphics2D g) 
    {
        
          switch( gameState )
        {
        
            case PLAYING:
                
                
                break;
                
            case PAUSED:
                
                
                break;
            
        }
          
    }

    @Override
    public void updateControl() 
    {
    }

    @Override
    public void manageNetworkData() throws IOException {
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent gpdsce) {
    }
    
    
    
    
}//
