package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.Job;
import model.Schedule;
import model.Volunteer;

/**
 * This class is the gui for the volunteer.
 * Note: it does no logic work.
 * @author Arsh
 *
 */
public class VolunteerGUI extends JFrame{

	//=================================
	//FIELDS
	//=================================
		
	
	/**
	 * This is an instance of a volunteer. It is used when a volunteer logs in.
	 */
	private Volunteer myVol;
	
	/**
	 * This is an instance of the schedule where a volunteer would be added if they
	 * sign up for a job.
	 */
	private Schedule mySched;

	
	
	
	//=================================
	//METHODS
	//=================================
	

	/**
	 * This is the constructor.
	 */
	public VolunteerGUI(Volunteer theVol, Schedule theSched) {
		super("Volunteer");

		myVol = theVol;
		mySched = theSched;
		
		startUp();
	}


	/**
	 * This makes the good stuff appear.
	 */
	private void startUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 865, 475);
		setResizable(false);
		setLayout(new BorderLayout());


		getContentPane().add(createViewJobsButton());
		getContentPane().add(createSignUpButton());
		getContentPane().add(createMyJobsButton());
		getContentPane().add(createLogOutButton());


		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

	/**
	 * Creates a button to view all available jobs.
	 */
	private JButton createViewJobsButton() {
		JButton b = new JButton("View Available Jobs");
		getContentPane().add(b);

		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				showJobs();
			}
		});

		return b;
	}


	/**
	 * This shows all of the jobs that are available to sign up for.
	 */
	private void showJobs() {
		JFrame frame2 = new JFrame("View Available Jobs");
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true);
		frame2.setSize(600,600);


		JPanel panel = new JPanel();
		frame2.setContentPane(panel);
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Jobs");
		panel.add(label, BorderLayout.NORTH);       

		JTextArea area = new JTextArea();
		textWrite(area);

		JScrollPane scrollPane = new JScrollPane(area);

		panel.add(scrollPane, BorderLayout.CENTER);

	}

	/**
	 * This adds text to an area.
	 * The text it adds is all of the jobs that are not in the past.
	 * @param theArea is a JTextArea.
	 */
	private void textWrite(JTextArea theArea) {
		theArea.setText("Here are the available jobs. \n");
		List<Job> jList = myVol.getTheJobs();
		for (Job j: jList) {

			//TODO dont let volunteer view a job that they have already signed up for.
			
			if (!j.isInPast()) {
				theArea.append("Job ID: " + j.getJobID() + "\n");
				theArea.append("     Start Date: " + j.getStartDate() + "\n");
				theArea.append("     End Date: " + j.getEndDate() + "\n");
				theArea.append("     Light Jobs : " + j.getLightCurrent() + "/" + j.getLightMax() + "\n");
				theArea.append("     Medium Jobs : " + j.getMediumCurrent() + "/" + j.getMediumMax() + "\n");
				theArea.append("     Heavy Jobs : " + j.getHeavyCurrent() + "/" + j.getHeavyMax() + "\n");
				theArea.append("     Location: " + j.getPark() + "\n");
				theArea.append("     Manager: " + j.getManager() + "\n");
				theArea.append("\n");
			}
		}
 
	}


 
	/**
	 * Creates a button to sign up for a job.
	 * @return 
	 */
	private JButton createSignUpButton() {
		JButton b = new JButton("Sign up");

		getContentPane().add(b);
		
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				joinJob();
			}
		});


		return b;
	}
	
	
	/** This method creates a new panel which will ask the user for
	 * what job they want to join.
	 */
	private void joinJob() {
		JFrame frame2 = new JFrame("Sign up for job");
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true);
		frame2.setSize(600, 400);
		
		JPanel panel = new JPanel();
		frame2.setContentPane(panel);
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("Sign up for a job by typing in the Job ID below.");
		panel.add(label, BorderLayout.NORTH);     
		
		JPanel centerPan = new JPanel();	//this panel will hold two text fields
		
		JTextField fieldID = new JTextField(); //user enters job ID here
		JTextField fieldLevel = new JTextField(); //user enters difficulty here
		centerPan.add(fieldID, BorderLayout.NORTH); 
		centerPan.add(fieldLevel, BorderLayout.SOUTH);
		
		JLabel label2 = new JLabel("What difficulty would you like to join? \n"
				+ "Enter 1 for Light \n Enter 2 for Medium \n Enter 3 for Heavy");
		centerPan.add(label2, BorderLayout.CENTER); //add label2 above the field text box
		
		JButton b = createSubmitButton(fieldID, fieldLevel); //create a button which will store info once clicked.
		panel.add(b, BorderLayout.SOUTH); //add the button to the bottom
		
		
	}
	
	/**
	 * This creates a submit button that will read from two text boxes and then
	 * figure out what to do.
	 * @param theID is the Job ID of the Job that the user wants to join.
	 * @param theLev is the difficulty level (or grade) that the user wants to do.
	 * @return a JButton.
	 */
	private JButton createSubmitButton(JTextField theID, JTextField theLev) {
		JButton b = new JButton("Submit");
		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				int ID = Integer.parseInt((theID.getText()));
				int lev = Integer.parseInt(theLev.getText());

				ArrayList<String> volArray = new ArrayList<String>();
				volArray.add(myVol.getEmail());
				switch(lev) {
				case 1:
					volArray.add("Light");
					break;
				case 2: 
					volArray.add("Medium");
					break;
				case 3:
					volArray.add("Heavy");
					break;
				default:
						volArray.add("blablabla"); //this will cause an exception to be thrown by the 
													//addVolunteerToJob method in Schedule.
				}
				
				try {
					mySched.addVolunteerToJob(volArray, ID);
				} catch (Exception e) {
					//this will create a popup box to explain what went wrong.
					JOptionPane.showMessageDialog(null, e.getMessage());
					
				}
			}
		});
		return b;
	}
	
	

	/**
	 * Creates a button to view the jobs the volunteer have signed up for.
	 * @return 
	 */
	private JButton createMyJobsButton() {
		JButton b = new JButton("See my jobs");
		getContentPane().add(b);

		b.addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent theEvent) {
				seeJobs();
			}
		});
		
		return b;
	}

	/**
	 * This method creates a new frame that shows all the available jobs.
	 */
	private void seeJobs() {
		JFrame frame2 = new JFrame("View Available Jobs");
		frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame2.setVisible(true);
		frame2.setSize(600,600);


		JPanel panel = new JPanel();
		frame2.setContentPane(panel);
		panel.setLayout(new BorderLayout());

		JLabel label = new JLabel("My Jobs");
		panel.add(label, BorderLayout.NORTH);       

		JTextArea area = new JTextArea();
		textWrite2(area);

		JScrollPane scrollPane = new JScrollPane(area);

		panel.add(scrollPane, BorderLayout.CENTER);
	}


	/**
	 * This writes the jobs that this volunteer has signed up for to a JTextArea.
	 * @param theArea is the JtextArea that needs to be filled out.
	 */
	private void textWrite2(JTextArea theArea){
		theArea.setText("Here are the jobs that you have signed up for. \n");

		List<Job> allJobs = myVol.getTheJobs();

		for (Job j: allJobs){
			ArrayList<ArrayList<String>> allVols = j.getVolunteerList();

			for (ArrayList<String> list: allVols) {
				if (list.get(0).equals(myVol.getEmail())) {
					if (!j.isInPast()) {
						theArea.append("Job ID: " + j.getJobID() + "\n");
						theArea.append("     Start Date: " + j.getStartDate() + "\n");
						theArea.append("     End Date: " + j.getEndDate() + "\n");
						theArea.append("     Light Jobs : " + j.getLightCurrent() + "/" + j.getLightMax() + "\n");
						theArea.append("     Medium Jobs : " + j.getMediumCurrent() + "/" + j.getMediumMax() + "\n");
						theArea.append("     Heavy Jobs : " + j.getHeavyCurrent() + "/" + j.getHeavyMax() + "\n");
						theArea.append("     Location: " + j.getPark() + "\n");
						theArea.append("     Manager: " + j.getManager() + "\n");
						theArea.append("\n");
					}
				}
			}
		}
	}


	/**
	 * Creates a log out button.
	 */
	private JButton createLogOutButton() {
		JButton b = new JButton("Logout");
		b.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				closeOut();
			}
		});

		return b;
	}

	private void closeOut() {
		super.dispose();
	}


	//NOTE: to merge guiMaster into guiMasterArsh: right click on guimasterArsh project,
	//then team > merge. Then click origin/guiMaster in remote tracking folder.

}
