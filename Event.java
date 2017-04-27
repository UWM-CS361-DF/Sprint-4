import java.util.ArrayList;

public interface Event{
//	public ArrayList<Competitor> startQueue = new ArrayList<Competitor>();
//	public ArrayList<Competitor> finishQueue = new ArrayList<Competitor>();
//	public ArrayList<Competitor> completed = new ArrayList<Competitor>();
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
