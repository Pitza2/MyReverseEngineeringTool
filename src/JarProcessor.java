import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class JarProcessor {
    private File jarFile;
    private URLClassLoader urlClassLoader;
    public JarProcessor(String filePath){
        jarFile = new File(filePath);
        try {
            urlClassLoader = new URLClassLoader(new URL[]{jarFile.toURI().toURL()});
        }catch (MalformedURLException e){
            System.out.println("Error creating URLClassLoader");
        }
    }
    public ClassData[] processJar(){
        JarFile jf;
        ArrayList <ClassData> classDataArr = new ArrayList<>();
        try{
            jf = new JarFile(jarFile);
        }catch (IOException e){
            System.out.println("Error opening jar file");
            return null;
        }
        var entries=jf.entries();
        entries.asIterator().forEachRemaining(x->{
            if(!x.getRealName().endsWith(".class")){return;}
            String classPackageName= x.getRealName().replace("/",".").replace(".class","");
            classDataArr.add(getClassData(classPackageName));
        });
        return classDataArr.toArray(ClassData[]::new);
    }
    private ClassData getClassData(String className){
        try {
            Class<?> clazz = urlClassLoader.loadClass(className);
            String classN;
            String superClassName="";
            String methodNames[];
            Set<String> fieldTypes;
            Pair<String,String> fields[];
            String interfaceNames[];
            if(clazz.getSuperclass()!=null && clazz.getSuperclass()!=Object.class)
            superClassName=truncatePackage(clazz.getSuperclass().getName());

            methodNames= Arrays.stream(clazz.getDeclaredMethods()).map(x->x.getName()).toArray(String[]::new);

            fieldTypes= Arrays.stream(clazz.getDeclaredFields()).map(x->
                            truncatePackage(x.getType().getName())).
                    collect(Collectors.toSet());

            fields=Arrays.stream(clazz.getDeclaredFields())
                    .map(x->new Pair<String,String>(truncatePackage(x.getType().getName()),x.getName())).
                    toArray(Pair[]::new);
            interfaceNames= Arrays.stream(clazz.getInterfaces()).map(x->truncatePackage(x.getName())).toArray(String[]::new);
            classN=truncatePackage(className);
            ClassData cd = new ClassData(classN,superClassName, clazz.isInterface(), methodNames,fieldTypes,interfaceNames,fields);
            return cd;
        }catch (ClassNotFoundException e){
            System.out.println("Error loading class");
            return null;
        }
    }
    private String truncatePackage(String className){
        return className.substring(className.lastIndexOf(".")+1);
    }

}
