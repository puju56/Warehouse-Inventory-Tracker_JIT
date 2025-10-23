package edu.test;

public class Warehouse {
	
	
	interface StockObserver{
		void stockAdd(String Productname,int quantity);
		void stockRemove(String Productname,int quantity);
	}

	interface AlertService{
		void stockLow(String Productname,int quantity);
	}
	
//	implementation of alertService
	
	class WarehouseAlert implements AlertService{
		public void stockLow(String Productname,int quantity) {
			System.out.println("Alert: low stock for "+Productname+"! Only "+quantity+" Left" );
		}
	}
	
//	implementation of stockobserver
	
	

	public static void main(String[] args) {

			}

}
