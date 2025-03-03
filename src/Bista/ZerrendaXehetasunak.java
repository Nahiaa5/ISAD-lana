package Bista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Kontroladorea.GestoreZerrenda;

import java.awt.Font;

public class ZerrendaXehetasunak extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private Controller controller;
	private JButton aldatu;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_3;
	private int Id;
	private JButton btnBueltatu;
	
	/**
	 * Create the frame.
	 */
	public ZerrendaXehetasunak(int ID) {
		GestoreZerrenda GZ = GestoreZerrenda.getnZZ();
		String izena = GZ.bilatuZerrenda(ID).getIzena();
		String pribazitatea;
		if (GZ.bilatuZerrenda(ID).getPribazitatea()==true) {
			pribazitatea = "Publikoa";
		} else {
			pribazitatea = "Pribatua";
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel = new JLabel("Zerrendaren izena:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(30, 28, 243, 23);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("Zerrendaren pribazitatea:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(30, 96, 243, 23);
		contentPane.add(lblNewLabel_1);
		
		aldatu = new JButton("Aldatu");
		aldatu.addActionListener(getCont());
		aldatu.setBounds(30, 225, 82, 29);
		contentPane.add(aldatu);
		
		lblNewLabel_3 = new JLabel(izena);
		lblNewLabel_3.setBounds(30, 49, 398, 23);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel(pribazitatea);
		lblNewLabel_4.setBounds(30, 118, 398, 23);
		contentPane.add(lblNewLabel_4);
		
		btnBueltatu = new JButton("Bueltatu");
		btnBueltatu.addActionListener(getCont());
		btnBueltatu.setBounds(122, 225, 82, 29);
		contentPane.add(btnBueltatu);
		
		this.Id = ID;
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
			if (e.getSource().equals(aldatu)) {
				ZXehetasunakAldatu x = new ZXehetasunakAldatu(Id);
				x.setVisible(true);
				setVisible(false);
			} else if (e.getSource().equals(btnBueltatu)) {
				ZerrendaPertsonalizatuaB ZPB = new ZerrendaPertsonalizatuaB(Id);
				ZPB.setVisible(true);
				setVisible(false);
			}
		}
	}
}
