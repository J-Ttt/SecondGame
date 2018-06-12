package secondgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class StartClass extends Applet implements Runnable, KeyListener {

	// Private objects
	private Robot robot;
	private Image image, character, background;
	private URL base;
	private Graphics second;
	private static Background bg1, bg2;

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

	}

	@Override
	public void start() {

		bg1 = new Background(0, 0);
		bg2 = new Background(2160, 0);

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
		while (true) {
			robot.update();
			bg1.update();
			bg2.update();
			repaint(); // Calls in paint method every 17 milliseconds

			// Try/Catch method is built-in fail-safe system
			try {
				Thread.sleep(17); // Thread will sleep for 17 milliseconds (60 updates per second (FPS))
			} catch (InterruptedException e) {
				e.printStackTrace();
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
		g.drawImage(character, robot.getCenterX() - 61, robot.getCenterY() - 63, this);

	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {

		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			System.out.println("Move down");
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			System.out.println("Move right");
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			System.out.println("Move left");
			break;

		case KeyEvent.VK_SPACE:
			robot.jump();
			System.out.println("Jump");
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
			System.out.println("Stop moving down");
			break;

		case KeyEvent.VK_RIGHT:
			robot.stop();
			System.out.println("Stop moving right");
			break;

		case KeyEvent.VK_LEFT:
			robot.stop();
			System.out.println("Stop moving left");
			break;

		case KeyEvent.VK_SPACE:
			System.out.println("Stop jumping");
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

	public static Background getBg1() {
		return bg1;
	}

	public static Background getBg2() {
		return bg2;
	}

}
