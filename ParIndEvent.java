import java.util.ArrayList;

public class ParIndEvent implements Event{
	public ArrayList<Competitor> startQueue;// = new ArrayDeque<Competitor>();
	public ArrayList<Competitor> finishQueue;// = new ArrayDeque<Competitor>();
	public ArrayList<Competitor> completed;// = new ArrayDeque<Competitor>();
	
	public ParIndEvent(){
		startQueue = new ArrayList<Competitor>();
		finishQueue = new ArrayList<Competitor>();
		completed = new ArrayList<Competitor>();
	}
	
	// if the start queue of the race is not empty
	// take out the head of the start queue
	// set the start time of the competitor
	// put the competitor to the finish queue
	@Override
	public boolean add(int competitorNo){
		Competitor temp=new Competitor(competitorNo);
		if(startQueue.contains(temp)||finishQueue.contains(temp)||completed.contains(temp))
			return false;
		startQueue.add(new Competitor(competitorNo));
		return true;
	}
	@Override
	public void start(int channel) {
		if(!startQueue.isEmpty()){
			Competitor temp=startQueue.remove(0);
			temp.setStartTime(Time.systemTime.getRunningTime());
			finishQueue.add(temp);
		}
	} 
	// if the finish queue is not empty
	// take out the head of the start queue
	// set the finish time of the competitor
	// put the competitor to the completed queue 
	@Override
	public void finish(int channel) {
		if(!finishQueue.isEmpty()){
			Competitor temp=finishQueue.remove(0);
			temp.setFinishTime(Time.systemTime.getRunningTime());
			completed.add(temp);
		}
	}
	@Override
	public void end() {
		startQueue.clear();
		while(!finishQueue.isEmpty())
			dnf();		
	}
	@Override
	public void dnf(){
		Competitor temp;
		temp=finishQueue.remove(0);
		temp.dnf=true;
		completed.add(temp);
	}
	@Override
	public void cancel(){
		startQueue.add(0,finishQueue.remove(finishQueue.size()-1));
	}
	@Override
	public void clear(int num){
		
	}
	@Override
	public boolean swap(){
		return false;
	}
	@Override
	public String getEventType(){
		return "PARIND";
	}
	@Override
	public ArrayList<Competitor> getCompleted(){
		return completed;
	}
	public String displayUI(){
		String starting="Racers Queued\n- - - - - - - - - - - - - - - - - - - - -";
		for(int i=1; i>-1;i--){
			if(startQueue.size()>i)
				starting=starting+'\n'+startQueue.get(i).getCompetitorNumber()+"\t00:00.00";
		}
		starting+=">"+'\n';
		
		String running="\nRunning Times\n- - - - - - - - - - - - - - - - - - - - - ";
		for(int i=0; i<finishQueue.size(); i++){
			running=running+'\n'+finishQueue.get(i).getCompetitorNumber()+'\t'+Time.systemTime.toString(Time.systemTime.getRunningTime()-finishQueue.get(i).getStartTime());
		}
		
		String finished="\n\nFinished Times\n- - - - - - - - - - - - - - - - - - - - - ";
		for(int i=1; i<3; i++){
			if(completed.size()>i-1)
				finished=finished+"\n"+completed.get(completed.size()-i).getCompetitorNumber()+'\t'+(completed.get(completed.size()-i).dnf ? "DNF":Time.systemTime.toString(completed.get(completed.size()-i).getRaceTime()));
		}
		
		return starting+running+finished;
	}
}
