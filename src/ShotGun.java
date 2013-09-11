import java.awt.geom.Point2D;

import GameEngine.GameTexture;

public class ShotGun extends Item 
{
	public ShotGun(float x, float y) {
		super(x, y);
	}
	public ShotGun(Point2D.Float v) {
		super(v.x, v.y);
	}
	public ShotGun(Point2D.Float v, GameTexture rocketTexture) {
		super (v.x, v.y , rocketTexture);
	}
	public static int fireRate = 80;
	public static int Damage = 60;
	public static String name = "ShotGun";
	public static int getFireRate()
    {
    	return fireRate;
    }
	public static int getDamage()
	{
		return Damage;
	}
	public static String getName()
	{
		return name;
	}
}