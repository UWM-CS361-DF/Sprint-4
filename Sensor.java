
public class Sensor {
	/*
	 * Determines the type of sensor was connected. If pad sensor
	 * was connected then an emulator will appear to simulate the
	 * sensor per Sprint 4 requirements.
	 */
	public enum SensorType {GATE, EYE, PAD, MANUAL;}
	private SensorType sensorType;
	private Sensor_UI sensor_UI;
	private int channelNo;
	public Sensor(String sensor, int channelNo) {
		this.channelNo=channelNo;
		switch (sensor) {
		case "Gate":
			setSensorType(SensorType.GATE);
			return;
		case "Eye":
			setSensorType(SensorType.EYE);
			return;
		case "Pad":
			setSensorType(SensorType.PAD);
			return;
		case "Manual":
			setSensorType(SensorType.MANUAL);
			return;
		}
	}
	public SensorType getSensorType() {
		return sensorType;
	}
	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
		sensor_UI = new Sensor_UI(channelNo, this);
		sensor_UI.setTitle("Channel "+channelNo+" Sensor");
		sensor_UI.setSize(300,200);
		sensor_UI.setVisible(true);
		sensor_UI.setResizable(false);
	}
	public void disc(){
		sensor_UI.setVisible(false);
	}
}