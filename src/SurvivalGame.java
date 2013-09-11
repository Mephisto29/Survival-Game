import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileInputStream;
import java.util.Random;
import java.util.Vector;

import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
import GameEngine.Game;
import GameEngine.GameFont;
import GameEngine.GameObject;
import GameEngine.GameTexture;

//==================================================================================================
//==================================================================================================

public class SurvivalGame extends Game {
	// Offset of the screen
	private Point2D.Float offset = new Point2D.Float(0, 0);

	private boolean alive = true;

	// A Collection of GameObjects in the world that will be used with the
	// collision detection system
	private Vector<GameObject> objects = new Vector<GameObject>();

	// Grid GameObjects
	private GameObject[][] gridTile;

	private Random randomGenerator = new Random();
	private int randomInt;
	private Sound sound = new Sound();

	// game objects for grids to be checked
	private Vector<GameObject> nhb;
	private Vector<GameObject> nhb1;
	private Vector<GameObject> nhb2;
	private Vector<GameObject> nhb3;
	private Vector<GameObject> nhb4;
	private Vector<GameObject> nhb5;

	// The cooldown of the gun (set this to 0 for a cool effect :> )
	private int cooldown = 10;
	private int cooldownTimer = 0;
	private int gridSize;
	private int score;
	private int life;
	private int timer1;
	private int timer2;
	private int timer3;
	private int timer4;
	private int Dtimer;

	// Important GameObjects
	private PlayerObject player; // the player

	// Textures that will be used
	private GameTexture bulletTexture;
	private GameTexture softRockTexture;
	private GameTexture rockTexture;
	private GameTexture rockBCRTexture;
	private GameTexture rockBCLTexture;
	private GameTexture rockTCRTexture;
	private GameTexture rockTCLTexture;
	private GameTexture rockLeftTexture;
	private GameTexture rockRightTexture;
	private GameTexture rockTopTexture;
	private GameTexture rockBottomTexture;
	private GameTexture tileTexture;
	private GameTexture enemyTexture;
	private GameTexture enemyTexture2;
	private GameTexture RocketLauncherTexture;
	private GameTexture MachineGunTexture;
	private GameTexture FlameThrowerTexture;
	private GameTexture ShotGunTexture;
	private GameTexture rocketTexture;
	private GameTexture flameTexture;
	private GameTexture healthTexture;
	private GameTexture BombTexture;

	// GameFonts that will be used
	private GameFont arial, serif;

	// The positin of the mouse
	private Point2D.Float mousePos = new Point2D.Float(0, 0);

	// a counter for how far the mousewheel has been moved (just an example)
	//private int mouseWheelTick = 0;

	// Information for the random line at the bottom of the screen
	Point2D.Float[] linePositions = { new Point2D.Float(0, 0),
			new Point2D.Float(100, 100) };
	float[][] lineColours = { { 1.0f, 1.0f, 1.0f, 1.0f },
			{ 1.0f, 0.0f, 0.0f, 1.0f } };

	// ==================================================================================================

	public SurvivalGame(int GFPS) {
		super(GFPS);
	}

	// ==================================================================================================

	public void initStep(ResourceLoader loader) {

		sound.playSound();
		//Loading up some fonts
		arial = loader.loadFont(  new Font("Arial", Font.ITALIC, 20) );
		serif = loader.loadFont(  new Font("Serif", Font.PLAIN, 20) );

		// - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


		//Loading up our textures
		softRockTexture =loader.loadTexture("Textures/softRock.png");
		rockTexture = loader.loadTexture("Textures/rock.png");
		rockBCRTexture = loader.loadTexture("Textures/rockBCR.png");
		rockBCLTexture = loader.loadTexture("Textures/rockBCL.png");
		rockTCRTexture = loader.loadTexture("Textures/rockTCR.png");
		rockTCLTexture = loader.loadTexture("Textures/rockTCL.png");
		rockLeftTexture = loader.loadTexture("Textures/rockLeft.png");
		rockRightTexture = loader.loadTexture("Textures/rockRight.png");
		rockTopTexture = loader.loadTexture("Textures/rockTop.png");
		rockBottomTexture = loader.loadTexture("Textures/rockBottom.png");
		tileTexture = loader.load("Textures/tile.png");
		enemyTexture = loader.load("Textures/enemy.png");
		enemyTexture2 = loader.load("Textures/enemy2.png");
		bulletTexture = loader.loadTexture("Textures/bullet.png");
		RocketLauncherTexture = loader.loadTexture("Textures/RocketLauncher.png");
		MachineGunTexture = loader.loadTexture("Textures/MachineGun.png");
		FlameThrowerTexture = loader.loadTexture("Textures/FlameThrower.png");
		ShotGunTexture = loader.loadTexture("Textures/ShotGun.png");
		rocketTexture = loader.loadTexture("Textures/rocket.png");
		flameTexture = loader.loadTexture("Textures/flame.png");
		healthTexture = loader.loadTexture("Textures/health.png");
		BombTexture = loader.loadTexture("Textures/Bomb.png");

		gridSize = 24;

		// creating some random rocks to shoot
		for (int i = 0 ; i < 8 ; i++ ) 
		{

			float x = (float)((Math.random()*(gridSize-4)+2)*tileTexture.getWidth());
			float y = (float)((Math.random()*(gridSize-4)+2)*tileTexture.getHeight());

			GameObject go = new GameObject(x, y);
        	go.addTexture(softRockTexture, 0, 0);
        	objects.add(go);
       }
		
       // creating the floor objects
       gridTile = new GameObject[gridSize][gridSize];
       for (int i = 0 ; i < gridSize ; i++ ) {
    	   for (int j = 0 ; j < gridSize ; j++ ) {
    		   	gridTile[i][j] = new GameObject(tileTexture.getWidth()*i,tileTexture.getHeight()*j);
    		   	gridTile[i][j].addTexture(tileTexture, 0, 0);
           }
       }


       // Creating wall objects
       for (int i = 0 ; i < tileTexture.getWidth()*gridSize ; i += rockTexture.getWidth()) {
    	   WallObject go = new WallObject(i, 0);
    	   if (i == 0)
    	   {
        	   go.addTexture(rockBCLTexture, 0, 0);
        	   objects.add(go);
        	   
        	   go = new WallObject(i, tileTexture.getHeight()*(gridSize-1));
        	   go.addTexture(rockTCLTexture, 0, 0);
        	   objects.add(go);
    	   }
    	   else if (i == (tileTexture.getWidth()*gridSize - rockTexture.getWidth()))
    	   {
        	   go.addTexture(rockBCRTexture, 0, 0);
        	   objects.add(go);
        	   
        	   go = new WallObject(i, tileTexture.getHeight()*(gridSize-1));
        	   go.addTexture(rockTCRTexture, 0, 0);
        	   objects.add(go);
    	   }
    	   else
    	   {
    	   go.addTexture(rockBottomTexture, 0, 0);
    	   objects.add(go);
    	   
    	   go = new WallObject(i, tileTexture.getHeight()*(gridSize-1));
    	   go.addTexture(rockTopTexture, 0, 0);
    	   objects.add(go);
    	   }

    	   
       }
       for (int i = tileTexture.getHeight() ; i < tileTexture.getHeight()*(gridSize-1) ; i += rockTexture.getHeight())
       {
    	   WallObject go = new WallObject(0, i);
    	   go.addTexture(rockLeftTexture, 0, 0);
    	   objects.add(go);

    	   go = new WallObject(rockTexture.getWidth()*(gridSize-1), i);
    	   go.addTexture(rockRightTexture, 0, 0);
    	   objects.add(go);
       }

       // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

       // Creating the player's ship
       player = new PlayerObject((float)(tileTexture.getWidth()*gridSize)/2f, (float)(tileTexture.getHeight()*gridSize)/2f);

       for (int i = 0 ; i < 72 ; i++) {
    	   player.addTexture(loader.load("Textures/ship/spaceship_sm"+i+".gif"),16, 16);
       }

       objects.add(player);

       // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	}

	// ==================================================================================================

	// this method is used to fire a bullet
	public void fireBullet() {
		cooldownTimer = cooldown;

		float dir = 90 + player.getDegreesTo(mousePos);
		BulletObject bullet = null;
		// if your current weapon changes, so does the different firing mechanisms and probably textures.
		if (player.getCurrentWeapon().equals("Rocket Launcher")) {
			cooldownTimer = RocketLauncher.getFireRate();
			bullet = new BulletObject(player.getPosition().x
					+ (float) Math.sin(Math.toRadians(dir)) * 32,
					player.getPosition().y
							- (float) Math.cos(Math.toRadians(dir)) * 32, 1f,
					300, rocketTexture);
			bullet.setDamage(RocketLauncher.getDamage());
			bullet.applyForceInDirection(dir, 6f);
			objects.add(bullet);
			sound.playRocket();
		} else if (player.getCurrentWeapon().equals("Machine Gun")) {
			cooldownTimer = MachineGun.getFireRate();
			bullet = new BulletObject(player.getPosition().x
					+ (float) Math.sin(Math.toRadians(dir)) * 32,
					player.getPosition().y
							- (float) Math.cos(Math.toRadians(dir)) * 32, 1f,
					300, bulletTexture);
			bullet.setDamage(MachineGun.getDamage());
			bullet.applyForceInDirection(dir, 6f);
			objects.add(bullet);
			sound.playMachineGun();
		} else if (player.getCurrentWeapon().equals("Flame Thrower")) {
			cooldownTimer = FlameThrower.getFireRate();
			bullet = new BulletObject(player.getPosition().x
					+ (float) Math.sin(Math.toRadians(dir)) * 32,
					player.getPosition().y
							- (float) Math.cos(Math.toRadians(dir)) * 32, 1f,
					300, flameTexture);
			bullet.setDamage(FlameThrower.getDamage());
			bullet.applyForceInDirection(dir, 3f);
			objects.add(bullet);
			sound.playFlame();
		} else if (player.getCurrentWeapon().equals("Shot Gun")) {
			cooldownTimer = ShotGun.getFireRate();

			for (int i = 0; i < 5; i++) {
				dir = 80 + player.getDegreesTo(mousePos);
				bullet = new BulletObject(player.getPosition().x
						+ (float) Math.sin(Math.toRadians(dir)) * 32,
						player.getPosition().y
								- (float) Math.cos(Math.toRadians(dir)) * 32,
						1f, 300, bulletTexture);
				bullet.setDamage(ShotGun.getDamage());
				bullet.applyForceInDirection(dir + 5 * i, 6f);
				objects.add(bullet);
			}
			sound.playShotGun();
		} else if(player.getCurrentWeapon().equals("Pistol"))
		{
			cooldownTimer = Pistol.getFireRate();
			bullet = new BulletObject(player.getPosition().x
					+ (float) Math.sin(Math.toRadians(dir)) * 32,
					player.getPosition().y
							- (float) Math.cos(Math.toRadians(dir)) * 32, 1f,
					300, bulletTexture);
			bullet.setDamage(Pistol.getDamage());
			bullet.applyForceInDirection(dir, 6f);
			objects.add(bullet);
			sound.playPistol();
		}

		// bullet.setVelocity(player.getVelocity());
	}

	public static boolean isPointInBox(final Point2D.Float point,
			final Rectangle2D.Float box) {
		return box.contains(point.x, point.y);
	}

	// dont use this any more :D
	// This is a pretty bad implementation and faster ones exist, itis suggested
	// you find a better one. At least try make use of theRectangle2D's
	// createIntersection method.
	public static boolean boxIntersectBox(final Rectangle2D.Float d,
			final Rectangle2D.Float d2) {
		return isPointInBox(new Point2D.Float(d.x, d.y), d2)
				|| isPointInBox(new Point2D.Float(d.x, d.y + d.height), d2)
				|| isPointInBox(new Point2D.Float(d.x + d.width, d.y), d2)
				|| isPointInBox(
						new Point2D.Float(d.x + d.width, d.y + d.height), d2)
				|| isPointInBox(new Point2D.Float(d2.x, d2.y), d)
				|| isPointInBox(new Point2D.Float(d2.x, d2.y + d2.height), d)
				|| isPointInBox(new Point2D.Float(d2.x + d2.width, d2.y), d)
				|| isPointInBox(new Point2D.Float(d2.x + d2.width, d2.y
						+ d2.height), d);
	}

	private void handleControls(GameInputInterface gii) {

		// ----------------------------------

		// This isn't so great, there are better and neater ways to do this, you
		// are encouraged to implement a better one
		boolean move = false;
		float directionToMove = 0;

		if (gii.keyDown(KeyEvent.VK_UP)) {
			move = true;
			if (gii.keyDown(KeyEvent.VK_LEFT)
					&& !gii.keyDown(KeyEvent.VK_RIGHT))
				directionToMove = 225;
			else if (gii.keyDown(KeyEvent.VK_RIGHT)
					&& !gii.keyDown(KeyEvent.VK_LEFT))
				directionToMove = 135;
			else
				directionToMove = 180;
		} else if (gii.keyDown(KeyEvent.VK_DOWN)) {
			move = true;
			if (gii.keyDown(KeyEvent.VK_LEFT)
					&& !gii.keyDown(KeyEvent.VK_RIGHT))
				directionToMove = -45;
			else if (gii.keyDown(KeyEvent.VK_RIGHT)
					&& !gii.keyDown(KeyEvent.VK_LEFT))
				directionToMove = 45;
			else
				directionToMove = 0;
		} else if (gii.keyDown(KeyEvent.VK_LEFT)
				&& !gii.keyDown(KeyEvent.VK_RIGHT)) {
			move = true;
			directionToMove = 270;
		} else if (gii.keyDown(KeyEvent.VK_RIGHT)
				&& !gii.keyDown(KeyEvent.VK_LEFT)) {
			move = true;
			directionToMove = 90;
		}
		if (move)
			player.moveInDirection(directionToMove);

		if (cooldownTimer <= 0) {
			if (gii.keyDown(KeyEvent.VK_SPACE)
					|| gii.mouseButtonDown(MouseEvent.BUTTON1)) {
				fireBullet();
			}
		}
		cooldownTimer--;

	}

	// ==================================================================================================

	public void logicStep(GameInputInterface gii) {

		/*
		 * if(gii.keyDown(KeyEvent.VK_W)) { offset.y -= 3.0; }
		 * if(gii.keyDown(KeyEvent.VK_S)) { offset.y += 3.0; }
		 * if(gii.keyDown(KeyEvent.VK_A)) { offset.x += 3.0; }
		 * if(gii.keyDown(KeyEvent.VK_D)) { offset.x -= 3.0; }
		 * if(gii.keyDown(KeyEvent.VK_ESCAPE)) { endGame(); }
		 */

		// some examples of the mouse interface
		//mouseWheelTick += gii.mouseWheelRotation();
		mousePos.x = (float) gii.mouseXScreenPosition() - offset.x;
		mousePos.y = (float) gii.mouseYScreenPosition() - offset.y;

		// ----------------------------------

		if (alive) {
			handleControls(gii);
			player.setDirection(90 + player.getDegreesTo(mousePos));
		}
		life = player.getLife();

		// NOTE: you must call doTimeStep for ALL game objects once per frame!
		// Updating step for each object
		for (int i = 0; i < objects.size(); i++) {
			objects.elementAt(i).doTimeStep();
		}

		// setting the camera offset
		offset.x = -player.getPosition().x
				+ (this.getViewportDimension().width / 2);
		offset.y = -player.getPosition().y
				+ (this.getViewportDimension().height / 2);

		nhb1 = new Vector<GameObject>();
		nhb2 = new Vector<GameObject>();
		nhb3 = new Vector<GameObject>();
		nhb4 = new Vector<GameObject>();
		nhb5 = new Vector<GameObject>();

		for (int oc = 0; oc < objects.size(); oc++) {
			int scale = gridSize / 2;
			GameObject item = objects.elementAt(oc);

			// bad case for when objects lie on the border lines
			// had to make it quite broad due to big texture sizes.
			if (((int) ((item.getPosition().x) / 64) >= (scale - scale / 3))
					&& ((int) (item.getPosition().y / 64) >= (scale - scale / 3))
					|| ((int) ((item.getPosition().x) / 64) <= (scale - scale / 3))
					&& ((int) (item.getPosition().y / 64) <= (scale - scale / 3))
					|| ((int) ((item.getPosition().x) / 64) <= (scale - scale / 3))
					&& ((int) (item.getPosition().y / 64) >= (scale - scale / 3))
					|| ((int) ((item.getPosition().x) / 64) >= (scale - scale / 3))
					&& ((int) (item.getPosition().y / 64) <= (scale - scale / 3))) {
				nhb5.add(objects.elementAt(oc));
			}
			// other wise quite simple
			else if (((int) ((item.getPosition().x) / 64) <= scale)
					&& ((int) (item.getPosition().y / 64) <= scale)) {
				nhb1.add(objects.elementAt(oc));
			} else if (((int) ((item.getPosition().x) / 64) <= scale)
					&& ((int) (item.getPosition().y / 64) > scale)) {
				nhb2.add(objects.elementAt(oc));
			} else if (((int) ((item.getPosition().x) / 64) > scale)
					&& ((int) (item.getPosition().y / 64) <= scale)) {
				nhb3.add(objects.elementAt(oc));
			} else if (((int) ((item.getPosition().x) / 64) > scale)
					&& ((int) (item.getPosition().y / 64) >= scale)) {
				nhb4.add(objects.elementAt(oc));
			}
		}

		// checking each unit against each other unit for collisions
		for (int i = 0; i < objects.size(); i++) {
			GameObject o1 = objects.elementAt(i);
			if (nhb1.contains(o1)) {
				nhb = nhb1;
			} else if (nhb2.contains(o1)) {
				nhb = nhb2;
			} else if (nhb3.contains(o1)) {
				nhb = nhb3;
			} else if (nhb4.contains(o1)) {
				nhb = nhb4;
			}
			// worst case
			else {
				nhb = nhb5;
			}

			for (int j = 0; j < nhb.size(); j++) {
				GameObject o2 = nhb.elementAt(j);

				// just check that they arent the same object :D
				if (o1 != o2) {
					if (o1 instanceof WallObject && o2 instanceof WallObject) {
						// do nothing
					} else if (o1 instanceof EnemyObject
							&& o2 instanceof EnemyObject) {
						// do nothing
					} else if (o1 instanceof BulletObject
							&& o2 instanceof BulletObject) {
						// do nothing
					}
					// COLLISIONS BETWEEN ENEMIES AND THE WALLS. This causes them to just get stuck. fixed by killing the creeps
					else if (((o1 instanceof EnemyObject || o1 instanceof EnemyObject2) && o2 instanceof WallObject)
							|| o1 instanceof WallObject
							&& (o2 instanceof EnemyObject || o2 instanceof EnemyObject2)) {
						if (collisionOccurs(o1, o2)) {
							if (o1 instanceof EnemyObject) {
								o1.setMarkedForDestruction(true);
								EnemyObject obj = (EnemyObject) o1;
								obj.revertPosition();
							} else {
								o2.setMarkedForDestruction(true);
								EnemyObject obj = (EnemyObject) o2;
								obj.revertPosition();
							}
						}
					}
					// COLLISIONS BETWEEN BULLETS AND WALL
					else if ((o1 instanceof BulletObject && o2 instanceof WallObject)
							|| o1 instanceof WallObject
							&& o2 instanceof BulletObject) {
						if (collisionOccurs(o1, o2)) {
							// just destroy the bullet, not the wall
							if (o1 instanceof BulletObject) {
								o1.setMarkedForDestruction(true);
							} else {
								o2.setMarkedForDestruction(true);
							}
						}
					}
					// COLLISIONS BETWEEN player AND Items
					else if ((o1 instanceof PlayerObject && o2 instanceof Item)
							|| o1 instanceof Item && o2 instanceof PlayerObject) {
						if (collisionOccurs(o1, o2)) {
							if (o1 instanceof RocketLauncher) {
								player.setCurrentWeapon("Rocket Launcher");
								o1.setMarkedForDestruction(true);
							} else if (o2 instanceof RocketLauncher) {
								player.setCurrentWeapon("Rocket Launcher");
								o2.setMarkedForDestruction(true);
							} else if (o1 instanceof MachineGun) {
								player.setCurrentWeapon("Machine Gun");
								o1.setMarkedForDestruction(true);
							} else if (o2 instanceof MachineGun) {
								player.setCurrentWeapon("Machine Gun");
								o2.setMarkedForDestruction(true);
							} else if (o1 instanceof FlameThrower) {
								player.setCurrentWeapon("Flame Thrower");
								o1.setMarkedForDestruction(true);
							} else if (o2 instanceof FlameThrower) {
								player.setCurrentWeapon("Flame Thrower");
								o2.setMarkedForDestruction(true);
							} else if (o1 instanceof ShotGun) {
								player.setCurrentWeapon("Shot Gun");
								o1.setMarkedForDestruction(true);
							} else if (o2 instanceof ShotGun) {
								player.setCurrentWeapon("Shot Gun");
								o2.setMarkedForDestruction(true);
							} else if (o1 instanceof Bomb) {
									for(int p = 0; p < objects.size(); p++)
									{
										GameObject obj = objects.elementAt(p);
										if(obj instanceof EnemyObject)
										{
											obj.setMarkedForDestruction(true);
										}
									}
									o1.setMarkedForDestruction(true);
									sound.playExplosion();
							} else if (o2 instanceof Bomb) {
								for(int p = 0 ; p < objects.size() ; p ++)
								{
									GameObject obj = objects.elementAt(p);
									if(obj instanceof EnemyObject)
									{
										obj.setMarkedForDestruction(true);
									}
								}
								o2.setMarkedForDestruction(true);
								sound.playExplosion();
							} else if (o1 instanceof HealthPack) {
								HealthPack obj1 = (HealthPack) o1;
								player.setLife(player.getLife()
										+ obj1.getHealth());
								if (player.getLife() > player.getMaxLife()) {
									player.setLife(player.getMaxLife());
								}
								o1.setMarkedForDestruction(true);
							} else if (o2 instanceof HealthPack) {
								HealthPack obj2 = (HealthPack) o2;
								player.setLife(player.getLife()
										+ obj2.getHealth());
								if (player.getLife() > player.getMaxLife()) {
									player.setLife(player.getMaxLife());
								}
								o2.setMarkedForDestruction(true);
							}
						}
					} else if (o1 instanceof Item || o2 instanceof Item) {
						// do nothing
					}
					/*
					 * COLLISIONS BETWEEN Enemies AND Items else if ((o1
					 * instanceof EnemyObject && o2 instanceof Item) || o1
					 * instanceof Item && o2 instanceof EnemyObject) { } //
					 * COLLISIONS BETWEEN Bullets AND Items else if ((o1
					 * instanceof BulletObject && o2 instanceof Item) || o1
					 * instanceof Item && o2 instanceof BulletObject) { }
					 */
					// Collisiont between Player and Enemies
					else if ((o1 instanceof PlayerObject && (o2 instanceof EnemyObject || o2 instanceof EnemyObject2))
							|| (o1 instanceof EnemyObject || o1 instanceof EnemyObject2)
							&& o2 instanceof PlayerObject) {
						if (collisionOccurs(o1, o2)) {
							if (o1 instanceof PlayerObject) {
								PlayerObject obj1 = (PlayerObject) o1;
								EnemyObject obj2 = (EnemyObject) o2;
								if (Dtimer >= 100) {
									obj1.setLife(obj1.getLife()
											- obj2.getDamage());
									Dtimer = 0;
								}
								if (obj1.getLife() <= 0) {
									// to prevent null pointer exception, just exit :D
									System.exit(0);
								//	alive = false;
								//	o1.setMarkedForDestruction(true);
								}
							} else {
								PlayerObject obj2 = (PlayerObject) o2;
								EnemyObject obj1 = (EnemyObject) o1;
								if (Dtimer >= 150) {
									obj2.setLife(obj2.getLife()
											- obj1.getDamage());
									Dtimer = 0;
								}
								if (obj2.getLife() <= 0) {
									// to prevent null pointer exception, just exit :D
									System.exit(0);
									//alive = false;
									//o2.setMarkedForDestruction(true);
								}
							}
						}
					}
					// Collision between Bullets and enemies
					else if ((o1 instanceof BulletObject && (o2 instanceof EnemyObject || o2 instanceof EnemyObject2))
							|| (o1 instanceof EnemyObject || o1 instanceof EnemyObject2)
							&& o2 instanceof BulletObject) {
						if (collisionOccurs(o1, o2)) {
							if (o1 instanceof BulletObject) {
								BulletObject obj1 = (BulletObject) o1;
								EnemyObject obj2 = (EnemyObject) o2;
								// damage enemy based on bullet damage
								if (o1.isMarkedForDestruction()) {}
								else
								{
								obj2.setLife(obj2.getLife() - obj1.getDamage());
								}
								// check if enemy is dead, and drop an item if possible
								if (obj2.getLife() <= 0) {
									randomInt = randomGenerator.nextInt(100);
									// check if the enemy has already been destroyed, to prevent double checks
									if (o2.isMarkedForDestruction()) {
									} else {
										if (randomInt <= 13) {
											randomItem(obj2);
										}
										score = score + 1;
										player.setExp(player.getExp() + 10);
										o2.setMarkedForDestruction(true);
									}
								}
								// flames are not stoped... they continue on till they fade out.
								if (player.getCurrentWeapon() != "Flame Thrower") {
									o1.setMarkedForDestruction(true);
								}
							} else {
								BulletObject obj1 = (BulletObject) o2;
								EnemyObject obj2 = (EnemyObject) o1;
								if (o2.isMarkedForDestruction()) {}
								else
								{
								obj2.setLife(obj2.getLife() - obj1.getDamage());
								}
								if (obj2.getLife() <= 0) {
									randomInt = randomGenerator.nextInt(100);
									// increase scores and experience after getting the kill
									if (o1.isMarkedForDestruction()) {
									} else {
										if (randomInt <= 13) {
											randomItem(obj2);
										}
										score = score + 1;
										player.setExp(player.getExp() + 10);
										o1.setMarkedForDestruction(true);
									}
								}
								if (player.getCurrentWeapon() != "Flame Thrower") {
									o2.setMarkedForDestruction(true);
								}
							}
						}
					}
					// ANY COLLISINS WITH THE PLAYER
					else if (o1 instanceof PlayerObject
							|| o2 instanceof PlayerObject) {
						if (collisionOccurs(o1, o2)) {
							player.revertPosition();
						}
					} else {
						if (collisionOccurs(o1, o2)) {
							System.out.println("Removing objects " + i + ":"
									+ j);
							if (o1 instanceof PlayerObject) {
							} else
								o1.setMarkedForDestruction(true);
							if (o2 instanceof PlayerObject) {
							} else
								o2.setMarkedForDestruction(true);
						}

						// Note: you can also implement something
						// likeo1.reduceHealth(5); if you don't want the object
						// to be immediatly destroyed
					}
				}
			}
		}

		// destroying units that need to be destroyed
		for (int i = 0; i < objects.size(); i++) {
			if (objects.elementAt(i).isMarkedForDestruction()) {
				if (objects.elementAt(i) == player) {

					System.exit(0);
					alive = false;
					player = null;
				}
				// removing object from list of GameObjects
				objects.remove(i);
				i--;
			}
		}

		// level checks
		if (player.getExp() >= player.getLevelExp()) {
			player.levelUp();
			player.setLevelExp((player.getLevelExp() * 2));
			player.setMax((player.getMaxLife() * 2));
			player.setLife(player.getMaxLife());
			player.setExp(0);
		}

		// timer items and time controlled events
		timer4++;
		timer3++;
		timer2++;
		timer1++;
		Dtimer++;
		// implementation of own loop to loops ound, file too large for continuous stream
		if(timer4 == 1530)
		{
			sound.playSound();
			timer4 = 0;
		}
		// damage timer, which prevents the player from taking damage every frame
		if (timer1 == 200) {
			for (int i = 0; i < objects.size(); i++) {
				if (objects.elementAt(i) instanceof Item) {
					Item lol = (Item) objects.elementAt(i);
					lol.setLife(lol.getLife() - 1);
					if (lol.getLife() <= 0) {
						lol.setMarkedForDestruction(true);
					}
				}
			}

			timer1 = 0;
		}
		// spawners
		if (timer2 == 300)
		{
			spawnEnemy(enemyTexture);
			timer2 = 0;
		}
		if (timer3 == 750)
		{
			spawnEnemy2(enemyTexture2);
			timer3 = 0;
		}
	}

	// ==================================================================================================

	// Enemy spawner
	public void spawnEnemy(GameTexture txt) {
		// TOP LEFT SPAWNER
		float x3 = (float) this.getViewportDimension().width - 700;
		float y3 = (float) this.getViewportDimension().height + (64 * 13 + 32);
		// TOP RIGHT SPAWNER
		float x = (float) this.getViewportDimension().width + 64 * 10;
		float y = (float) this.getViewportDimension().height + (64 * 13 + 32);
		// BOTTOM RIGHT SPAWNER
		float x2 = (float) this.getViewportDimension().width + 64 * 10;
		float y2 = (float) this.getViewportDimension().height - 464;
		// BOTTOM LEFT SPAWNER
		float x4 = (float) this.getViewportDimension().width - 700;
		float y4 = (float) this.getViewportDimension().height - 464;

		EnemyObject go = new EnemyObject(x, y);
		EnemyObject go2 = new EnemyObject(x2, y2);
		EnemyObject go3 = new EnemyObject(x3, y3);
		EnemyObject go4 = new EnemyObject(x4, y4);

		float dir = 90 + go.getDegreesTo(player);
		float dir2 = 90 + go2.getDegreesTo(player);
		float dir3 = 90 + go3.getDegreesTo(player);
		float dir4 = 90 + go4.getDegreesTo(player);
		
		EnemyObject enemy;
		EnemyObject enemy2;
		EnemyObject enemy3;
		EnemyObject enemy4;

		if(txt == enemyTexture)
		{
		enemy = new EnemyObject(x, y, 1f, txt, player);
		enemy2 = new EnemyObject(x2, y2, 1f, txt, player);
		enemy3 = new EnemyObject(x3, y3, 1f, txt, player);
		enemy4 = new EnemyObject(x4, y4, 1f, txt, player);
		}
		else
		{
			enemy = new EnemyObject(x, y, 2f, txt, player);
			enemy2 = new EnemyObject(x2, y2, 2f, txt, player);
			enemy3 = new EnemyObject(x3, y3, 2f, txt, player);
			enemy4 = new EnemyObject(x4, y4, 2f, txt, player);
		}

		enemy.applyForceInDirection(dir, 1f);
		enemy2.applyForceInDirection(dir2, 0.5f);
		enemy3.applyForceInDirection(dir3, 0.3f);
		enemy4.applyForceInDirection(dir4, 0f);

		objects.add(enemy);
		objects.add(enemy2);
		objects.add(enemy3);
		objects.add(enemy4);
	}
	public void spawnEnemy2(GameTexture txt) {
		// TOP LEFT SPAWNER
		float x3 = (float) this.getViewportDimension().width - 700;
		float y3 = (float) this.getViewportDimension().height + (64 * 13 + 32);
		// TOP RIGHT SPAWNER
		float x = (float) this.getViewportDimension().width + 64 * 10;
		float y = (float) this.getViewportDimension().height + (64 * 13 + 32);
		// BOTTOM RIGHT SPAWNER
		float x2 = (float) this.getViewportDimension().width + 64 * 10;
		float y2 = (float) this.getViewportDimension().height - 464;
		// BOTTOM LEFT SPAWNER
		float x4 = (float) this.getViewportDimension().width - 700;
		float y4 = (float) this.getViewportDimension().height - 464;

		EnemyObject2 go = new EnemyObject2(x, y);
		EnemyObject2 go2 = new EnemyObject2(x2, y2);
		EnemyObject2 go3 = new EnemyObject2(x3, y3);
		EnemyObject2 go4 = new EnemyObject2(x4, y4);

		float dir = 90 + go.getDegreesTo(player);
		float dir2 = 90 + go2.getDegreesTo(player);
		float dir3 = 90 + go3.getDegreesTo(player);
		float dir4 = 90 + go4.getDegreesTo(player);
		
		EnemyObject2 enemy;
		EnemyObject2 enemy2;
		EnemyObject2 enemy3;
		EnemyObject2 enemy4;

		if(txt == enemyTexture)
		{
		enemy = new EnemyObject2(x, y, 1f, txt, player);
		enemy2 = new EnemyObject2(x2, y2, 1f, txt, player);
		enemy3 = new EnemyObject2(x3, y3, 1f, txt, player);
		enemy4 = new EnemyObject2(x4, y4, 1f, txt, player);
		}
		else
		{
			enemy = new EnemyObject2(x, y, 2f, txt, player);
			enemy2 = new EnemyObject2(x2, y2, 2f, txt, player);
			enemy3 = new EnemyObject2(x3, y3, 2f, txt, player);
			enemy4 = new EnemyObject2(x4, y4, 2f, txt, player);
		}

		enemy.applyForceInDirection(dir, 0f);
		enemy2.applyForceInDirection(dir2, 0f);
		enemy3.applyForceInDirection(dir3, 0f);
		enemy4.applyForceInDirection(dir4, 0f);

		objects.add(enemy);
		objects.add(enemy2);
		objects.add(enemy3);
		objects.add(enemy4);
	}

	// method to check for collision
	public boolean collisionOccurs(GameObject o1, GameObject o2) {
		if (boxIntersectBox(o1.getAABoundingBox(), o2.getAABoundingBox())) {
			Rectangle2D intersect = IntersectionBox(o1.getAABoundingBox(),
					o2.getAABoundingBox());
			;

			if ((pixelBasedCheck(o1, o2, intersect))) {
				return true;
			}
		}
		return false;
	}

	// method for finding rectangle of intersection - got from internet.....
	public static Rectangle2D IntersectionBox(final Rectangle2D.Float area1,
			final Rectangle2D.Float area2) {
		return area1.createIntersection(area2);
	}

	// method for checking if pixels collide
	public static boolean pixelBasedCheck(GameObject o1, GameObject o2,
			Rectangle2D IntersectionRecangle) {
		int[][] array1 = BitMasks(o1.getCurrentTexture());
		int[][] array2 = BitMasks(o2.getCurrentTexture());

		for (int x = 0; x < (int) IntersectionRecangle.getMaxX()
				- (int) IntersectionRecangle.getMinX(); x++)
			for (int y = 0; y < (int) IntersectionRecangle.getMaxX()
					- (int) IntersectionRecangle.getMinX(); y++) {
				int o1x;
				int o2x;
				int o1y;
				int o2y;
				o1y = (int) o1.getAABoundingBox().getHeight()
						- (y + ((int) IntersectionRecangle.getMinY() - (int) o1
								.getAABoundingBox().getMinY()));
				o2y = (int) o2.getAABoundingBox().getHeight()
						- (y + ((int) IntersectionRecangle.getMinY() - (int) o2
								.getAABoundingBox().getMinY()));
				o1x = x
						+ ((int) IntersectionRecangle.getMinX() - (int) o1
								.getAABoundingBox().getMinX());
				o2x = x
						+ ((int) IntersectionRecangle.getMinX() - (int) o2
								.getAABoundingBox().getMinX());

				// Ensures values do not go out of the range of the bitMask
				// arrays...

				if (o1x < 0) {
					o1x = 0;
				}

				if (o2x < 0) {
					o2x = 0;
				}

				if (o1y == o1.getAABoundingBox().getHeight() && o1y >= 1) {
					o1y = (int) o1.getAABoundingBox().getHeight() - 1;
				} else if (o1y < 0) {
					o1y = 0;
				}

				if (o2y == o2.getAABoundingBox().getHeight() && o2y >= 1) {
					o2y = (int) o2.getAABoundingBox().getHeight() - 1;
				} else if (o2y < 0) {
					o2y = 0;
				}

				// returns true if collision occurs
				if ((array1[o1x][o1y] & array2[o2x][o2y]) != 0) {
					return true;
				}

				// reduces the number of loops necessary if possible
				// return ((BitMasks(o1.getCurrentTexture())[o1x][o1y] &
				// BitMasks(o2.getCurrentTexture())[o2x][o2y]) == 1);
			}
		// no collision
		return false;
	}

	// method for creating bit masks of texturess
	public static int[][] BitMasks(GameTexture texture) {
		// create a byte array for storing values
		byte[] buffer;
		// variables to be used
		int[] alpha;
		int[][] mask;
		int alphaCounter;
		int row;
		int colm;
		int width;
		int height;

		// initializing variables

		width = texture.getWidth();
		height = texture.getHeight();
		buffer = new byte[width * height * 4];
		alpha = new int[width * height];
		mask = new int[width][height];
		alphaCounter = 0;
		row = 0;
		colm = 0;

		try {
			texture.getByteBuffer().get(buffer);
			texture.getByteBuffer().rewind();
		} catch (Exception e) {
			System.out.print("Epic fail" + e);
		}
		// loop through buffer only checking every 4th entry (the alpha value)
		for (int i = -1; i < buffer.length; i += 4) {
			if (i > -1) {
				// store alpha value in alpha array
				alpha[alphaCounter] = buffer[i];
				alphaCounter++;
			}
		}
		// loop to populate the mask array
		for (int i = 0; i < alpha.length; i++) {
			// increase now count
			if (i % (height) == 0 && i > 0 && row < height - 1) {
				row++;
			}

			// store variables to check for transparency
			if (alpha[i] != 0) {
				mask[colm][row] = 1;
			} else {
				mask[colm][row] = 0;
			}

			// increase column
			if (colm == width - 1) {
				colm = 0;
			} else {
				colm++;
			}
		}

		return mask;
	}

	public void randomItem(EnemyObject obj2) {
		if (randomInt <= 2) {
			Item lol = new RocketLauncher(obj2.getPosition1(),
					RocketLauncherTexture);
			lol.setLife(5);
			objects.add(lol);
		} else if (randomInt <= 4) {
			Item lol = new MachineGun(obj2.getPosition1(), MachineGunTexture);
			lol.setLife(5);
			objects.add(lol);
		} else if (randomInt <= 6) {
			Item lol = new FlameThrower(obj2.getPosition1(),
					FlameThrowerTexture);
			lol.setLife(5);
			objects.add(lol);
		} else if (randomInt <= 8) {
			Item lol = new ShotGun(obj2.getPosition1(), ShotGunTexture);
			lol.setLife(5);
			objects.add(lol);
		} else if (randomInt <= 10) {
			Item lol = new ShotGun(obj2.getPosition1(), ShotGunTexture);
			lol.setLife(5);
			objects.add(lol);
		} else if (randomInt <= 12){
			Item lol = new HealthPack(obj2.getPosition1(), healthTexture);
			lol.setLife(5);
			objects.add(lol);
		} else{
			Item lol = new Bomb(obj2.getPosition1(), BombTexture);
			lol.setLife(5);
			objects.add(lol);
		}
		
	}

	public void renderStep(GameDrawer drawer) {
		// For every object that you want to be rendered, you must call the draw
		// function with it as a parameter

		// NOTE: Always draw transparent objects last!

		// Offsetting the world so that all objects are drawn
		drawer.setWorldOffset(offset.x, offset.y);
		drawer.setColour(1.0f, 1.0f, 1.0f, 1.0f);

		// drawing the ground tiles
		for (int i = 0; i < gridTile.length; i++) {
			for (int j = 0; j < gridTile[i].length; j++) {
				drawer.draw(gridTile[i][j], -1);
			}
		}

		// drawing all the objects in the game
		for (GameObject o : objects) {
			drawer.draw(o, 1.0f, 1.0f, 1.0f, 1.0f, 0);
		}

		// this is just a random line drawn in the corner of the screen
		// drawer.draw(GameDrawer.LINES, linePositions, lineColours, 0.5f);

		if (player != null) {
			Point2D.Float[] playerLines = { mousePos, player.getPosition() };
			drawer.draw(GameDrawer.LINES, playerLines, lineColours, 0.5f);
			// drawer.drawRect(10, 10, 100, 100);
		}

		drawer.setColour(1.0f, 1.0f, 1.0f, 1.0f);

		// Changing the offset to 0 so that drawn objects won't move with the
		// camera
		drawer.setWorldOffset(0, 0);

		// this is just a random line drawn in the corner of the screen (but not
		// offsetted this time ;) )
		// drawer.draw(GameDrawer.LINES, linePositions, lineColours, 0.5f);

		// Some debug type info to demonstrate the font drawing
		if (player != null) {
			drawer.draw(arial, "Exp : " + player.getExp(), new Point2D.Float(
					10, 75), 1.0f, 0.5f, 0.0f, 0.0f, 0.1f);
		}
		// drawer.draw(arial, ""+mouseWheelTick, new Point2D.Float(20,68), 1.0f,
		// 0.5f, 0.0f, 0.7f, 0.1f);
		drawer.draw(arial, "Killz : " + score, new Point2D.Float(10, 45), 1.0f,
				0.5f, 0.0f, 1.0f, 0.1f);
		// drawer.draw(serif, ""+mousePos.x +":"+mousePos.y, new
		// Point2D.Float(20,20), 1.0f, 0.5f, 0.0f, 0.7f, 0.1f);
		drawer.draw(serif, "Life : " + life, new Point2D.Float(10, 20), 1.0f,
				0.0f, 0.0f, 1.0f, 0.0f);

		/*
		 * GameObject o = null;
		 * if(player.getCurrentWeapon().equals("Rocket Launcher")) { o = new
		 * RocketLauncher(new Point2D.Float(500,45),RocketLauncherTexture);
		 * objects.add(o); } else
		 * if(player.getCurrentWeapon().equals("Machine Gun")) { o = new
		 * RocketLauncher(new Point2D.Float(500,45),RocketLauncherTexture);
		 * objects.add(o); } else
		 * if(player.getCurrentWeapon().equals("Flame Thrower")) { o = new
		 * RocketLauncher(new Point2D.Float(500,45),RocketLauncherTexture);
		 * objects.add(o); } else
		 * if(player.getCurrentWeapon().equals("Shot Gun")) { o = new
		 * RocketLauncher(new Point2D.Float(500,45),RocketLauncherTexture);
		 * objects.add(o); }
		 */

		drawer.draw(serif, "Current Weapon : " + player.getCurrentWeapon(),
				new Point2D.Float(500, 20), 1.0f, 0.0f, 0.0f, 1.0f, 0.0f);
	}
}
