package Services;

import com.sun.jna.platform.win32.WinDef;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {
    private static List<File> fileList = new ArrayList<>();

    @Override
    public void setWallPaper(String destPath) {
        SPI.INSTANCE.SystemParametersInfo(
                new WinDef.UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
                new WinDef.UINT_PTR(0),
                destPath,
                new WinDef.UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));
    }

    @Override
    public List<File> getAllImagePaths(String path) {
        File[] pathNames;
        File f = new File(path);
        fileList.clear();

            if (f.isDirectory()) {
                pathNames = f.listFiles();
                assert pathNames != null;
                for (File pathName : pathNames) {
                    if (pathName.getAbsolutePath().contains(".jpg") || pathName.getAbsolutePath().contains(".jpeg")) {
                        fileList.add(pathName);
                    }
                }
            }
            return fileList;
    }

    @Override
    public void setWallPaperDir(Paths path) {

    }

    @Override
    public void timer() {

    }

    @Override
    public void mkDestDir(Paths path) {

    }
}
