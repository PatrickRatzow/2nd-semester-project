package util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static volatile Properties properties;
    public static Config instance;

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

    public static Config getInstance() throws IOException {
        if (instance == null) {
            synchronized(Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }

        return instance;
    }

    public Properties getProperties() {
        return properties;
    }

    public static String getProperty(String key) throws IOException {
        return Config.getInstance().getProperties().getProperty(key);
    }
}
