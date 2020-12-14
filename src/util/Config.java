package util;

import datasource.DBConnectionOptions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static volatile Properties properties;
    private static Config instance;

    private Config() throws IOException {
        Properties props = new Properties();

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
        if (inputStream != null) {
            props.load(inputStream);
        } else {
            throw new FileNotFoundException("Unable to locate config file");
        }

        properties = props;
    }

    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    try {
                        instance = new Config();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return instance;
    }
    
    private String getDataSourceEnvironmentProperty(String key, boolean test) {
        return getProperty("dataSource." + (test ? "dev" : "prod") + "." + key);
    }
    
    public DBConnectionOptions getDatabaseOptions() {
        String dataSource = getProperty("dataSource");
        boolean test = JUnit.isJUnitTest();
        String host = getDataSourceEnvironmentProperty("host", test);
        String user = getDataSourceEnvironmentProperty("user", test);
        String database = getDataSourceEnvironmentProperty("database", test);
        String password = getDataSourceEnvironmentProperty("password", test);
        int poolSize = Integer.parseInt(getDataSourceEnvironmentProperty("poolSize", test));
        int port = Integer.parseInt(getDataSourceEnvironmentProperty("port", test));
        
        return new DBConnectionOptions(dataSource, host, user, database, password, poolSize, port, test);
    }
    
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
