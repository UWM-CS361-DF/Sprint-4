import java.text.SimpleDateFormat;
import java.util.Calendar;
/* This class is mainly about to set system time and get the race 
 * time via subtracting. Also it transfer the format to fit to the 
 * given units of time, which is "hundredth of seconds".
 */
public class Time {
	public static Time systemTime = new Time();
	String[] time;
	private double hour;
	private double minute;
	private double second;
	private double clock;
	private boolean file;
	
	public void setTime(){
		clock=systemTime.toSeconds(new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
	}
	public void setTime(double time){
		clock = time;
		file=true;
	}
	public double getRunningTime(){//get the race time
		if(file)
			return clock;
		else
			return systemTime.toSeconds(new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
	}
	public String toString(double time){//parse to String format
		//int hour = (int) (time/3600);
		//int min = (int) time/60;
		//double second = time%60;
		//return String.format("%02d", min)+":"+String.format("%.2f",second);
		return String.format("%.2f",time);
	}
	public double toSeconds(String time){//parse time to seconds format	
		this.time= time.split(":");
		hour=Integer.parseInt(this.time[0]);
		minute=Integer.parseInt(this.time[1]);
		second=Float.parseFloat(this.time[2]);
		return (double) (hour*3600+minute*60+second);
	}
	
}
