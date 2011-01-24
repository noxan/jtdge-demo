package jtdge.lunarlander;

import java.awt.Color;
import java.awt.event.WindowEvent;

import com.github.noxan.jtdge.core.Engine;
import com.github.noxan.jtdge.display.event.EngineWindowAdapter;
import com.github.noxan.jtdge.ewt.comp.EButton;
import com.github.noxan.jtdge.ewt.comp.ELabel;
import com.github.noxan.jtdge.ewt.event.ActionEvent;
import com.github.noxan.jtdge.ewt.event.ActionListener;

public class LunarEnine extends Engine {
	public static void main(String[] args) {
		new LunarEnine();
	}
	
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_CRASH = 2;
	public static final int STATUS_SPACE = 3;
	
	private LanderEntity lander;
	private MoonEntity moon;
	
	private ELabel fuelLabel;
	private EButton resetButton;
	private ELabel statusLabel;
	
	public LunarEnine() {
		super("Lunar Lander", 800, 600, 60.f);
		
		lander = new LanderEntity(400);
		getStage().addEntity(lander);
		lander.init();
		
		moon = new MoonEntity(520, 800, 80);
		getStage().addEntity(moon);
		
		addEngineWindowListener(new EngineWindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		
		fuelLabel = new ELabel("Fuel: ", 5, 5, 75, 20);
		getStage().addComponent(fuelLabel);
		
		statusLabel = new ELabel("Status", (getEngineDisplay().getWidth()-400)/2, getEngineDisplay().getHeight()/2, 400, 10);
		statusLabel.setBackground(Color.BLACK);
		statusLabel.setForeground(Color.WHITE);
		statusLabel.setVisible(false);
		getStage().addComponent(statusLabel);
		resetButton = new EButton("Reset Game", (getEngineDisplay().getWidth()-100)/2, (getEngineDisplay().getHeight()/2)+15);
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		resetButton.setVisible(false);
		getStage().addComponent(resetButton);
		
		getEngineDisplay().requestFocus();
		
		start();
	}
	
	public void finish(int status) {
		switch(status) {
		case STATUS_SUCCESS:
			statusLabel.setText("successful!");
			break;
		case STATUS_CRASH:
			statusLabel.setText("crash!");
			break;
		case STATUS_SPACE:
			statusLabel.setText("welcome to space!");
			break;
		}
		statusLabel.setVisible(true);
		resetButton.setVisible(true);
	}
	
	public void reset() {
		statusLabel.setVisible(false);
		statusLabel.setText("-");
		resetButton.setVisible(false);
		moon.reset();
		lander.reset();
	}
	
	protected MoonEntity getMoon() {
		return moon;
	}
	
	@Override
	public void update(long delta) {
		super.update(delta);
		
		fuelLabel.setText("Fuel: "+lander.getFuel());
		
	}
}
