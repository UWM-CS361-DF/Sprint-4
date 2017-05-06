import java.util.ArrayList;

/*
The ParGrpEvent class
This class defines the Parallel Grp Event
*/
public class ParGrpEvent implements Event{
	private ArrayList<Competitor> startQueue;
	private ArrayList<Competitor> finishQueue;
	private ArrayList<Competitor> completed;
	private double startTime, finishTime;
	
	public ParGrpEvent(){
		startQueue = new ArrayList<Competitor>(8);
		finishQueue = new ArrayList<Competitor>(8);
		completed = new ArrayList<Competitor>(0);
		for(int i=0;i<8;i++){
			startQueue.add(null);
			finishQueue.add(null);
		}
		startTime=0;
		finishTime=0;
	}
	//add a racer into the start queue to the next available lane
	@Override
	public boolean add(int competitorNo) {
		Competitor temp=new Competitor(competitorNo);
		int index = startQueue.indexOf(null); 
		if(index==-1||startTime!=0||startQueue.contains(temp)||finishQueue.contains(temp)||completed.contains(temp))
			return false;
		startQueue.remove(index);
		startQueue.add(index,new Competitor(competitorNo));
		return true;
	}
	//start the race running time. Same for all racers
	@Override
	public void start(int channel) {
		if(startTime==0&&channel==1){
			startTime=Time.systemTime.getRunningTime();
			for(int i=0;i<8;i++){
				//Competitor temp=startQueue.remove(0);
				//if(temp!=null)
				if(startQueue.get(i)!=null)
					startQueue.get(i).setStartTime(Time.systemTime.getRunningTime());
			//	finishQueue.add(i,temp);
				}
		}
		else
			finish(channel);
	}
	//set finish time for racer on channel that was triggered
	@Override
	public void finish(int channel) {
		if(startTime!=0 && startQueue.get(channel-1)!=null){
			Competitor temp=startQueue.remove(channel-1);
			startQueue.add(channel-1, null);
			if(temp!=null){
				temp.setFinishTime(Time.systemTime.getRunningTime());
				temp.setStartTime(startTime);
			}
			finishQueue.remove(channel-1);
			finishQueue.add(channel-1, temp);
			completed.add(temp);
		}	
	}
	//endrun, set finish time, and dnf racers with no finish time
	@Override
	public void end() {
		finishTime=Time.systemTime.getRunningTime();
		for(int i=0;i<startQueue.size();i++){
			if(startQueue.get(i)!=null)
				dnf();
		}
	}
	//set finish time to DNF
	@Override
	public void dnf() {
		Competitor temp;
		int index=finishQueue.indexOf(null);
		temp=startQueue.remove(index);
		startQueue.add(index, null);
		if(temp!=null){
			temp.dnf=true;
			finishQueue.remove(index);
			finishQueue.add(index,temp);
			completed.add(temp);
		}
	}
	//cancel the race. set start time to zero
	@Override
	public void cancel() {	
		startTime=0;
		startQueue = new ArrayList<Competitor>(8);
		finishQueue = new ArrayList<Competitor>(8);
		completed = new ArrayList<Competitor>(0);
	}
	//clear racer with bib number for the queue
	@Override
	public void clear(int num) {
		Competitor temp=new Competitor(num);
		int index=startQueue.indexOf(temp);
		startQueue.remove(index);
		startQueue.add(index, null);
	}
	//no swap for this event
	@Override
	public boolean swap() {
		//not used for this event
		return false;
	}
	//return the event type
	@Override
	public String getEventType() {
		return "PARGRP";
	}
	//returns the completed queue of racers
	@Override
	public ArrayList<Competitor> getCompleted() {
		return completed;
	}
	//display for the PARGRP event
	@Override
	public String displayUI() {
		String runningt="Running Time\t00:00.00\n\n";
		if(startTime>0){
			runningt="Running Time\t"+Time.systemTime.toString((finishTime!=0?finishTime:Time.systemTime.getRunningTime())-startTime)+"\n\n";
	}
		String starting="Racers \n- - - - - - - - - - - - - - - - - - - - -";
		for(int i=0; i<startQueue.size();i++){
			if(startQueue.size()>i){
				if(startQueue.get(i)!=null)
				starting=starting+"\nLane "+(i+1)+": "+startQueue.get(i).getCompetitorNumber();
			}
		}
		starting+='\n';
		String finished="\nFinished Times\n- - - - - - - - - - - - - - - - - - - - - ";
		for(int i=0; i<completed.size(); i++)
			if(completed.get(i)!=null)
				finished=finished+"\n"+completed.get(i).getCompetitorNumber()+'\t'+(completed.get(i).dnf ? "DNF":Time.systemTime.toString((completed.get(i).getRaceTime())));
		
		return runningt+starting+finished;
	}

}
