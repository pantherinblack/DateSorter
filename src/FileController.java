import java.io.File;
import java.util.Locale;
import java.util.Vector;

public class FileController {
    public FileController(String path, String format) {
        format = format.toUpperCase(Locale.ROOT);
        String[] allFiles = new File(path).list();
        Vector<String> files = new Vector<>();

        for (String file : allFiles) {
            if (file.contains(".")) files.add(file);
        }

        if (allFiles.length > 0) {
            TimeFile[] timeFiles = new TimeFile[files.size()];
            for (int i = 0; i < timeFiles.length; i++) {
                timeFiles[i] = new TimeFile(path + "\\" + files.get(i));
            }

            for (TimeFile timefile : timeFiles) {
                StringBuilder exportPath = new StringBuilder(path + "\\");
                int dayCounter = 0;
                int monthCounter = 0;
                int yearCounter = 0;
                for (int c = 0; c < format.length(); c++) {
                    switch (format.charAt(c)) {
                        case 'D': {
                            exportPath.append(timefile.getCreationDay().charAt(dayCounter));
                            dayCounter++;
                        }
                        case 'M': {
                            exportPath.append(timefile.getCreationMonth().charAt(monthCounter));
                            monthCounter++;
                        }
                        case 'Y': {
                            exportPath.append(timefile.getCreationYear().charAt(yearCounter));
                            yearCounter++;
                        }
                        case '.':
                            exportPath.append(".");
                        case '-':
                            exportPath.append("\\");
                    }
                }
                timefile.exportFile(exportPath.toString());
            }
        }
    }
}
