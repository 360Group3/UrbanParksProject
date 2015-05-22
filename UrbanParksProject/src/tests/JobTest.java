package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import model.Job;
import model.ParkManager;

import org.junit.Before;
import org.junit.Test;

public class JobTest {
	
	private Job myJob;
	private ParkManager myParkManager;
	
	@Before
	public void setUp() throws Exception {
		String startDate = "06111992";
		String endDate = "06111992";
		
		myJob = new Job(1, "Ariel Park", 3, 3, 3, 
					    startDate, endDate,
					    myParkManager.getEmail(),
					    new ArrayList<ArrayList<String>>());
	}

	@Test
	public void testConstructor() {
		assertEquals(3, myJob.getLightMax());
		assertEquals(3, myJob.getMediumMax());
		assertEquals(3, myJob.getHeavyMax());
		assertEquals(0, myJob.getLightCurrent());
		assertEquals(0, myJob.getMediumCurrent());
		assertEquals(0, myJob.getHeavyCurrent());
		
		
		for (int i = 1; i <= 5; i++)
		{
			Job j = new Job(i, "Ariel Park", 2,2,2, 
							"06" + Integer.toString(11+i) + 1992, 
							"06" + Integer.toString(11+i) + 1992, 
							"moverby@gmail.com",
						    new ArrayList<ArrayList<String>>());
			assertEquals(i, j.getJobID());
		}
	}
	
	@Test
	public void testHasLightRoom() {
		assertTrue(myJob.hasLightRoom());
		
		ArrayList<String> vol0 = new ArrayList<>();
		ArrayList<String> vol1 = new ArrayList<>();
		ArrayList<String> vol2 = new ArrayList<>();
		
		vol0.add("moverby@gmail.com");
		vol0.add("Light");
		myJob.addVolunteer(vol0);
		assertTrue(myJob.hasLightRoom());
		vol1.add("choberby@gmail.com");
		vol1.add("Light");
		myJob.addVolunteer(vol1);
		assertTrue(myJob.hasLightRoom());
		vol2.add("gotosleep@gmail.com");
		vol2.add("Light");
		myJob.addVolunteer(vol2);
		assertFalse(myJob.hasLightRoom());
	}

	@Test
	public void testHasMediumRoom() {
		assertTrue(myJob.hasMediumRoom());
		
		ArrayList<String> vol3 = new ArrayList<>();
		ArrayList<String> vol4 = new ArrayList<>();
		ArrayList<String> vol5 = new ArrayList<>();
		
		vol3.add("yaaaaa@gmail.com");
		vol3.add("Medium");
		myJob.addVolunteer(vol3);
		assertTrue(myJob.hasMediumRoom());
		vol4.add("foop@gmail.com");
		vol4.add("Medium");
		myJob.addVolunteer(vol4);
		assertTrue(myJob.hasMediumRoom());
		vol5.add("witchtable@gmail.com");
		vol5.add("Medium");
		myJob.addVolunteer(vol5);
		assertFalse(myJob.hasMediumRoom());
	}
	
	@Test
	public void testHasHeavyRoom() {
		assertTrue(myJob.hasHeavyRoom());
			
		ArrayList<String> vol6 = new ArrayList<>();
		ArrayList<String> vol7 = new ArrayList<>();
		ArrayList<String> vol8 = new ArrayList<>();
		
		vol6.add("whodunnit@gmail.com");
		vol6.add("Heavy");
		myJob.addVolunteer(vol6);
		assertTrue(myJob.hasHeavyRoom());
		vol7.add("gitblame@gmail.com");
		vol7.add("Heavy");
		myJob.addVolunteer(vol7);
		assertTrue(myJob.hasHeavyRoom());
		vol8.add("manager@gmail.com");
		vol8.add("Heavy");
		myJob.addVolunteer(vol8);
		assertFalse(myJob.hasHeavyRoom());
	}
	
	@Test
	public void testHasRoom() {
		assertTrue(myJob.hasLightRoom());
		
		ArrayList<String> vol0 = new ArrayList<>();
		ArrayList<String> vol1 = new ArrayList<>();
		ArrayList<String> vol2 = new ArrayList<>();
		ArrayList<String> vol3 = new ArrayList<>();
		ArrayList<String> vol4 = new ArrayList<>();
		ArrayList<String> vol5 = new ArrayList<>();
		ArrayList<String> vol6 = new ArrayList<>();
		ArrayList<String> vol7 = new ArrayList<>();
		ArrayList<String> vol8 = new ArrayList<>();

		vol0.add("moverby@gmail.com");
		vol0.add("Light");
		myJob.addVolunteer(vol0);
		assertTrue(myJob.hasRoom());
		vol1.add("choberby@gmail.com");
		vol1.add("Light");
		myJob.addVolunteer(vol1);
		assertTrue(myJob.hasRoom());
		vol2.add("gotosleep@gmail.com");
		vol2.add("Light");
		myJob.addVolunteer(vol2);
		assertTrue(myJob.hasRoom());
		
		vol3.add("yaaaaa@gmail.com");
		vol3.add("Medium");
		myJob.addVolunteer(vol3);
		assertTrue(myJob.hasRoom());
		vol4.add("foop@gmail.com");
		vol4.add("Medium");
		myJob.addVolunteer(vol4);
		assertTrue(myJob.hasRoom());
		vol5.add("witchtable@gmail.com");
		vol5.add("Medium");
		myJob.addVolunteer(vol5);
		assertTrue(myJob.hasRoom());
		
		vol6.add("whodunnit@gmail.com");
		vol6.add("Heavy");
		myJob.addVolunteer(vol6);
		assertTrue(myJob.hasRoom());
		vol7.add("gitblame@gmail.com");
		vol7.add("Heavy");
		myJob.addVolunteer(vol7);
		assertTrue(myJob.hasRoom());
		vol8.add("manager@gmail.com");
		vol8.add("Heavy");
		myJob.addVolunteer(vol8);
		assertFalse(myJob.hasRoom());
	}

	@Test
	public void testGetLightCurrent() {
		assertEquals(0, myJob.getLightCurrent());
		
		ArrayList<String> vol0 = new ArrayList<>();
		ArrayList<String> vol1 = new ArrayList<>();
		ArrayList<String> vol2 = new ArrayList<>();
		
		vol0.add("moverby@gmail.com");
		vol0.add("Light");
		myJob.addVolunteer(vol0);
		assertEquals(1, myJob.getLightCurrent());
		vol1.add("choberby@gmail.com");
		vol1.add("Light");
		myJob.addVolunteer(vol1);
		assertEquals(2, myJob.getLightCurrent());
		vol2.add("gotosleep@gmail.com");
		vol2.add("Light");
		myJob.addVolunteer(vol2);
		assertEquals(3, myJob.getLightCurrent());
	}

	@Test
	public void testGetMediumCurrent() {
		assertEquals(0, myJob.getMediumCurrent());
		
		ArrayList<String> vol3 = new ArrayList<>();
		ArrayList<String> vol4 = new ArrayList<>();
		ArrayList<String> vol5 = new ArrayList<>();
	
		vol3.add("yaaaaa@gmail.com");
		vol3.add("Medium");
		myJob.addVolunteer(vol3);
		assertEquals(1, myJob.getMediumCurrent());
		vol4.add("foop@gmail.com");
		vol4.add("Medium");
		myJob.addVolunteer(vol4);
		assertEquals(2, myJob.getMediumCurrent());
		vol5.add("witchtable@gmail.com");
		vol5.add("Medium");
		myJob.addVolunteer(vol5);
		assertEquals(3, myJob.getMediumCurrent());
	}
	
	@Test
	public void TestGetHeavyCurrent() {
		assertEquals(0, myJob.getHeavyCurrent());
		
		ArrayList<String> vol6 = new ArrayList<>();
		ArrayList<String> vol7 = new ArrayList<>();
		ArrayList<String> vol8 = new ArrayList<>();
		
		vol6.add("whodunnit@gmail.com");
		vol6.add("Heavy");
		myJob.addVolunteer(vol6);
		assertEquals(1, myJob.getHeavyCurrent());
		vol7.add("gitblame@gmail.com");
		vol7.add("Heavy");
		myJob.addVolunteer(vol7);
		assertEquals(2, myJob.getHeavyCurrent());
		vol8.add("manager@gmail.com");
		vol8.add("Heavy");
		myJob.addVolunteer(vol8);
		assertEquals(3, myJob.getHeavyCurrent());
	}
}
