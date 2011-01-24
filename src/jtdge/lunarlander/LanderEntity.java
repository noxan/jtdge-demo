package jtdge.lunarlander;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.github.noxan.jtdge.entity.AbstractMovableEntity;
import com.github.noxan.jtdge.entity.graphics.DrawTexture;
import com.github.noxan.jtdge.input.event.EngineKeyAdapter;
import com.github.noxan.jtdge.input.event.EngineKeyEvent;

public class LanderEntity extends AbstractMovableEntity<LunarEnine> {
	public LanderEntity(int x) {
		super(new DrawTexture(10, 10) {
			@Override
			public void draw(Graphics2D g2) {
				g2.fillOval(0, 0, (int)getWidth(), (int)getHeight());
			}
		}, x, 10);
		setSpeed(0, 0);
	}
	
	private int fuel = 500;
	private boolean key_up = false;
	private boolean key_left = false;
	private boolean key_right = false;
	
	public void init() {
		getStage().addEngineKeyListener(new EngineKeyAdapter() {
			@Override
			public void keyPressed(EngineKeyEvent event) {
				switch(event.getKey()) {
				case KeyEvent.VK_UP:
					key_up = true;
					break;
				case KeyEvent.VK_RIGHT:
					key_right = true;
					break;
				case KeyEvent.VK_LEFT:
					key_left = true;
					break;
				}
			}
			@Override
			public void keyReleased(EngineKeyEvent event) {
				switch(event.getKey()) {
				case KeyEvent.VK_UP:
					key_up = false;
					break;
				case KeyEvent.VK_RIGHT:
					key_right = false;
					break;
				case KeyEvent.VK_LEFT:
					key_left = false;
					break;
				}
			}
		});
	}
	
	private void speedUp() {
		if(fuel>0) {
			setSpeedY(getSpeedY()-1);
			fuel--;
		}
	}
	private void speedRight() {
		if(fuel>0) {
			setSpeedX(getSpeedX()+1);
			fuel--;
		}
	}
	private void speedLeft() {
		if(fuel>0) {
			setSpeedX(getSpeedX()-1);
			fuel--;
		}
	}
	
	public int getFuel() {
		return fuel;
	}
	
	public void reset() {
		fuel = 500;
		key_up = false;
		key_left = false;
		key_right = false;
		setSpeed(0.0f, 0.0f);
		setX(400);
		setY(10);
	}
	
	@Override
	public void doLogic(long delta) {
		super.doLogic(delta);
		
		float r = getEngine().getEngineDisplay().getHeight()+200-getY();
		float g = (float)(100.0f/r);
		setSpeedY(getSpeedY()+g);
		
		if(key_right) {
			speedRight();
		}
		if(key_left) {
			speedLeft();
		}
		if(key_up) {
			speedUp();
		}
		
		if(getEngine().getMoon().isLP((int)(getX()+getWidth()/2), (int)(getY()+getHeight()))) {
			if(getSpeedY()>10.0f) {
				getEngine().finish(LunarEnine.STATUS_CRASH);
				setSpeed(0.0f, 0.0f);
			} else {
				getEngine().finish(LunarEnine.STATUS_SUCCESS);
				setSpeed(0.0f, 0.0f);
			}
		} else if(getEngine().getMoon().collide((int)(getX()+getWidth()/2), (int)(getY()+getHeight()))) {
			getEngine().finish(LunarEnine.STATUS_CRASH);
			setSpeed(0.0f, 0.0f);
		}
		
		if(getX()+getWidth()>getStage().getWidth()) {
			setX(getStage().getWidth()-getWidth());
			getEngine().finish(LunarEnine.STATUS_SPACE);
			setSpeed(0.0f, 0.0f);
		} else if(getX()<0) {
			setX(0);
			setSpeed(0.0f, 0.0f);
			getEngine().finish(LunarEnine.STATUS_SPACE);
		}
		if(getY()+getHeight()>getStage().getHeight()) {
			setY(getStage().getHeight()-getHeight());
			setSpeed(0.0f, 0.0f);
			getEngine().finish(LunarEnine.STATUS_CRASH);
		} else if(getY()<0) {
			setY(0);
			setSpeed(0.0f, 0.0f);
			getEngine().finish(LunarEnine.STATUS_SPACE);
		}
	}
}
