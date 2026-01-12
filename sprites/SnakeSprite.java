import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SnakeSprite implements DisplayableSprite, MovableSprite, CollidingSprite {

	private static Image image;	
	private double centerX;
	private double centerY;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;
	private enum Direction {LEFT, UP, RIGHT, DOWN};
	private Direction direction = Direction.RIGHT;
	
	
	private double velocityX = 0;
	private double velocityY = 0;

	private double speed = 150;
	private static final double MAX_SPEED = 250;

	public SnakeSprite(double centerX, double centerY, double height, double width) {
		this(centerX, centerY);
		
		this.height = height;
		this.width = width;
	}

	
	public SnakeSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		
		velocityX = speed;
		velocityY = 0;
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/simple-sprite.png"));
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	public Image getImage() {
		return image;
	}
	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return true;
	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getCenterX() {
		return centerX;
	};

	public double getCenterY() {
		return centerY;
	};
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void update(Universe universe, long actual_delta_time) {
		
		KeyboardInput keyboard = KeyboardInput.getKeyboard();

		if (keyboard.keyDown(37) && direction != Direction.RIGHT) 
            direction = Direction.LEFT;
		
		 else if (keyboard.keyDown(38) && direction != Direction.DOWN) {
            direction = Direction.UP;
		
		} else if (keyboard.keyDown(39) && direction != Direction.LEFT) {
            direction = Direction.RIGHT;
		
		} else if (keyboard.keyDown(40) && direction != Direction.UP) {
            direction = Direction.DOWN;
		
		}
		
		switch (direction) {
        case LEFT:
            velocityX = -speed;
            velocityY = 0;
            break;
        case RIGHT:
            velocityX = speed;
            velocityY = 0;
            break;
        case UP:
            velocityX = 0;
            velocityY = -speed;
            break;
        case DOWN:
            velocityX = 0;
            velocityY = speed;
            break;
    }

        centerX += velocityX * actual_delta_time * 0.001;
		
        centerY += velocityY * actual_delta_time * 0.001;

	}
	
	public void increaseSpeed(double amount) {
	    speed += amount;

	    if (speed > MAX_SPEED) {
	        speed = MAX_SPEED;
	    }
	}


	@Override
	public void setDispose(boolean dispose) {
		this.dispose = true;
	}


	@Override
	public void setCenterX(double centerX) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setCenterY(double centerY) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public double getVelocityX() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public double getVelocityY() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void setVelocityX(double pixelsPerSecond) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setVelocityY(double pixelsPerSecond) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public long getScore() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public String getProximityMessage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean getIsAtExit() {
		// TODO Auto-generated method stub
		return false;
	}

}
