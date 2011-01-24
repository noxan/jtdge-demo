package jtdge.demo.ewt.eml1;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.github.noxan.jtdge.core.Engine;
import com.github.noxan.jtdge.core.factory.EngineFactory;
import com.github.noxan.jtdge.display.DefaultEngineDisplay;
import com.github.noxan.jtdge.display.EngineDisplay;
import com.github.noxan.jtdge.display.event.EngineWindowAdapter;
import com.github.noxan.jtdge.ewt.border.LineBorder;
import com.github.noxan.jtdge.ewt.comp.EButton;
import com.github.noxan.jtdge.ewt.comp.EComponent;
import com.github.noxan.jtdge.ewt.comp.EPanel;
import com.github.noxan.jtdge.ewt.event.ActionEvent;
import com.github.noxan.jtdge.ewt.event.ActionListener;
import com.github.noxan.jtdge.ewt.xml.EWTMLParser;
import com.github.noxan.jtdge.input.DefaultEngineInput;
import com.github.noxan.jtdge.input.EngineInput;
import com.github.noxan.jtdge.pref.Version;
import com.github.noxan.jtdge.thread.DefaultEngineThread;
import com.github.noxan.jtdge.thread.EngineThread;

public class EMLDemo1 extends Engine {
	public static void main(String[] args) {
		new EMLDemo1().start();
	}
	
	private int buttonCount = 0;
	
	public EMLDemo1() {
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
				return new DefaultEngineDisplay("jtdge-eml@"+Version.getVersion(), 100, 50, 800, 600);
			}
		});
		addEngineWindowListener(new EngineWindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				stop();
			}
		});
		
		EPanel panel = new EPanel(200, 200, 400, 200);
		panel.setBorder(new LineBorder(Color.RED));
		panel.setBackground(new Color(50, 50, 75));
		panel.add(new EDemoButton(10, 10));
		panel.add(new EDemoButton(10, 40));
		panel.add(new EDemoButton(100, 70));
		panel.add(new EDemoButton(100, 100));
		EButton exitButton = new EButton("Exit", 295, 170);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}
		});
		panel.add(exitButton);
		
		try {
			@SuppressWarnings("unused")
			EComponent component = EWTMLParser.load("data/sample1.eml");
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch(SAXException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		getStage().addComponent(panel);
		
		
	}
	
	private class EDemoButton extends EButton {
		public EDemoButton(int x, int y) {
			super("Demo"+buttonCount++, x, y);
			addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("button["+getText()+"] pressed.");
				}
			});
		}
	}
}
