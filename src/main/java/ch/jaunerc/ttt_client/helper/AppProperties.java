package ch.jaunerc.ttt_client.helper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    public final static String DEFAULT_FILENAME = "app_properties";
    private final static String PROPERTY_COMMENT = "Properties for Tic Tac Toe client";

    private Properties properties;

    public AppProperties() {
        properties = new Properties();
    }

    public void loadPropertiesFromFile(final String fileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new IOException("Loading was not successfully. Msg: " + e.getMessage());
        }
    }

    public void savePropertiesToFile(final String fileName) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            properties.store(fos, PROPERTY_COMMENT);
        } catch (IOException e) {
            throw new Exception("Saving was not successfully. Msg: " + e.getMessage());
        }
    }

    public String getPropertyByName(final PropertyInfo propertyInfo) {
        return properties.getProperty(propertyInfo.name, propertyInfo.defaultValue);
    }

    public enum PropertyInfo {
        BACKEND_ADDRESS("backend_address", "localhost"), BACKEND_PORT("backend_port", "8080"),
        BACKEND_PATH("backend_path", "minimax");

        String name;
        String defaultValue;

        PropertyInfo(String name, String defaultValue) {
            this.name = name;
            this.defaultValue = defaultValue;
        }
    }
}
