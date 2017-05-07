/*
 The competitor class :)
 This class defines the competitor object 
 Each competitor has its number, start time, and finish time. 
 The competitor object has also a boolean value 'dnf' which 
 indicates whether the competitor "did not finish" the race
 */

public class Competitor implements Comparable<Object> {
	private int competitorNo;
	private double startTime;
	private double finishTime;
	boolean dnf;
	private String name;
	
	public Competitor(int competitorNo){
		this.competitorNo = competitorNo;
	}
	public Competitor(int competitorNo, String name){
		this.competitorNo = competitorNo;
		this.name=name;
	}
	public String getName(){
		return name;
	}
	public void setStartTime(double time){
		startTime = time;
	}
	public void setFinishTime(double time){
		finishTime = time;
	}
	public double getStartTime(){
		return startTime;
	}
	public double getFinishTime(){
		return finishTime;
	}
	public double getRaceTime(){
		return Double.parseDouble(Time.systemTime.toString(finishTime-startTime));
	}
	public int getCompetitorNumber(){
		return competitorNo;
	}
	public void setCompetitorNumber(int competitorNo){
		this.competitorNo = competitorNo;
	}
	@Override
	public boolean equals(Object c){
		if(c==null)
			return false;
		return ((Competitor)c).getCompetitorNumber()==this.competitorNo;
	}
	@Override
	public int compareTo(Object o) {
		if (o instanceof Competitor) {
			Competitor other = (Competitor) o;
			if(dnf)
				return 1;
			else if(other.dnf)
				return -1;
			else if(getRaceTime()>other.getRaceTime())
				return 1;
			else if(getRaceTime()<other.getRaceTime())
				return -1;
			else
				return 0;
		}
		return 0;
	}
}
