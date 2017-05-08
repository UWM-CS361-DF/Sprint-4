import java.util.ArrayList;

/*GRP Event. The racers have one start and multiple finishes
 * on channel 2. Each finish receives a place holder 001, 002, etc
 * until the race is ended and bib numbers can be assigned 
 */
public class GrpEvent implements Event{
	private ArrayList<Competitor> completed;
	private double startTime,finishTime;

	public GrpEvent(){
		completed = new ArrayList<Competitor>();
		startTime=0;
		finishTime=0;
	}
	
	/*add will only be allowed if the run is ended and all times 
	*have not been assigned yet. 
	*/
	@Override
	public boolean add(int competitorNo) {
		Competitor temp=new Competitor(competitorNo);
		if(completed.contains(temp)||ChronoInterface.chronoTimer.runInProgress)
			return false;
		for(int i=0;i<completed.size();i++){
			if(completed.get(i).getCompetitorNumber()<0){
				completed.get(i).setCompetitorNumber(competitorNo);
				return true;
			}
		}
		return false;
	}
	//start the race running time
	@Override
	public void start(int channel) {
		if(startTime==0&&channel==1)
			startTime=Time.systemTime.getRunningTime();
	}
	//add new finish time to completed queue
	@Override
	public void finish(int channel) {
		Competitor temp=new Competitor(-(completed.size()+1));
		temp.setFinishTime(Time.systemTime.getRunningTime());
		temp.setStartTime(startTime);
		completed.add(temp);
	}
	//endrun and set finish time
	@Override
	public void end() {
		if(finishTime==0)
			finishTime=Time.systemTime.getRunningTime();		
	}
	//if race is not ended dnf a race time  
	@Override
	public void dnf() {
		if(startTime !=0 && finishTime==0){
			Competitor temp=new Competitor(-(completed.size()+1));
			temp.dnf=true;
			completed.add(temp);
		}
	}
	//cancel removes the last finish time
	@Override
	public void cancel() {
		if(!completed.isEmpty())
			completed.remove(completed.size()-1);
	}
	//no clear for GRP event
	@Override
	public void clear(int num) {//not used no racers to clear from queue
	}
	//no swap for GRP event
	@Override
	public boolean swap() {
		return false;	
	}
	//return the event type
	@Override
	public String getEventType() {
		return "GRP";
	}
	//returns the completed queue of racers
	@Override
	public ArrayList<Competitor> getCompleted() {
		return completed;
	}
	//display for the GRP event
	public String displayUI(){	
		String running="Running Time\t00:00.00";
		if(startTime>0){
			running="Running Time\t"+Time.systemTime.toString((finishTime!=0?finishTime:Time.systemTime.getRunningTime())-startTime);
	}
		
		String finished="\n\nFinished Times\n- - - - - - - - - - - - - - - - - - - - -\n ";
		if(!completed.isEmpty()){
			int temp=completed.get(completed.size()-1).getCompetitorNumber();
			finished=finished+ (temp<0 ? String.format("%05d", -temp)+ "\t": temp+ "\t");
			finished=finished+(completed.get(completed.size()-1).dnf ? "DNF":Time.systemTime.toString((completed.get(completed.size()-1).getRaceTime())));
		}
		return running+finished;
	}
}
