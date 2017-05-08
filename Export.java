import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

/*Export creates a file of the race output
 * as a JSON string
 */
public class Export {
	private Gson g = new Gson();
	private String json;
	private String address;
	
	public Export(Event run, int runNum, String address) throws Exception{
		FileWriter file = new FileWriter("RUN"+runNum+".txt");
		BufferedWriter buffer = new BufferedWriter(file);
		json = g.toJson(run);
		buffer.write(json);
		buffer.flush();
		buffer.close();
		this.address=address;
		update(run, runNum);
	}
	public void update(Event run, int runNum){
		try{
			System.out.println("in the client");
	
			// Client will connect to this location
			URL site = new URL(address);
			HttpURLConnection conn = (HttpURLConnection) site.openConnection();
	
			// now create a POST request
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	
			// build a string that contains JSON from console
			//String content = "";
			//content = getJSON();
			Gson g = new Gson();
			String json = g.toJson(run.getCompleted());
	
			// write out string to output buffer for message
			out.writeBytes(json);
			out.flush();
			out.close();
	
			System.out.println("Done sent to server");
	
			InputStreamReader inputStr = new InputStreamReader(conn.getInputStream());
	
			// string to hold the result of reading in the response
			StringBuilder sb = new StringBuilder();
	
			// read the characters from the request byte by byte and build up
			// the Response
			int nextChar;
			while ((nextChar = inputStr.read()) > -1) {
				sb = sb.append((char) nextChar);
			}
			System.out.println("Return String: " + sb);
	
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
