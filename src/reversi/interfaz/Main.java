package reversi.interfaz;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	try {
					UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
					new MainView().setVisible(true);
				} catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                
            }
        });
    }

}
