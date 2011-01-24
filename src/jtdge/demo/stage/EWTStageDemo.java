package jtdge.demo.stage;

import java.awt.event.WindowEvent;

import com.github.noxan.jtdge.core.Engine;
import com.github.noxan.jtdge.display.event.EngineWindowAdapter;
import com.github.noxan.jtdge.render.DefaultCamera;

public class EWTStageDemo extends Engine {
	public EWTStageDemo() {
		super("EWTStageDemo", 640, 480, 30.f);
		
		addEngineWindowListener(new EngineWindowAdapter() {
			@Override public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		
		
		getStage().addEntity(new StageDemoEntity(50, 50));
		
		
		getStage().setCamera(new DefaultCamera(0, 0, .75f, .75f));
		
		
		start();
	}
	
	public static void main(String[] args) {
		new EWTStageDemo();
	}
}
