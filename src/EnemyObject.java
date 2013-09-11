import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Float;

import GameEngine.GameTexture;
import GameEngine.Game.ResourceLoader;



public class EnemyObject extends PhysicalObject 
{
	public int life = 100;
	public PlayerObject Player = null;
	int Damage = 10;
	float direction;
	Point2D.Float oldPosition;
	
	private GameTexture enemyTexture;
	private GameTexture enemyTexture2;
	
	public void initStep(ResourceLoader loader) {
	enemyTexture = loader.load("Textures/enemy.png");
	enemyTexture2 = loader.load("Textures/enemy2.png");
	}
	
    public EnemyObject(float x, float y){
    	super(x,y);
    }
    public EnemyObject(float x, float y, float m, GameTexture enemyTexture, PlayerObject player) 
    {
    	super (x, y, m);
        addTexture(enemyTexture);
        Player = player;
	}
	public void doTimeStep() 
    {
    	float dir = 90 + this.getDegreesTo(Player);
    	//this.applyForceInDirection(dir, .01f);
    	this.moveInDirection(dir);
        super.doTimeStep();
       // oldPosition = this.getPosition();
        
    }
	public void moveInDirection(float direction) {
		oldPosition = this.getPosition();
		incrementPosition((float)Math.sin(Math.toRadians(direction)), -(float)Math.cos(Math.toRadians(direction)));
		setDirection(direction);
		
	}
	public int getDamage()
    {
    	return Damage;
    }
	public Float getPosition1()
    {
    	return oldPosition;
    }
	public int getLife()
    {
    	return life;
    }
	
    public void setLife(int l)
    {
    	life = l;
    }
    public void revertPosition () {
    	this.setPosition(oldPosition);
    }
    public float getDirection() {
		return direction;
	}
    public void setDirection(float direction) {
        while (direction < 0.0) direction += 360.0;
        while (direction >= 360.0) direction -= 360.0;
        
        this.direction = direction;
    }
    
}
