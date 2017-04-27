import static org.junit.Assert.*;

import org.junit.Test; 
import org.junit.internal.runners.JUnit38ClassRunner;

public class ChronoTimerTest{
	ChronoInterface myTimer1 = new ChronoInterface();	
		
	@Test
	public void testPower() throws Exception{
		//-test turn power on and off
		assertFalse(myTimer1.power.powerStatus);
		myTimer1.power();
		assertTrue(myTimer1.power.powerStatus);
		myTimer1.power();
		assertFalse(myTimer1.power.powerStatus);
		myTimer1.power();
		assertTrue(myTimer1.power.powerStatus);
	}
	
	@Test
	public void testTog() throws Exception{
		//-test TOG
		myTimer1.power();
		assertFalse(myTimer1.system.channels.get(1).isArmed);
		assertFalse(myTimer1.system.channels.get(2).isArmed);
		myTimer1.tog("1");
		assertTrue(myTimer1.system.channels.get(1).isArmed);
		myTimer1.tog("2");
		assertTrue(myTimer1.system.channels.get(2).isArmed);
		myTimer1.tog("1");
		assertFalse(myTimer1.system.channels.get(1).isArmed);
		myTimer1.tog("2");		
	}
	
	@Test
	public void testTrig() throws Exception{
		//-test TRIG
		myTimer1.trig("1");
		
	}
	
	@Test
	public void testStart() throws Exception{
		//-test reset
	}
	
	@Test
	public void testFinish() throws Exception{
		//-test reset
	}
	@Test
	public void testEvent() throws Exception{
		//-test reset
	}
	@Test
	public void testNewRun() throws Exception{
		//-test reset
	}
	@Test
	public void testEndRun() throws Exception{
		//-test reset
	}
	@Test
	public void testPrint()) throws Exception{
		//-test reset
	}
	@Test
	public void testNum() throws Exception{
		//-test reset
	}
	@Test
	public void testDNF() throws Exception{
		//-test reset
	}
	@Test
	public void testCancel() throws Exception{
		//-test reset
	}
	@Test
	public void testReset() throws Exception{
		//-test reset
	}
}