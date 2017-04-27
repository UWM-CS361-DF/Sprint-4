import java.util.ArrayList;

public class ParGrpEvent implements Event{
	public ArrayList<Competitor> startQueue;// = new ArrayDeque<Competitor>();
	public ArrayList<Competitor> finishQueue;// = new ArrayDeque<Competitor>();
	public ArrayList<Competitor> completed;// = new ArrayDeque<Competitor>();
	private double startTime;
	
	public ParGrpEvent(){
		startQueue = new ArrayList<Competitor>(8);
		finishQueue = new ArrayList<Competitor>(8);
		completed = new ArrayList<Competitor>(8);
		startTime=0;
	}
	
	@Override
	public boolean add(int competitorNo) {
		Competitor temp=new Competitor(competitorNo);
		if(startTime!=0||startQueue.contains(temp)||finishQueue.contains(temp)||completed.contains(temp))
			return false;
		startQueue.add(new Competitor(competitorNo));
		return true;
	}

	@Override
	public void start(int channel) {
		if(startTime==0){
			startTime=Time.systemTime.getRunningTime();
			while(!startQueue.isEmpty()){
				Competitor temp=startQueue.remove(0);
				if(temp!=null){
					temp.setStartTime(Time.systemTime.getRunningTime());
					finishQueue.add(temp);
				}
			}
		}
		else
			finish(channel);
	}

	@Override
	public void finish(int channel) {
		if(startTime!=0 && !finishQueue.isEmpty()){
			Competitor temp=finishQueue.remove(channel-1);
			finishQueue.add(channel-1, null);
			if(temp!=null){
				temp.setFinishTime(Time.systemTime.getRunningTime());
				temp.setStartTime(startTime);
				completed.add(temp);
			}
		}	
	}

	@Override
	public void end() {
		startQueue.clear();
		while(!finishQueue.isEmpty())
			dnf();
	}

	@Override
	public void dnf() {
		Competitor temp;
		temp=finishQueue.remove(0);
		temp.dnf=true;
		completed.add(temp);
	}

	@Override
	public void cancel() {	
	}

	@Override
	public void clear(int num) {
		Competitor temp=new Competitor(num);
		startQueue.remove(temp);
	}

	@Override
	public boolean swap() {
		//not used for this event
		return false;
	}

	@Override
	public String getEventType() {
		return "PARGRP";
	}

	@Override
	public ArrayList<Competitor> getCompleted() {
		return completed;
	}

	@Override
	public String displayUI() {
		String runningt="\nRunning Time\t00:00.00";
		if(startTime>0){
			runningt="\nRunning Time\t"+Time.systemTime.toString(Time.systemTime.getRunningTime()-startTime);
	}
		String starting="Racers Queued\n- - - - - - - - - - - - - - - - - - - - -";
		for(int i=2; i>-1;i--){
			if(startQueue.size()>i)
				starting=starting+'\n'+startQueue.get(i).getCompetitorNumber()+"\t00:00.00";
		}
		starting+=">"+'\n';
		
		String running="\nRunning Times\n- - - - - - - - - - - - - - - - - - - - - ";
		for(int i=0; i<finishQueue.size(); i++){
			running=running+'\n'+finishQueue.get(i).getCompetitorNumber()+'\t'+Time.systemTime.toString(Time.systemTime.getRunningTime()-finishQueue.get(i).getStartTime());
		}
		
		String finished="\n\nFinished Times\n- - - - - - - - - - - - - - - - - - - - - ";
		if(!completed.isEmpty())
				finished="\n\nFinished Times\n- - - - - - - - - - - - - - - - - - - - - \n"+completed.get(completed.size()-1).getCompetitorNumber()+'\t'+(completed.get(completed.size()-1).dnf ? "DNF":Time.systemTime.toString((completed.get(completed.size()-1).getRaceTime())));
		
		return runningt+starting+running+finished;
	}

}
