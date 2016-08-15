package turneros.repository;


import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;

import turneros.connection.SqlLiteConnection;
import turneros.entidades.Turno;


public class TurnoRepository {
	private Dao<Turno, Integer> turnoDao;
	private SecuenciaRepository seqRepo;
	
	public TurnoRepository(String ruta) {
		JdbcConnectionSource con;
		
		try {
			con = SqlLiteConnection.getConnection(ruta);
			turnoDao = DaoManager.createDao(con,Turno.class);
			seqRepo = new SecuenciaRepository(ruta);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public Turno guardarTurno(Turno turno) throws SQLException {
		turnoDao.create(turno);
		System.out.println("Se guarda el turno:"+turno.getTurno());
		return turno;
	}
	
	public int obtenerTurno(int minTurno,int maxTurno) throws SQLException {
		return seqRepo.proximaSecuencia("turno",minTurno,maxTurno).getValor();
	}
	
}
