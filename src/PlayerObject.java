

import java.awt.geom.Point2D;

import GameEngine.GameObject;

class PlayerObject extends GameObject {
    
    int numberOfDirectionTextures = 72;
    int life = 100;
    int Maxlife = 100;
    int level;
    int exp = 0;
    int levelexp = 1000;
    float direction;
    String currentWeapon = "Pistol";
    
    Point2D.Float oldPosition;
    
	//==================================================================================================
    public PlayerObject (float x, float y) {
        super (x, y);
    }
    
    //==================================================================================================
    
    public void setDirection(float direction) {
        while (direction < 0.0) direction += 360.0;
        while (direction >= 360.0) direction -= 360.0;
        
        this.direction = direction;
        
        // settign the correct texture
        float offsetDirection = direction+(360.0f/numberOfDirectionTextures)/2.0f;
        while (offsetDirection >= 360.0) offsetDirection -= 360.0;
        
        setActiveTexture((int)((float)(((offsetDirection)/360.0))*numberOfDirectionTextures));
    }
    public String getCurrentWeapon()
    {
    	return currentWeapon;
    }
    public void setCurrentWeapon(String cw)
    {
    	currentWeapon = cw;
    }
    
    public int getLife()
    {
    	return life;
    }
    public int getExp()
    {
    	return exp;
    }
    public int getLevelExp()
    {
    	return levelexp;
    }
    public int getLevel()
    {
    	return level;
    }
    public int getMaxLife()
    {
    	return Maxlife;
    }
	
    public void setLife(int l)
    {
    	life = l;
    }
    public void setExp(int e)
    {
    	exp = e;
    }
    public void setLevelExp(int e)
    {
    	levelexp = levelexp + e;
    }
    public void setMax(int e)
    {
    	Maxlife = e;
    }
    public void levelUp()
    {
    	level = level++;
    }
    
    public void revertPosition () {
    	this.setPosition(oldPosition);
    }
    
    public float getDirection() {
		return direction;
	}


	public void moveInDirection(float direction) {
		oldPosition = this.getPosition();
		incrementPosition((float)Math.sin(Math.toRadians(direction))*2, -(float)Math.cos(Math.toRadians(direction))*2);
		setDirection(direction);
	}
}
