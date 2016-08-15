package turneros.comm;


import gnu.io.*;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.TooManyListenersException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * Connection service. Conforms to osji connection service spec
 *
 * @author Liliya Butorina
 */
public class SerialConnection {

    private final String[] baudNames = {"baudrate", "baud"};        // allowed synonyms of parameter
    private final String[] baudVals = {"4800","9600", "38400", "115200"};  // allowed values of parameter
    private static final int DEFAULT_BAUD_RATE = 115200;
    private final String[] dataNames = {"databits", "data"};
    private final String[] dataVals = {"5", "6", "7", "8"};
    private static final int DEFAULT_DATA_BITS = 8;
    private final String[] stopNames = {"stopbits", "stop"};
    private final String[] stopVals = {"1", "2", "3"};
    private static final int DEFAULT_STOP_BITS = 1;
    private final String[] parityNames = {"parity"};
    private final String[] parityVals = {"none", "odd", "even", "mark", "space"};
    private static final int DEFAULT_PARITY = 0;
    private final String[] flowNames = {"flow", "flowcontrol"};
    private final String[] flowVals = {"none", "hard", "soft", "both"};
    private static final int DEFAULT_FLOW = 0;
    private final String[] timeNames = {"timeout", "time"};
    private int timeout = -1;
    private int baudRate = DEFAULT_BAUD_RATE;
    private int dataBits = DEFAULT_DATA_BITS;
    private int stopBits = DEFAULT_STOP_BITS;
    private int parity = DEFAULT_PARITY;
    private int flow = DEFAULT_FLOW;
    SerialPort serialPort = null;

    public String[] getSupportedProtocols() {
        return new String[]{"serial", "usb", "spi"};
    }

    public SerialConnection() {
    //    LogManager.getHandler(this.getClass().getName());
    }

    public SerialStreamConnection createConnection(String puerto, int baudRate, int dataBits, int stopBits, int parity) throws IOException {
        CommPortIdentifier portId;
        
        try {
        	
            portId = CommPortIdentifier.getPortIdentifier(puerto);
            if (portId.getPortType() != CommPortIdentifier.PORT_SERIAL) {
                throw new IllegalArgumentException("invalid port type " + puerto);
            }
            serialPort = (SerialPort) portId.open("turneros", 0);
            initPort(serialPort, baudRate, dataBits, stopBits, parity); //parameters baudRate, stopbits, ..
            System.out.println("Abrio coneccion serial");
            return new SerialStreamConnection(serialPort);
        } catch (NoSuchPortException e) {
            Logger.getLogger(SerialConnection.class.getName()).log(Level.SEVERE, "no such port " + puerto, e);
            JOptionPane.showMessageDialog(null, "El puerto :"+puerto+" no existe");
        } catch (PortInUseException e) {
            Logger.getLogger(SerialConnection.class.getName()).log(Level.SEVERE, "port in use " + puerto, e);
            JOptionPane.showMessageDialog(null, "El puerto :"+puerto+" ya esta en uso");
        } catch (UnsupportedCommOperationException e) {
            serialPort.close();
            Logger.getLogger(SerialConnection.class.getName()).log(Level.SEVERE, "failed to open " + puerto, e);
            JOptionPane.showMessageDialog(null, "El puerto :"+puerto+" no se puede abrir");
        } catch (TooManyListenersException e) {
            Logger.getLogger(SerialConnection.class.getName()).log(Level.SEVERE, "failed to open " + puerto, e);
            JOptionPane.showMessageDialog(null, "El puerto :"+puerto+" no se puede abrir");
        }
        return null;

    }

    private void initPort(final SerialPort serialPort, int baudRate, int dataBits, int stopBits, int parity) throws UnsupportedCommOperationException {
        initPortParams(baudRate, dataBits, stopBits, parity);
        serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
        if (flow == 0) {
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
        } else if (flow == 1) {
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
        } else if (flow == 2) {
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
        } else if (flow == 3) {
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT
                    | SerialPort.FLOWCONTROL_XONXOFF_IN | SerialPort.FLOWCONTROL_XONXOFF_OUT);
        }
        // todo use timeout ?
    }

    private void initPortParams(int baudRate, int dataBits, int stopBits, int parity) {
        this.baudRate = baudRate;
        this.stopBits = stopBits;
        this.parity = parity;
        this.dataBits = dataBits;
    }

    public ArrayList listarPuertosSeriales() {
        Enumeration pList = CommPortIdentifier.getPortIdentifiers();
        ArrayList puertosSeriales = new ArrayList();
        while (pList.hasMoreElements()) {
            CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
            if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                puertosSeriales.add(cpi.getName());
            }
        }
        return puertosSeriales;
    }
    
    public SerialPort getSerialPort() {
    	return serialPort;
    }

    // Inner class - SerialStreamConnection
    public class SerialStreamConnection {

        SerialPort port;
        InputStream is = null;
        OutputStream os = null;

        private SerialStreamConnection(SerialPort port) throws TooManyListenersException {
            this.port = port;
            port.notifyOnDataAvailable(true);
            port.notifyOnOutputEmpty(true);
        }

        public InputStream openInputStream() throws IOException {
            if (is == null) {
                is = new SerialInputStream(port.getInputStream());
                return is;
            } else {
                throw new IOException("duplicate open of " + port.getName() + " InputStream");
            }
        }

        public OutputStream openOutputStream() throws IOException {
            if (os == null) {
                os = new SerOutputStream(port.getOutputStream());
                return os;
            } else {
                throw new IOException("duplicate open of " + port.getName() + " OutputStream");
            }
        }

        public DataInputStream openDataInputStream() throws IOException {
            return new DataInputStream(openInputStream());
        }

        public DataOutputStream openDataOutputStream() throws IOException {
            return new DataOutputStream(openOutputStream());
        }

        public void close() throws IOException {

            if (os != null) {
                os.close();
                os = null;
            }
            if (is != null) {
                is.close();
                is = null;
            }
            port.close();

        }

        // Inner class - InputStream
        public class SerOutputStream extends OutputStream {

            protected OutputStream outputStream;

            public SerOutputStream(OutputStream outputStream) {
                this.outputStream = outputStream;
            }

            @Override
            public void write(int i) throws IOException {
                outputStream.write(i);
            }

            public void writeString(ArrayList<Integer> cadena) throws IOException {
                for (int i = 0; i < cadena.size(); i++) {
                    outputStream.write(cadena.get(i));
                }
            }
        }

        // Inner class - InputStream
        public class SerialInputStream extends InputStream{

            protected InputStream inputStream;

            public SerialInputStream(InputStream inputStream) {
                this.inputStream = inputStream;
            }
            
            public int[] readTrama() throws IOException {
            	ensureOpen();
                
                int lectura = 0;
    			int[] trama = new int[8];
                
                for (int i = 0; i < 8; i++) {
					lectura = inputStream.read();
					if(lectura == 0) {
						return null;
					}
					trama[i] = lectura;
				}
                
                return trama;
            }

            public int read() throws IOException {
               return inputStream.read();
            }

            public int read(byte[] b, int off, int len) throws IOException {
                int res = 0;
                int n = 0;
                while (res < len && n != -1) { // not EOF
                    ensureOpen();
                    n = inputStream.read(b, off + res, len - res);
                    res += n == -1 ? 0 : n;
                }
                return res;
            }
            

            public int available() throws IOException {
                return inputStream.available();
            }

            public long skip(long n) throws IOException {
                return inputStream.skip(n);
            }

            public synchronized void mark(int readlimit) {
                inputStream.mark(readlimit);
            }

            public synchronized void reset() throws IOException {
                inputStream.reset();
            }

            public boolean markSupported() {
                return inputStream.markSupported();
            }

            public void close() throws IOException {
                ensureOpen();
                inputStream.close();
                inputStream = null;
            }

            protected void ensureOpen() throws IOException {
                if (inputStream == null) {
                    throw new IOException("stream is closed");
                }
            }
        }
    }
}
