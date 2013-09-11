

import GameEngine.GameTexture;

public class BulletObject extends PhysicalObject {
    private int destroyTimer = 0;
    public int Damage = 10;
    public GameTexture currentTexture = null;
    
    public BulletObject (float x, float y, float m, int time, GameTexture bt) {
        super (x, y, m);
        setDestroyTimer(time);
        addTexture(bt);
        currentTexture = bt;
    }
    
    public void setDestroyTimer(int time) {
        destroyTimer = time;
    }
    
    public void doTimeStep() {
        destroyTimer--;
        if (destroyTimer == 0)
            setMarkedForDestruction(true);
        
        super.doTimeStep();
    }
    public void setDamage(int d)
    {
    	Damage = d;
    }
    public int getDamage()
    {
    	return Damage;
    }
	public GameTexture getTexture() {
		return currentTexture;
		
	}
}
