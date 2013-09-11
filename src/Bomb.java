import java.awt.geom.Point2D;

import GameEngine.GameTexture;

public class Bomb extends Item 
{
	public Bomb(float x, float y) {
		super(x, y);
	}
	public Bomb(Point2D.Float v) {
		super(v.x, v.y);
	}
	public Bomb(Point2D.Float v, GameTexture rocketTexture) {
		super (v.x, v.y , rocketTexture);
	}
}