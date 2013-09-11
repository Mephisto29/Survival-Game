import java.awt.geom.Point2D;

import GameEngine.GameTexture;

public class HealthPack extends Item 
{
	public HealthPack(float x, float y) {
		super(x, y);
	}
	public HealthPack(Point2D.Float v) {
		super(v.x, v.y);
	}
	public HealthPack(Point2D.Float v, GameTexture healthTexture) {
		super (v.x, v.y , healthTexture);
	}
	public static int health = 25;
	public int getHealth()
    {
    	return health;
    }
}