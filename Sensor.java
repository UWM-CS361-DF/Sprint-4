public class Sensor {
	
	public enum SensorType {GATE, EYE, PAD, MANUAL;}
	public SensorType sensorType;
	

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