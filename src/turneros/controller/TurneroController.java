package turneros.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.sql.SQLException;

import turneros.entidades.Taquilla;
import turneros.entidades.Turno;
import turneros.bo.ConfiguracionTurneroBO;
import turneros.bo.TurnoBO;
import turneros.configuration.Configuration;
import turneros.taquillas.TaquillasEvent;
import turneros.taquillas.TaquillasEventListener;
import turneros.taquillas.TaquillasListener;
import turneros.view.MonitorView;
import turneros.view.TrayMenuManager;
import turneros.view.TurneroConfiguracion;
import turneros.view.TvTunerPanel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.media.format.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class TurneroController implements PropertyChangeListener,
		TaquillasEventListener {
	// creo un patron observador en el controlador
	private MonitorView monitorView;
	private TaquillasListener taquillaListener;
	private TurnoBO boTurno;
	private ConfiguracionTurneroBO boConfiguracionTurnero;
	Object turneroLock = new Object();
	private TrayMenuManager trayMenu;
	private TurneroConfiguracion tc;
	private TvTunerPanel tvTuner;
	public TurneroController() {
		try {
			tvTuner = new TvTunerPanel();
			int maximoTurnos = Integer.parseInt(Configuration
					.getPreference("maximoTurnos"));
			int tamanoTurnos = Integer.parseInt(Configuration
					.getPreference("tamanoTurnos"));
			int tamanoEncabezadoTurnos = Integer.parseInt(Configuration
					.getPreference("tamanoEncabezadoTurnos"));
			taquillaListener = new TaquillasListener();
			taquillaListener.addTurnoEventListener(this);
			monitorView = new MonitorView(maximoTurnos, tamanoTurnos,
					tamanoEncabezadoTurnos,tvTuner);
			boTurno = new TurnoBO();
			boConfiguracionTurnero = new ConfiguracionTurneroBO();
			
			FileHandler fh = new FileHandler("mylog.txt");
			tc = new TurneroConfiguracion(boConfiguracionTurnero,tvTuner);
			trayMenu = new TrayMenuManager(tc);
			java.util.logging.Logger.getLogger(
					TurneroController.class.getName()).addHandler(fh);
		} catch (RemoteException ex) {
			// TODO Auto-generated catch block

			java.util.logging.Logger.getLogger(
					TurneroController.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (MalformedURLException ex) {
			// TODO Auto-generated catch block

			java.util.logging.Logger.getLogger(
					TurneroController.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			java.util.logging.Logger.getLogger(
					TurneroController.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			java.util.logging.Logger.getLogger(
					TurneroController.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
	}

	/*
	 * Funcion que permite solicitar un turno desde una taquilla
	 */
	public Turno solicitarTurno(Taquilla taquilla) {
		System.out.println("solicitarTurno-asignarReservaTaquilla");
		Turno turno = null;
		try {
			this.soundChange();
			turno = boTurno.obtenerTurno(taquilla);
			monitorView.addTurno(turno);
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			java.util.logging.Logger.getLogger(
					TurneroController.class.getName()).log(
					java.util.logging.Level.SEVERE, null, ex);
		}
		return turno;
	}

	public void actualizarTablaTurnos(Turno turno) {
		monitorView.addTurno(turno);
	}

	public static void main(String[] args) {
		TurneroController controlador = new TurneroController();
		/*
		 * for (int i = 0; i < 10; i++) { Taquilla taquilla = new Taquilla();
		 * taquilla.setCodigoTaquilla(String.valueOf(i));
		 * taquilla.setNombre("Taquilla " + i);
		 * controlador.solicitarTurno(taquilla); }
		 */

	}

	public void soundChange() {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(Configuration
					.getPreference("nombreAudiofile"))));
			FloatControl volume = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			volume.setValue(Float.parseFloat(Configuration
					.getPreference("volumenTurnero")));

			clip.addLineListener(new LineListener() {
				public void update(LineEvent e) {
					if (e.getType() == LineEvent.Type.STOP) {
						e.getLine().close();
					}
				}
			});

			clip.start();
			clip.drain();
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		
		Turno turno = (Turno) event.getNewValue();
		this.actualizarTablaTurnos(turno);
	}

	@Override
	public void taquillaCambiada(TaquillasEvent evt) {
		// TODO Auto-generated method stub
		synchronized (turneroLock) {
			this.solicitarTurno((Taquilla) evt.getSource());
		}
	}
}
