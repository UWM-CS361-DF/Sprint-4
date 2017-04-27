import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Print {
	public Print(List<Event> runs){
		for(int i=1;i<runs.size();i++){
			ChronoInterface.chronoTimer.println("\nRun " +(i)+" "+runs.get(i).getEventType()+" Event");
			ChronoInterface.chronoTimer.println("NUM\tTime");
			ArrayList<Competitor> temp = runs.get(i).getCompleted();
			Collections.sort(temp);
			for(Competitor competitor:temp){
				int tempBib=competitor.getCompetitorNumber();
				ChronoInterface.chronoTimer.print(tempBib<0 ? String.format("%05d", -tempBib)+ "\t": tempBib+ "\t");
				ChronoInterface.chronoTimer.println(competitor.dnf ? "DNF" : Time.systemTime.toString(competitor.getRaceTime()));
			}
		}
	}
}
