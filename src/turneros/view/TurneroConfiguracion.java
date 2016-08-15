package turneros.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Event;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import turneros.bo.ConfiguracionTurneroBO;
import turneros.entidades.ConfiguracionTurnero;
import turneros.taquillas.TaquillasEvent;
import turneros.taquillas.TaquillasEventListener;

public class TurneroConfiguracion extends JFrame {
	private JLabel lblMaximoTurnosMostrar;
	private JTextField txtMaximoTurnosMostrar;
	private JLabel lblAltoColumnaTurnos;
	private JTextField txtAltoColumnaTurnos;
	private JLabel lblAltoEncabezadoTurnos;
	private JTextField txtAltoEncabezadoTurnos;
	private JLabel lblNumeroMaximoTurnosTabla;
	private JTextField txtNumeroMaximoTurnosTabla;
	private JLabel lblTurnoMinimo;
	private JTextField txtTurnoMinimo;
	private JLabel lblTurnoMaximo;
	private JTextField txtTurnoMaximo;
	private JLabel lblBaudios;
	private JTextField txtBaudios;
	private JLabel lblPuertoSerial;
	private JTextField txtPuertoSerial;
	private JLabel lblAudioTurno;
	private JTextField txtAudioTurno;
	private JLabel lblCanalTv;
	private JTextField txtCanalTv;
	private JLabel lblDispositivoVideoTvTuner;
	private JTextField txtDispositivoVideoTvTuner;
	private JLabel lblDispositivoAudioTvTuner;
	private JTextField txtDispositivoAudioTvTuner;
	private JLabel lblFiltroVideoTvTuner;
	private JTextField txtFiltroVideoTvTuner;
	private JLabel lblVolumenTvTuner;
	private JTextField txtVolumenTvTuner;
	private JLabel lblVolumenTurno;
	private JTextField txtVolumenTurno;
	private JLabel lblTurnoActual;
	private JTextField txtTurnoActual;
	private JButton btnAceptar;
	private ConfiguracionTurneroBO boConfiguracionTurnero;
	private TvTunerPanel tvTuner;
	public TurneroConfiguracion(ConfiguracionTurneroBO boConfiguracionTurnero,TvTunerPanel tvTuner) {
		this.boConfiguracionTurnero = boConfiguracionTurnero;
		this.tvTuner = tvTuner;
		getContentPane().setLayout(new GridLayout(0,2,20,20));
	    Border margin = new EmptyBorder(10, 10, 10, 10);
        getRootPane().setBorder(margin);
		this.setTitle("Configuracion turnero");
		int yPos = 0;
		
		GridBagConstraints gridBagConstraintForLabel = new GridBagConstraints();
		gridBagConstraintForLabel.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraintForLabel.insets = new Insets(0, 0, 5, 5);
		gridBagConstraintForLabel.gridx = 0;
		gridBagConstraintForLabel.gridy = yPos;
		gridBagConstraintForLabel.weightx = 1.0;
		gridBagConstraintForLabel.weighty = 0.0;
		
		GridBagConstraints gridBagConstraintForTextField = new GridBagConstraints();
	    gridBagConstraintForTextField.fill = GridBagConstraints.HORIZONTAL;
	    gridBagConstraintForTextField.insets = new Insets(0, 0, 5, 0);
	    gridBagConstraintForTextField.gridx = 1;
	    gridBagConstraintForTextField.gridy = yPos;
	    gridBagConstraintForTextField.weightx = 1.0;
	    gridBagConstraintForTextField.weighty = 0.0;
		
		lblMaximoTurnosMostrar = new JLabel("Numero Turnos a mostrar");
		txtMaximoTurnosMostrar = new JTextField();
		
		this.addElement(yPos,lblMaximoTurnosMostrar,txtMaximoTurnosMostrar,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblAltoColumnaTurnos = new JLabel("Alto Fila de la tabla Turnos");
		txtAltoColumnaTurnos = new JTextField();
		this.addElement(yPos,lblAltoColumnaTurnos,txtAltoColumnaTurnos,gridBagConstraintForLabel,gridBagConstraintForTextField);
	
		yPos++;
		lblAltoEncabezadoTurnos = new JLabel("Alto Encabezado de la tabla de Turnos");
		txtAltoEncabezadoTurnos = new JTextField();
		this.addElement(yPos,lblAltoEncabezadoTurnos,txtAltoEncabezadoTurnos,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblNumeroMaximoTurnosTabla = new JLabel("Numero Maximo de Turnos a mostrar en la tabla de Turnos");
		txtNumeroMaximoTurnosTabla = new JTextField();
		this.addElement(yPos,lblAltoEncabezadoTurnos,txtAltoEncabezadoTurnos,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblTurnoMinimo = new JLabel("Turno minimo");
		txtTurnoMinimo = new JTextField();
		this.addElement(yPos,lblTurnoMinimo,txtTurnoMinimo,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblTurnoMaximo = new JLabel("Turno maximo");
		txtTurnoMaximo = new JTextField();
		this.addElement(yPos,lblTurnoMaximo,txtTurnoMaximo,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblBaudios = new JLabel("Baudios");
		txtBaudios = new JTextField();
		this.addElement(yPos,lblTurnoMaximo,txtTurnoMaximo,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblPuertoSerial = new JLabel("Puerto Serial");
		txtPuertoSerial = new JTextField();
		this.addElement(yPos,lblPuertoSerial,txtPuertoSerial,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblAudioTurno = new JLabel("Archivo Audio Turno");
		txtAudioTurno = new JTextField();
		this.addElement(yPos,lblAudioTurno,txtAudioTurno,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblCanalTv = new JLabel("Canal Tv");
		txtCanalTv = new JTextField();
		this.addElement(yPos,lblCanalTv,txtCanalTv,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblDispositivoVideoTvTuner = new JLabel("Dispositivo Video Tv Tuner");
		txtDispositivoVideoTvTuner = new JTextField();
		this.addElement(yPos,lblDispositivoVideoTvTuner,txtDispositivoVideoTvTuner,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblDispositivoAudioTvTuner = new JLabel("Dispositivo Audio Tv Tuner");
		txtDispositivoAudioTvTuner = new JTextField();
		this.addElement(yPos,lblDispositivoAudioTvTuner,txtDispositivoAudioTvTuner,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblFiltroVideoTvTuner = new JLabel("Filtro Video Tv Tuner");
		txtFiltroVideoTvTuner = new JTextField();
		this.addElement(yPos,lblFiltroVideoTvTuner,txtFiltroVideoTvTuner,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblVolumenTvTuner = new JLabel("Volumen Tv Tuner");
		txtVolumenTvTuner = new JTextField();
		this.addElement(yPos,lblVolumenTvTuner,txtVolumenTvTuner,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblVolumenTurno = new JLabel("Volumen Turno");
		txtVolumenTurno = new JTextField();
		this.addElement(yPos,lblVolumenTurno,txtVolumenTurno,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		lblTurnoActual = new JLabel("Turno Actual");
		txtTurnoActual = new JTextField();
		this.addElement(yPos,lblTurnoActual,txtTurnoActual,gridBagConstraintForLabel,gridBagConstraintForTextField);
		
		yPos++;
		btnAceptar = new JButton("Aceptar");
		gridBagConstraintForLabel.gridy = yPos;
		gridBagConstraintForLabel.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(btnAceptar,gridBagConstraintForLabel);
		
		this.loadConfiguracionTurnero();
		
		btnAceptar.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				guardarConfiguracionTurnero();
			} 
		});
	}
	
	
	public void addElement(int yPos,JLabel label, JTextField txt,GridBagConstraints gbcLabel,GridBagConstraints gblTxt) {
		gbcLabel.gridy = yPos;
		gblTxt.gridy = yPos;
		this.getContentPane().add(label,gbcLabel);
		this.getContentPane().add(txt,gblTxt);
	}
	
	public void loadConfiguracionTurnero() {
		try {
			ConfiguracionTurnero ct = boConfiguracionTurnero.loadConfiguracionTurnero();
			txtMaximoTurnosMostrar.setText(ct.getMaximoTurnosMostrar());
			txtAltoColumnaTurnos.setText(ct.getAltoEncabezadoTurnos());
			txtAltoEncabezadoTurnos.setText(ct.getAltoEncabezadoTurnos());
			txtNumeroMaximoTurnosTabla.setText(ct.getNumeroMaximoTurnosTabla());
			txtTurnoMinimo.setText(ct.getTurnoMinimo());
			txtTurnoMaximo.setText(ct.getTurnoMaximo());
			txtBaudios.setText(ct.getBaudios());
			txtPuertoSerial.setText(ct.getPuertoSerial());
			txtAudioTurno.setText(ct.getAudioTurno());
			txtCanalTv.setText(ct.getCanalTv());
			txtDispositivoVideoTvTuner.setText(ct.getDispositivoVideoTvTuner());
			txtDispositivoAudioTvTuner.setText(ct.getDispositivoAudioTvTuner());
			txtFiltroVideoTvTuner.setText(ct.getFiltroVideoTvTuner());
			txtVolumenTvTuner.setText(ct.getVolumenTvTuner());
			txtVolumenTurno.setText(ct.getVolumenTurno());
			txtTurnoActual.setText(ct.getTurnoActual());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void guardarConfiguracionTurnero() {
		ConfiguracionTurnero ct = new ConfiguracionTurnero(); 
		ct.setMaximoTurnosMostrar(this.txtMaximoTurnosMostrar.getText());
		ct.setAltoEncabezadoTurnos(this.txtAltoEncabezadoTurnos.getText());
		ct.setNumeroMaximoTurnosTabla(this.txtNumeroMaximoTurnosTabla.getText());
		ct.setTurnoMinimo(this.txtTurnoMinimo.getText());
		ct.setTurnoMaximo(this.txtTurnoMaximo.getText());
		ct.setBaudios(this.txtBaudios.getText());
		ct.setPuertoSerial(this.txtPuertoSerial.getText());
		ct.setAudioTurno(this.txtAudioTurno.getText());
		ct.setCanalTv(this.txtCanalTv.getText());
		ct.setDispositivoVideoTvTuner(this.txtDispositivoVideoTvTuner.getText());
		ct.setDispositivoAudioTvTuner(this.txtDispositivoAudioTvTuner.getText());
		ct.setFiltroVideoTvTuner(this.txtFiltroVideoTvTuner.getText());
		ct.setVolumenTvTuner(this.txtVolumenTvTuner.getText());
		ct.setVolumenTurno(this.txtVolumenTurno.getText());
		ct.setTurnoActual(this.txtTurnoActual.getText());
		try {
			boConfiguracionTurnero.guardarConfiguracionTurnero(ct);
			String canal = this.txtCanalTv.getText();
			String volumenTv = this.txtVolumenTvTuner.getText();
			tvTuner.cambiarCanal(Integer.parseInt(canal));
			tvTuner.cambiarVolumen(Float.parseFloat(volumenTv));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		TvTunerPanel tvTuner = new TvTunerPanel();
		ConfiguracionTurneroBO boConfiguracionTurnero = new ConfiguracionTurneroBO();
		TurneroConfiguracion tc = new TurneroConfiguracion(boConfiguracionTurnero,tvTuner);
		tc.pack();
		tc.show();
	}
	

}
