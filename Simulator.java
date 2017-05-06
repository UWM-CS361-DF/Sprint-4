import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.Scanner;
/**************************************************
The purpose of the simulator class is to take the
input from the test cases and disseminate to the 
appropriate ChronoInterface method. 
**************************************************/
public class Simulator {
	/*parse method takes the input passed from the input and calls the appropriate method 
	of the chronoTimer. The chronoTimer has a method for each expected string input. 
	All other input is considered "Illegal Input""
	*/
	public static void parse(String lineInput) throws Exception{
		
		String[] input;
		
		input=lineInput.split(" ");
        
		if(input.length==1){
			Method method = ChronoInterface.chronoTimer.getClass().getMethod(input[0].toLowerCase());
			method.invoke(ChronoInterface.chronoTimer);
		}
		else if(input.length==2){
			Method method = ChronoInterface.chronoTimer.getClass().getMethod(input[0].toLowerCase(), String.class);
			method.invoke(ChronoInterface.chronoTimer, input[1]);
		}
		else if(input.length==3){
			Method method = ChronoInterface.chronoTimer.getClass().getMethod(input[0].toLowerCase(), String.class, String.class);
			method.invoke(ChronoInterface.chronoTimer, input[1], input[2]);
		}
	}
	
	/*main method decides whether there is a file input or console input, 
	gathers the next line input and sends it to the parse method. */
	public static void main(String[] args) throws Exception{
		Scanner scIn;
		String stringInput;
		String stringTime;
		
		GUI gui = new GUI();
		gui.setTitle("Top View");
		gui.setSize(800,600);
		gui.setVisible(true);
		ChronoInterface.chronoTimer=new ChronoInterface(gui);
		try{
			if (args.length > 0 ){
				scIn = new Scanner(new File(args[0]));
			}
			else{
				scIn = new Scanner(System.in);
			}
			loop:while(scIn.hasNextLine()){
				try{
					if(args.length>0){
						stringTime=scIn.next();
						Time.systemTime.setTime(Time.systemTime.toSeconds(stringTime));
						stringInput=scIn.nextLine().trim();
						Thread.sleep(1000); //wait 1 second before next line is executed
					}
					else{
						stringInput=scIn.nextLine();
						//Time.systemTime.setTime();
					}
					if(stringInput.equals("EXIT")){
						System.out.println("Exited Simulator");
						scIn.close();
						System.exit(0);
					}
					else if(stringInput.equals("RESET")){
						ChronoInterface.chronoTimer=new ChronoInterface(gui);
						ChronoInterface.chronoTimer.power();
					}
					else if(stringInput.equals("POWER")&&!ChronoInterface.chronoTimer.powerStatus){
						ChronoInterface.chronoTimer=new ChronoInterface(gui);
						ChronoInterface.chronoTimer.power();
				}
					else 
						parse(stringInput);
				}catch(Exception e){
					System.out.println("Illegal Input");
					continue loop;
				}
			}
		}catch(FileNotFoundException e){
			System.out.println("Invalid Input File");
		}
	}
}