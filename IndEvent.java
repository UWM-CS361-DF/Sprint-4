import java.util.ArrayList;

/*
 The IndEvent class
 This class defines the Individual Event 
 */
public class IndEvent implements Event{
	private ArrayList<Competitor> startQueue;
	private ArrayList<Competitor> finishQueue;
	private ArrayList<Competitor> completed;
	
	public IndEvent(){
		startQueue = new ArrayList<Competitor>();
		finishQueue = new ArrayList<Competitor>();
		completed = new ArrayList<Competitor>();
	}
	
	//add a racer into the start queue
	@Override
	public boolean add(int competitorNo){
		Competitor temp=new Competitor(competitorNo);
		if(startQueue.contains(temp)||finishQueue.contains(temp)||completed.contains(temp))
			return false;
		startQueue.add(new Competitor(competitorNo));
		return true;
	}
	/*if start queue is not empty, remove racer, set start time, 
	 * and place in the queue to finish.
	 */
	@Override
	public void start(int channel) {
		if(!startQueue.isEmpty()){
			Competitor temp=startQueue.remove(0);
			temp.setStartTime(Time.systemTime.getRunningTime());
			finishQueue.add(temp);
		}
	} 
	/*if finish queue is not empty, remove racer, set finish time, 
	 * and place in the completed.
	 */
	@Override
	public void finish(int channel) {
		if(!finishQueue.isEmpty()){
			Competitor temp=finishQueue.remove(0);
			temp.setFinishTime(Time.systemTime.getRunningTime());
			completed.add(temp);
		}
	}
	/* clears the start queue of racers who did not start, and set the racers
	 * who did not finish to DNF
	 */
	@Override
	public void end() {
		startQueue.clear();
		while(!finishQueue.isEmpty())
			dnf();	
	}
	/*set next racer in finish queue to DNF
	 */
	@Override
	public void dnf(){
		Competitor temp;
		if(!finishQueue.isEmpty()){
			temp=finishQueue.remove(0);
			temp.dnf=true;
			completed.add(temp);
		}
	}
	/*cancels the last racer which started and places back into start queue
	 */
	@Override
	public void cancel(){
		if(!finishQueue.isEmpty())
			startQueue.add(0,finishQueue.remove(finishQueue.size()-1));
	}
	//return the event type
	@Override
	public String getEventType(){
		return "IND";
	}
	//returns the completed queue of racers
	@Override
	public ArrayList<Competitor> getCompleted(){
		return completed;
	}
	//clears the racer with bib number
	@Override
	public void clear(int num){
		Competitor temp=new Competitor(num);
		startQueue.remove(temp);
	}
	//swaps the next 2 racers to finish
	@Override
	public boolean swap(){
		if(finishQueue.size()<2)
			return false;
		Competitor temp1=finishQueue.remove(0);
		Competitor temp2=finishQueue.remove(0);
		finishQueue.add(0,temp1);
		finishQueue.add(0,temp2);
		return true;
	}
	//display for the IND event
	public String displayUI(){
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
		
		return starting+running+finished;
	}
}
