import java.util.Set;
public class ClassData {
    private String className;
    private String superClassName;
    private String methodNames[];
    private Set<String> dependencies;

    private Pair<String,String> fields[];
    private String interfaceNames[];

    boolean isInterface=false;
    public ClassData(String className, String superClassName,boolean isInterface, String methodNames[], Set<String> dependencies, String interfaceNames[], Pair<String,String> fields[]) {
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

}
