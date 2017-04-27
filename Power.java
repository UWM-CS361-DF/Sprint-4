
public class Power {
	
	private boolean powerStatus=false;
	
	public boolean power(){
		for(int i=1;i<9;i++)
			ChronoInterface.chronoTimer.channels.get(i).isArmed=false;//disarm channels when poweroff
		//ChronoInterface.chronoTimer.race.event.startQueue.clear();//reset race
		//ChronoInterface.chronoTimer.race.event.finishQueue.clear();//reset race
		//ChronoInterface.chronoTimer.race.completed.clear();//reset race
		powerStatus=!powerStatus;//switch on when poweroff and vice versa
		return powerStatus;
	}
	public boolean powerStatus(){
		return powerStatus;
	}
}
