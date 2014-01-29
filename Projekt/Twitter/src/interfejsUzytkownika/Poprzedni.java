package interfejsUzytkownika;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

//VS4E -- DO NOT REMOVE THIS LINE!
public class Poprzedni extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JList jList0;
	private JScrollPane jScrollPane0;
	private JButton jButton0;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	private Okno f;

	
	public Poprzedni(Okno f) {
		initComponents();
		this.f = f;
		this.jButton0.addActionListener(this);
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getJScrollPane0(), new Constraints(new Leading(107, 100, 10, 10), new Leading(30, 135, 10, 10)));
		add(getJButton0(), new Constraints(new Leading(132, 12, 12), new Leading(190, 10, 10)));
		setSize(320, 240);
	}

	private JButton getJButton0() {
		if (jButton0 == null) {
			jButton0 = new JButton();
			jButton0.setText("OK");
		}
		return jButton0;
	}

	private JScrollPane getJScrollPane0() {
		if (jScrollPane0 == null) {
			jScrollPane0 = new JScrollPane();
			jScrollPane0.setViewportView(getJList0());
		}
		return jScrollPane0;
	}

	private JList getJList0() {
		if (jList0 == null) {
			jList0 = new JList();
			
		}
			
		
		return jList0;
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
				Poprzedni frame = new Poprzedni(null);
				frame.setDefaultCloseOperation(Poprzedni.EXIT_ON_CLOSE);
				frame.setTitle("Poprzedni");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
	
	public void zaladujTagi() {
		DefaultListModel m = new DefaultListModel();
		Vector<String> tagi = this.f.tweetomat.baza.przekazTagi();
		for (int i = 0; i < tagi.size(); ++i) {
			m.addElement(tagi.elementAt(i));
		}
		this.jList0.setModel(m);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
		if (source == this.jButton0) {
			if (this.jList0.getSelectedValue() != null) {
				this.f.obecnyTag = (String)this.jList0.getSelectedValue();
				this.f.aktualizujTag();
				this.setVisible(false);
			}
		}
		
	}

}
