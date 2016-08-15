package turneros.entidades;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "turno_secuencia")
public class TurnoSecuencia implements Serializable {
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}

	@DatabaseField
	public String nombre;
	@DatabaseField
	private int valor;
	@DatabaseField(id = true, columnName = "codigo_secuencia")
	private int codigoSecuencia;
	
	public int getCodigoSecuencia() {
		return codigoSecuencia;
	}
	public void setCodigoSecuencia(int codigoSecuencia) {
		this.codigoSecuencia = codigoSecuencia;
	}
	public TurnoSecuencia(String nombre,
		  int valor) {

          this.valor = valor;
          this.nombre = nombre;
    }
	public TurnoSecuencia() {} // For deserialization
	
	@Override
    public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("nombre=").append(nombre);
		sb.append(" valor=").append(valor);
		return sb.toString();
    }
	
}
