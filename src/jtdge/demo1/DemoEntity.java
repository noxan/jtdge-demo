package jtdge.demo1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import com.github.noxan.jtdge.entity.AbstractMovableEntity;
import com.github.noxan.jtdge.entity.graphics.DrawTexture;


public class DemoEntity extends AbstractMovableEntity<EngineDemo> {
	public static final Random rnd = new Random();
	
	public DemoEntity(int x, int y, float dx, float dy, int rad) {
		super(new DrawTexture(rad, rad) {
			public Color color = new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
			@Override
			public void draw(Graphics2D g2) {
				g2.setColor(color);
				g2.fillOval(0, 0, (int)getWidth(), (int)getHeight());
			}
		}, x, y, dx, dy);
	}
	
	@Override
	public void doLogic(long delta) {
		super.doLogic(delta);
		if(getX()+getWidth()>getStage().getWidth()&&getSpeedX()>0) {
			setX(getStage().getWidth()-getWidth());
			setSpeed(-getSpeedX(), getSpeedY());
		} else if(getX()<0&&getSpeedX()<0) {
			setX(0);
			setSpeed(-getSpeedX(), getSpeedY());
		}
		if(getY()+getHeight()>getStage().getHeight()&&getSpeedY()>0) {
			setY(getStage().getHeight()-getHeight());
			setSpeed(getSpeedX(), -getSpeedY());
		} else if(getY()<0&&getSpeedY()<0) {
			setY(0);
			setSpeed(getSpeedX(), -getSpeedY());
		}
	}
}
