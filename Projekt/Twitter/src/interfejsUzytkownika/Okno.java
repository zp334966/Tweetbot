package interfejsUzytkownika;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.Style;

import obslugaTwittera.Tweetomat;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;


//VS4E -- DO NOT REMOVE THIS LINE!
public class Okno extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JMenuItem jMenuItem0;
	private JMenuItem jMenuItem1;
	private JMenu jMenu0;
	private JMenuBar jMenuBar0;
	private JLabel jLabel0;
	private JToggleButton jToggleButton0;
	private JLabel jLabel1;
	private JComboBox jComboBox0;
	private JButton jButton0;
	private JLabel jLabel2;
	private JTextField textMin;
	private JTextField textGodz;
	private JLabel labelCzas;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	public Tweetomat tweetomat = new Tweetomat();
	public String obecnyTag = "";
	private short skutecznoscTagu = 0;
	private boolean start = false;
	private Nowy nowy = new Nowy(this);
	private Poprzedni poprzedni  = new Poprzedni(this);
	
	
	public Okno() {
		initComponents();
	}

	private void initComponents() {
		setTitle("Tweetomat");
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(44, 12, 12), new Leading(51, 12, 12)));
		add(getJToggleButton0(), new Constraints(new Leading(258, 78, 10, 10), new Leading(46, 12, 12)));
		add(getJLabel1(), new Constraints(new Leading(44, 12, 12), new Leading(108, 10, 10)));
		add(getJComboBox0(), new Constraints(new Leading(200, 350, 12, 12), new Leading(104, 50, 50)));
		add(getJButton0(), new Constraints(new Leading(261, 77, 10, 10), new Leading(155, 10, 10)));
		add(getJLabel2(), new Constraints(new Leading(208, 12, 12), new Leading(160, 12, 12)));
		add(getTextMin(), new Constraints(new Leading(450, 40, 22, 22), new Leading(48, 15, 15)));
		add(getTextGodz(), new Constraints(new Leading(400, 40, 22, 22), new Leading(48, 15, 15)));
		add(getLabelCzas(), new Constraints(new Leading(350, 200, 22, 22), new Leading(30, 15, 15)));
		setJMenuBar(getJMenuBar0());
		setSize(600, 240);
		this.jButton0.addActionListener(this);
		this.jMenuItem0.addActionListener(this);
		this.jMenuItem1.addActionListener(this);
		this.jToggleButton0.addActionListener(this);
		this.jComboBox0.addActionListener(this);
	}

	private Component getLabelCzas() {
		if (labelCzas == null) {
			labelCzas = new JLabel();
			labelCzas.setText("Minimalny czas dzialania:");
		}
		return labelCzas;
	}

	private JTextField getTextGodz() {
		if (textGodz == null) {
			textGodz = new JTextField();
			textGodz.setText("godz");
		}
		return textGodz;
	}

	private JTextField getTextMin() {
		if (textMin == null) {
			textMin = new JTextField();
			textMin.setText("min");
			textMin.setVisible(true);
		}
		return textMin;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setText(Short.toString(this.skutecznoscTagu));
		}
		return jLabel2;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("Oblicz");
		}
		return jButton0;
	}
	

	private JComboBox getJComboBox0() {
		if (jComboBox0 == null) {
			jComboBox0 = new JComboBox();
		} 
		jComboBox0.setModel(new DefaultComboBoxModel(this.tweetomat.baza.przekazTagi()));
		//jComboBox0.setDoubleBuffered(false);
		//jComboBox0.setBorder(null);
		jComboBox0.repaint();
		jComboBox0.revalidate();
		return jComboBox0;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setText("Skutecznosc tagu");
		}
		return jLabel1;
	}

	private JToggleButton getJToggleButton0() {
		if (jToggleButton0 == null) {
			jToggleButton0 = new JToggleButton();
			jToggleButton0.setText("Start");
		}
		return jToggleButton0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Obecny tag: " + this.obecnyTag);
		}
		return jLabel0;
	}

	private JMenuBar getJMenuBar0() {
		if (jMenuBar0 == null) {
			jMenuBar0 = new JMenuBar();
			jMenuBar0.add(getJMenu0());
		}
		return jMenuBar0;
	}

	private JMenu getJMenu0() {
		if (jMenu0 == null) {
			jMenu0 = new JMenu();
			jMenu0.setText("Uruchom");
			jMenu0.add(getJMenuItem0());
			jMenu0.add(getJMenuItem1());
		}
		return jMenu0;
	}

	private JMenuItem getJMenuItem1() {
		if (jMenuItem1 == null) {
			jMenuItem1 = new JMenuItem();
			jMenuItem1.setText("Poprzedni");
		}
		return jMenuItem1;
	}

	private JMenuItem getJMenuItem0() {
		if (jMenuItem0 == null) {
			jMenuItem0 = new JMenuItem();
			jMenuItem0.setText("Nowy");
		}
		return jMenuItem0;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class.
	 * Note: This class is only created so that you can easily preview the result at runtime.
	 * It is not expected to be managed by the designer.
	 * You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Okno frame = new Okno();
				frame.setDefaultCloseOperation(Okno.EXIT_ON_CLOSE);
				frame.setTitle("Tweetomat");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				frame.nowy.getContentPane().setPreferredSize(frame.nowy.getSize());
				frame.nowy.pack();
				frame.poprzedni.getContentPane().setPreferredSize(frame.poprzedni.getSize());
				frame.poprzedni.pack();
			}
		});
	}
	
	public void aktualizujTag() {
		this.jLabel0.setText("Obecny tag: " + this.obecnyTag);
		this.jLabel0.revalidate();
		this.jLabel0.repaint();
	}
	
	public void nowy() {
		this.nowy.setVisible(true);
		this.getJComboBox0();
	}
	
	
	public void poprzedni() {
		this.poprzedni.zaladujTagi();
		this.poprzedni.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == this.jMenuItem0) {//nowe
			nowy();
		}
		if (source == this.jMenuItem1) {//poprzedni
			poprzedni();
		}
		if (source == this.jToggleButton0) {//start
			start();
		}
		if (source == this.jButton0) {//oblicz
			oblicz();
		}
		if (source == this.jComboBox0) {
			
			this.jComboBox0.repaint();
			this.jComboBox0.revalidate();
		}
	}


	private void oblicz() {
		if (this.jComboBox0.getSelectedItem() != null) {
			this.skutecznoscTagu = this.tweetomat.baza.policzSkutecznoscTagu((String) this.jComboBox0.getSelectedItem());
			this.jLabel2.setText(Short.toString(skutecznoscTagu));
			this.jLabel2.revalidate();
			this.jLabel2.repaint();
		}
		
	}

	private void start() {
		if (this.obecnyTag != "") {
			Calendar poczatkowyCzas = Calendar.getInstance();
			poczatkowyCzas.setTimeInMillis(System.currentTimeMillis());
			Calendar koncowyCzas = Calendar.getInstance();
			koncowyCzas.setTimeInMillis(System.currentTimeMillis());
			this.setVisible(false);
			try {
				koncowyCzas.add(Calendar.HOUR, Integer.parseInt(this.textGodz.getText()));
				koncowyCzas.add(Calendar.MINUTE, Integer.parseInt(this.textMin.getText()));
				while (koncowyCzas.after(poczatkowyCzas)) {
					this.tweetomat.wyszukaj(this.obecnyTag);
					this.tweetomat.aktualizujOsobowosci(this.obecnyTag);
					this.tweetomat.usunNieskutecznych(this.obecnyTag);
					this.tweetomat.szukajPobocznych(this.obecnyTag);
					this.tweetomat.aktualizujOsobowosci(this.obecnyTag);
					this.tweetomat.usunNieskutecznych(this.obecnyTag);
					this.tweetomat.retweetuj(obecnyTag);
					this.repaint();
					this.validate();
					poczatkowyCzas.setTimeInMillis(System.currentTimeMillis());
				}
				this.getJComboBox0();
				this.jToggleButton0.setSelected(false);
				this.jToggleButton0.revalidate();
			
			} catch (NumberFormatException e) {
				System.out.println("Niepoprawna liczba godzin lub minut");
			}
		} else {
			System.out.println("Wprowadz tag");
		}
		this.setVisible(true);
	}


}
