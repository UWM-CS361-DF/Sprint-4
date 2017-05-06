import java.text.SimpleDateFormat;
import java.util.Calendar;
/* This is the Time class for the ChronoTimer. Determines 
 * whether the timer is running off system time or time 
 * read from a file 
 */
public class Time {
	public static Time systemTime = new Time();
	private String[] time;
	private double hour;
	private double minute;
	private double second;
	private double clock;
	private boolean file;
	
	//setTime from system
	public void setTime(){
		clock=systemTime.toSeconds(new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
	}
	//setTime given from file
	public void setTime(double time){
		clock = time;
		file=true;
	}
	//return "current" time whether time passed from file or system time
	public double getRunningTime(){
		if(file)
			return clock;
		else
			return systemTime.toSeconds(new SimpleDateFormat("HH:mm:ss.SSS").format(Calendar.getInstance().getTime()));
	}
	//String of given time
	public String toString(double time){
		return String.format("%.2f",time);
	}
	//returns time in seconds
	public double toSeconds(String time){
		this.time= time.split(":");
		hour=Integer.parseInt(this.time[0]);
		minute=Integer.parseInt(this.time[1]);
		second=Float.parseFloat(this.time[2]);
		return (double) (hour*3600+minute*60+second);
	}
	
}
