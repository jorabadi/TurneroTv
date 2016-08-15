package turneros.bo;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import turneros.repository.TurnoRepository;
import turneros.entidades.Taquilla;
import turneros.entidades.Turno;
import turneros.configuration.Configuration;

public class TurnoBO {
	private TurnoRepository turnoRepository;
	private int minTurno = 0;
	private int maxTurno = 0;
	private String ruta = "";
	
	public TurnoBO() {
		this.ruta = Configuration.getPreference("databaseUrl");
		turnoRepository = new TurnoRepository(this.ruta);
		this.minTurno = Integer.parseInt(Configuration.getPreference("minTurno"));
		this.maxTurno = Integer.parseInt(Configuration.getPreference("maxTurno"));
	}
	
	public Turno obtenerTurno(Taquilla taquilla) throws SQLException {
	    int numTurno = turnoRepository.obtenerTurno(minTurno,maxTurno);
	  
	    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	    Date fecha = new Date();
	    
	    Turno turno = new Turno(Integer.toString(numTurno),taquilla.getCodigoTaquilla(),dateFormat.format(fecha));
	    turno = turnoRepository.guardarTurno(turno);
		return turno;
	}
	
	public static void main (String []args) {
		Taquilla taquilla = new Taquilla();
		taquilla.setCodigoTaquilla("1");
		taquilla.setNombre("Taquilla 1");
		TurnoBO turnoBo = new TurnoBO();
		try {
			
			turnoBo.obtenerTurno(taquilla);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
