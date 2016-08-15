package turneros.view;

import java.awt.AWTException;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import turneros.configuration.Configuration;

public class TrayMenuManager {
	private TrayIcon trayIcon;
	private TurneroConfiguracion tc;

	public TrayMenuManager(TurneroConfiguracion tc) {
		this.tc = tc;
		this.inicializeTrayMenu();
	}

	public void inicializeTrayMenu() {
		Runnable runner = new Runnable() {
			public void run() {
				if (SystemTray.isSupported()) {
					SystemTray tray = SystemTray.getSystemTray();
					Image image = Toolkit.getDefaultToolkit().getImage(
							Configuration.getPreference("icono"));
					
					ActionListener confListener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							tc.pack();
							tc.show();
						}
					};
					
					PopupMenu popup = new PopupMenu();
					MenuItem confItem = new MenuItem("Editar Configuracion");
					confItem.addActionListener(confListener);
					popup.add(confItem);

					ActionListener exitListener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							System.exit(0);
						}
					};

					
					MenuItem exitItem = new MenuItem("Salir");
					exitItem.addActionListener(exitListener);
					popup.add(exitItem);
					
					trayIcon = new TrayIcon(image, "Configuracion", popup);
					trayIcon.setImageAutoSize(true);
					
					try {
						tray.add(trayIcon);
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};

		EventQueue.invokeLater(runner);
	}
}
