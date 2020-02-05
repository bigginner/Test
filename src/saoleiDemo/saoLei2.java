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
	private static Font font = new Font("宋体", 1, 20);
	private static Color clrObverse = Color.BLACK; // 开始时，游戏按钮颜色
	private int rows = 9; // 表示行数
	private int cols = 9; // 表示列数
	private JLabel lblPlan;
	private JLabel lblTime;
	Bomb btnGame[][]; // 定义一个JButton数组存储方块按钮
	private GamePanel pnlgame; // 游戏面板
	private int rightBomb; // 找到的地雷数
	private int restBomb; // 剩余地雷数
	private int restBlock; // 剩余方块数
	boolean isFirstClick = true; // 是否第一次点击
	private int currenttime;
	// 静态代码块--实现窗口关闭功能
	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
	}

	// 建立构造函数,布局
	public saoLei2() {

		pnlgame = new GamePanel();

		setTitle("扫雷游戏");
		setJMenuBar(new MainMenuBar());

		Container localContainer = getContentPane();
		localContainer.setLayout(new BorderLayout());
		localContainer.add(new NorthPanel(), "North");
		localContainer.add(pnlgame, "Center");

		setDefaultCloseOperation(3); // 一个指示窗口关闭操作的整数
		setSize(rows * 42 + 10, cols * 30 + 100);

		setLocationRelativeTo(this);
		setVisible(true);
	}

	private final class MainMenuBar extends JMenuBar {
		// 菜单栏
		public MainMenuBar() {
			JMenu localJMenu1 = new JMenu("游戏(S)");
			JMenu localJMenu3 = new JMenu("帮助(H)");
			JMenu localJMenu4 = new JMenu("难度(L)");

			JMenuItem localJMenuItem1 = new JMenuItem("开始");
			JMenuItem localJMenuItem2 = new JMenuItem("退出");
			JMenuItem localJMenuItem5 = new JMenuItem("关于游戏");

			ButtonGroup localButtonGroup = new ButtonGroup();

			localJMenuItem1.setActionCommand("Start");
			localJMenuItem2.setActionCommand("exit");
			localJMenuItem5.setActionCommand("Help");

			localJMenuItem1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "新开一局吗？", "新游戏",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.initialize();

					lblPlan.setText("剩余炸弹数：0/" + rows * cols / 5);

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
					jd.setTitle("游戏帮助");
					jd.setSize(505, 400);
					JTextArea jt = new JTextArea();
					jt.setBackground(Color.yellow);
					jt.setEditable(false);
					jt.setText("开发者：Name.范\n开发工具：eclipse\n开发版本：5.0\n\n            游戏规则如下： 在"
							+ "程序运行后，游戏开始，并计时，用鼠标左键点击网格，点到的网\n 格中显示“雷”，即游戏失败，若为数字，则表示相邻的东、"
							+ "南、西、北、西北、东北、东\n 西、东南方向的格子中含有该数字个地雷，若为空白格，则翻开周围相连接无数字隔开的空\n "
							+ "白格，直到有数字格子阻挡结束；若用鼠标右键点击，第一次则表明玩家确认该单元格一定\n 是雷，第二次点击表明不确认是"
							+ "不是雷，第三次恢复初始状态；\n          游戏中有地雷区、地雷计数器、计时器\n          游戏难度等级：菜鸟、入门"
							+ "、牛人、大神、传说\n          "
							+ "判断胜负：若除去“雷”的其它网格全部被清除或者玩家点击右键一次，找过认为自己\n 确认的雷，并且是正确的，则游戏胜利"
							+ "，中途若想不玩或想直接玩下一盘，可选择退出或重\n 玩游戏。\n          "
							+ "游戏技巧：点击非雷区，会显示出包含0-8的其中一个数字，代表着以点击的网格中心\n （不包括点击的网格），包围着的8个"
							+ "网格含有该数字个“雷”的数量。");
					jd.add(jt);
					jd.setVisible(true);
					jd.setLocationRelativeTo(null);
				}

			});

			JRadioButtonMenuItem localJRadioButtonMenuItem = new JRadioButtonMenuItem(
					String.valueOf("菜鸟"));
			localJRadioButtonMenuItem.setActionCommand("level");
			localJRadioButtonMenuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "进入菜鸟级别的挑战吗？", "新游戏",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					rows = 9;
					cols = 9;
					lblPlan.setText("剩余炸弹数：0/" + rows * cols / 5);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem1 = new JRadioButtonMenuItem(
					String.valueOf("入门"));
			localJRadioButtonMenuItem1.setActionCommand("level");
			localJRadioButtonMenuItem1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "进入入门级别的挑战吗？", "新游戏",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 16;
					cols = 16;
					lblPlan.setText("剩余炸弹数：0/" + rows * cols / 5);
					saoLei2.this.setSize(rows * 42 + 10, cols * 30 + 100);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem2 = new JRadioButtonMenuItem(
					String.valueOf("牛人"));
			localJRadioButtonMenuItem2.setActionCommand("level");
			localJRadioButtonMenuItem2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "进入牛人级别的挑战吗？", "新游戏",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 25;
					cols = 25;
					lblPlan.setText("剩余炸弹数：0/" + rows * cols / 5);
					saoLei2.this.setSize(rows * 42 + 10, cols * 30 + 100);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem3 = new JRadioButtonMenuItem(
					String.valueOf("大神"));
			localJRadioButtonMenuItem3.setActionCommand("level");
			localJRadioButtonMenuItem3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "进入大神级别的挑战吗？", "新游戏",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 35;
					cols = 30;
					lblPlan.setText("剩余炸弹数：0/" + rows * cols / 5);
					saoLei2.this.setSize(rows * 42 + 10, cols * 30 + 100);
					saoLei2.this.setLocationRelativeTo(null);
					pnlgame.initialize();
				}

			});
			JRadioButtonMenuItem localJRadioButtonMenuItem4 = new JRadioButtonMenuItem(
					String.valueOf("传说"));
			localJRadioButtonMenuItem4.setActionCommand("level");
			localJRadioButtonMenuItem4.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "进入传说级别的挑战吗？", "新游戏",
							JOptionPane.INFORMATION_MESSAGE);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.removeAll();
					rows = 62;
					cols = 30;
					lblPlan.setText("剩余炸弹数：0/" + rows * cols / 5);
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
			localJMenu1.addSeparator(); // 将新分隔符追加到菜单的末尾，即为分割线
			localJMenu1.add(localJMenu4);
			localJMenu1.addSeparator();
			localJMenu1.add(localJMenuItem2);
			localJMenu3.add(localJMenuItem5);

			add(localJMenu1);
			add(localJMenu3);
		}

	}

	/**
	 * 计算有多少个标记为1的按钮
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

	// 标签记录进度
	private final class NorthPanel extends JPanel {
		public NorthPanel() {
			super();

			lblPlan = new JLabel("剩余炸弹数：0/" + rows * cols / 5, 2);
			JButton localJButton = new JButton("重 玩");
			timego = new Timego();

			localJButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					timego.isStop = false;
					JOptionPane.showMessageDialog(null, "重新一局吗？", "重玩",
							JOptionPane.INFORMATION_MESSAGE);
					// timego.isStop = true;
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
					new Thread(timego).start();
					pnlgame.removeAll();
					pnlgame.initialize();
					lblPlan.setText("剩余炸弹数：0/" + rows * cols / 5);
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
		// 通过构造函数调用initialize()
		public GamePanel() {
			initialize();
		}

		// 为布局设置按钮
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
						 * 右键点击
						 */
						@Override
						public void mouseClicked(MouseEvent e) {
							Bomb bombSource = (Bomb) e.getSource();
							// 如果鼠标事件指定右边鼠标按键，则返回 true。
							boolean right = SwingUtilities
									.isRightMouseButton(e);
							// if语句表示确保鼠标被右键点击，并且之前是没有被点击过的，即为原始状态
							if ((right == true)
									&& (bombSource.isClicked == false)) {
								// 开始判断，右键点击了多少遍，只能限制为1、2、3次右键
								bombSource.BombFlag = (bombSource.BombFlag + 1) % 3;

								// int i = turnedCount();
								// lblPlan.setText("剩余炸弹数：" + i + "/"
								// + rows * cols / 5);
								//
								// 右键点了一遍，即当作被确认为雷，右键点了两遍时候，变为了不确定的因子，当变成了3次即还原原始状态
								if (bombSource.BombFlag == 1) {
									// 剩余雷数量为0时候，就不再打印标记，如果剩余雷数大于0，则当作确认了雷，便会让雷数的标签减少1

									if (restBomb > 0) {
										// 打印显示的标记
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
								// 如果右键点击确认是雷，右键一次，则找到的地雷数+1，右键两次，不确定，就把+1后的结果返回，即-1
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
							lblPlan.setText("剩余炸弹数：" + i + "/" + rows * cols
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
		 * 开始布雷
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
		 * 计算方块周围雷数
		 */
		private void CountRoundBomb() {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					int count = 0;
					// 当点击的不是雷时，统计周围的地雷数
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
		 * 判断胜负
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
				JOptionPane.showMessageDialog(this, "哈哈哈......就你这只旱鸭子都能赢！",
						"恭喜您，胜利了！", JOptionPane.INFORMATION_MESSAGE);
				timego.isStop = true;
				timego.i = 0;
				timego.l.setText("用时：" + timego.i);
				new Thread(timego).start();
				startBomb();
			}
		}

		/**
		 * 选中的周围没有地雷，则翻开周围的地图
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
		 * 鼠标点击后，发生的事件
		 * 
		 * @param bomb
		 */
		private void turn(Bomb ClickedButton) {

			// 禁用按钮
			ClickedButton.setEnabled(false);
			ClickedButton.isClicked = true;
			ClickedButton.setBackground(Color.YELLOW);
			// 如果点击按钮所显示的数字大于0，则旁边就有该数字个雷，否则，该button为空白处，则翻开周围的地图
			if (ClickedButton.BombRoundCount > 0) {
				ClickedButton.setText(ClickedButton.BombRoundCount + "");
			} else {
				isNull(ClickedButton);
			}
		}

		/**
		 * 左键点击
		 */

		public void actionPerformed(ActionEvent e) {

			// 地雷没有被点击，地雷也不是被右键点击，则执行if语句，否则执行else，左键点中地雷了
			if (((Bomb) e.getSource()).isClicked == false
					&& ((Bomb) e.getSource()).isRight == false) {
				// 如果点击的不是地雷，则发生turn
				if (((Bomb) e.getSource()).isBomb == false) {
					turn(((Bomb) e.getSource()));
					isWin();
				} else {
					timego.isStop = false;
					// 判断已经输了。最后显示出所有地雷的位置
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
					// setForeground设置组件的前景色,说白了就是设置字的颜色
					// ((Bomb) e.getSource()).setForeground(Color.RED);
					// ((Bomb) e.getSource()).setFont(new Font("",
					// Font.BOLD,12));
					// ((Bomb) e.getSource()).setText("B");
					((Bomb) e.getSource()).setIcon(new ImageIcon(saoLei2.class
							.getResource("/image2/boom.jpg")));
					JOptionPane.showMessageDialog(this,
							"踩到地雷了吧...英雄，站起来重新战斗吧！", "很遗憾，您输了！", 2);
					timego.isStop = true;
					timego.i = 0;
					timego.l.setText("用时：" + timego.i);
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
	int num_x, num_y; // 第几号放方块
	int BombRoundCount; // 周围雷数
	boolean isBomb; // 是否为雷
	boolean isClicked; // 是否被点击
	int BombFlag; // 探雷标记
	boolean isRight; // 是否点击右键

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