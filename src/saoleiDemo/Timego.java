package saoleiDemo;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Timego extends JPanel implements Runnable {
	int i = 0;
	boolean isStop = true;
	JLabel l = new JLabel();

	public void run() {
		while (isStop) {
			try {
				Thread.sleep(1000);
				i++;
				l.setText("用时：" + i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public Timego() {
		this.i = i;
		l.setSize(150, 50);
		// l.setFont();
		l.setText("用时：" + 0);
		this.add(l);
	}
}
