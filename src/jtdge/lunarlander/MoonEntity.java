package jtdge.lunarlander;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Random;

import com.github.noxan.jtdge.entity.AbstractEntity;
import com.github.noxan.jtdge.entity.graphics.DrawTexture;

public class MoonEntity extends AbstractEntity<LunarEnine> {
	private int tdetail;
	private int twidth;
	private Polygon polygon;
	private int terrain[];
	private Point lp;
	
	public MoonEntity(int y, int width, int height) {
		super(0, y, width, height);
		tdetail = (width/30);
		twidth = width/tdetail;
		initTerrain(width, height);
		initPolygon();
		initAppearance();
	}
	
	public void reset() {
		initTerrain((int)getWidth(), (int)getHeight());
		initPolygon();
		initAppearance();
	}
	
	public boolean collide(int x, int y) {
		return polygon.contains(x, y-getY());
	}
	
	public boolean isLP(int x, int y) {
		return x>=lp.x*twidth && x<=(lp.x+1)*twidth && y>=getY()+lp.y;
	}
	
	private void initTerrain(int width, int height) {
		Random rnd = new Random();
		terrain = new int[tdetail+2];
		lp = new Point(rnd.nextInt(tdetail), 0);
		
		terrain[0] = rnd.nextInt(height)+1;
		for(int index=1;index<tdetail+2;index++) {
			if(index-1==lp.x) {
				terrain[index] = terrain[index-1];
			} else {
				int value = rnd.nextInt(25)+1;
				if(terrain[index-1]+value>height) {
					terrain[index] = terrain[index-1]-value;
				} else if(terrain[index-1]-value<0) {
					terrain[index] = terrain[index-1]+value;
				} else if(rnd.nextBoolean()) {
					terrain[index] = terrain[index-1]+value;
				} else {
					terrain[index] = terrain[index-1]-value;
				}
			}
		}
		lp.y = terrain[lp.x];
	}
	private void initPolygon() {
		if(polygon==null) {
			polygon = new Polygon();
		}
		polygon.reset();
		polygon.addPoint(0, (int)getHeight());
		for(int index=0;index<terrain.length;index++) {
			polygon.addPoint(index*twidth, terrain[index]);
		}
		polygon.addPoint((int)getWidth(), (int)getHeight());
	}
	private void initAppearance() {
		DrawTexture dt = new DrawTexture(getWidth(), getHeight()) {
			@Override
			public void draw(Graphics2D g2) {
				g2.setColor(Color.GRAY);
				g2.fillPolygon(polygon);
				g2.setColor(Color.CYAN);
				g2.drawLine(lp.x*twidth, lp.y, (lp.x+1)*twidth, lp.y);
				g2.setColor(Color.RED);
				for(int index=0;index<terrain.length;index++) {
					g2.drawLine(index*twidth, terrain[index], index*twidth, terrain[index]);
				}
			}
		};
		setAppearance(dt);
	}
	
}
