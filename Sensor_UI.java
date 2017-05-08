import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Sensor_UI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Sensor_UI(int channelNo, Sensor type){
		JFrame frame = new JFrame();
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(1,1));
		frame.add(contentPane);
		
		//set location referenced with channel number to avoid overlap
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
	    int screenWidth = screenSize.width;
		setLocation(screenWidth/2+(300*((channelNo-1)%2)), 50*(channelNo+channelNo%2));
		this.setContentPane(contentPane);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(1,1));
		JButton padButton = new JButton(type.getSensorType()+" "+channelNo);
		padButton.setForeground(Color.BLUE);;
		padButton.addActionListener(new SensorTrigListener(channelNo));
		buttonPane.add(padButton);
		contentPane.add(buttonPane);
	}
	//sensor trigger
	private class SensorTrigListener implements ActionListener {
			String channel;
			SensorTrigListener(int i){
				channel = String.valueOf(i);
			}
			public void actionPerformed(ActionEvent e) {
				ChronoInterface.chronoTimer.trig(channel);
			}
	}
}
