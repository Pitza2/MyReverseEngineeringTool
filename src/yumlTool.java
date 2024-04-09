import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

public class yumlTool implements UmlTool {
    static String[] args;
    static String[] blackList;
    ArrayList<String> primitives =new ArrayList<>(Arrays.asList("int","float","double","long","short","byte","char","boolean","String"));
    ArrayList<String> primitiveArrays=new ArrayList<>(Arrays.asList("int[]","float[]","double[]","long[]","short[]","byte[]","char[]","boolean[]","String[]"));
    public String Output(ClassData[] classData, String[] a){
        this.args=a;
        if(Arrays.stream(args).anyMatch(x->x.equals("-b"))){
            blackList=args[Arrays.asList(args).indexOf("-b")+1].replace("}","").replace("{","").split(",");
        }

        StringBuilder sb = new StringBuilder();
        for (ClassData cd :classData){
            if(primitives.contains(truncatePackage(cd.getClassName()))) continue;
            if(Arrays.stream(blackList).anyMatch(x->cd.getClassName().contains(x)))continue;
            //add methods and fields
            sb.append("[");
            sb.append(truncatePackage(cd.getClassName()));
            sb.append("|");
            if(!Arrays.stream(args).anyMatch(x->x.equals("-a"))) {
                for (MyField field : cd.getFields()) {
                    if(Arrays.stream(blackList).anyMatch(x->field.getFirst().contains(x))) continue;

                    if (Modifier.isPrivate(field.getThird()))
                        sb.append("- ");
                    else if (Modifier.isPublic(field.getThird()))
                        sb.append("+ ");
                    else if (Modifier.isProtected(field.getThird()))
                        sb.append("# ");

                    sb.append(truncatePackage(field.getFirst())).append(": ")
                            .append(truncatePackage(field.getSecond())).append("; ");
                }
            }

            sb.append("|");
            if(!Arrays.stream(args).anyMatch(x->x.equals("-m"))) {
                for (String methodName : cd.getMethodNames()) {
                    sb.append(methodName).append("();");
                }
            }
                sb.append("]\n");
                //add Interface inheritance
                for (String interfaceName : cd.getInterfaceNames()) {
                    if (interfaceName.isEmpty() || Arrays.stream(blackList).anyMatch(x -> interfaceName.contains(x))) continue;
                        sb.append("[").append(truncatePackage(interfaceName)).append("]^-.-[")
                                .append(truncatePackage(cd.getClassName())).append("]\n");
                }
            //add class inheritance
            if(!cd.getSuperClassName().isEmpty() && ! Arrays.stream(blackList).anyMatch(x->cd.getSuperClassName().contains(x)))
            sb.append("[").append(truncatePackage(cd.getSuperClassName())).append("]^-[").append(truncatePackage(cd.getClassName())).append("]\n");
            //add association
            for(String association : cd.getAssociations()){
                if(isPrimitive(truncatePackage(association))) continue;
                sb.append("[").append(truncatePackage(cd.getClassName())).append("]->[").append(truncatePackage(association)).append("]\n");
            }
            //add dependencies
            if(!cd.isInterface)
            for(String dependency : cd.getDependencies()){
                if(isPrimitive(truncatePackage(dependency)) || Arrays.stream(blackList).anyMatch(x->dependency.contains(x))) continue;
                sb.append("[").append(truncatePackage(cd.getClassName())).append("]uses-.->[")
                        .append(truncatePackage(dependency)).append("]\n");
            }
        }
        return sb.toString();
    }
    boolean isPrimitive(String className){

        return primitives.contains(className) || primitiveArrays.contains(className);
    }
    private String truncatePackage(String className){

        if(Arrays.stream(args).anyMatch(x->x.equals("-n"))) return className;

        return className.substring(className.lastIndexOf(".")+1);
    }
}
