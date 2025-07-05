package com.braintribe.logging.juli.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import com.braintribe.logging.Logger;
import com.braintribe.logging.Logger.LogLevel;

public class LogLevelManager {
	private static Logger logger = Logger.getLogger(LogLevelManager.class);

	private static Map<Level, LogLevel> levelToStd = new IdentityHashMap<>();

	private static boolean initialized;
	
	private static Map<String, String> configuredLevels;

	static {
		levelToStd.put(Level.FINEST, LogLevel.TRACE);
		levelToStd.put(Level.FINER, LogLevel.TRACE);
		levelToStd.put(Level.FINE, LogLevel.DEBUG);
		levelToStd.put(Level.CONFIG, LogLevel.INFO);
		levelToStd.put(Level.INFO, LogLevel.INFO);
		levelToStd.put(Level.WARNING, LogLevel.WARN);
		levelToStd.put(Level.SEVERE, LogLevel.ERROR);
	}

	public static LogLevel levelToStd(Level level) {
		return levelToStd.get(level);
	}

	public static Level levelToJul(LogLevel level) {
		switch (level) {
		case DEBUG:
			return Level.FINE;
		case ERROR:
			return Level.SEVERE;
		case INFO:
			return Level.INFO;
		case TRACE:
			return Level.FINER;
		case WARN:
			return Level.WARNING;
		default:
			return Level.INFO;
		}
	}

	public static void readLevelConfig(File file) {
		Map<String, String> levels = new HashMap<>();

		levels.putAll(readLevelsFromFile(file));
		levels.putAll(readLevelsFromDb());

		configureLevels(levels);
	}

	private static Map<String, String> readLevelsFromFile(File file) {
		if (file == null)
			return Collections.emptyMap();
		
		return readLevels(() -> new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
	}
	
	private static Map<String, String> readLevelsFromText(String text) {
		return readLevels(() -> new StringReader(text));
	}
	
	private static Map<String, String> readLevels(Callable<Reader> readerSupplier) {
		Properties properties = new Properties();
		try (Reader reader = readerSupplier.call()) {
			properties.load(reader);
		} catch (Exception e) {
			logger.error("Error while reading log level configuration from: " + file, e);
			return Collections.emptyMap();
		}
		
		return (Map<String, String>) (Map<?, ?>) properties;
	}

	private static Map<String, String> readLevelsFromDb() {
		Connection connection = tryOpenConnection();
		
		if (connection == null)
			return Collections.emptyMap();
		
		Map<String, String> logLevelMap = new HashMap<>();

		try (Statement stmt = connection.createStatement();
		     ResultSet rs = stmt.executeQuery("SELECT name, level FROM LOGCFG_LEVELS")) {

		    while (rs.next()) {
		        String name = rs.getString("name");
		        String level = rs.getString("level");
		        logLevelMap.put(name, level);
		    }

		} catch (SQLException e) {
			logger.error("Error while reading log level configuration from db", e);
		    return Collections.emptyMap();
		}
		
		return logLevelMap;
	}
	
	public static void updateLevels(String levels) {
		updateLevels(readLevelsFromText(levels));
	}
	
	public static void updateLevels(Map<String, String> levels) {
		
	}

	private static void configureLevels(Map<String, String> levels) {
		for (Map.Entry<String, String> entry : levels.entrySet()) {
			String name = entry.getKey();
			String level = entry.getValue();

			final java.util.logging.Logger julLogger;

			try {
				julLogger = java.util.logging.Logger.getLogger(name);
			} catch (Exception e) {
				logger.error("Error accessing configured logger: " + name, e);
				continue;
			}

			final LogLevel logLevel;

			try {
				logLevel = LogLevel.valueOf(level);
			} catch (Exception e) {
				logger.error("Invalid log level " + level + " for logger " + name);
				continue;
			}

			julLogger.setLevel(levelToJul(logLevel));
		}
	
		configuredLevels = levels;
	}

	/**
	 * Returns a JDBC connection if possible (configured and available) otherwise
	 * null
	 */
	private static Connection tryOpenConnection() {
		String url = System.getenv("HC_LOGCFG_DB_URL");

		if (url == null)
			return null;

		String user = System.getenv("HC_LOGCFG_DB_USER");
		String password = System.getenv("HC_LOGCFG_DB_PASSWORD");

		try {
			return initialize(DriverManager.getConnection(url, user, password));
		} catch (SQLException e) {
			logger.error("Could not open JDBC connection for logging configuration", e);
			return null;
		}
	}

	private static Connection initialize(Connection connection) {
		if (initialized)
			return connection;

		try (Statement stmt = connection.createStatement()) {
		    stmt.execute("CREATE TABLE IF NOT EXISTS LOGCFG_LEVELS (name VARCHAR(255) PRIMARY KEY, level VARCHAR(8) NOT NULL)");
		    initialized = true;
		    return connection;
		} catch (SQLException e) {
		    logger.error("Could not initialize LOGCFG_LEVELS table", e);
		    return connection;
		}
		
	}
}
