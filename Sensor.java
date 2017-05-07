
public class Sensor {
	/*
	 * Determines the type of sensor was connected. If pad sensor
	 * was connected then an emulator will appear to simulate the
	 * sensor per Sprint 4 requirements.
	 */
	public enum SensorType {GATE, EYE, PAD, MANUAL;}
	private SensorType sensorType;
	private Pad_UI pad;
	public Sensor(String sensor, int channelNo) {
		switch (sensor) {
		case "Gate":
			setSensorType(SensorType.GATE);
			return;
		case "Eye":
			setSensorType(SensorType.EYE);
			return;
		case "Pad":
			setSensorType(SensorType.PAD);
			pad = new Pad_UI(channelNo);
			pad.setTitle("Channel "+channelNo+" Sensor");
			pad.setSize(300,200);
			pad.setVisible(true);
			pad.setResizable(false);
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
	}
	public void disc(){
		pad.setVisible(false);
	}
}