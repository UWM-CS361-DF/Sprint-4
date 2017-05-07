
public class Channel{
	private Sensor sensorType;
	private int channelNo;
	private boolean isArmed;

	public Channel(int channelNo){
		this.channelNo=channelNo;
		isArmed=false;
	}
	//triggers channel if channel is enabled
	public boolean trig(){
		if(!isArmed)
			return false;
		else{
			if(channelNo%2==0){
				ChronoInterface.chronoTimer.runs.get(ChronoInterface.chronoTimer.runNum).finish(channelNo);
			}	
			else{
				ChronoInterface.chronoTimer.runs.get(ChronoInterface.chronoTimer.runNum).start(channelNo);
			}
			return true;
		}
	}
	//toggles enable/disable state
	public boolean tog(){
		isArmed=!isArmed;
		return isArmed;
	}
	
	//creates the sensor being "connected" to the system
	public Sensor conn(String sensor){
		sensorType=new Sensor(sensor, channelNo);
		return sensorType;
	}
	//disconnects the sensor replacing with null
	public Sensor disc(){
		sensorType.disc();
		sensorType=null;
		return sensorType;
	}
}