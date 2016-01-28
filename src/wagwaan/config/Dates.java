package wagwaan.config;

class Dates{
    public static void main(String[] args) {
        Dates d=new Dates();
        d.tarehe();
        System.out.println("\n\n\n");
        d.termcalculation();
    }
    void tarehe(){
    String j="std. 1";
        String k="std. 2";
        String l="baby class";
        String A=j.substring(5, 6);
        String B=k.substring(5, 6);
        System.out.println("A is "+A);
        System.out.println("B is "+B);
        int a=Integer.valueOf(A);
        int b=Integer.valueOf(B);
        ++a;
        System.out.println("a is "+a);
        if(a!=b)
            
        System.out.println("you cannot upgrade to more than one class ");
        else
            System.out.println("new class  is std. "+a);
    }
    void termcalculation(){
    String term_id1="1/15";
    String term_id2="2/15";
    String term_id3="3/15";
    
    String t1=term_id1.substring(0, 1);
    String t2=term_id2.substring(0, 1);
    String t3=term_id3.substring(0, 1);
    String year=term_id1.substring(2, 4);
    
    System.out.println("term 1: "+t1);
    System.out.println("term 2: "+t2);
    System.out.println("term 3: "+t3);
    System.out.println("year: "+year);
    
    if(t1.equals(String.valueOf(1)))
        System.out.println("Previous term is: "+(Integer.valueOf(t1)+2)+"/"+(Integer.valueOf(year)-1));
    if(t2.equals(String.valueOf(2)))
        System.out.println("Previous term is: "+(Integer.valueOf(t2)-1)+"/"+(Integer.valueOf(year)));
    if(t3.equals(String.valueOf(3)))
        System.out.println("Previous term is: "+(Integer.valueOf(t3)-1)+"/"+(Integer.valueOf(year)));
    }
    
        
}
