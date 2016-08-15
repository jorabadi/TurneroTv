package turneros.taquillas;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import javax.swing.event.EventListenerList;

import turneros.comm.SerialConnection;
import turneros.comm.SerialConnection.SerialStreamConnection;
import turneros.comm.SerialConnection.SerialStreamConnection.SerOutputStream;
import turneros.comm.SerialConnection.SerialStreamConnection.SerialInputStream;
import turneros.configuration.Configuration;
import turneros.entidades.Taquilla;

public class TaquillasListener implements SerialPortEventListener{
	protected EventListenerList listenerList = new EventListenerList();
	private SerialConnection coneccion;
	private SerialStreamConnection sstream;
	private SerialInputStream inputStream;

	public TaquillasListener() {
		try {
			coneccion = new SerialConnection();
			this.abrirConeccionSerial();
			SerialPort port = coneccion.getSerialPort();
			port.addEventListener(this);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			Logger.getLogger(TaquillasListener.class.getName())
					.log(Level.SEVERE,
							"No pudo establecer coneccion no existe la configuracion inicial ",
							ex);
		} 
	}

	public void abrirConeccionSerial() throws IOException,gnu.io.NoSuchPortException {
		sstream = coneccion.createConnection(Configuration
				.getPreference("puertoSerial"),
				Integer.valueOf(Configuration.getPreference("baudios"))
						.intValue(), SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		//outStream = (SerOutputStream) sstream.openOutputStream();
		inputStream = (SerialInputStream) sstream.openInputStream();
	}

	public void cerrarConeccionSerial() throws IOException {
		sstream.close();
	}

	public void turnoSolicitado(int numeroTaquilla) {
		Taquilla taquilla = new Taquilla();
		taquilla.setCodigoTaquilla(Integer.toString(numeroTaquilla));
		taquilla.setNombre("Taquilla " + numeroTaquilla);
		this.fireTurnoEvent(new TaquillasEvent(taquilla));
	}

	public void addTurnoEventListener(TaquillasEventListener listener) {
		listenerList.add(TaquillasEventListener.class, listener);
	}

	public void removeTurnoEventListener(TaquillasEventListener listener) {
		listenerList.remove(TaquillasEventListener.class, listener);
	}

	void fireTurnoEvent(TaquillasEvent evt) {
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0; i < listeners.length; i = i + 2) {
			if (listeners[i] == TaquillasEventListener.class) {
				((TaquillasEventListener) listeners[i + 1]).taquillaCambiada(evt);
			}
		}
	}

	public int[] listenTramaTaquilla() {
		int[] resultado = new int[8];
		try {
			resultado = inputStream.readTrama();
			return resultado;
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			Logger.getLogger(TaquillasListener.class.getName())
					.log(Level.SEVERE,
							"No pudo establecer coneccion no existe la configuracion inicial ",
							ex);
		}
		return resultado;
	}

	@Override
	public void serialEvent(SerialPortEvent arg0) {
		int[] trama = this.listenTramaTaquilla();
		System.out.println("serialEvent");
		
		if(trama != null) {
			int taquilla = trama[7] - 48;
			System.out.println("Taquilla diferente de null, taquilla:"+taquilla);
			this.turnoSolicitado(taquilla);
		} else {
			System.out.println("Taquilla diferente de nula");
		}
	}

}
