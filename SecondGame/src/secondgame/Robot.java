package secondgame;

public class Robot {
	/*
	 * In Java, Class variables should be private so that only it's methods can
	 * change them.
	 */

	/*
	 * centerX, centerY are the x, y coordinates of our robot character's center.
	 * Origin (0,0) is at TOP LEFT. This means that if character has positive
	 * speedY, he is FALLING, not RISING
	 */
	final int JUMPSEED = -15;
	final int MOVESPEED = 5;
	final int GROUND = 382;
	
	private int centerX = 100;
	private int centerY = GROUND;

	/*
	 * jumped changes to true if the character is in the air, and reverts to false
	 * when grounded.
	 */
	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;
	
	private static Background bg1 = StartClass.getBg1();
	private static Background bg2 = StartClass.getBg2();

	// speedX, speed Y are the rate at which these x and y positions change.
	private int speedX = 0;
	private int speedY = 1;

	public void update() { // update() / this method will be called on each iteration of the for loop

		// Moves character or scrolls background accordingly
		if (speedX < 0) {
			centerX += speedX; // This changes centerX by adding speedX.
		} else if (speedX == 0) {
			System.out.println("Do not scroll background");
		} else {
			if (centerX <= 150) { // If the character's centerX is in the left 150 pixels
				centerX += speedX; // Change centerX by adding speedX.
			} else {
				System.out.println("Scroll background here");
			}
		}

		// Updates Y position
		if (centerY + speedY >= 382) {
			// 382 is where the character's centerY would be if he were standing on the
			// ground.
			centerY = 382;
		} else {
			centerY += speedY; // Add speedY to centerY to determine its new position
		}

		// Handles jumping
		if (jumped == true) {
			speedY += 1; // While the character is in the air, add 1 to his speedY.
			// NOTE: This will bring the character downwards!

			if (centerY + speedY >= 382) {
				centerY = 382;
				speedY = 0;
				jumped = false;
			}
		}

		// Prevents going beyond X coordinate 0
		if (centerX + speedX <= 60) { // If speedX plus centerX would bring the character outside the screen,
			centerX = 61;
			// Fix the character's centerX at 60 pixels.
		}
	}

	public void moveRight() {
		speedX = 6; // Sets the character's horizontal speed (speedX) as 6.
	}

	public void moveLeft() {
		speedX = -6; // Sets character's horizontal speed (speedX) as -6.
	}

	public void stop() {
		speedX = 0; // Sets character's horizontal speed (speedX) as 0.
	}

	public void jump() {
		if (jumped == false) {
			speedY = -15; // Sets the vertical speed (speedY) as -15.
			jumped = true;
		}
	}

	/* Getters and Setters, common practice is to set class-wide variables as
	   PRIVATE. For other classes to access private variables, they must use helper
	   functions known as getters and setters. */
	   
	public int getCenterX() {
		return centerX;
	}

	public int getCenterY() {
		return centerY;
	}

	public boolean isJumped() {
		return jumped;
	}

	public int getSpeedX() {
		return speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped) {
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}
}
