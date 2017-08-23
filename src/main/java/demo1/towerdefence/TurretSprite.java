/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo1.towerdefence;

import com.ilusion2.level.GameLevel;
import com.ilusion2.sprite.Sprite;
import java.awt.Image;
import java.util.List;

/**
 *
 * @author nigthmare
 */
public class TurretSprite extends Sprite
{
   
    
    Sprite target;
    Sprite bullet;
    int prepareBullet;
    boolean bulletLoaded;
   
    
    
    public TurretSprite( Image img )
    {
    super( img );
    }
    
    
    
    public void seekAndDestroy( List<Sprite> spriteLsit, GameLevel level )
    {
    
        
        //buscar el sprite mas cerca que este a 100 de rango
        // y si lo hay agregarlo como target
        this.target =
                this.spriteNearest(spriteLsit, level, 100 );
//        System.out.println("::: target: "+target);
        
        if( null != target && target.isVisible() &&  bulletLoaded )
        {
            System.out.println(":::disparo");
            bulletLoaded = false;
            
            //set rotation of bullet
            bullet.setDegrees( ( int ) this.degrees );
            bullet.setPosition( this.getCenterX(), this.getCenterY() );
            //set direction and speed for bullets
            bullet.moveTo( target, 5 );
            
        }//
        else
        {
        System.out.println(":::NOdisparo");
        }
        
        //if there is/was target but is invisible then
        //we have to look for another
        if( target != null && !target.isVisible())
        {target = null;}
            
        if( !bulletLoaded )    
          prepareBullet ++;
        
        if( prepareBullet >= 100)
        {
            System.out.println("::: cargo  bala");
        bulletLoaded = true;
        prepareBullet = 0;
        }
        
    
    }
    
    
    
    //getters & setters

    public Sprite getTarget() {
        return target;
    }

    public void setTarget(Sprite target) {
        this.target = target;
    }

    public Sprite getBullet() {
        return bullet;
    }

    public void setBullet(Sprite bullet) {
        this.bullet = bullet;
    }

    public int getPrepareBullet() {
        return prepareBullet;
    }

    public void setPrepareBullet(int prepareBullet) {
        this.prepareBullet = prepareBullet;
    }

    public boolean isBulletLoaded() {
        return bulletLoaded;
    }

    public void setBulletLoaded(boolean bulletLoaded) {
        this.bulletLoaded = bulletLoaded;
    }
    
    
    
    
    
    
}//class
