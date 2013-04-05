import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**
* GUI prototyyppiluokka (l‰hinn‰ layoutin testaukseen)
**/
class BiblexGUI implements ActionListener {
	private JFrame p_window;
	
	private JPanel p_pane;
	private JScrollPane p_scrollPane;
	private JMenuBar p_menu;
	
	private JTextField p_fieldNameInput;
	
	
	public BiblexGUI() {
		p_window = new JFrame("Biblex");
		
		p_window.setSize(550, 350);
		p_window.setMinimumSize(new Dimension(450, 300));
		p_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		populate();
		
		p_window.setVisible(true);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand() == "AddField") {
			addField(p_fieldNameInput.getText());
			p_fieldNameInput.setText("");
		}
	}
	
	
	private void populate() {
		p_menu = new JMenuBar();
		p_window.setJMenuBar(p_menu);
		
		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
		topPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		topPane.add(new JLabel("Viitteen tyyppi:"));
		topPane.add(Box.createRigidArea(new Dimension(5, 0)));
		topPane.add(new JComboBox<String>());
		
		JPanel bottomPane = new JPanel();
		p_fieldNameInput = new JTextField();
		JButton addFieldButton = new JButton("Lis‰‰ Kentt‰");
		addFieldButton.setActionCommand("AddField");
		addFieldButton.addActionListener(this);
		
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.X_AXIS));
		bottomPane.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(p_fieldNameInput);
		bottomPane.add(Box.createRigidArea(new Dimension(3, 0)));
		bottomPane.add(addFieldButton);
		
		p_pane = new JPanel();
		p_pane.setLayout(new BoxLayout(p_pane, BoxLayout.Y_AXIS));
		
		p_scrollPane = new JScrollPane(p_pane);
		p_scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		p_scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 0, 3));
		
		p_window.getContentPane().add(topPane, BorderLayout.NORTH);
		p_window.getContentPane().add(p_scrollPane, BorderLayout.CENTER);
		p_window.getContentPane().add(bottomPane, BorderLayout.SOUTH);
	}
	
	
	private void addField(String name) {
		if (name.trim().isEmpty())
			return;
		
		JPanel pane = new JPanel();
		pane.setLayout(new BoxLayout(pane, BoxLayout.X_AXIS));
		
		JLabel nameLabel = new JLabel(name.trim().toLowerCase());
		JTextField nameField = new JTextField();
		
		nameLabel.setMinimumSize(new Dimension(150, 20));
		nameLabel.setPreferredSize(new Dimension(150, 20));
		nameLabel.setMaximumSize(new Dimension(225, 20));
		nameField.setMinimumSize(new Dimension(225, 20));
		nameField.setPreferredSize(new Dimension(225, 20));
		nameField.setMaximumSize(new Dimension(Short.MAX_VALUE, 20));
		
		pane.add(nameLabel);
		pane.add(Box.createRigidArea(new Dimension(10, 0)));
		pane.add(nameField);
		
		p_pane.add(pane);
		p_pane.validate();
		p_scrollPane.validate();
	}
	
	
	/**
	* Main metodi GUI:n testausta varten
	*/
	public static void main(String args[]) {
		BiblexGUI bbg = new BiblexGUI();
	}
}