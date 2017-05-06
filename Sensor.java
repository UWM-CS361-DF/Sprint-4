public class Sensor {
	/*
	 * Determines the type of sensor was connected. If pad sensor
	 * was connected then an emulator will appear to simulate the
	 * sensor per Sprint 4 requirements.
	 */
	public enum SensorType {GATE, EYE, PAD, MANUAL;}
	private SensorType sensorType;
	

	public Sensor(String sensor) {
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
	}
}