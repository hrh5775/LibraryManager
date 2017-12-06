package at.team2.client.configuration;

import java.io.*;
import java.util.Map;

public class ConfigurationManager {
    public static Configuration loadConfiguration(File configurationFile, String delimiter) throws IOException {
        if(configurationFile != null && configurationFile.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(configurationFile));

            String line;
            String[] tmp;
            String tmpValue;

            Configuration config = new Configuration();

            while((line = reader.readLine()) != null) {
                tmp = line.split(delimiter);

                if(tmp.length == 2) {
                    tmpValue = tmp[1].trim();

                    switch (tmp[0].trim()) {
                        case Configuration.VERSION:
                            config.setVersion(Double.parseDouble(tmpValue));
                            break;
                        case Configuration.ROOT_DIR:
                            config.setRootDir(tmpValue);
                            break;
                        case Configuration.APP_NAME:
                            config.setAppName(tmpValue);
                            break;
                        case Configuration.HEIGHT:
                            config.setHeight(Integer.parseInt(tmpValue));
                            break;
                        case Configuration.WIDTH:
                            config.setWidth(Integer.parseInt(tmpValue));
                            break;
                        case Configuration.SHOW_CLOSE_WARNING:
                            config.setShowCloseWarning(Boolean.parseBoolean(tmpValue));
                            break;
                        case Configuration.SHOW_MENU_BAR:
                            config.setShowMenuBar(Boolean.parseBoolean(tmpValue));
                            break;
                        case Configuration.SERVER_URL:
                            config.setServerURL(tmpValue);
                            break;
                        case Configuration.ADDITIONAL_SERVER_URL_EXTENSION:
                            config.setAdditionalServerUrlExtension(tmpValue);
                            break;
                        case Configuration.PORT:
                            config.setPort(Integer.parseInt(tmpValue));
                            break;
                        case Configuration.GLASSFISH_DIRECTORY:
                            config.setGlassfishDirectory(tmpValue);
                            break;
                        case Configuration.USE_EJB:
                            config.setUseEJB(Boolean.parseBoolean(tmpValue));
                            break;
                        /*case Configuration.OPEN_IN_WEBBROWSER:
                            config.setOpenInWebbrowser(Boolean.parseBoolean(tmpValue));
                            break;
                        case Configuration.USE_JX_BROWSER:
                            config.setUseJxBrowser(Boolean.parseBoolean(tmpValue));
                            break;
                        case Configuration.USE_WEB_CLIENT:
                            config.setUseWebClient(Boolean.parseBoolean(tmpValue));
                            break;*/
                        default:
                            break;
                    }
                } else {
                    reader.close();
                    throw new IOException("format is not valid");
                }
            }

            reader.close();

            if(config.getVersion() < Configuration._version) {
                System.out.println("old configuration detected, regenerate the configuration, but use old settings");

                if(config.getVersion() == 1.1) {
                    Configuration configuration = new Configuration();
                    configuration.setRootDir(config.getRootDir());
                    configuration.setAppName(config.getAppName());
                    configuration.setHeight(config.getHeight());
                    configuration.setWidth(config.getWidth());
                    configuration.setShowCloseWarning(config.getShowCloseWarning());
                    configuration.setShowMenuBar(config.getShowMenuBar());

                    if(config.getGlassfishDirectory() != null || config.getGlassfishDirectory() != "./") {
                        // this settings are normally not available in this version, but due to a missing versioning system in the old app version we have to support this settings
                        configuration.setPort(config.getPort());
                        configuration.setGlassfishDirectory(config.getGlassfishDirectory());
                    }

                    config = configuration;
                    System.out.println(config.getVersion());
                }
            }

            return config;
        }

        throw new FileNotFoundException("configuration not found");
    }

    public static boolean saveConfiguration(Configuration conf, String delimiter, File file, boolean createIfNotExist) throws IOException {
        if(file != null) {
            if(!file.exists()) {
                if (createIfNotExist) {
                    file.createNewFile();
                } else {
                    return false;
                }
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String, Object> item : conf.getList()) {
                writeLine(writer, item.getKey(), item.getValue().toString(), delimiter);
            }

            writer.flush();
            writer.close();

            return true;
        }

        return false;
    }

    private static void writeLine(Writer writer, String confKey, String confValue, String delimiter) throws IOException {
        writer.write(confKey + delimiter + confValue + "\n");
    }
}