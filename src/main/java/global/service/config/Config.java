package global.service.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.PropertiesConfigurationLayout;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Config {

	public static PropertiesConfiguration getConfiguracao() throws ConfigurationException, IOException {

		PropertiesConfiguration config = new PropertiesConfiguration();
		PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout();
		config.setLayout(layout);

		File file = new File("config.properties");

		if (!file.exists()) {

			/* Define configuration basic for APP */
			file.createNewFile();
			config.setHeader("Configuracoes - Banco - E-mail - etc");
			config.setProperty("db_host", "localhost");
			config.setProperty("db_local", "BANCO.FDB");
			config.setProperty("db_porta", "3050");
			config.setProperty("path_clientes", "Backups\\");
//			config.setHeader("# Configuracoes E-mail");
			config.setProperty("email_emitente_email", "");
			config.setProperty("email_emitente_nome", "");
			config.setProperty("email_emitente_passwd", "");
			config.setProperty("email_server_smtp", "");
			config.setProperty("email_server_smtp_porta", "");
			config.setProperty("email_server_smtp_ssl", false);
			config.setProperty("email_server_smtp_tls", false);

			layout.save(config, new FileWriter(file));
		} else {
			layout.load(config, new InputStreamReader(new FileInputStream(file)));
		}

		return config;
	}
}
