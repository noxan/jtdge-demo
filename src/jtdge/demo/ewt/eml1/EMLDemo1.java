package jtdge.demo.ewt.eml1;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.github.noxan.jtdge.core.Engine;
import com.github.noxan.jtdge.display.event.EngineWindowAdapter;
import com.github.noxan.jtdge.ewt.border.LineBorder;
import com.github.noxan.jtdge.ewt.comp.EComponent;
import com.github.noxan.jtdge.ewt.comp.EPanel;
import com.github.noxan.jtdge.ewt.xml.EWTMLParser;

public class EMLDemo1 extends Engine {
	public static void main(String[] args) {
		new EMLDemo1().start();
	}
	
	public EMLDemo1() {
		super("EMLDemo1", 640, 480, 30);
		addEngineWindowListener(new EngineWindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				stop();
			}
		});
		
		try {
			EPanel root = new EPanel(0, 0);
			root.setBorder(new LineBorder(Color.RED));
			EComponent component = EWTMLParser.load(root, "data/sample1.eml");
			getStage().addComponent(component);
		} catch(ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch(SAXException ex) {
			ex.printStackTrace();
		} catch(IOException ex) {
			ex.printStackTrace();
		}
	}
}
