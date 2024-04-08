public class MyField{
    private String first;
    private String second;
    private int third;
    public MyField(String first, String second,int third){
        this.first=first;
        this.second=second;
        this.third=third;
    }
    public String getFirst(){
        return first;
    }
    public String getSecond(){
        return second;
    }
    public int getThird(){
        return third;
    }
    public void setFirst(String first){
        this.first=first;
    }
    public void setSecond(String second){
        this.second=second;
    }
    public String toString(){
        return first.toString()+": "+second.toString();
    }
}
