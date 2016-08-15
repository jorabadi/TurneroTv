package turneros.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Configuration {

	private static Properties properties = null;
	private static String configurationFile = "conf.xml";
	private static File config;

	public static void initProperties() {
		config = new File(configurationFile);
		properties = new Properties();
	}

	// Writing and Saving Configurations
	public static void setPreference(String Key, String Value) {
		try {
			initProperties();
			properties.setProperty(Key, Value);
			OutputStream oStream = new FileOutputStream(config);
			properties.storeToXML(oStream, "Configuracion");
			oStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void setPreferences(Map<String, String> preferencias) {
		try {
			initProperties();
			Map<String, String> pref = loadConfigurations();
			
			for (String kPreferencia : preferencias.keySet()) {
				pref.put(kPreferencia, preferencias.get(kPreferencia));
			}
			
			for (String kPreferencia : pref.keySet()) {
				properties.setProperty(kPreferencia,
						pref.get(kPreferencia));
			}

			OutputStream oStream = new FileOutputStream(config);
			properties.storeToXML(oStream, "Configuracion");
			oStream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Reading Configurations
	public static String getPreference(String Key) {
		try {
			initProperties();
			InputStream f = new FileInputStream(config);
			properties.loadFromXML(f);
			f.close();
		} catch (IOException ex) {
			java.util.logging.Logger.getLogger(Configuration.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(Configuration.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}
		return (properties.getProperty(Key));
	}

	public static HashMap<String, String> loadConfigurations() {
		initProperties();
		try {
			InputStream f = new FileInputStream(config);
			properties.loadFromXML(f);
			f.close();
		} catch (IOException ex) {
			java.util.logging.Logger.getLogger(Configuration.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(Configuration.class.getName())
					.log(java.util.logging.Level.SEVERE, null, ex);
		}

		Enumeration propiedades = properties.keys();
		HashMap<String, String> parameters = new HashMap<String, String>();

		while (propiedades.hasMoreElements()) {
			String key = (String) propiedades.nextElement();
			String value = (String) properties.get(key);
			parameters.put(key, value);
		}
		return parameters;
	}
}