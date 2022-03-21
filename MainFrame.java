import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfFirstName;
	private JTextField tfLastName;
	private JTextField tfEmail;
	private JTextField tfPhone;
	private JLabel lblFNameH;
	private JLabel lblLNameH;
	private JLabel lblEmailPhoneH;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public MainFrame() throws IOException {
		
		String url = "https://o3m5qixdng.execute-api.us-east-1.amazonaws.com/api/m\r\n" + 
				"anagers";
		URL obj = new URL (url);
		
		
		try {
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Supervisor", "Mozilla/5/0");
			int responseCode = con.getResponseCode();
			System.out.println("Sending GET request to url: " + url 
					+ " and Responce Code is " + responseCode);
			
			BufferedReader in = new BufferedReader(new FileReader(url));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while((inputLine = in.readLine()) != null)
			{
				response.append(inputLine);
			}
			in.close();
			String supList = response.toString();
			
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		//Read JSON response and 
		//System.out.println(response.body);
		
		setTitle("Notification Form");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name");
		lblFirstName.setBounds(24, 11, 114, 25);
		contentPane.add(lblFirstName);
		
		tfFirstName = new JTextField();
		tfFirstName.setBounds(24, 32, 151, 20);
		contentPane.add(tfFirstName);
		tfFirstName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Last Name");
		lblNewLabel.setBounds(238, 11, 114, 25);
		contentPane.add(lblNewLabel);
		
		tfLastName = new JTextField();
		tfLastName.setBounds(238, 32, 165, 20);
		contentPane.add(tfLastName);
		tfLastName.setColumns(10);
		
		JLabel lblHow = new JLabel("How would you prefer to be notified?");
		lblHow.setBounds(24, 80, 182, 20);
		contentPane.add(lblHow);
		
		JCheckBox chckbxEmail = new JCheckBox("Email");
		chckbxEmail.setBounds(24, 100, 97, 23);
		contentPane.add(chckbxEmail);
		
		JCheckBox chckbxPhoneNumber = new JCheckBox("Phone Number");
		chckbxPhoneNumber.setBounds(238, 100, 97, 23);
		contentPane.add(chckbxPhoneNumber);
		
		tfEmail = new JTextField();
		tfEmail.setBounds(24, 125, 151, 20);
		contentPane.add(tfEmail);
		tfEmail.setColumns(10);
		
		tfPhone = new JTextField();
		tfPhone.setBounds(238, 125, 165, 20);
		contentPane.add(tfPhone);
		tfPhone.setColumns(10);
		
		JLabel lblSupervisor = new JLabel("Supervisor");
		lblSupervisor.setBounds(115, 178, 91, 20);
		contentPane.add(lblSupervisor);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(134, 198, 139, 20);
		contentPane.add(comboBox);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fName = tfFirstName.getText();
				String lName = tfLastName.getText();
				
				if(fName.length() == 0) {
					lblFNameH.setText("First Name can not be blank");
				} else if (fName.matches(".*[0-9].*")) {
					lblFNameH.setText("First Name includes numbers");
				} else {
					lblFNameH.setText("");
				}
				if(lName.length() == 0) {
					lblLNameH.setText("Last Name can not be blank");
				} else if(lName.matches(".*[0-9].*")) {
					lblLNameH.setText("Last Name includes numbers");
				} else {
					lblLNameH.setText("");
				}
				
				String email = tfEmail.getText();
				String phone = tfPhone.getText();
				
				if(!chckbxEmail.isSelected() && !chckbxPhoneNumber.isSelected()) {
					lblEmailPhoneH.setText("Either Email or Phone Number must be check marked");
					 
				} else {
					if(chckbxEmail.isSelected() && chckbxPhoneNumber.isSelected()) {
						if(email.length() == 0 && phone.length() == 0) {
							lblEmailPhoneH.setText("Email and Phone Number is required");
						} else if(email.length() == 0 && phone.length() != 0) {
							
							if(!isNumber(phone) || phone.length() != 10) {
								lblEmailPhoneH.setText("Email is required and Phone Number not valid");
							} else {
								lblEmailPhoneH.setText("Email is required");
							}
						} else if(email.length() != 0 && phone.length() == 0) {
							
							
							if(!isValidEmailAddress(email)) {
								lblEmailPhoneH.setText("Email is not valid and Phone Number is required");
							} else {
								lblEmailPhoneH.setText("Phone Number is required");
							}
							
						} else {
							lblEmailPhoneH.setText("");
						}
					} else {
						if(chckbxEmail.isSelected()) {
							if(email.length() == 0) {
								lblEmailPhoneH.setText("Email is required");
							} else {
								if(!isValidEmailAddress(email)) {
									lblEmailPhoneH.setText("Email is not valid");
								} else {
									lblEmailPhoneH.setText("");
								}
							}
						}
						if(chckbxPhoneNumber.isSelected()) {
							if(phone.length() == 0) {
								lblEmailPhoneH.setText("Phone Number is required");
							} else {
								if(!isNumber(phone) || phone.length() != 10) {
									lblEmailPhoneH.setText("Phone Number not valid");
								} else {
									lblEmailPhoneH.setText("");
								}
							}
						}
					}
				}
			}
		});
		btnSubmit.setBounds(173, 229, 89, 23);
		contentPane.add(btnSubmit);
		
		lblFNameH = new JLabel("");
		lblFNameH.setBounds(24, 55, 182, 20);
		contentPane.add(lblFNameH);
		
		lblLNameH = new JLabel("");
		lblLNameH.setBounds(238, 55, 182, 20);
		contentPane.add(lblLNameH);
		
		lblEmailPhoneH = new JLabel("");
		lblEmailPhoneH.setBounds(24, 150, 379, 22);
		contentPane.add(lblEmailPhoneH);
	}
	
	private boolean isNumber( String s ) {
	    try {
	        Integer.parseInt( s );
	    } catch ( Exception e ) {
	        return false; // if it is not a number it throws an exception
	    }
	    return true;
	}
	
	public static boolean isNumeric(String str) {
		try {
			// double d = Double.parseDouble(str);
			double d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	
}
