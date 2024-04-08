import java.util.Set;
public class ClassData {
    private String className;
    private String superClassName;
    private String methodNames[];
    private Set<String> dependencies;

    private MyField fields[];
    private String interfaceNames[];

    boolean isInterface=false;
    public ClassData(String className, String superClassName,boolean isInterface, String methodNames[], Set<String> dependencies, String interfaceNames[], MyField fields[]) {
        this.className = className;
        this.methodNames = methodNames;
        this.dependencies = dependencies;
        this.fields=fields;//e redundanta aici dar te scapa de niste logica mai tarziu la toString
        this.interfaceNames = interfaceNames;
        this.superClassName = superClassName;
        this.isInterface=isInterface;
    }

    public String toString() {
        if(isInterface){
            String result="////////////////////////////////////////////////////////////////////\n";
            result += "Interface: " + className + "\n";
            result += "Methods: ";
            for (String methodName : methodNames) {
                result += methodName + " ";
            }
            result += "\nInterfaces: ";
            for (String interfaceName : interfaceNames) {
                result += interfaceName + " ";
            }
            return result;

        }
        String result="////////////////////////////////////////////////////////////////////\n";

        result += "Class: " + className + "\n";
        result += "Superclass: " + superClassName + "\n";
        result += "Methods: ";
        for (String methodName : methodNames) {
            result += methodName + " ";
        }
        result += "\nFields: ";
        for (var field : fields) {
            result += field + " ";
        }
        result += "\nInterfaces: ";
        for (String interfaceName : interfaceNames) {
            result += interfaceName + " ";
        }
        return result;
    }

    public String getClassName() {
        return className;
    }

    public MyField[] getFields() {
        return fields;
    }

    public Set<String> getDependencies() {
        return dependencies;
    }

    public String getSuperClassName() {
        return superClassName;
    }

    public String[] getInterfaceNames() {
        return interfaceNames;
    }

    public String[] getMethodNames() {
        return methodNames;
    }
}
