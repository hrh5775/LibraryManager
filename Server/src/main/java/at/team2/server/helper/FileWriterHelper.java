package at.team2.server.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class FileWriterHelper {
    public static synchronized void writeFile(String content, String fileName, String directory, boolean append) throws IOException {
        File dir = new File(directory);

        if(!dir.exists()) {
            dir.mkdirs();
        }

        File file = Paths.get(directory, fileName).toFile();

        FileWriter fileWriter = new FileWriter(file.toString(), append);

        IOException exception = null;

        try {
            fileWriter.write(content);
        } catch (IOException e) {
            exception = e;
        } finally {
            fileWriter.close();

            if(exception != null) {
                throw exception;
            }
        }
    }
}