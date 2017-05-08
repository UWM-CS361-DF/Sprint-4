import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*Prints each run in the system and the race times for each it its racers
 */
public class Print {
	public Print(List<Event> runs){
		for(int i=1;i<runs.size();i++){
			ChronoInterface.chronoTimer.println("\nRun " +(i)+" "+runs.get(i).getEventType()+" Event");
			ChronoInterface.chronoTimer.println("NUM\tTime");
			ArrayList<Competitor> temp = runs.get(i).getCompleted();
			if(!runs.get(i).getEventType().equals("PARGRP"))
				Collections.sort(temp);
			for(Competitor competitor:temp){
				if(competitor!=null){
					int tempBib=competitor.getCompetitorNumber();
					ChronoInterface.chronoTimer.print(tempBib<0 ? String.format("%05d", -tempBib)+ "\t": tempBib+ "\t");
					ChronoInterface.chronoTimer.println(competitor.dnf ? "DNF" : ""+String.format("%.2f",competitor.getRaceTime()));
				}
			}
		}
	}
}
