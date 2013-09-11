import java.awt.geom.Point2D;

import GameEngine.GameTexture;

public class RocketLauncher extends Item 
{
	public RocketLauncher(float x, float y) {
		super(x, y);
	}
	public RocketLauncher(Point2D.Float v) {
		super(v.x, v.y);
	}
	public RocketLauncher(Point2D.Float v, GameTexture rocketTexture) {
		super (v.x, v.y , rocketTexture);
	}
	public static int fireRate = 60;
	public static int Damage = 100;
	public static String name = "RocketLauncher";
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