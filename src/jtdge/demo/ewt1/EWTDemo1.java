package jtdge.demo.ewt1;

import java.awt.Color;
import java.awt.event.WindowEvent;

import com.github.noxan.jtdge.core.Engine;
import com.github.noxan.jtdge.display.event.EngineWindowAdapter;
import com.github.noxan.jtdge.ewt.comp.EButton;
import com.github.noxan.jtdge.ewt.comp.ELabel;
import com.github.noxan.jtdge.ewt.comp.ETextField;
import com.github.noxan.jtdge.ewt.event.ActionEvent;
import com.github.noxan.jtdge.ewt.event.ActionListener;
import com.github.noxan.jtdge.stage.background.ColorStageBackground;

public class EWTDemo1 extends Engine {
	public static void main(String[] args) {
		new EWTDemo1().start();
	}
	
	public EWTDemo1() {
		super("EWTDemo1", 50, 50, 800, 600, 60.f);
		//init engine
		addEngineWindowListener(new EngineWindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		//init stage
		getStage().setBackground(new ColorStageBackground(Color.RED));
		
		getStage().addComponent(new ELabel("Label 1", 10, 10));
		for(int index=0;index<5;index++) {
			EButton button = new EButton("Button"+(index+1), 10, 50+(index*30));
			button.setAction("$b_index"+(index+1));
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(e.getTime()+": "+e.getAction());
				}
			});
			getStage().addComponent(button);
		}
		EButton disabledButton = new EButton("Disabled", 10, 210);
		disabledButton.setEnabled(false);
		getStage().addComponent(disabledButton);
		EButton exitButton = new EButton("Exit", 10, 250);
		exitButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		getStage().addComponent(exitButton);
		/*EButton testButton = new EButton("Test with a very long text ...", 10, 300);
		getStage().addComponent(testButton);
		EButton testButton2 = new EButton("Test with a two\n lines or even more? ...", 10, 330);
		getStage().addComponent(testButton2);*/
		
		
		getStage().addComponent(new ETextField("Hello World", 500, 500));
	}
}
