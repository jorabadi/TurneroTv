package turneros.bo;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import turneros.repository.SecuenciaRepository;
import turneros.repository.TurnoRepository;
import turneros.entidades.ConfiguracionTurnero;
import turneros.entidades.Taquilla;
import turneros.entidades.Turno;
import turneros.entidades.TurnoSecuencia;
import turneros.configuration.Configuration;

public class ConfiguracionTurneroBO {
	private SecuenciaRepository seqRepository;
	private String ruta = "";
	
	public ConfiguracionTurneroBO() {
		this.ruta = Configuration.getPreference("databaseUrl");
		seqRepository = new SecuenciaRepository(this.ruta);
	}
	
	public void guardarConfiguracionTurnero(ConfiguracionTurnero ct) throws SQLException {
		Map<String, String> preferencias = new HashMap<String, String>();
		preferencias.put("maximoTurnos", ct.getMaximoTurnosMostrar());
		preferencias.put("tamanoTurnos", ct.getAltoEncabezadoTurnos());
		preferencias.put("maximoValorTurno", ct.getNumeroMaximoTurnosTabla());
		preferencias.put("minTurno", ct.getTurnoMinimo());
		preferencias.put("maxTurno", ct.getTurnoMaximo());
		preferencias.put("baudios", ct.getBaudios());
		preferencias.put("puertoSerial", ct.getPuertoSerial());
		preferencias.put("nombreAudiofile", ct.getAudioTurno());
		preferencias.put("canaTvInicial", ct.getCanalTv());
		preferencias.put("nombreDispositivoVideo", ct.getDispositivoVideoTvTuner());
		preferencias.put("nombreDispositivoAudio", ct.getDispositivoAudioTvTuner());
		preferencias.put("nombreFiltroVideo", ct.getFiltroVideoTvTuner());
		preferencias.put("volumenTv", ct.getVolumenTvTuner());
		preferencias.put("volumenTurnero", ct.getVolumenTurno());
		Configuration.setPreferences(preferencias);
		TurnoSecuencia ts = new TurnoSecuencia();
		ts.setNombre("turno");
		ts.setValor(Integer.parseInt(ct.getTurnoActual()));
		seqRepository.guardarTurno(ts);
	}
	
	public ConfiguracionTurnero loadConfiguracionTurnero() throws SQLException {
		ConfiguracionTurnero ct = new ConfiguracionTurnero();
		ct.setMaximoTurnosMostrar(Configuration.getPreference("maximoTurnos"));
		ct.setAltoEncabezadoTurnos(Configuration.getPreference("tamanoTurnos"));
		ct.setNumeroMaximoTurnosTabla(Configuration.getPreference("maximoValorTurno"));
		ct.setTurnoMinimo(Configuration.getPreference("minTurno"));
		ct.setTurnoMaximo(Configuration.getPreference("maxTurno"));
		ct.setBaudios(Configuration.getPreference("baudios"));
		ct.setPuertoSerial(Configuration.getPreference("puertoSerial"));
		ct.setAudioTurno(Configuration.getPreference("nombreAudiofile"));
		ct.setCanalTv(Configuration.getPreference("canaTvInicial"));
		ct.setDispositivoVideoTvTuner(Configuration.getPreference("nombreDispositivoVideo"));
		ct.setDispositivoAudioTvTuner(Configuration.getPreference("nombreDispositivoAudio"));
		ct.setFiltroVideoTvTuner(Configuration.getPreference("nombreFiltroVideo"));
		ct.setVolumenTvTuner(Configuration.getPreference("volumenTv"));
		ct.setVolumenTurno(Configuration.getPreference("volumenTurnero"));
		
		TurnoSecuencia ts = seqRepository.getSecuencia("turno");
		ct.setTurnoActual(String.valueOf(ts.getValor()));
		return ct;
	}
	
}
