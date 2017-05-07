import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Pad_UI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public Pad_UI(int channelNo){
		JFrame frame = new JFrame();

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new GridLayout(3,3));
		frame.add(contentPane);
		setLocationRelativeTo(null);
		this.setContentPane(contentPane);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		for(int i = 1; i < 10; i++){
			JPanel buttonPane = new JPanel();
			if(i != 5){
				JLabel emp = new JLabel(" ");
				buttonPane.add(emp);
			}
			else{
				buttonPane.setLayout(new GridLayout(1,1));
				JButton padButton = new JButton(channelNo+" PAD");
				padButton.setForeground(Color.BLUE);;
				padButton.addActionListener(new SensorTrigListener(channelNo));
				buttonPane.add(padButton);
			}
			contentPane.add(buttonPane);
		}
	}
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
