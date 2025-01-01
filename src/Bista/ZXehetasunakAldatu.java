package Bista;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Kontroladorea.GestoreZerrenda;

public class ZXehetasunakAldatu extends JFrame {

	private static ZXehetasunakAldatu nZXA = null;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private Controller controller;
	private JCheckBox publikoa;
	private JCheckBox pribatua;
	private int ID;
	private JButton btnNewButton;
	private JLabel lblNewLabel_2;
	private String izena;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ZXehetasunakAldatu frame = new ZXehetasunakAldatu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static ZXehetasunakAldatu getnZXA(){
		if(nZXA==null) {
			nZXA=new ZXehetasunakAldatu();
		}
		return nZXA;
	}

	/**
	 * Create the frame.
	 */
	public ZXehetasunakAldatu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Sartu zerrendaren izena:");
		lblNewLabel.setBounds(30, 28, 243, 23);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(30, 52, 96, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblNewLabel_1 = new JLabel("Aukeratu zerrendaren pribazitatea:");
		lblNewLabel_1.setBounds(30, 96, 243, 23);
		contentPane.add(lblNewLabel_1);
		
		publikoa = new JCheckBox("Publikoa");
		buttonGroup.add(publikoa);
		publikoa.setBounds(30, 120, 99, 23);
		contentPane.add(publikoa);
		
		pribatua = new JCheckBox("Pribatua");
		buttonGroup.add(pribatua);
		pribatua.setBounds(30, 146, 99, 23);
		contentPane.add(pribatua);
		
		btnNewButton = new JButton("Jarraitu");
		btnNewButton.addActionListener(getCont());
		btnNewButton.setBounds(286, 204, 142, 50);
		contentPane.add(btnNewButton);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(30, 219, 210, 20);
		contentPane.add(lblNewLabel_2);
	}
	
	public void setID(int ID) {
		this.ID = ID;
	}
	
	public Controller getCont() {
		if (controller == null) {
			controller = new Controller();
		}
		return controller;
	}
	
	public class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnNewButton)) {
				if (textField.getText().equals("") || (!publikoa.isSelected() && !pribatua.isSelected())) {
					lblNewLabel_2.setText("Ez dira eremu guztiak bete");
				} else {
					Boolean pribazitatea = null;
					izena = textField.getText();
					if (publikoa.isSelected()) {
						pribazitatea = true;
					} else if (pribatua.isSelected()) {
						pribazitatea = false;
					}
					GestoreZerrenda GZ = GestoreZerrenda.getnZZ();
					GZ.aldatuXehetasunak(ID, izena, pribazitatea);
					ZerrendaXehetasunak ZX = ZerrendaXehetasunak.getnZX();
					ZX.setID(ID);
					ZX.setVisible(true);
					dispose();
				}
				
			}
		}
	}
}
