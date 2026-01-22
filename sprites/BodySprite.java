import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class BodySprite implements DisplayableSprite {
	
	private static Image image;	
	private double centerX;
	private double centerY;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;
	
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
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/Snake body.png"));
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return image;
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
		// TODO Auto-generated method stub
		
	}
	
	public void setPosition(double x, double y) {
	    this.centerX = x;
	    this.centerY = y;
	}

	@Override
	public void update(Universe universe, long actual_delta_time) {
		// TODO Auto-generated method stub
		
	}


	public void setCenterX(double prevX) {
		// TODO Auto-generated method stub
		
	}


	public void setCenterY(double prevY) {
		// TODO Auto-generated method stub
		
	}

}
