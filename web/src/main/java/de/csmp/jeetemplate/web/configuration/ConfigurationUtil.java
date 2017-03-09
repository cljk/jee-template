package de.csmp.jeetemplate.web.configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.DatabaseConfiguration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.BasicConfigurationBuilder;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.csmp.jeetemplate.common.AppConstants;

public class ConfigurationUtil {
	@Inject
	private static final Logger log = LogManager.getLogger(ConfigurationUtil.class);
	
	private static final String CONFIG_FILE_NAME = "jee-template.conf";

	private static final String DATABASE_CONFIGURATION_VALUE_COLUMN = "CONFIGURATION_VALUE";
	private static final String DATABASE_CONFIGURATION_KEY_COLUMN = "CONFIGURATION_KEY";
	private static final String DATABASE_CONFIGURATION_TABLE = "CONFIGURATION";
	
	private static final String CONFIGURATION_VERSION_PROPERTIES = "version.properties";
	private static final String CONFIGURATION_PROD_CONFIGURATION_PROPERTIES = "defaultConfiguration.properties";
	private static final String CONFIGURATION_DEFAULT_CONFIGURATION_PROPERTIES = "defaultConfiguration.properties";

	/**
     * setup Config
	 * 
	 * @param dataSource Datasource for overriding configuration
	 * @return ready to use Apache Commons configuration object (CompositeConfiguration)
	 */
	public static CompositeConfiguration initConfiguration(DataSource dataSource) {
		log.info(">> initConfiguration");
		Parameters dbParams = new Parameters();
		BasicConfigurationBuilder<DatabaseConfiguration> builderDatabase = null;

		if (dataSource != null) {
			log.debug("load config from DataSource");
			builderDatabase = 
			     new BasicConfigurationBuilder<DatabaseConfiguration>(DatabaseConfiguration.class);
			builderDatabase.configure(
					dbParams.database()
			         .setDataSource(dataSource)
			         .setTable(DATABASE_CONFIGURATION_TABLE)
			         .setKeyColumn(DATABASE_CONFIGURATION_KEY_COLUMN)
			         .setValueColumn(DATABASE_CONFIGURATION_VALUE_COLUMN)
			     );
		} else {
			log.info("skip config from DataSource - dataSource is null");
		}
		
		
		Parameters propertyFileParams = new Parameters();
		
		
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderFileSystemConfigProperties = null;
		File configFile = null;
		configFile = new File(CONFIG_FILE_NAME);
		if (configFile.exists()) {
			try {
				builderFileSystemConfigProperties = loadConfigFromFile(propertyFileParams, configFile);
			} catch (Exception ex) {
				log.error(ex.getMessage());
			}
		} else {
			configFile = new File("/etc/" + CONFIG_FILE_NAME);
			if (configFile.exists()) {
				try {
					builderFileSystemConfigProperties = loadConfigFromFile(propertyFileParams, configFile);
				} catch (Exception ex) {
					log.error(ex.getMessage());
				}
			} else {
				log.warn("filesystem configuration {} missing", CONFIG_FILE_NAME);
			}
		}
		
//		URL prodURL = ConfigurationUtil.class.getClassLoader().getResource(CONFIGURATION_PROD_CONFIGURATION_PROPERTIES);
//		log.debug("load config from {}", prodURL);
//		FileBasedConfigurationBuilder<FileBasedConfiguration> builderProdProperties;
//		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
//		    .configure(propertyFileParams.properties()
//		        .setURL(prodURL));
		
		URL versionURL = ConfigurationUtil.class.getClassLoader().getResource(CONFIGURATION_VERSION_PROPERTIES);
		log.debug("load config from {}", versionURL);
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderVersionProperties =
		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(propertyFileParams.properties()
		        .setURL(versionURL));
		
		URL defaultConfigurationURL = ConfigurationUtil.class.getClassLoader().getResource(CONFIGURATION_DEFAULT_CONFIGURATION_PROPERTIES);
		log.debug("load config from {}", defaultConfigurationURL);
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderDefaultConfiguration =
		    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
		    .configure(propertyFileParams.properties()
		        .setURL(defaultConfigurationURL));

		// the properties of configurations that were added first hide the property values of configurations added later
		CompositeConfiguration config = new CompositeConfiguration();
		try {
			if (builderDatabase != null) {
				config.addConfiguration(builderDatabase.getConfiguration());
			}
			if (builderFileSystemConfigProperties != null) {
				config.addConfiguration(builderFileSystemConfigProperties.getConfiguration());
			}
			config.addConfiguration(builderDefaultConfiguration.getConfiguration());
			config.addConfiguration(builderVersionProperties.getConfiguration());
		} catch (ConfigurationException ex) {
			throw new RuntimeException("Configuration setup failed: " + ex.getMessage());
		}
		
		log.info("<< initConfiguration - environment: " + config.getString("environment"));
		return config;
	}


	private static FileBasedConfigurationBuilder<FileBasedConfiguration> loadConfigFromFile(Parameters propertyFileParams, File configFile) throws MalformedURLException {
		log.debug("load config from {}", configFile.getAbsolutePath());
		FileBasedConfigurationBuilder<FileBasedConfiguration> builderFileSystemConfigProperties;
		builderFileSystemConfigProperties =  new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
			    .configure(propertyFileParams.properties()
				        .setURL(configFile.toURI().toURL()));
		return builderFileSystemConfigProperties;
	}
	
	/**
	 * assume configuration object is initialized
	 * 
	 * @param configuration Apache Configuration object
	 */
	public static void initAppConstantsFromConfiguration(Configuration configuration) {
		//log.info(">> initAppConstantsFromConfiguration");
		String myVersion = configuration.getString("version");
		String buildTimestamp = configuration.getString("build.timestamp");

		if (StringUtils.isEmpty(myVersion)) {
			log.warn("myVersion undefined - cannot init APP-Version");
		} else {
			AppConstants.APP_VERSION = myVersion;
			
			if (StringUtils.isEmpty(buildTimestamp)) {
				log.warn("buildTimestamp undefined - cannot init APP-Buildtime");
			} else {
				AppConstants.APP_FULL_VERSION = myVersion + " " + buildTimestamp;
				AppConstants.APP_BUILDTIME = buildTimestamp;
			}
		}
		//log.info("<< initAppConstantsFromConfiguration");
	}
}
