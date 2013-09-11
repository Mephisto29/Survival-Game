import java.awt.geom.Point2D;

import GameEngine.GameTexture;

public class Pistol extends Item 
{
	public Pistol(float x, float y) {
		super(x, y);
	}
	public Pistol(Point2D.Float v) {
		super(v.x, v.y);
	}
	public Pistol(Point2D.Float v, GameTexture rocketTexture) {
		super (v.x, v.y , rocketTexture);
	}
	public static int fireRate = 70;
	public static int Damage = 80;
	public static String name = "Pistol";
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