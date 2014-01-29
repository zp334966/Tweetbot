package interfejsUzytkownika;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import baza.Baza;

//VS4E -- DO NOT REMOVE THIS LINE!
public class Nowy extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JLabel jLabel0;
	private JButton jButton0;
	private JTextField jTextField0;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	private Okno f;
	
	
	
	public Nowy(Okno f) {
		initComponents();
		this.f = f;
	}

	private void initComponents() {
		setTitle("Nowy tag");
		setLayout(new GroupLayout());
		add(getJLabel0(), new Constraints(new Leading(131, 12, 12), new Leading(52, 12, 12)));
		add(getJButton0(), new Constraints(new Leading(135, 12, 12), new Leading(154, 10, 10)));
		add(getJTextField0(), new Constraints(new Leading(12, 296, 12, 12), new Leading(97, 10, 10)));
		setSize(320, 240);
		this.jButton0.addActionListener(this);
		this.jTextField0.addActionListener(this);
	}

	private JTextField getJTextField0() {
		if (jTextField0 == null) {
			jTextField0 = new JTextField();
		}
		return jTextField0;
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("OK");
		}
		return jButton0;
	}

	private JLabel getJLabel0() {
		if (jLabel0 == null) {
			jLabel0 = new JLabel();
			jLabel0.setText("Wpisz tag:");
		}
		return jLabel0;
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
				Nowy frame = new Nowy(null);
				frame.setDefaultCloseOperation(Nowy.EXIT_ON_CLOSE);
				frame.setTitle("Nowy");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// TODO Auto-generated method stub
		if (source == this.jButton0) {
			if (this.jTextField0.getText() != null) {
				if (! f.tweetomat.baza.jestTag(this.jTextField0.getText())) {
					this.f.obecnyTag = this.jTextField0.getText();
					this.f.aktualizujTag();
					this.setVisible(false);
				}
			}
		}
		
	}

}
