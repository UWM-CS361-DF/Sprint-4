import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

//******************************************************
// The ChronoInterface class acts as a literal interface
// for the ChronoTimer. This has all of the methods that
// could be directly related to the interface buttons to 
// generate a command.(i.e the tog "button" is pressed
// it relates to tog() method. The chronoInterface then 
// determines what to do with the information provided
//******************************************************

public class ChronoInterface {
	public static ChronoInterface chronoTimer;// = new ChronoInterface();
	List<Channel> channels = new ArrayList<Channel>(9);//0 will be an empty channel location for ease of assigning
	Power power = new Power();
	boolean runInProgress=true;
	int runNum=1;
	ArrayList<Event> runs= new ArrayList<Event>();
	Gson g = new Gson();
	String json;
	GUI gui;
	boolean printerPower;

	public ChronoInterface(GUI gui){
		channels.add(0,null);
		for(int i=1;i<9;i++){
			channels.add(i,new Channel(i));
		}
		runs.add(0,null);
		runs.add(runNum, new IndEvent());
		Time.systemTime.setTime();
		this.gui=gui;
		printerPower=false;
	}
	public void power(){
		if(power.power())
			println("Power On");
		else{
			println("Power Off");
			chronoTimer = new ChronoInterface(gui);
		}		
	}
	public void time(String time){
		if(power.powerStatus()){
			Time.systemTime.setTime(Time.systemTime.toSeconds(time));
			println("Set Time to "+ Time.systemTime.toString(Time.systemTime.getRunningTime()));
		}
	}
	public void dnf(){
		if(power.powerStatus())
			runs.get(runNum).dnf();
	}
	public void cancel(){
		if(power.powerStatus())
			runs.get(runNum).cancel();
	}
	public void tog(String channel){
		if(power.powerStatus())
			println(channels.get(Integer.parseInt(channel)).tog() ? "Channel "+channel+" is Armed" : "Channel "+channel+" is not Armed");
	}
	public void trig(String channel){
		if(power.powerStatus())
			if(channels.get(Integer.parseInt(channel)).trig())
				println("Triggered Channel "+channel);
			else
				println("Unable to Trigger Channel "+channel);		
	}
	public void start(){
		if(power.powerStatus())
			trig("1");
	}
	public void finish(){
		if(power.powerStatus())
			trig("2");
	}
	public void conn(String sensor, String channel){
		//String sensorType="";
		if(power.powerStatus()){
			String sensorType=""+channels.get(Integer.parseInt(channel)).conn(sensor).getSensorType();
			if(!sensorType.equals(null))
				println("Connected "+sensorType+" Sensor to Channel "+channel);
			else
				println("Failed to Connect "+sensorType+" Sensor to Channel "+channel);
		}
	}
	public void disc(String channel){
		Sensor sensorType;
		if(power.powerStatus()){
			sensorType=channels.get(Integer.parseInt(channel)).disc();
			if(sensorType==null)
				println("Disconnected Sensor from Channel "+channel);
			else
				println("Failed to Disconnect Sensor from Channel "+channel);
		}
	}
	public void event(String type){
		if(power.powerStatus()){
			runs.remove(runNum);
			switch (type) {
			case "IND":
				runs.add(runNum, new IndEvent());
				println("Created "+type+" event");
				return;
			case "PARIND":
				runs.add(runNum, new ParIndEvent());
				println("Created "+type+" event");
				return;
			case "GRP":
				runs.add(runNum, new GrpEvent());
				println("Created "+type+" event");
				return;
			case "PARGRP":
				runs.add(runNum, new ParGrpEvent());
				println("Created "+type+" event");
				return;
		}
		println("Failed to create "+type+" event");
		}
	}
	public void newrun(){
		if(power.powerStatus())
			if(!runInProgress){
				runNum++;
				runs.add(runNum, new IndEvent());
				runInProgress=true;
				println("Created Run "+runNum);
			}
			else
				println("Run "+runNum+" Still In Progress");
	}
	public void endrun() throws Exception{
		if(power.powerStatus()){
			runs.get(runNum).end();
			new Export(runs.get(runNum),runNum);
			runInProgress=false;
			println("Ended Run "+(runNum));
		}
	}
	public void print(){
		if(power.powerStatus())
			new Print(runs);
	}
	public void printer(){
		printerPower=!printerPower;
		println(printerPower? "Printer Powering On": "Printer Powering Off");
	}
	public void export(String run) throws Exception{
		if(power.powerStatus()){
			new Export(runs.get(runNum),runNum);
			runInProgress=false;
		}
	}
	public void num(String number){
		if(power.powerStatus())
			println(runs.get(runNum).add(Integer.parseInt(number)) ? "Added "+number+" to Race queue": "Failed to add "+number+" to Race queue");
	}
	public void clr(String number){
		if(power.powerStatus())
			runs.get(runNum).clear(Integer.parseInt(number));
	}
	public void swap(){
		if(power.powerStatus())
			runs.get(runNum).swap();
	}
	public void println(String out){
		System.out.println(out);
		if(printerPower)
			gui.output(out+'\n');
	}
	public void print(String out){
		System.out.print(out);
		if(printerPower)
			gui.output(out);
	}
	public String displayRun(){
		return runs.get(runNum).displayUI();
	}
}