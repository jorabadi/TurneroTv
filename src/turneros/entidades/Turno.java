package turneros.entidades;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "turno")
public class Turno implements Serializable {
	@DatabaseField
	public String turno;
	@DatabaseField
	private String taquilla;
	
    public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	@DatabaseField
	private String fecha;
	
	public String getTurno() {
		return turno;
	}
	public void setTurno(String turno) {
		this.turno = turno;
	}
	public String getTaquilla() {
		return taquilla;
	}
	public void setTaquilla(String taquilla) {
		this.taquilla = taquilla;
	}
	
	
	public Turno(String turno,
		  String taquilla,
		  String fecha) {

          this.turno = turno;
          this.taquilla = taquilla;
          this.fecha = fecha;
    }
	public Turno() {} // For deserialization
	
	@Override
    public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("turno=").append(turno);
		sb.append(" taquilla=").append(taquilla);
		sb.append(" fecha=").append(fecha);
		return sb.toString();
    }
	
}
