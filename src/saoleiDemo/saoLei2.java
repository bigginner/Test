package saoleiDemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class saoLei2 extends JFrame {
	Timego timego;
	private static Font font = new Font("����", 1, 20);
	private static Color clrObverse = Color.BLACK; // ��ʼʱ����Ϸ��ť��ɫ
	private int rows = 9; // ��ʾ����
	private int cols = 9; // ��ʾ����
	private JLabel lblPlan;
	private JLabel lblTime;
	Bomb btnGame[][]; // ����һ��JButton����洢���鰴ť
	private GamePanel pnlgame; // ��Ϸ���
	private int rightBomb; // �ҵ��ĵ�����
	private int restBomb; // ʣ�������
	private int restBlock; // ʣ�෽����
	boolean isFirstClick = true; // �Ƿ��һ�ε��
	private int currenttime;
	// ��̬�����--ʵ�ִ��ڹرչ���
	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
	}

	// �������캯��,����
	public saoLei2() {

		pnlgame = new GamePanel();

		setTitle("ɨ����Ϸ");
		setJMenuBar(new MainMenuBar());

		Container localContainer = getContentPane();
		localContainer.setLayout(new BorderLayout());
		localContainer.add(new NorthPanel(), "North");
		localContainer.add(pnlgame, "Center");

		setDefaultCloseOperation(3); // һ��ָʾ���ڹرղ���������
		setSize(rows * 42 + 10, cols * 30 + 100);

		setLocationRelativeTo(this);
		setVisible(true);
	}

	private final class MainMenuBar extends JMenuBar {
		// �˵���
		public MainMenuBar() {
			JMenu localJMenu1 = new JMenu("��Ϸ(S)");
			JMenu localJMenu3 = new JMenu("����(H)");
			JMenu localJMenu4 = new JMenu("�Ѷ�(L)");

			JMenuItem localJMenuItem1 = new JMenuItem("��ʼ");
			JMenuItem localJMenuItem2 = new JMenuItem("�˳�");
			JMenuItem localJMenuItem5 = new JMenuItem("������Ϸ");

			ButtonGroup localButtonGroup = new ButtonGroup();

			localJMenuItem1.setActionCommand("Start");
			localJMenuItem2.setActionCommand("exit");
			localJMenuItem5.setActionCommand("Help");

			localJMenuItem1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "�¿�һ����", "����Ϸ",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.initialize();

					lblPlan.setText("ʣ��ը������0/" + rows * cols / 5);

				}

			});
			localJMenuItem2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);

				}

			});

			localJMenuItem5.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

					JDialog jd = new JDialog();
					jd.setTitle("��Ϸ����");
					jd.setSize(505, 400);
					JTextArea jt = new JTextArea();
					jt.setBackground(Color.yellow);
					jt.setEditable(false);
					jt.setText("�����ߣ�Name.��\n�������ߣ�eclipse\n�����汾��5.0\n\n            ��Ϸ�������£� ��"
							+ "�������к���Ϸ��ʼ������ʱ����������������񣬵㵽����\n ������ʾ���ס�������Ϸʧ�ܣ���Ϊ���֣����ʾ���ڵĶ���"
							+ "�ϡ�����������������������\n �������Ϸ���ĸ����к��и����ָ����ף���Ϊ�հ׸��򷭿���Χ�����������ָ����Ŀ�\n "
							+ "�׸�ֱ�������ָ����赲��������������Ҽ��������һ����������ȷ�ϸõ�Ԫ��һ��\n ���ף��ڶ��ε��������ȷ����"
							+ "�����ף������λָ���ʼ״̬��\n          ��Ϸ���е����������׼���������ʱ��\n          ��Ϸ�Ѷȵȼ�����������"
							+ "��ţ�ˡ����񡢴�˵\n          "
							+ "�ж�ʤ��������ȥ���ס�����������ȫ�������������ҵ���Ҽ�һ�Σ��ҹ���Ϊ�Լ�\n ȷ�ϵ��ף���������ȷ�ģ�����Ϸʤ��"
							+ "����;���벻�����ֱ������һ�̣���ѡ���˳�����\n ����Ϸ��\n          "
							+ "��Ϸ���ɣ����������������ʾ������0-8������һ�����֣��������Ե������������\n ����������������񣩣���Χ�ŵ�8��"
							+ "�����и����ָ����ס���������");
					jd.add(jt);
					jd.setVisible(true);
					jd.setLocationRelativeTo(null);
				}

			});

			JRadioButtonMenuItem localJRadioButtonMenuItem = new JRadioButtonMenuItem(
					String.valueOf("����"));
			localJRadioButtonMenuItem.setActionCommand("level");
			localJRadioButtonMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "������񼶱����ս��", "����Ϸ",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					rows = 9;
					cols = 9;
					lblPlan.setText("ʣ��ը������0/" + rows * cols / 5);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem1 = new JRadioButtonMenuItem(
					String.valueOf("����"));
			localJRadioButtonMenuItem1.setActionCommand("level");
			localJRadioButtonMenuItem1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "�������ż������ս��", "����Ϸ",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 16;
					cols = 16;
					lblPlan.setText("ʣ��ը������0/" + rows * cols / 5);
					saoLei2.this.setSize(rows * 42 + 10, cols * 30 + 100);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem2 = new JRadioButtonMenuItem(
					String.valueOf("ţ��"));
			localJRadioButtonMenuItem2.setActionCommand("level");
			localJRadioButtonMenuItem2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "����ţ�˼������ս��", "����Ϸ",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 25;
					cols = 25;
					lblPlan.setText("ʣ��ը������0/" + rows * cols / 5);
					saoLei2.this.setSize(rows * 42 + 10, cols * 30 + 100);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem3 = new JRadioButtonMenuItem(
					String.valueOf("����"));
			localJRadioButtonMenuItem3.setActionCommand("level");
			localJRadioButtonMenuItem3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "������񼶱����ս��", "����Ϸ",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 35;
					cols = 30;
					lblPlan.setText("ʣ��ը������0/" + rows * cols / 5);
					saoLei2.this.setSize(rows * 42 + 10, cols * 30 + 100);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem4 = new JRadioButtonMenuItem(
					String.valueOf("��˵"));
			localJRadioButtonMenuItem4.setActionCommand("level");
			localJRadioButtonMenuItem4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "���봫˵�������ս��", "����Ϸ",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 62;
					cols = 30;
					lblPlan.setText("ʣ��ը������0/" + rows * cols / 5);
					saoLei2.this.setSize(rows * 27 + 10, cols * 30 + 100);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			localButtonGroup.add(localJRadioButtonMenuItem);
			localButtonGroup.add(localJRadioButtonMenuItem1);
			localButtonGroup.add(localJRadioButtonMenuItem2);
			localButtonGroup.add(localJRadioButtonMenuItem3);
			localButtonGroup.add(localJRadioButtonMenuItem4);
			localJMenu4.add(localJRadioButtonMenuItem);
			localJMenu4.add(localJRadioButtonMenuItem1);
			localJMenu4.add(localJRadioButtonMenuItem2);
			localJMenu4.add(localJRadioButtonMenuItem3);
			localJMenu4.add(localJRadioButtonMenuItem4);

			localJMenu1.add(localJMenuItem1);
			localJMenu1.addSeparator(); // ���·ָ���׷�ӵ��˵���ĩβ����Ϊ�ָ���
			localJMenu1.add(localJMenu4);
			localJMenu1.addSeparator();
			localJMenu1.add(localJMenuItem2);
			localJMenu3.add(localJMenuItem5);

			add(localJMenu1);
			add(localJMenu3);
		}

	}

	/**
	 * �����ж��ٸ����Ϊ1�İ�ť
	 * 
	 * @author Administrator
	 */
	private int turnedCount() {
		int i = 0;
		for (int j = 0; j < rows; j++) {
			for (int k = 0; k < cols; k++) {
				if (btnGame[j][k].isRight == true
						&& (btnGame[j][k].BombFlag) % 3 == 1) {
					i++;
				}
				if (btnGame[j][k].isRight == true
						&& (btnGame[j][k].BombFlag) % 3 == 2) {
					i--;
				}
				if (btnGame[j][k].isRight == true
						&& (btnGame[j][k].BombFlag) % 3 == 0) {
					i = i;
				}
			}
		}
		return i;
	}

	// ��ǩ��¼����
	private final class NorthPanel extends JPanel {
		public NorthPanel() {
			super();

			lblPlan = new JLabel("ʣ��ը������0/" + rows * cols / 5, 2);
			JButton localJButton = new JButton("�� ��");
			timego = new Timego();

			localJButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "����һ����", "����",
							JOptionPane.INFORMATION_MESSAGE);
					// timego.isStop = true;
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.initialize();
					lblPlan.setText("ʣ��ը������0/" + rows * cols / 5);
				}
			});

			lblPlan.setFont(saoLei2.font);
			localJButton.setMnemonic(65);
			localJButton.setActionCommand("new");

			add(lblPlan);
			add(localJButton);
			add(timego);

			new Thread(timego).start();
		}
	}

	public final class GamePanel extends JPanel implements ActionListener {
		// ͨ�����캯������initialize()
		public GamePanel() {
			initialize();
		}

		// Ϊ�������ð�ť
		private void initialize() {
			btnGame = new Bomb[rows][cols];
			setLayout(new GridLayout(rows, cols));
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					btnGame[i][j] = new Bomb(i, j);
					btnGame[i][j].setFont(new Font("", Font.PLAIN, 20));
					btnGame[i][j].setForeground(Color.white);
					btnGame[i][j].addActionListener(this);
					btnGame[i][j].addMouseListener(new MouseListener() {

						/**
						 * �Ҽ����
						 */
						@Override
						public void mouseClicked(MouseEvent e) {
							Bomb bombSource = (Bomb) e.getSource();
							// �������¼�ָ���ұ���갴�����򷵻� true��
							boolean right = SwingUtilities
									.isRightMouseButton(e);
							// if����ʾȷ����걻�Ҽ����������֮ǰ��û�б�������ģ���Ϊԭʼ״̬
							if ((right == true)
									&& (bombSource.isClicked == false)) {
								// ��ʼ�жϣ��Ҽ�����˶��ٱ飬ֻ������Ϊ1��2��3���Ҽ�
								bombSource.BombFlag = (bombSource.BombFlag + 1) % 3;

								// int i = turnedCount();
								// lblPlan.setText("ʣ��ը������" + i + "/"
								// + rows * cols / 5);
								//
								// �Ҽ�����һ�飬��������ȷ��Ϊ�ף��Ҽ���������ʱ�򣬱�Ϊ�˲�ȷ�������ӣ��������3�μ���ԭԭʼ״̬
								if (bombSource.BombFlag == 1) {
									// ʣ��������Ϊ0ʱ�򣬾Ͳ��ٴ�ӡ��ǣ����ʣ����������0������ȷ�����ף�����������ı�ǩ����1

									if (restBomb > 0) {
										// ��ӡ��ʾ�ı��
										bombSource.setForeground(Color.RED);
										bombSource.setFont((new Font("",
												Font.BOLD, 12)));
										bombSource.setText("F");
										bombSource.isRight = true;
										restBomb--;
									} else {
										bombSource.BombFlag = 0;
									}
								} else if (bombSource.BombFlag == 2) {

									restBomb++;
									bombSource.setForeground(Color.YELLOW);
									bombSource.setFont((new Font("", Font.BOLD,
											12)));
									bombSource.setText("?");
									bombSource.isRight = false;
								} else {

									bombSource.setText("");
								}
								// ����Ҽ����ȷ�����ף��Ҽ�һ�Σ����ҵ��ĵ�����+1���Ҽ����Σ���ȷ�����Ͱ�+1��Ľ�����أ���-1
								if (bombSource.isBomb == true) {
									if (bombSource.BombFlag == 1) {
										rightBomb++;
									} else if (bombSource.BombFlag == 2) {
										rightBomb--;
									}
								}

								isWin();
							}
							int i = turnedCount();
							lblPlan.setText("ʣ��ը������" + i + "/" + rows * cols
									/ 5);
						}

						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseReleased(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseEntered(MouseEvent e) {
							// TODO Auto-generated method stub

						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub

						}
					});
					btnGame[i][j].setActionCommand(String.valueOf(i));
					// btnGame[i][j].setBackground(saoLei2.clrObverse);
					add(btnGame[i][j]);
				}
			}
			startBomb();
		}

		/**
		 * ��ʼ����
		 */
		public void startBomb() {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					btnGame[i][j].isBomb = false;
					btnGame[i][j].isClicked = false;
					btnGame[i][j].isRight = false;
					btnGame[i][j].BombFlag = 0;
					btnGame[i][j].BombRoundCount = 9;
					btnGame[i][j].setBackground(Color.BLACK);
					btnGame[i][j].setEnabled(true);
					btnGame[i][j].setText("");
					btnGame[i][j].setFont(new Font("", Font.BOLD, 14));
					btnGame[i][j].setForeground(Color.BLUE);
					rightBomb = 0;
					restBomb = rows * cols / 5;
					restBlock = rows * cols - rows * cols / 5;
				}
			}
			for (int i = 0; i < rows * cols / 5;) {
				int x = (int) (Math.random() * (rows));
				int y = (int) (Math.random() * (cols));

				if (btnGame[x][y].isBomb != true) {
					btnGame[x][y].isBomb = true;
					i++;
				}
			}
			CountRoundBomb();
		}

		/**
		 * ���㷽����Χ����
		 */
		private void CountRoundBomb() {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					int count = 0;
					// ������Ĳ�����ʱ��ͳ����Χ�ĵ�����
					if (btnGame[i][j].isBomb != true) {
						for (int x = i - 1; x < i + 2; x++) {
							for (int y = j - 1; y < j + 2; y++) {
								if ((x >= 0) && (y >= 0) && (x < rows)
										&& (y < cols)) {
									if (btnGame[x][y].isBomb == true) {
										count++;
									}
								}
							}
						}
						btnGame[i][j].BombRoundCount = count;
					}
				}
			}
		}

		/**
		 * �ж�ʤ��
		 */
		public void isWin() {
			restBlock = rows * cols - rows * cols / 5;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (btnGame[i][j].isClicked == true) {
						restBlock--;
					}
				}
			}

			// System.out.println("restBlock:"+ restBlock);

			if (rightBomb == rows * cols / 5 || restBlock == 0) {

				timego.isStop = false;
				JOptionPane.showMessageDialog(this, "������......������ֻ��Ѽ�Ӷ���Ӯ��",
						"��ϲ����ʤ���ˣ�", JOptionPane.INFORMATION_MESSAGE);
				timego.isStop = true;
				timego.i = 0;
				timego.l.setText("��ʱ��" + timego.i);
				new Thread(timego).start();
				startBomb();
			}
		}

		/**
		 * ѡ�е���Χû�е��ף��򷭿���Χ�ĵ�ͼ
		 * 
		 * @param ClickedButton
		 */
		public void isNull(Bomb ClickedButton) {
			int i, j;
			i = ClickedButton.num_x;
			j = ClickedButton.num_y;

			for (int x = i - 1; x < i + 2; x++) {
				for (int y = j - 1; y < j + 2; y++) {
					if (((x != i) || (y != j)) && (x >= 0) && (y >= 0)
							&& (x < rows) & (y < cols)) {
						if (btnGame[x][y].isBomb == false
								&& btnGame[x][y].isClicked == false
								&& btnGame[x][y].isRight == false) {
							btnGame[x][y].setBackground(Color.yellow);
							turn(btnGame[x][y]);
						}
					}
				}
			}
		}

		/**
		 * ������󣬷������¼�
		 * 
		 * @param bomb
		 */
		private void turn(Bomb ClickedButton) {

			// ���ð�ť
			ClickedButton.setEnabled(false);
			ClickedButton.isClicked = true;
			ClickedButton.setBackground(Color.YELLOW);
			// ��������ť����ʾ�����ִ���0�����Ա߾��и����ָ��ף����򣬸�buttonΪ�հ״����򷭿���Χ�ĵ�ͼ
			if (ClickedButton.BombRoundCount > 0) {
				ClickedButton.setText(ClickedButton.BombRoundCount + "");
			} else {
				isNull(ClickedButton);
			}
		}

		/**
		 * ������
		 */

		public void actionPerformed(ActionEvent e) {

			// ����û�б����������Ҳ���Ǳ��Ҽ��������ִ��if��䣬����ִ��else��������е�����
			if (((Bomb) e.getSource()).isClicked == false
					&& ((Bomb) e.getSource()).isRight == false) {
				// �������Ĳ��ǵ��ף�����turn
				if (((Bomb) e.getSource()).isBomb == false) {
					turn(((Bomb) e.getSource()));
					isWin();
				} else {
					timego.isStop = false;
					// �ж��Ѿ����ˡ������ʾ�����е��׵�λ��
					// System.out.println("right key");
					for (int i = 0; i < rows; i++) {
						for (int j = 0; j < cols; j++) {
							if (btnGame[i][j].isBomb == true) {
								// btnGame[i][j].setFont(new
								// Font("",Font.BOLD,12));
								// btnGame[i][j].setText("T");
								btnGame[i][j]
										.setIcon(new ImageIcon(
												saoLei2.class
														.getResource("/image2/thunder.jpg")));
							}
						}
					}
					// setForeground���������ǰ��ɫ,˵���˾��������ֵ���ɫ
					// ((Bomb) e.getSource()).setForeground(Color.RED);
					// ((Bomb) e.getSource()).setFont(new Font("",
					// Font.BOLD,12));
					// ((Bomb) e.getSource()).setText("B");
					((Bomb) e.getSource()).setIcon(new ImageIcon(saoLei2.class
							.getResource("/image2/boom.jpg")));
					JOptionPane.showMessageDialog(this,
							"�ȵ������˰�...Ӣ�ۣ�վ��������ս���ɣ�", "���ź��������ˣ�", 2);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("��ʱ��" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					initialize();
				}
			}
		}

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

class Bomb extends JButton {
	int num_x, num_y; // �ڼ��ŷŷ���
	int BombRoundCount; // ��Χ����
	boolean isBomb; // �Ƿ�Ϊ��
	boolean isClicked; // �Ƿ񱻵��
	int BombFlag; // ̽�ױ��
	boolean isRight; // �Ƿ����Ҽ�

	public Bomb(int x, int y) {
		num_x = x;
		num_y = y;
		BombFlag = 0;
		BombRoundCount = 9;
		isBomb = false;
		isClicked = false;
		isRight = false;
	}
}

class Bomb_mouseAdapter extends MouseAdapter {
	private saoLei2 adaptee;

	Bomb_mouseAdapter(saoLei2 gamePanel) {
		this.adaptee = gamePanel;
	}

	public void mouseClicked(MouseEvent e) {
		adaptee.mouseClicked(e);
	}
}