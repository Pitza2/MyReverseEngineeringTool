import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Consumer;
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
            Set<String> dependencies=new java.util.HashSet<String>();
            Pair[] fields;
            String interfaceNames[];
            if(clazz.getSuperclass()!=null && clazz.getSuperclass()!=Object.class)
            superClassName=clazz.getSuperclass().getName();

            methodNames= Arrays.stream(clazz.getDeclaredMethods()).map(x->x.getName()).toArray(String[]::new);


            for(var field : clazz.getDeclaredFields()) {
                dependencies.add(field.getGenericType().getTypeName().
                        replace("<", " of ").
                        replace(">", "").replace("interface ", ""));
                if (field.getGenericType() instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) field.getGenericType();
                    for (var type : pt.getActualTypeArguments()) {
                        dependencies.add(type.getTypeName().
                                replace("<", " of ").
                                replace(">", "").replace("interface ",""));
                    }
                }
            }

//            dependencies= Arrays.stream(clazz.getDeclaredFields()).filter(x->!x.getType().isPrimitive()).map( x ->
//                    {
//
//                        return x.getType().getName();
//                    }).
//                    collect(Collectors.toSet());
//            for(var methods: clazz.getDeclaredMethods()){
//                for(var vars: methods.getParameters()){
//                    dependencies.add(vars.getType().getName());
//                }
//            }
//            Set<String> methodDependencies=Arrays.stream(clazz.getDeclaredMethods()).
//                    flatMap(x->Arrays.stream(getMethodParameterTypesString(x))).collect(Collectors.toSet());
//            dependencies.addAll(methodDependencies);

            fields=Arrays.stream(clazz.getDeclaredFields()).filter(x->!x.getType().isPrimitive())
                    .map(x->new Pair<String,String>(x.getGenericType().toString().
                            replace("<"," of ").
                            replace(">",""),x.getName())).
                    toArray(Pair[]::new);
            interfaceNames= Arrays.stream(clazz.getInterfaces()).map(x->x.getName()).toArray(String[]::new);
            classN=className;
            ClassData cd = new ClassData(classN,superClassName, clazz.isInterface(), methodNames,dependencies,interfaceNames,fields);
            return cd;
        }catch (ClassNotFoundException e){
            System.out.println("Error loading class");
            return null;
        }
    }
    private String[] getMethodParameterTypesString(Method m){
        return Arrays.stream(m.getGenericParameterTypes()).map(x->x.toString().
                replace("<", " of ").
                replace(">","")).toArray(String[]::new);
    }
}
