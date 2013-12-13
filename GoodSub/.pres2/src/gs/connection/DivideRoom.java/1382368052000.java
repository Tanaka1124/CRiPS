package gs.connection;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DivideRoom implements ActionListener {
	public static void main(String[] args) {
		new DivideRoom().makeFrame();
	}

	private static List<Integer> rooms = new ArrayList<Integer>();
	private JFrame frame = new JFrame();
	private JDialog dialog = new JDialog();
	private JTextField tf = new JTextField(10);

	public int selectRoomNum() throws IOException {
		System.out.print("�����ԍ� : ");
		BufferedReader input = new BufferedReader(new InputStreamReader(
				System.in));
		String str = input.readLine();
		int roomNum = Integer.parseInt(str);
		return roomNum;
	}

	public boolean checkRoomNum(int roomNum) {
		for (int number : rooms) {
			if (roomNum == number) {
				// ���������݂�����true
				return true;
			}
		}
		// �����ԍ����Ȃ������烊�X�g�ɉ�����
		rooms.add(roomNum);
		// ���݂��Ȃ�������false
		return false;
	}

	// ���������X�g�̉��Ԗڂɂ��邩�𒲂ׂ�
	public int countRoom(int roomNum) {
		int i = 0;
		for (int number : rooms) {
			if (roomNum == number) {
				return i;
			}
			i++;
		}
		return -1;
	}

	public void makeFrame() {
		frame.setTitle("CheCoPro -�W�-");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 300, 500);
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		JButton plus = new JButton("+");
		plus.setMaximumSize(new Dimension(300, 32));
		plus.addActionListener(this);
		plus.setActionCommand("plus");
		frame.add(plus);
		frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String pushed = e.getActionCommand();
		if (pushed.equals("plus")) {
			makeDialog();
		} else if (pushed.equals("cancel")) {
			dialog.dispose();
		} else if (pushed.equals("OK")) {
			String gn = tf.getText();
			System.out.println("group name is " + gn);
			dialog.dispose();
			makeRoomButton(gn);
		} else {
			System.out.println("log in " + pushed);
		}
	}

	public void makeDialog() {
		JLabel label = new JLabel("�O���[�v��");
		JButton okbtn = new JButton("OK");
		JButton canbtn = new JButton("�L�����Z��");
		JPanel gnPanel = new JPanel();
		JPanel btnPanel = new JPanel();

		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setTitle("�V�K�W��̍쐬");
		dialog.setBounds(150, 150, 200, 150);
		gnPanel.add(label);
		gnPanel.add(tf);
		btnPanel.add(okbtn);
		btnPanel.add(canbtn);
		dialog.add(gnPanel, BorderLayout.CENTER);
		dialog.add(btnPanel, BorderLayout.PAGE_END);

		okbtn.addActionListener(this);
		okbtn.setActionCommand("OK");
		canbtn.addActionListener(this);
		canbtn.setActionCommand("cancel");

		dialog.setModal(true);
		dialog.setVisible(true);
	}

	public void makeRoomButton(String gn) {
		JButton btn = new JButton(gn);

		btn.setMaximumSize(new Dimension(300, 32));
		btn.addActionListener(this);
		btn.setActionCommand("plus");
		frame.add(btn);
		frame.setVisible(true);
	}

}