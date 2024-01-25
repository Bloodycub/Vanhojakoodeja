package Gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.osbot.rs07.script.Script;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import java.awt.Button;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import Core.Skeleton;

import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;

public class gui extends Script {

	@Override
	public void onStart() throws InterruptedException {
		JFrame frame = new gui();
	}
	private JPanel contentPane;
	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui frame = new gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public gui() {
		setAlwaysOnTop(true);
		setAutoRequestFocus(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 221, 274);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 128, 0));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);

		
		
		
		Button Combat = new Button("Combat");
		Combat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		toolBar.add(Combat);
		
		Button button_3 = new Button("Skills");
		toolBar.add(button_3);
		
		Button button_5 = new Button("Quests");
		toolBar.add(button_5);
		
		Button button_4 = new Button("Inventory");
		toolBar.add(button_4);
		
		Button button_1 = new Button("New button");
		toolBar.add(button_1);
		
		Button button_2 = new Button("Worn items");
		toolBar.add(button_2);
		
		Button button = new Button("Bank");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(button);
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("");
		lblNewJgoodiesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesLabel.setIcon(new ImageIcon(gui.class.getResource("/Images/BobModel.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addGap(5)
					.addComponent(lblNewJgoodiesLabel))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(1)
					.addComponent(lblNewJgoodiesLabel))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
