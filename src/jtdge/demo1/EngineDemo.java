package jtdge.demo1;

import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.github.noxan.jtdge.core.Engine;
import com.github.noxan.jtdge.core.factory.EngineFactory;
import com.github.noxan.jtdge.display.DefaultEngineDisplay;
import com.github.noxan.jtdge.display.EngineDisplay;
import com.github.noxan.jtdge.display.event.EngineWindowAdapter;
import com.github.noxan.jtdge.entity.Entity;
import com.github.noxan.jtdge.ewt.comp.EButton;
import com.github.noxan.jtdge.ewt.comp.ELabel;
import com.github.noxan.jtdge.ewt.event.ActionEvent;
import com.github.noxan.jtdge.ewt.event.ActionListener;
import com.github.noxan.jtdge.input.DefaultEngineInput;
import com.github.noxan.jtdge.input.EngineInput;
import com.github.noxan.jtdge.input.event.EngineKeyAdapter;
import com.github.noxan.jtdge.input.event.EngineKeyEvent;
import com.github.noxan.jtdge.pref.Version;
import com.github.noxan.jtdge.thread.DefaultEngineThread;
import com.github.noxan.jtdge.thread.EngineThread;


public class EngineDemo extends Engine {
	public static void main(String[] args) {
		EngineDemo demo = new EngineDemo();
		demo.start();
		demo.demo();
	}
	
	private List<Entity<EngineDemo>> entities;
	private ELabel countLabel;
	private ELabel fpsLabel;
	private EButton addButton;
	private EButton removeButton;
	
	public EngineDemo() {
		super(new EngineFactory() {
			@Override
			public EngineThread createEngineThread() {
				return new DefaultEngineThread((long)(1e9f/60));
			}
			@Override
			public EngineInput createEngineInput() {
				return new DefaultEngineInput(true);
			}
			@Override
			public EngineDisplay createEngineDisplay() {
				return new DefaultEngineDisplay("jtdge-ball@"+Version.getVersion(), 100, 50, 800, 600);
			}
		});
		addEngineWindowListener(new EngineWindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		entities = new LinkedList<Entity<EngineDemo>>();
		
		countLabel = new ELabel("Loading...", 10, 10);
		getStage().addComponent(countLabel);
		fpsLabel = new ELabel("0.0fps", 120, 10);
		getStage().addComponent(fpsLabel);
		addButton = new EButton("Add", 230, 10);
		addButton.setEnabled(false);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addDemoEntity();
				removeButton.setEnabled(true);
			}
		});
		getStage().addComponent(addButton);
		removeButton = new EButton("Remove", 340, 10);
		removeButton.setEnabled(false);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				removeDemoEntity();
			}
		});
		getStage().addComponent(removeButton);
		getStage().addEngineKeyListener(new EngineKeyAdapter() {
			@Override
			public void keyPressed(EngineKeyEvent event) {
				if(event.getKey()==KeyEvent.VK_ESCAPE) {
					stop();
				} else if(event.getKey()==KeyEvent.VK_ADD) {
					addDemoEntity();
				}
			}
		});
	}
	
	public void demo() {
		while(entities.size()<5000 && getEngineThread().isRunning()) {
			addDemoEntity();
			try {
				Thread.sleep(100);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		addButton.setEnabled(true);
		removeButton.setEnabled(true);
	}
	private void removeDemoEntity() {
		if(entities.size()>0) {
			getStage().removeEntity(entities.get(0));
			entities.remove(0);
			updateCountLabel();
		} else {
			removeButton.setEnabled(false);
		}
	}
	private void addDemoEntity() {
		Random random = new Random();
		DemoEntity entity = new DemoEntity(random.nextInt(800), random.nextInt(600), random.nextInt(40)+10, random.nextInt(40)+10, random.nextInt(5)+20);
		entities.add(entity);
		getStage().addEntity(entity);
		updateCountLabel();
	}
	
	@Override
	public void update(long delta) {
		super.update(delta);
		
		fpsLabel.setText(""+getEngineThread().getFps()+"fps");
	}
	
	private void updateCountLabel() {
		countLabel.setText("Circles: "+entities.size());
	}
}