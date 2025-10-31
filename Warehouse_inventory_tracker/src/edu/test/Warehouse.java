package edu.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Warehouse {

	interface StockObserver {
		void stockAdd(String Productname, int quantity);

		void receiveShipment(String Productname, int quantity);

		void fullFillOrder(String productname, int quantity);
	}

	interface AlertService {
		void stockLow(String Productname, int quantity);

		void stockUpdate(String Productname, int quantity);
	}

//	implementation of alertService

	class WarehouseAlert implements AlertService {

		public void stockLow(String Productname, int quantity) {

			String Alertmsg = "Alert: low stock for " + Productname + "! Only " + quantity + " Left";
			System.out.println(Alertmsg);
		}

//		@Override
		public void stockUpdate(String Productname, int quantity) {

			String msg = "Stock updated for " + Productname + " current quantity: " + quantity;
			System.out.println(msg);

		}
	}

//	implementation of stockobserver

	class wareHouseObserver implements StockObserver {
		private AlertService alertService;
		private Map<String, Integer> stockmap;
		private final int Low_Stock_ThresHold = 5;

		public wareHouseObserver(AlertService alertService) {
			this.alertService = alertService;
			this.stockmap = new HashMap<>();
		}

		@Override
		public void stockAdd(String Productname, int quantity) {
			int currQty = stockmap.getOrDefault(Productname, 0);
			currQty += quantity;
			alertService.stockUpdate(Productname, quantity);
			stockmap.put(Productname, currQty);

		}

		@Override
		public void receiveShipment(String Productname, int quantity) {
			System.out.println("Receive Shipment for " + Productname + " qauntity : " + quantity);
			stockAdd(Productname, quantity);
		}

		@Override
		public void fullFillOrder(String productname, int quantity) {
			int currQty = stockmap.getOrDefault(productname, 0);
			if (quantity > currQty) {
				System.out.println("can't fullfill-order for " + productname + " not enough stock");
				return;
			}
			currQty -= quantity;
			if(currQty<=Low_Stock_ThresHold)
			{
				alertService.stockLow(productname, currQty);
			}

			System.out.println("fullfill-order for " + productname + " quantity: " + quantity);

		}

		public void stockLow(String Productname, int quantity) {
			if (quantity <= Low_Stock_ThresHold) {
				alertService.stockLow(Productname, quantity);
			}
		}

		public void showStock() {
			System.out.println("Current Warehouse Stock: ");
			if (stockmap.isEmpty()) {
				System.out.println("No item in stock. ");
			} else {
				for (Map.Entry<String, Integer> entry : stockmap.entrySet()) {
					System.out.println(entry.getKey() + ": " + entry.getValue());
				}
			}
			System.out.println();
		}

	}

	public static void main(String[] args) {

		Warehouse outer = new Warehouse();
		AlertService alertService = outer.new WarehouseAlert();
		wareHouseObserver warehobserver = outer.new wareHouseObserver(alertService);
		Scanner sc = new Scanner(System.in);
		Map<String,wareHouseObserver>warehouseList=new HashMap<>();
		int choice;

		System.out.println("---WAREHOUSE INVENTORY TRACKER----");

		while (true) {
			System.out.println("1. Add Stock ");
			System.out.println("2. Receive Shipment ");
			System.out.println("3. Fullfill Order ");
			System.out.println("4. Show Stock ");
			System.out.println("5.Create Warehouse ");
			System.out.println("0. Exit ");

			System.out.println("Enter your choice: ");
			choice = sc.nextInt();
			sc.nextLine();

			switch (choice) {
			case 1:
				System.out.println(" Enter Warehouse Name: ");
				String w1 = sc.nextLine();
				wareHouseObserver wobj1=warehouseList.get(w1);
				if(wobj1==null) {
					System.out.println("warehouse not found ");
					break;
				}

				System.out.println("Enter Item Name: ");
				String Additem = sc.nextLine();
				System.out.println("Enter Quantity to add: ");
				int addQty = sc.nextInt();
				wobj1.stockAdd(Additem, addQty);
//				warehobserver.stockAdd(Additem, addQty);
				System.out.println("Stock added sucessfully!");
				break;

			case 2:
				
				System.out.println(" Enter Warehouse Name: ");
				String w2 = sc.nextLine();
				wareHouseObserver wobj2=warehouseList.get(w2);
				if(wobj2==null) {
					System.out.println("warehouse not found ");
					break;
				}
				
				System.out.println("Enter Item Name: ");
				String shipmentItem = sc.nextLine();
				System.out.println("Enter Shipment Quantity: ");
				int shipmentQty = sc.nextInt();
				warehobserver.receiveShipment(shipmentItem, shipmentQty);
				break;

			case 3:
				System.out.println(" Enter Warehouse Name: ");
				String w3 = sc.nextLine();
				wareHouseObserver wobj3=warehouseList.get(w3);
				if(wobj3==null) {
					System.out.println("warehouse not found ");
					break;
				}
				System.out.println(" Enter Item Name: ");
				String orderitem = sc.nextLine();
				System.out.println("Enter order quantity: ");
				int orderQty = sc.nextInt();
				warehobserver.fullFillOrder(orderitem, orderQty);
				break;

			case 4:
				System.out.println(" Enter Warehouse Name: ");
				String w4 = sc.nextLine().trim();
				wareHouseObserver wobj4=warehouseList.get(w4);
				if(wobj4==null) {
					System.out.println("warehouse not found ");
					break;
				}
//				warehobserver.showStock();
				wobj4.showStock();
				
				

			case 5:
				System.out.println(" Enter Warehouse Name: ");
				String wname = sc.nextLine();
				warehouseList.put(wname, outer.new wareHouseObserver (alertService));
				System.out.println(" warehouse name : "+wname);
				
				break;
				
			case 0:
				System.out.println("Exiting.....");

			default:
				System.out.println("Invalid choice try again");

			}

		}

	}

}
