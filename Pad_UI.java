import java.awt.Color;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Pad_UI extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Pad_UI pad = new Pad_UI();
				pad.setTitle("Pad Sensor");
				pad.setSize(300,200);
				pad.setVisible(true);
				pad.setResizable(false);
			}
		});
	}
	
	public Pad_UI(){
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
				JButton padButton = new JButton("PAD");
				padButton.setForeground(Color.BLUE);;
				buttonPane.add(padButton);
			}
			contentPane.add(buttonPane);
		}
	}
	
}
