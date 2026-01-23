import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;



public class BodySprite implements DisplayableSprite {
	
	private static Image[] images;
	
	public enum Direction { LEFT, UP, RIGHT, DOWN }
	
    private static Image right;
    private static Image down;
    private static Image left;
    private static Image up;
	
    private Direction direction = Direction.RIGHT;
    private double centerX;
	private double centerY;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;
	private static final int PERIOD_LENGTH = 200;
	private static final int IMAGES_IN_CYCLE = 2;

	
	private ArrayList<DisplayableSprite> bodySprites = new ArrayList<DisplayableSprite>();
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	
	public BodySprite(double centerX, double centerY, double height, double width) {
		this(centerX, centerY);
		
		this.height = height;
		this.width = width;
	}

	
	public BodySprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		
		if (images == null) {
			try {
				images = new Image[4];
                for (int i = 0; i < 4; i++) {
                    String path = String.format("res/body/body-%d.png", i);
                    images[i] = ImageIO.read(new File(path));
                }
                
                right = images[0];
                down = images[1];
                left = images[2];
                up = images[3];

			
			}catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}
	
	public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

	@Override
	public Image getImage() {
		switch (direction) {
        case UP:
            return up;
        case DOWN:
            return down;
        case LEFT:
            return left;
        case RIGHT:
        default:
            return right;
    }
}


	@Override
	public boolean getVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public double getMinX() {
		// TODO Auto-generated method stub
		return centerX - (width / 2);
	}

	@Override
	public double getMaxX() {
		// TODO Auto-generated method stub
		return centerX + (width / 2);
	}

	@Override
	public double getMinY() {
		// TODO Auto-generated method stub
		return centerY - (height / 2);
	}

	@Override
	public double getMaxY() {
		// TODO Auto-generated method stub
		return centerY + (height / 2);
	}

	@Override
	public double getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public double getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public double getCenterX() {
		// TODO Auto-generated method stub
		return centerX;
	}

	@Override
	public double getCenterY() {
		// TODO Auto-generated method stub
		return centerY;
	}

	@Override
	public boolean getDispose() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
		
	}
	
	public void setPosition(double x, double y) {
	    this.centerX = x;
	    this.centerY = y;
	}

	@Override
	public void update(Universe universe, long actual_delta_time) {
		// TODO Auto-generated method stub
		
	}


	public void setCenterX(double centerX) {
		this.centerX = centerX;
		
	}


	public void setCenterY(double centerY) {
		this.centerY = centerY;
		
	}

}
