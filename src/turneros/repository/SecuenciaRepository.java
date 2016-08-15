package turneros.repository;


import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.UpdateBuilder;

import turneros.connection.SqlLiteConnection;
import turneros.entidades.Turno;
import turneros.entidades.TurnoSecuencia;


public class SecuenciaRepository {
	private Dao<TurnoSecuencia, Integer> turnoSecuenciaDao;
	
	public SecuenciaRepository(String ruta) {
		JdbcConnectionSource con;
		try {
			con = SqlLiteConnection.getConnection(ruta);
			turnoSecuenciaDao = DaoManager.createDao(con,TurnoSecuencia.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public TurnoSecuencia proximaSecuencia(String seq,int min,int max) throws SQLException {
		long valor = turnoSecuenciaDao.queryRawValue("select valor from turno_secuencia where nombre ='"+seq+"'");
		valor++;
		valor = (valor == 0) ? min:(valor);
		if(valor > max) { // en caso de que llegue al maximo se devuelve al menor turo
			valor = min;
		}
		TurnoSecuencia turnoSeq = new TurnoSecuencia();
		turnoSeq.setNombre(seq);
		turnoSeq.setValor((int)valor);
		//turnoSecuenciaDao.update(turnoSeq);
		UpdateBuilder<TurnoSecuencia, Integer> updateBuilder = turnoSecuenciaDao.updateBuilder();
		updateBuilder.where().eq("nombre", seq);
		updateBuilder.updateColumnValue("valor", valor);
		updateBuilder.update();
		System.out.println("Se genera la secuencia:"+Integer.toString(turnoSeq.getValor()));
		return turnoSeq;
	}
	
	public TurnoSecuencia guardarTurno(TurnoSecuencia ts) throws SQLException {
		List<TurnoSecuencia> lsTs = turnoSecuenciaDao.queryBuilder().where().eq("nombre", ts.getNombre()).query();
		System.out.println("Registros turno secuencia:"+lsTs.size());
		if(lsTs.size() > 0) {
			System.out.println("Update ");
			UpdateBuilder<TurnoSecuencia, Integer> updateBuilder = turnoSecuenciaDao.updateBuilder();
			updateBuilder.where().eq("nombre", ts.getNombre());
			updateBuilder.updateColumnValue("valor", ts.getValor());
			updateBuilder.update();
		} else {
			System.out.println("Insertar ");
			turnoSecuenciaDao.create(ts);
		}
		return ts;
	}
	
	public TurnoSecuencia getSecuencia(String seq) throws SQLException {
		long valor = turnoSecuenciaDao.queryRawValue("select valor from turno_secuencia where nombre ='"+seq+"'");
		TurnoSecuencia turnoSeq = new TurnoSecuencia();
		turnoSeq.setNombre(seq);
		turnoSeq.setValor((int)valor);
		return turnoSeq;
	}
	
}
