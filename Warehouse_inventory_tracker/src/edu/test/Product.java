package edu.test;

public class Product {
   private String id;
   private String Productname;
  private int quantity;
  private int threshold;
  
  public Product(String id,String Productname,int quantity,int threshold) {
	  this.id=id;
	  this.Productname=Productname;
	  this.quantity=quantity;
	  this.threshold=threshold;
  }
   public String getId() {
	return id;
   }
   public void setId(String id) {
	this.id = id;
   }
   public String getProductname() {
	return Productname;
   }
   public void setProductname(String productname) {
	Productname = productname;
   }
   public synchronized void increaseQuantity(int amount) {
	this.quantity+=amount;
   }
   public synchronized void  decreaseQuantity(int amount) {
	this.quantity -=amount;
   }
   public int getThreshold() {
	return threshold;
   }
   public void setThreshold(int threshold) {
	this.threshold = threshold;
   }
   
   public String toString() {
	   return id+", "+Productname+", "+quantity+" ,"+threshold;
   }
}
