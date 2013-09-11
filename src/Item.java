import java.awt.geom.Point2D;

import GameEngine.GameObject;
import GameEngine.GameTexture;

public class Item extends GameObject 
{
	int life;
	public Item(float x, float y) {
		super(x, y);
	}
	public Item(Point2D.Float v, GameTexture texture) {
		super (v.x, v.y);
        addTexture(texture);
	}
	public Item(float x, float y, GameTexture texture) {
		super (x, y);
        addTexture(texture);
	}
	public void setLife(int num)
	{
		life = num;
	}
	public int getLife()
	{
		return life;
	}
}