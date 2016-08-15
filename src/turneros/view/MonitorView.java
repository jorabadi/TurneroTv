package turneros.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import turneros.entidades.Turno;
import turneros.view.TablaTurnosUI;


public class MonitorView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private TablaTurnosUI tablaTurnos;
	private TvTunerPanel tvTuner;
	
	public MonitorView(int maximoTurnos,int tamanoTurnos,int tamanoEncabezadoTurnos,TvTunerPanel tvTuner){
		super();
		this.tvTuner = tvTuner;
		this.setLayout(new GridBagLayout());
		this.crearTvPanel();
		this.crearTablaTurnos(maximoTurnos,tamanoTurnos,tamanoEncabezadoTurnos);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setUndecorated(true);
		this.pack();
		this.show();
		this.recalculateTvScreen();
	}
	
	public void crearTablaTurnos(int maximoTurnos,int tamanoTurnos,int tamanoEncabezadoTurnos) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 1;
	    gbc.gridy = 0;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 1;
	    gbc.weightx = 0.35;
	    gbc.weighty = 1.0;
	    gbc.fill = GridBagConstraints.BOTH;
		tablaTurnos = new TablaTurnosUI(maximoTurnos,tamanoTurnos,tamanoEncabezadoTurnos);
		JScrollPane panel = new JScrollPane(tablaTurnos);
	    this.add(panel,gbc);
	}
	
	public void crearTvPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 1;
	    gbc.gridheight = 1;
	    gbc.weightx = 1.0;
	    gbc.weighty = 1.0;
	    gbc.fill = GridBagConstraints.BOTH;
		this.add(new JScrollPane(tvTuner),gbc);
		tvTuner.createGraph();
	}
	
	public void drawLastElement(Turno turno) {
		((TablaTurnosUI) tablaTurnos).agregarTurno(new String[]{turno.getTurno(),turno.getTaquilla()});
	}
	
	/***
	 * Funcion que agrega un turno
	 * @param turno
	 */
	public void addTurno(Turno turno) {
		if(turno!=null) {
			drawLastElement(turno);
		}
	}
	
	public void recalculateTvScreen() {
		tvTuner.recalculateTvScreen();
	}
	
	public static void main(String[] args) {
		TvTunerPanel tvTuner = new TvTunerPanel();
		MonitorView view = new MonitorView(5,25,34,tvTuner);
		view.show();
	}
}

