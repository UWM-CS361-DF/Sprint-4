import java.util.ArrayList;
/* Event is a simple interface for the different
 * event types IND, PARIND, GRP, PARGRP
 */
public interface Event{
	public boolean add(int competitorNo);
	public void start(int channel);
	public void finish(int channel);
	public void end();
	public void dnf();
	public void cancel();
	public void clear(int num);
	public boolean swap();
	public String getEventType();
	public ArrayList<Competitor> getCompleted();
	public String displayUI();
}
