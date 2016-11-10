package test;

import dev.factory.AnnotationParser.Autowired;

public class boss {
  private office office;
  private car car;
  
  public boss() {  
  }
  
  @Autowired
  public boss(car car ,office office){
      this.car = car;
      this.office = office ;
  }

  public void setOffice(office office) {
	  this.office = office;
  }
  
  public void setCar(car car) {
	  this.car = car;
  }
    
  public String toString(){
	  return "this boss has "+car.toString()+" and in "+ office.toString();
  }
}
