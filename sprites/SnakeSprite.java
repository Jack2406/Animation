import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SnakeSprite implements DisplayableSprite, MovableSprite, CollidingSprite {

	private static Image[] images;	
	private double centerX;
	private double centerY;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;
	private enum Direction {LEFT, UP, RIGHT, DOWN};
	private Direction direction = Direction.RIGHT;
	private static Image down;
	private static Image up;
	private static Image right;
	private static Image left;
	private static final int IMAGES_IN_CYCLE = 2;
	
	private final ArrayList<double[]> trail = new ArrayList<>();
	 
	private ArrayList<BodySprite> bodySegments = new ArrayList<>();

	
	private double velocityX = 0;
	private double velocityY = 0;

	private long elapsedTime = 0;
	private double speed = 150;
	private static final double MAX_SPEED = 300;
	private static final int PERIOD_LENGTH = 200;
	
	private long score = 0;
	

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
		
		if (images == null) {
			try {
				images = new Image[4];
				for (int i = 0; i < 4; i++) {
					String path = String.format("res/snake/snake-%d.png", i);
					images[i] = ImageIO.read(new File(path));
				}
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}
		
		right = images[0];
		down = images[1];
		left = images[2];
		up = images[3];
		
		for (int i = 0; i < 100; i++) {
            trail.add(new double[] { centerX, centerY });
        }
    }
	
	 private int getTrailSpacing() {
	        return Math.max(6, (int)(3000 / speed));
	    }


	

	public Image getImage() {
		
		long period = elapsedTime / PERIOD_LENGTH;
		int image = (int) (period % IMAGES_IN_CYCLE);

		if (image == 0) {
			switch (direction) {
			case UP:
				return up;
			case DOWN:
				return down;
			case LEFT:
				return left;
			case RIGHT:
				return right;
			}

	    }
		return null;
	}
	
	public ArrayList<BodySprite> getBodySegments() {
        return bodySegments;
    }
	
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

        double deltaX = actual_delta_time * 0.001 * velocityX;
		double deltaY = actual_delta_time * 0.001 * velocityY;
		
		if (checkCollisionWithBarrier(universe.getSprites(), deltaX, deltaY) ||  checkCollisionWithBodySegments(deltaX, deltaY)) {
            dispose = true;
            return;
        }
		
		 trail.add(0, new double[] { centerX, centerY });

		    int spacing = getTrailSpacing();
	        int maxTrailNeeded = (bodySegments.size() + 3) * spacing + 40;
	        while (trail.size() > maxTrailNeeded) {
	            trail.remove(trail.size() - 1);
	        }
		
		centerX += deltaX;
        centerY += deltaY;
		
		AppleSprite apple = checkCollisionWithApple(universe.getSprites(), deltaX, deltaY);
	    if (apple != null) {
	        apple.setDispose(true);   
	        increaseSpeed(25); 
	        ShellAnimation.addScore(1);
	        growBody(universe);
	    }
		
	    updateBodyPositionsFromTrail();

	}
	

	
	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> headsprites, double deltaX, double deltaY) {

		//deltaX and deltaY represent the potential change in position
		boolean colliding = false;

		for (DisplayableSprite sprite : headsprites) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + deltaX, this.getMinY() + deltaY, 
						this.getMaxX()  + deltaX, this.getMaxY() + deltaY, 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}
	
	private AppleSprite checkCollisionWithApple(ArrayList<DisplayableSprite> sprites, double deltaX, double deltaY) {

	    for (DisplayableSprite sprite : sprites) {
	        if (sprite instanceof AppleSprite) {
	            if (CollisionDetection.overlaps(getMinX() + deltaX, getMinY() + deltaY,
	                    getMaxX() + deltaX, getMaxY() + deltaY,
	                    sprite.getMinX(), sprite.getMinY(),
	                    sprite.getMaxX(), sprite.getMaxY())) {

	                return (AppleSprite) sprite;
	            }
	        }
	    }
	    return null;
	}
	
	private boolean checkCollisionWithBodySegments(double deltaX, double deltaY) {

       
        if (bodySegments.size() < 8) {
            return false;
        }

                int startIndex = 6;
        if (startIndex >= bodySegments.size()) {
            return false;
        }

        for (int i = startIndex; i < bodySegments.size(); i++) {
            BodySprite body = bodySegments.get(i);

            if (CollisionDetection.overlaps(getMinX() + deltaX, getMinY() + deltaY,
            		getMaxX() + deltaX, getMaxY() + deltaY,
                    body.getMinX(), body.getMinY(),
                    body.getMaxX(), body.getMaxY())) {
                return true;
            }
        }

        return false;
    }
	
	private BodySprite.Direction directionFromDelta(double deltaX, double deltaY) {
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return (deltaX > 0) ? BodySprite.Direction.RIGHT : BodySprite.Direction.LEFT;
        }
        return (deltaY > 0) ? BodySprite.Direction.DOWN : BodySprite.Direction.UP;
    }

    private void growBody(Universe universe) {

        double spawnX = centerX;
        double spawnY = centerY;

        int spacing = getTrailSpacing();
        int tailIndex = (bodySegments.size() + 1) * spacing;

        if (tailIndex < trail.size()) {
            spawnX = trail.get(tailIndex)[0];
            spawnY = trail.get(tailIndex)[1];
        }

        BodySprite newSeg = new BodySprite(spawnX, spawnY, height, width);
        
        if (bodySegments.isEmpty()) {
            newSeg.setDirection(directionFromDelta(velocityX, velocityY));
        } else {
            newSeg.setDirection(bodySegments.get(bodySegments.size() - 1).getDirection());
        }
        
        bodySegments.add(newSeg);
        universe.getSprites().add(newSeg);
    }

    private void updateBodyPositionsFromTrail() {

        int spacing = getTrailSpacing();

        for (int i = 0; i < bodySegments.size(); i++) {
            int trailIndex = (i + 1) * spacing;

            if (trailIndex < trail.size()) {
                double[] p = trail.get(trailIndex);
                bodySegments.get(i).setCenterX(p[0]);
                bodySegments.get(i).setCenterY(p[1]);
            }
        }
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
		this.centerX = centerX;
		
	}


	@Override
	public void setCenterY(double centerY) {
		this.centerY = centerY;
		
	}


	@Override
	public double getVelocityX() {
		// TODO Auto-generated method stub
		return velocityX;
	}


	@Override
	public double getVelocityY() {
		// TODO Auto-generated method stub
		return velocityY;
	}


	@Override
	public void setVelocityX(double pixelsPerSecond) {
		velocityX = pixelsPerSecond;
		
	}


	@Override
	public void setVelocityY(double pixelsPerSecond) {
		velocityY = pixelsPerSecond;
		
	}


	@Override
	public long getScore() {
		// TODO Auto-generated method stub
		return score;
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
