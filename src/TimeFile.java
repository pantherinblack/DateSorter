import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class TimeFile extends File {
    private String[] creationDate;

    public TimeFile(String src) {
        super(src);
        try {
            BasicFileAttributes fileTime = Files.readAttributes(this.toPath(), BasicFileAttributes.class);
            creationDate = fileTime.lastModifiedTime().toInstant().toString().split("T")[0].split("-");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getCreationDates() {
        return creationDate;
    }

    public String getCreationDate() {
        return creationDate[2] + "." + creationDate[1] + "." + creationDate[0];
    }

    public String getCreationYear() {
        return creationDate[0];
    }

    public String getCreationMonth() {
        return creationDate[1];
    }

    public String getCreationDay() {
        return creationDate[2];
    }

    public void exportFile(String path) {
        if (new File(path + this.getName()).exists())
            System.out.println("File" + getName() + "alreadyExistsinexpecteddictionary,overriding");
        try {
            if (!new File(path).exists()) Files.createDirectories(Paths.get(path));
            Files.copy(Paths.get(this.getAbsolutePath()), Paths.get(path + "\\" + this.getName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignored) {
        }
    }
}
