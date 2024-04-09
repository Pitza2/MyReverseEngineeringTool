public class ClassDataProcessor {
    UmlTool umlTool;
    public ClassDataProcessor(UmlTool umlTool){
        this.umlTool=umlTool;
    }
    public String processClassData(ClassData[] classData, String[] args){
        return umlTool.Output(classData,args);
    }
}
