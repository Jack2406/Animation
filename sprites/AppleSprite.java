
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AppleSprite implements DisplayableSprite  {
	
	private static Image image;
	private boolean visible = true;
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;
	
    public AppleSprite(double centerX, double centerY) {
		
		if (image == null && visible) {
			try {
				image = ImageIO.read(new File("res/apple.jpg"));
				System.out.println(this.getClass().toString());
			}
			catch (IOException e) {
				e.printStackTrace();
			}		
		}
		
		this.centerX = centerX;
		this.centerY = centerY;

    }
    
    
	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public boolean getVisible() {
		return this.visible;
	}

	@Override
	public double getMinX() {	
	    return centerX - (width / 2);
	}

	@Override
	public double getMaxX() {
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
		return this.dispose;
	}

	@Override
	public void setDispose(boolean dispose) {
		this.dispose = dispose;
		
	}

	@Override
	public void update(Universe universe, long actual_delta_time) {
		// TODO Auto-generated method stub
		
	}

}
