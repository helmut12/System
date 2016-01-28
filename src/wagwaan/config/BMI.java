package wagwaan.config;


public class BMI{
	private String names;
	private int age;
	private double weight;
	private double height;
	
	public BMI(String names, int age, double weight, double height){
		this.names=names;
		this.age=age;
		this.weight=weight;
		this.height=height;
	}
	
	public String getNames(){
		return names;
	}
	public int getAge(){
		return age;
	}
	public double getWeight(){
		return weight;
	}
	public double getHeight(){
		return height;
	}
	public double getBmi(){
		double weight=getWeight();
		double height=getHeight();
		double BMI=weight/(Math.pow(height/100, 2));
		return BMI;
	}
}