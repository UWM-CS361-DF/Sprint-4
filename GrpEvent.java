import java.util.ArrayList;

public class GrpEvent implements Event{
	public ArrayList<Competitor> startQueue;// = new ArrayDeque<Competitor>();
	public ArrayList<Competitor> finishQueue;// = new ArrayDeque<Competitor>();
	public ArrayList<Competitor> completed;// = new ArrayDeque<Competitor>();
	private double startTime;

	public GrpEvent(){
		startQueue = new ArrayList<Competitor>();
		finishQueue = new ArrayList<Competitor>();
		completed = new ArrayList<Competitor>();
		startTime=0;
	}
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

	@Override
	public void start(int channel) {
		if(startTime==0&&channel==1)
			startTime=Time.systemTime.getRunningTime();
	}

	@Override
	public void finish(int channel) {
		Competitor temp=new Competitor(-(completed.size()+1));
		temp.setFinishTime(Time.systemTime.getRunningTime());
		temp.setStartTime(startTime);
		completed.add(temp);
	}
	@Override
	public void end() {
		startQueue.clear();
		while(!finishQueue.isEmpty())
			dnf();		
	}
	@Override
	public void dnf() {
		Competitor temp=new Competitor(-(completed.size()+1));
		temp.dnf=true;
		completed.add(temp);
	}

	@Override
	public void cancel() {	
		startTime=0;
	}

	@Override
	public void clear(int num) {//not used no racers to clear from queue
	}

	@Override
	public boolean swap() {
		return false;	
	}

	@Override
	public String getEventType() {
		return "GRP";
	}

	@Override
	public ArrayList<Competitor> getCompleted() {
		return completed;
	}
	public String displayUI(){	
		String running="\nRunning Time\t00:00.00";
		if(startTime>0){
			running="\nRunning Time\t"+Time.systemTime.toString(Time.systemTime.getRunningTime()-startTime);
	}
		
		String finished="\n\nFinished Times\n- - - - - - - - - - - - - - - - - - - - -\n ";
		if(!completed.isEmpty()){
			int temp=completed.get(completed.size()-1).getCompetitorNumber();
			finished=finished+ (temp<0 ? String.format("%05d", -temp)+ "\t": temp+ "\t");
			finished=finished+Time.systemTime.toString(completed.get(completed.size()-1).getRaceTime());
		}
		return running+finished;
	}
}
