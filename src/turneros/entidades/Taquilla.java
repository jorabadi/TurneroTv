package turneros.entidades;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "taquilla")
public class Taquilla implements Serializable {
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigoTaquilla() {
		return codigoTaquilla;
	}
	public void setCodigoTaquilla(String codigoTaquilla) {
		this.codigoTaquilla = codigoTaquilla;
	}
	@DatabaseField
	private String nombre;
	@DatabaseField(id = true)
	private String codigoTaquilla;
	
}
