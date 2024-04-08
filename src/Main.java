import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class Main {
    public static void main(String[] args) {

        JarProcessor jp = new JarProcessor("EventNotifier.jar");
        ClassDataProcessor cdp = new ClassDataProcessor(new yumlTool());
        System.out.println(cdp.processClassData(jp.processJar()));

    }
}