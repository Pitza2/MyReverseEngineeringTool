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

        System.out.println("Hello world!");
        JarProcessor jp = new JarProcessor("EventNotifier.jar");
        for (var cd : jp.processJar()) {
            System.out.println(cd);
        }

    }
}