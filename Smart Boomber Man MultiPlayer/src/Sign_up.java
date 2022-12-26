
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import CONTROL.DBControl;
public class Sign_up extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	JLabel User_Name;
	JTextField field_User_Name;
	JLabel First_Name;
	JTextField field_First_Name;
	JLabel Last_Name;
	JTextField field_Last_Name;
	JLabel password;
	JPasswordField field_password;
	JButton BUTTON_Sign_up;
	MainFrame frm;
	JButton BACK;
	public Sign_up(MainFrame frm){
		this.frm = frm;
		setLayout(new GridLayout(0,1));
		setPreferredSize(new Dimension(250,300));
		User_Name=new JLabel("Enter your Username");
		User_Name.setFont(new Font("Serif",Font.PLAIN,20));
		password=new JLabel("Enter a new Password");
		password.setFont(new Font("Serif",Font.PLAIN,20));
		field_User_Name=new JTextField();
		field_User_Name.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		field_User_Name.setPreferredSize(new Dimension(100,50));
		field_password=new JPasswordField();
		field_password.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		field_password.setPreferredSize(new Dimension(100,50));
		
		First_Name=new JLabel("Enter your First Name");
		First_Name.setFont(new Font("Serif",Font.PLAIN,20));
		Last_Name=new JLabel("Enter your Last Name");
		Last_Name.setFont(new Font("Serif",Font.PLAIN,20));
		BACK= new JButton("Back");
		BACK.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		BACK.setBackground(SystemColor.activeCaption);
		BACK.addActionListener(this);
		
		
		field_First_Name=new JTextField();
		field_First_Name.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		field_First_Name.setPreferredSize(new Dimension(100,50));
		field_Last_Name=new JTextField();
		field_Last_Name.setFont(new Font("Times New Roman", Font.PLAIN, 30));
		field_Last_Name.setPreferredSize(new Dimension(100,50));
		
		
		
		
		BUTTON_Sign_up = new JButton("Sign up");
		BUTTON_Sign_up.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		BUTTON_Sign_up.setBackground(SystemColor.activeCaption);
		BUTTON_Sign_up.addActionListener(this);
		
		add(User_Name);
		add(field_User_Name);
		add(First_Name);
		add(field_First_Name);
		add(Last_Name);
		add(field_Last_Name);
		add(password);
		add(field_password);
		add(BUTTON_Sign_up);
		add(BACK);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(BUTTON_Sign_up)){
		DBControl db=new DBControl();
		try{
		db.insert_Players(field_User_Name.getText(),field_First_Name.getText(),field_Last_Name.getText(),field_password.getText());
		}
		catch (ClassNotFoundException ex) {System.out.println(ex.getMessage());}
	    catch (IllegalAccessException ex) {System.out.println(ex.getMessage());}
	    catch (InstantiationException ex) {System.out.println(ex.getMessage());}
	    catch (SQLException ex)           {System.out.println(ex.getMessage());}
		
		frm.changePanel(new Login(frm));
		}
		if(e.getSource().equals(BACK)){
			frm.changePanel(new MainPanel(frm));
			}
	}
	
}
