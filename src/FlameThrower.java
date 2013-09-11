import java.awt.geom.Point2D;

import GameEngine.GameTexture;

public class FlameThrower extends Item 
{
	public FlameThrower(float x, float y) {
		super(x, y);
	}
	public FlameThrower(Point2D.Float v) {
		super(v.x, v.y);
	}
	public FlameThrower(Point2D.Float v, GameTexture rocketTexture) {
		super (v.x, v.y , rocketTexture);
	}
	public static int fireRate = 7;
	public static int Damage = 1;
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