package secondgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;

public class StartClass extends Applet implements Runnable, KeyListener {

	// Private objects
	private Robot robot;
	private Heliboy hb1, hb2;
	private static Background bg1, bg2;
	private Image image, character, background, charDuck, charJump, currentSprite, heliboy;
	private URL base;
	private Graphics second;

	@Override
	public void init() {
		setSize(800, 480); // Sets screen size 800 x 480 pixels
		setBackground(Color.BLACK); // Imports Color since BLACK is constant and sets background color
		setFocusable(true); // Statement that makes sure when game starts, Applet take focus and our input
							// goes directly to it
		Frame frame = (Frame) this.getParent().getParent(); // Imports Frame and assigns Applet window to frame variable
		frame.setTitle("Q-Bot Alpha"); // Sets frame title
		addKeyListener(this); // More in the future but think of it as "this" class. More specifically, "this"
								// instance of the class.

		try {
			base = getDocumentBase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		character = getImage(base, "data/character.png");
		background = getImage(base, "data/background.png");
		charJump = getImage(base, "data/characterJumped.png");
		charDuck = getImage(base, "data/characterDown.png");
		currentSprite = character;
		heliboy = getImage(base, "data/heliboy.png");
	}

	@Override
	public void start() {
		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);
		hb1 = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);

		robot = new Robot(); /*
								 * We have called instance of Robot and Background class and now we can call
								 * methods from Robot class by robot.MethodName();
								 */

		Thread thread = new Thread(this); // More to come but think of it as "this" class. More specifically, "this"
											// instance of the class.
		thread.start();
		
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			robot.update();
			if (robot.isJumped()) {
				currentSprite = charJump;
			} else if (!robot.isJumped() && !robot.isDucked() ) {
				currentSprite = character;
			}
			
			ArrayList projectiles = robot.getProjectiles();
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = (Projectile) projectiles.get(i);
				if (p.isVisible() == true) {
					p.update();
				} else {
					projectiles.remove(i);
				}
			}
			
			hb1.update();
			hb2.update();
			bg1.update();
			bg2.update();
			repaint(); // Calls in paint method every 17 milliseconds

			// Try/Catch method is built-in fail-safe system
			try {
				Thread.sleep(17); // Thread will sleep for 17 milliseconds (60 updates per second (FPS))
			} catch (InterruptedException ie) {
				ie.printStackTrace();
				break;
			}

		}
	}

	@Override
	public void update(Graphics g) { // Update method is for double buffering - a technique that is used to prevent
										// tearing and flickering.
		if (image == null) {
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}
		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);

	}

	@Override
	public void paint(Graphics g) { // Paint method is used for drawing our graphics to the screen
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
		
		ArrayList projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = (Projectile) projectiles.get(i);
			g.setColor(Color.YELLOW);
			g.fillRect(p.getX(), p.getY(), 10, 5);
		}
		
		g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
		g.drawImage(heliboy, hb1.getCenterX() - 48, hb1.getCenterY() - 48, this);
		g.drawImage(heliboy, hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = charDuck;
			if (!robot.isJumped()) {
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			System.out.println("Move down");
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			System.out.println("Move right");
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			System.out.println("Move left");
			break;

		case KeyEvent.VK_SPACE:
			robot.jump();
			System.out.println("Jump");
			break;
		
		case KeyEvent.VK_CONTROL:
			if (!robot.isDucked() && !robot.isJumped()) {
				robot.shoot();
			}
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = character;
			robot.setDucked(false);
			System.out.println("Stop moving down");
			break;

		case KeyEvent.VK_RIGHT:
			robot.stopRight();
			System.out.println("Stop moving right");
			break;

		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			System.out.println("Stop moving left");
			break;

		case KeyEvent.VK_SPACE:
			System.out.println("Stop jumping");
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

}
