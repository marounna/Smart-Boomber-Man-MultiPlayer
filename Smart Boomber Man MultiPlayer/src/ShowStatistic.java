import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import CONTROL.DBControl;

public class ShowStatistic extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	JLabel User_Name;
	JTextField field_User_Name;
	JButton Show;
	JButton BACK;
	MainFrame frm;
	DBControl DBC;
	
	public ShowStatistic(MainFrame frm){
		this.frm = frm;
		setLayout(new GridLayout(0,1));
		setPreferredSize(new Dimension(400,400));
		User_Name=new JLabel("Enter your Username");
		User_Name.setFont(new Font("Serif",Font.PLAIN,20));
		
		field_User_Name=new JTextField();
		field_User_Name.setFont(new Font("Times New Roman", Font.PLAIN, 27));
		field_User_Name.setPreferredSize(new Dimension(100,50));
		
		//txtName.setEnabled(false);
		
		BACK= new JButton("Back");
		BACK.setBackground(SystemColor.activeCaption);
		BACK.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		BACK.addActionListener(this);
		Show = new JButton("Show");		
		Show.setBackground(SystemColor.activeCaption);
		Show.setFont(new Font("Tempus Sans ITC", Font.PLAIN, 34));
		Show.addActionListener(this);
		
		add(User_Name);
		add(field_User_Name);	
		add(Show);
		add(BACK);
		DBC= new DBControl();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String User;
		User=this.field_User_Name.getText();
		//DBControl db=new DBControl();
		if(e.getSource().equals(Show))
			try {
				this.DBC.Show_Statistic(User);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("NOOt good ");

				e1.printStackTrace();
			}	
		
		if(e.getSource().equals(BACK)){
			frm.changePanel(new MainPanel(frm));
			}
		
	}
}
			
		
	
