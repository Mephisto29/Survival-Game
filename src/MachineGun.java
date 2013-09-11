import java.awt.geom.Point2D;

import GameEngine.GameTexture;

public class MachineGun extends Item 
{
	public MachineGun(float x, float y) {
		super(x, y);
	}
	public MachineGun(Point2D.Float v) {
		super(v.x, v.y);
	}
	public MachineGun(Point2D.Float v, GameTexture rocketTexture) {
		super (v.x, v.y , rocketTexture);
	}
	public static int fireRate = 10;
	public static int Damage = 20;
	public static int getFireRate()
    {
    	return fireRate;
    }
	public static int getDamage()
	{
		return Damage;
	}
	public static String name = "FlameThrower";
	public static String getName()
	{
		return name;
	}
}