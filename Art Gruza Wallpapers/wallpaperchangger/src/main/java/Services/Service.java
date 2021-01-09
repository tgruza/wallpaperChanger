package Services;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public interface Service {
    void setWallPaper(String destPath);
    void timer();
    void mkDestDir(Paths path);
    void setWallPaperDir(Paths path);
    List<File> getAllImagePaths (String path);
}
