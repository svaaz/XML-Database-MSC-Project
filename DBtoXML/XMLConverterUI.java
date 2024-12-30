/*
 * Created on Mar 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author srini
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;



public class XMLConverterUI extends JFrame implements ActionListener
{
	
	
	


		JLabel l1, l2, l3, l4, empty, l5, l,text;

		JButton b1, b2, b3, b4;

		JTextField t1, t3;

		JPasswordField t2;

		String namepassword;

		static String dele = "";

		static String ss = "";

		static String ss1 = "";

		static String ss2 = "";

		static String ss3 = "";

		static boolean visible = false;

		JComboBox folderlist;

		InetAddress inet;

		JComboBox serverList, syncList;

		XMLGenerator gen=new XMLGenerator();
		

				XMLConverterUI() 
				{
			setTitle("Database to XML Convertor");
			
			String folders[] = new String[20];
			addWindowListener(
			          new WindowAdapter() {
			            public void windowClosing(WindowEvent e) {System.exit(0);}});

			JPanel obj = new JPanel();
			obj.setBounds(0, 0, 500, 114);
			obj.setLayout(null);
			Container content = getContentPane();
			content.setLayout(new BorderLayout());
			String[] ServerIPs = new String[10];
			String[] Tables = { "orders", "products",
					"customers", "categories","employees" ,
					"shippers","suppliers"
					};
			int i = 0;
		
	
			//folders
			
		
			folderlist = new JComboBox(Tables);
			folderlist.setSelectedIndex(0);
			folderlist.addActionListener(this);
			Container contentPane = this.getContentPane();
			contentPane.applyComponentOrientation(ComponentOrientation
					.getOrientation(contentPane.getLocale()));

			//Create the combo box, select the item at index 4.
			//Indices start at 0, so 4 specifies the .
			serverList = new JComboBox(ServerIPs);
			//serverList.setSelectedIndex(0);
			serverList.addActionListener(this);
			//Create the combo box, select the item at index 4.
			//Indices start at 0, so 4 specifies the .
			syncList = new JComboBox(Tables);
			//syncList.setSelectedIndex(0);
			syncList.addActionListener(this);

			l3 = new JLabel("               Choose Table Name :              ");
			
			//dont delete
			empty = new JLabel(
					"                                                                                                ");
			l5 = new JLabel("Choose Account           ");
			ImageIcon i1 = new ImageIcon("Images//auto.jpg");
			b1 = new JButton("Convert to XML", i1);
			ImageIcon i2 = new ImageIcon("Images//close.png");
			b2 = new JButton("Cancel", i2);
			ImageIcon i3 = new ImageIcon("Images//options.png");
			b3 = new JButton("Options>>", i3);
			t1 = new JTextField(20);
			t2 = new JPasswordField(null, 20);
			t3 = new JTextField(20);
			JPanel panel = new JPanel();
			text = new JLabel("**** This Demo uses Northwind Database ****");
			
			
			panel.add(l3);
			panel.add(syncList);
			panel.add(l5);
			panel.add(folderlist);
			panel.add(empty);
			panel.add(b1);
			panel.add(b2);
			panel.add(b3);
			panel.add(text);
			content.add(panel);
			
			l5.setVisible(false);
			syncList.setVisible(false);
			folderlist.setVisible(true);
			setSize(388, 190);
			
			b1.addActionListener(this);
			b3.addActionListener(this);
			b2.addActionListener(this);
			setLocation(330, 135);
			setVisible(true);
			t2.setOpaque(true);
		}
		
		public void actionPerformed(ActionEvent ae)
		{
			//repaint();
			
			Object o = ae.getSource();
			b1.requestFocus();
			if (o == b1)
			{
				try
				{
				gen.Convert(folderlist.getSelectedItem().toString());	
				System.out.println(folderlist.getSelectedItem().toString());
			}
				catch (Exception w){}
				
				if (o == b2)
				{
					System.exit(0);
					setVisible(false);
					dispose();
				}
			}

			
			
		}//actionPerformed()

		public static void main(String args[]) {
			XMLConverterUI authenobj = new XMLConverterUI();
			//System.out.println("Login ok");
		}
	}

