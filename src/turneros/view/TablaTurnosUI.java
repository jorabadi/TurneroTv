package turneros.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

public class TablaTurnosUI extends JTable {
	private int maximoTurnos;
	private int tamanoTurnos;
	private int tamanoEncabezadoTurnos;
	private FlashRenderer renderer;
	
	public TablaTurnosUI(int maximoTurnos,int tamanoTurnos,int tamanoEncabezadoTurnos) {
		
		this.maximoTurnos = maximoTurnos;
		this.tamanoTurnos = tamanoTurnos;
		this.tamanoEncabezadoTurnos = tamanoEncabezadoTurnos;
		
		DefaultTableModel model = new DefaultTableModel();
	    model.addColumn("TURNO");
	    model.addColumn("TAQ");
	    this.setModel(model);
	    this.setPreferredScrollableViewportSize(this.getPreferredSize());
	    this.setShowVerticalLines(true);
	    this.setGridColor(Color.decode("#005EAA"));
	    this.setEnabled(true);
	    this.setRowHeight(tamanoTurnos+20);
	     
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
	    
	    renderer = new FlashRenderer(this,this.tamanoTurnos);
	    renderer.setHorizontalAlignment(JLabel.CENTER);
	    this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    int tW = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	    int anchoTurno = (int)(0.3 * 0.35 * tW);
	    this.getColumnModel().getColumn(0).setCellRenderer( renderer );
	    this.getColumnModel().getColumn(1).setCellRenderer( renderer );
	    this.getColumnModel().getColumn(0).setPreferredWidth(anchoTurno);
        this.setAutoResizeMode( JTable.AUTO_RESIZE_LAST_COLUMN );
	    
	     //estilo de la tabla
	    JTableHeader header = this.getTableHeader();
	    header.setBackground(Color.decode("#388479"));
	    header.setForeground(Color.WHITE);
	    header.setFont(new Font("Arial", Font.CENTER_BASELINE, tamanoEncabezadoTurnos));
	    
	  /*  SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	renderTablaTurno();
	        }
	    });*/
	    
	}
	
	public void agregarTurno(String[] turno) {
		System.out.println("Agregar turno tabla");
		DefaultTableModel model = (DefaultTableModel) this.getModel();
		if(model.getRowCount() == this.maximoTurnos) {
			model.removeRow(model.getRowCount() - 1);
		}
        model.addRow(turno);
        model.moveRow(model.getRowCount() - 1, model.getRowCount() - 1, 0);
		this.validate();
		this.repaint();
		renderer.flashRow();
	}
	
	
	public void renderTablaTurno() {
		int w = this.getWidth();
		System.out.println("W:"+w);
		int anchoTurno = (int)(0.7 * w);
	    int anchoTaquilla = (int)(0.3 * w);
	    this.getColumnModel().getColumn(0).setPreferredWidth(anchoTurno);
	    this.getColumnModel().getColumn(1).setPreferredWidth(anchoTaquilla);
	    this.repaint();
	}
	
	
}
