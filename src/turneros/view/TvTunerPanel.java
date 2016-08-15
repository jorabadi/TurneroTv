package turneros.view;

/**
 dsj demo code.
 You may use, modify and redistribute this code under the terms laid out in the header of the DSJDemo application.
 copyright 2009
 N.Peters
 humatic GmbH
 Berlin, Germany
 **/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

import de.humatic.dsj.*;
import de.humatic.dsj.DSFilterInfo.DSPinInfo;
import de.humatic.dsj.rc.RendererControls;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;

import turneros.configuration.Configuration;

/**
 * Shows presentation of native filter properties pages in a non-modal,
 * non-blocking Swing dialog.
 **/

public class TvTunerPanel extends JPanel implements ActionListener,
		java.beans.PropertyChangeListener {

	private DSCapture graph;

	private JAudioPlayer jap;
	private Component graphPanel;
	private boolean running = true;
	private String nombreDispositivoVideo = "";
	private String nombreDispositivoAudio = "";
	private String nombreFiltroVideo = "";

	public TvTunerPanel() {
		
	}

	public void createGraph() {
		//DSEnvironment.unlockDLL("jbarraza@eafit.edu.co", 652104, 1534145, 0);
		this.nombreDispositivoVideo = Configuration
				.getPreference("nombreDispositivoVideo");
		this.nombreDispositivoAudio = Configuration
				.getPreference("nombreDispositivoAudio");
		this.nombreFiltroVideo = Configuration
				.getPreference("nombreFiltroVideo");
		this.setBackground(Color.BLACK);
	    this.setLayout(new GridBagLayout());
	  //  this.setBorder(BorderFactory.createLineBorder(Color.red));
		DSFilterInfo[][] dsi = DSCapture.queryDevices(1);
		ArrayList<DSFilterInfo> selectedDevice = this.getSelectedDevices(dsi);
		
		if(selectedDevice.size() < 2) {
			JOptionPane.showMessageDialog(null, "No esta conectado el dispositivo de tv");
			return;
		}
		
		graph = new DSCapture(DSFiltergraph.DD7 | DSCapture.MAX_RESIZEABLE,
				selectedDevice.get(0), true, selectedDevice.get(1), this);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 1;
	    gbc.weightx = 1f;
	    gbc.weighty = 1f;
	    gbc.fill = GridBagConstraints.BOTH;
	    graphPanel = graph.asComponent();
	    this.add(graphPanel,gbc);
		final DSFilter[] filtersInGraph = graph.listFilters();
		DSFiltergraph.DSAudioStream das = graph.getAudioStream();
		
		if (das != null) {
			jap = new JAudioPlayer(das);
			jap.start();
		}

		this.applyFilter(filtersInGraph);
	}
	

	public void closeConnections() {
		running = false;
		try {
			if (jap != null) {
				jap.close();
			}

			if (graph != null) {
				graph.dispose();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void applyFilter(DSFilter[] filtersInGraph) {
		for (DSFilter filtro : filtersInGraph) {
			String name = filtro.getName();
			if (name.equals(nombreFiltroVideo)) {
				filtro.applyPropPageSettings();
				DSCapture.CaptureDevice tv = graph.getActiveVideoDevice();
				tv.setTVChannel(Integer.parseInt(Configuration
						.getPreference("canaTvInicial")), false);
				tv.activate();
			}
		}
		
		graph.setVolume(Float.parseFloat(Configuration
				.getPreference("volumenTv")));
	}
	
	public void recalculateTvScreen() {
		int w = this.getWidth();
		int h = (int)(this.getHeight());
		RendererControls rc = graph.getRendererControls();
		rc.displayResized(0,0,w,h);
		DSEnvironment.unlockDLL("jbarraza@eafit.edu.co", 652104, 1534145, 0);
	}

	private ArrayList<DSFilterInfo> getSelectedDevices(DSFilterInfo[][] dsi) {
		ArrayList<DSFilterInfo> resultado = new ArrayList<DSFilterInfo>();
		String nombreDevice = "";
		for (DSFilterInfo[] dsFilters : dsi) {
			for (DSFilterInfo dsFilterInfo : dsFilters) {
				nombreDevice = dsFilterInfo.getName();
				if (nombreDevice.equals(nombreDispositivoVideo)) {
					resultado.add(dsFilterInfo);
					
					DSPinInfo[] pines = dsFilterInfo.getDownstreamPins();
					for(DSPinInfo pin : pines) {
						String nombre = pin.getName();
						
						if(nombre.equals(Configuration
						.getPreference("nombrePin"))) {
							DSMediaType[] formatos = pin.getFormats();
							int indiceFormato = 0;
							for(DSMediaType formato : formatos) {
								String nombreFormato = formato.getDisplayString();
								if(nombreFormato.equals(Configuration
						.getPreference("nombreFormato"))) {
									pin.setPreferredFormat(indiceFormato);
								}
								indiceFormato++;
							}
						}
					}
				}
			}
		}

		for (DSFilterInfo dsFilterInfo : dsi[1]) {
			nombreDevice = dsFilterInfo.getName();
			if (nombreDevice.equals(nombreDispositivoAudio)) {
				resultado.add(dsFilterInfo);
			}
		}
		return resultado;
	}
	
	public void cambiarCanal(int canal) {
		DSCapture.CaptureDevice tv = graph.getActiveVideoDevice();
		tv.setTVChannel(canal, false);
	}
	
	public void cambiarVolumen(float volumen) {
		graph.setVolume(volumen);
	}

	public static void main(String[] args) {
		final TvTunerPanel tvPanel = new TvTunerPanel();
		tvPanel.createGraph();
		JFrame frame = new JFrame();
		frame.getContentPane().setLayout(new GridBagLayout());
		JPanel panel2 = new JPanel();
		panel2.setBackground(Color.GREEN);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 1;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    gbc.fill = GridBagConstraints.BOTH;
	    gbc.anchor = GridBagConstraints.PAGE_START;
		frame.getContentPane().add(tvPanel, gbc);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.gridwidth = 1;
		gbc2.gridheight = 1;
		gbc2.weightx = 1.0;
		gbc2.weighty = 1.0;
		gbc2.fill = GridBagConstraints.BOTH;
		gbc2.anchor = GridBagConstraints.PAGE_END;
		frame.getContentPane().add(panel2, gbc2);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	private class JAudioPlayer extends Thread {

		private AudioInputStream ais;

		private AudioFormat format;

		private SourceDataLine line;

		private int bufferSize;

		private DataLine.Info info;

		private JAudioPlayer(DSFiltergraph.DSAudioStream dsAudio) {

			try {

				format = dsAudio.getFormat();
				bufferSize = dsAudio.getBufferSize();

				ais = new AudioInputStream(dsAudio, format, -1);

				info = new DataLine.Info(SourceDataLine.class, format);

				if (!AudioSystem.isLineSupported(info)) {
					System.out.println("Line matching " + info
							+ " not supported.");
					return;
				}

			} catch (Exception e) {
			}

		}

		public void run() {

			try {

				line = (SourceDataLine) AudioSystem.getLine(info);

				line.open(format, bufferSize * format.getFrameSize());

				line.start();

			} catch (LineUnavailableException ex) {

				System.out.println("Unable to open the line: " + ex);

				return;

			}

			try {

				byte[] data = new byte[bufferSize];
				int numBytesRead = 0;
				int written = 0;

				while (running) {

					try {

						if ((numBytesRead = ais.read(data)) == -1)
							break;

						int numBytesRemaining = numBytesRead;

						while (numBytesRemaining > 0) {

							written = line.write(data, 0, numBytesRemaining);

							numBytesRemaining -= written;

						}

					} catch (ArrayIndexOutOfBoundsException ae) {

						/**
						 * Some capture devices eventually deliver larger
						 * buffers than they originally say they would. Catch
						 * that and reset the data buffer
						 **/

						bufferSize = numBytesRead;

						data = new byte[bufferSize];

					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("Error during playback: " + e);
						break;
					}

				}

				line.stop();
				line.flush();
				line.close();

			} catch (Exception e) {
			}

		}

		private void close() {
			try {
				running = false;
				ais.close();

			} catch (Exception e) {
			}

		}

	}

}