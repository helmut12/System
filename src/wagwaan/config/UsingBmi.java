package wagwaan.config;

//create a class that will output the names, age and bmi for a specific individual

class UsingBmi{
	public static void main(String [] args){
		BMI b=new BMI("Helmut Maurice", 23, 57.0, 170.0);
		System.out.println(b.getNames()+" is "+b.getAge()+" years old and has a BMI of "+b.getBmi());
	}
}