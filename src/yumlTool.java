import java.util.ArrayList;
import java.util.Arrays;

public class yumlTool implements UmlTool {
    ArrayList<String> primitives =new ArrayList<>(Arrays.asList("int","float","double","long","short","byte","char","boolean","String"));
    public String Output(ClassData[] classData) {
        StringBuilder sb = new StringBuilder();
        for (ClassData cd :classData){
            if(primitives.contains(truncatePackage(cd.getClassName()))) continue;
            //add methods and fields
            sb.append("[");
            sb.append(truncatePackage(cd.getClassName()));
            sb.append("|");
            for(Pair<String,String> field : cd.getFields()){
                sb.append(truncatePackage(field.getFirst())).append(": ")
                        .append(truncatePackage(field.getSecond())).append("; ");
            }
            sb.append("|");
            for(String methodName : cd.getMethodNames()){
                sb.append(methodName).append("();");
            }sb.append("]\n");
            //add Interface inheritance
            for(String interfaceName : cd.getInterfaceNames()){
                if(!interfaceName.isEmpty())
                sb.append("[<<").append(truncatePackage(interfaceName)).append(">>]^-.-[")
                        .append(truncatePackage(cd.getClassName())).append("]\n");
            }
            //add class inheritance
            if(!cd.getSuperClassName().isEmpty())
            sb.append("[").append(truncatePackage(cd.getSuperClassName())).append("]^-[").append(truncatePackage(cd.getClassName())).append("]\n");
            //add dependencies
            for(String dependency : cd.getDependencies()){
                if(primitives.contains(truncatePackage(dependency))) continue;
                sb.append("[").append(truncatePackage(cd.getClassName())).append("]uses-.->[")
                        .append(truncatePackage(dependency)).append("]\n");
            }
        }
        return sb.toString();
    }
    private String truncatePackage(String className){
        return className.substring(className.lastIndexOf(".")+1);
    }
}