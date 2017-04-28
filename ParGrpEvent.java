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
		for(int i=0;i<8;i++){
			startQueue.add(null);
			completed.add(null);
		}
		startTime=0;
	}
	
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

	@Override
	public void start(int channel) {
		if(startTime==0&&channel==1){
			startTime=Time.systemTime.getRunningTime();
			for(int i=0;i<8;i++){
				Competitor temp=startQueue.remove(0);
				if(temp!=null)
					temp.setStartTime(Time.systemTime.getRunningTime());
				finishQueue.add(i,temp);
				}
		}
		else
			finish(channel);
	}

	@Override
	public void finish(int channel) {
		if(startTime!=0 && finishQueue.get(channel-1)!=null){
			Competitor temp=finishQueue.remove(channel-1);
			finishQueue.add(channel-1, null);
			if(temp!=null){
				temp.setFinishTime(Time.systemTime.getRunningTime());
				temp.setStartTime(startTime);
			}
			completed.remove(channel-1);
			completed.add(channel-1, temp);
		}	
	}

	@Override
	public void end() {
		startQueue.clear();
		for(int i=0;i<finishQueue.size();i++){
			if(finishQueue.get(i)!=null)
				dnf();
		}
	}

	@Override
	public void dnf() {
		Competitor temp;
		int index=completed.indexOf(null);
		temp=finishQueue.remove(index);
		finishQueue.add(index, null);
		temp.dnf=true;
		completed.remove(index);
		completed.add(index,temp);
	}

	@Override
	public void cancel() {	
		startTime=0;
	}

	@Override
	public void clear(int num) {
		Competitor temp=new Competitor(num);
		int index=startQueue.indexOf(temp);
		startQueue.remove(index);
		startQueue.add(index, null);
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
		String runningt="\nRunning Time\t00:00.00\n\n";
		if(startTime>0){
			runningt="\nRunning Time\t"+Time.systemTime.toString(Time.systemTime.getRunningTime()-startTime)+"\n\n";
	}
		String starting="Racers \n- - - - - - - - - - - - - - - - - - - - -";
		for(int i=0; i<startQueue.size();i++){
			if(startQueue.size()>i){
				if(startQueue.get(i)!=null)
				starting=starting+"\nLane "+(i+1)+": "+startQueue.get(i).getCompetitorNumber();
			}
		}
		for(int i=0; i<finishQueue.size(); i++){
			if(finishQueue.get(i)!=null)
				starting=starting+"\nLane "+(i+1)+": "+finishQueue.get(i).getCompetitorNumber();
		}
		starting+='\n';
		String finished="\nFinished Times\n- - - - - - - - - - - - - - - - - - - - - ";
		for(int i=0; i<completed.size(); i++)
			if(completed.get(i)!=null)
				finished=finished+"\nLane "+(i+1)+": "+completed.get(i).getCompetitorNumber()+'\t'+(completed.get(i).dnf ? "DNF":Time.systemTime.toString((completed.get(i).getRaceTime())));
		
		return runningt+starting+finished;
	}

}
