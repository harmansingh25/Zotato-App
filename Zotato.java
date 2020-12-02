import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

class Reader {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
            //TODO add check for eof if necessary
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
    static long nextLong() throws IOException {
        return Long.parseLong( next() );
    }
   	
    
}






interface Common{
	
	public void printDetails();
	public double giveDisc(double n);
	public void displayRewards();
	

	
}


class User{
	
	protected final String name;
	protected final  String location;
	protected double rewards;
	
	public User(String n, String l) {
		
		name=n;
		location=l;
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getLoc() {
		return location;
	}
	
	public double  getRewards() {
		return rewards;
	}
	
	public void setRewards(double x) {
		rewards+=x;
	}
	
	
	
}


class Customer extends User implements Common{
	

	
	protected double wallet;

	
	private Queue<String> recentOrders;

	
	private HashMap<FoodItem,Restaurant> cartH;
	
	public Customer(String n, String l) {
		
		super(n,l);
		
		recentOrders=new LinkedList<String>();

		wallet=1000;
		rewards=0;


		cartH=new HashMap<FoodItem,Restaurant>();
		
	}
			
	
	public void addToCart(FoodItem f,Restaurant r) {

		cartH.put(f,r);
		
	}
	
	
	
	public int charge() {
		return 40;
	}
	public HashMap<FoodItem,Restaurant> getCartH(){
		return cartH;
	}
	public double getWallet() {
		return this.wallet;
	}
	
	public void setWallet(double bill) {
		this.wallet-=bill;
	}

	
	@Override
	public void printDetails() {
		
		System.out.println(name+", "+location+", "+wallet+"/-");
	}

	
	@Override
	public double giveDisc(double total) {
		return total;
	}
	
	public void useRewards(double total) {
		rewards-=total;
	}
	
	public Queue<String> getRecentOrders(){
		return recentOrders;
	}
	
	public void addRecentOrder(String s) {
		
		if(recentOrders.size()>=10) {
			recentOrders.poll();
		}
		recentOrders.add(s);
		
	}
	
	public void displayRecentOrders() {
		
		for(String s: recentOrders) {
			System.out.println(s);
		}
		
	}
	
	@Override
	public void displayRewards() {
		System.out.println("Total Rewards: "+rewards);
	}
	
	
	
	public void selectRestaurant(Customer cus, HashMap<Integer,Restaurant> restaurants)throws IOException {
		
		
	
		
			
		
		int choice=Reader.nextInt();
		Restaurant r=restaurants.get(choice);
		System.out.println("Choose item by code");
		HashMap<Integer,FoodItem> items=r.getItems();
		for(int ID: items.keySet()) {
			System.out.print(ID+" ");
			FoodItem f=items.get(ID);
			System.out.print(r.getName()+" - "+f.getName()+" "+f.getPrice()+" "+f.getQuan()+" "+f.getDisc()+"% off "+f.getCat());
			System.out.println();
			
			
		}
		int chosen=Reader.nextInt();
		System.out.println("Enter item quantity - ");
		int quan=Reader.nextInt();
		
		FoodItem f=items.get(chosen);
		
		
		FoodItem inCart=new FoodItem(f.getName(),f.getPrice(),f.getID(),quan,f.getDisc(),f.getCat());
		
		cus.addToCart(inCart,r);
		
		System.out.println("Items added to cart");
		
		
		
		
		
		
	}
	
	
	public void checkOut(Customer c)throws IOException {
		
		Queue<String> recent=c.getRecentOrders();
		
		
		System.out.println("Items in Cart - ");
		
		HashMap<FoodItem,Restaurant> items=c.getCartH();
		
		for(FoodItem f:items.keySet()) {
			
			System.out.println(f.getID()+" "+items.get(f).getName()+" - "+f.getName()+" - "+f.getPrice()+" - "+f.getQuan()+" - "+f.getDisc()+"% off ");
			
		}
		System.out.println("Delivery charge - "+c.charge()+"/-");
		
		double totalCharge=0;
		
		Restaurant r=new Restaurant("","");
		
		String BI="";
		
		for(FoodItem f:items.keySet()) {
			
		
			
			totalCharge+=(f.getPrice()-((double)(f.getDisc()*f.getPrice()))/(double)100)*(double)f.getQuan();
			
			r=items.get(f);
			
			BI+=f.getName()+", ";
			
			
			
			
			
			
		}
		
		
		
		totalCharge=r.giveDisc(totalCharge);
		
		totalCharge=c.giveDisc(totalCharge);
		
		double pre=totalCharge;
		
		totalCharge+=c.charge();
		
		double x=totalCharge;
		
		if(x>c.getRewards()+c.getWallet()) {
			
			System.out.println("Insufficient Balance.");
			System.out.println("      1)Delete items");
			
			int y=Reader.nextInt();
			
			
			while(totalCharge>c.getRewards()+c.getWallet()) {
			System.out.println("Select an item by ID to reduce quantity");
			for(FoodItem f:items.keySet()) {
				
				System.out.println(f.getID()+" "+items.get(f).getName()+" - "+f.getName()+" - "+f.getPrice()+" - "+f.getQuan()+" - "+f.getDisc()+"% off ");
				
			}
			
			
			
			int z=Reader.nextInt();
			
			System.out.println("Enter changed Quantity : ");
			
			int nquan=Reader.nextInt();
			
			for(FoodItem f : items.keySet()) {
				
				if(f.getID()==z) {
					
					f.setQuan(nquan);
					break;
				}
			}
			
			totalCharge=0;
			
			r=new Restaurant("","");
			
			BI="";
			
			for(FoodItem f:items.keySet()) {
				
			
				
				totalCharge+=(f.getPrice()-((double)(f.getDisc()*f.getPrice()))/(double)100)*(double)f.getQuan();
				
				r=items.get(f);
				
				
				if(f.getQuan()!=0)
				BI+=f.getName()+", ";
				
				
			}
			
			
			totalCharge=r.giveDisc(totalCharge);
			
			totalCharge=c.giveDisc(totalCharge);
			
			pre=totalCharge;
			
			totalCharge+=c.charge();
			}
			
			
			
			
		}
		
		int totalQuan=0;
		for(FoodItem f: items.keySet()) {
			totalQuan+=f.getQuan();
		}
		
		
		
		
		if(totalQuan>0) {
		double forRewards=totalCharge;
		
		
		double forWallet=0;
		
		
		
		int flag1=0;
		int flag2=0;
		
		if(c.getRewards()>forRewards) {
			c.setRewards(-forRewards);
			flag1=1;
		}
		else if(c.getRewards()<forRewards && c.getRewards()!=0) {
			
			forWallet=forRewards-c.getRewards();
			
			c.setRewards(-c.getRewards());
			flag2=1;
			
			
			
		}
		
		
		
		if(flag2==1) {
			
			c.setWallet(forWallet);
		}
			
			
		else if(flag1!=1)	
		c.setWallet(totalCharge);
		
		
		

		
		Zotato.setBal(0.01*pre);
		
		int rewardss= r.giveRewards(pre);
		
		c.setRewards(rewardss);
		r.setRewards(rewardss);
		
		Zotato.setDelc(c.charge());

		
		if(totalQuan>0) {
		System.out.println("Total order value - INR "+totalCharge+"/-");
		System.out.println("    1)Proceed to checkout");
		int n=Reader.nextInt();
		}

		
		String s="Bought item: "+BI+"Quantity: "+totalQuan+" for RS "+(totalCharge-c.charge()+" from Restaurant: "+r.getName()+" and Delivery Charge :" +c.charge());
		
		if(totalQuan>0) {
		c.addRecentOrder(s);
		
		System.out.println(totalQuan + " items have been bought for INR "+totalCharge+"/-");
		System.out.println();
		}
		
		
		
		c.getCartH().clear();
		
		if(totalQuan>0) {
			r.addOrder();
		}
		}
		
		
		
		
	}
	
}

class EliteC extends Customer{
	
	public EliteC(String n,String l) {
		super(n,l);
	}
	
	@Override
	public int charge() {
		return 0;
	}
	
	@Override
	public void printDetails() {
		
		System.out.println(name+"(Elite), "+location+", "+wallet+"/-");
	}
	
	@Override
	public String getName() {
		return name+" (Elite) ";
	}
	
	@Override
	public double giveDisc(double total) {
		if(total>200) {
			return total-50;
		}
		else {
			return total;
		}
	}
	
	
	
}

class SpecialC extends Customer{
	
	public SpecialC(String n,String l) {
		super(n,l);
	}
	
	@Override
	public int charge() {
		return 20;
	}
	
	@Override
	public void printDetails() {
		
		System.out.println(name+"(Special), "+location+", "+wallet+"/-");
	}
	
	@Override
	public String getName() {
		return name+" (Special) ";
	}
	@Override
	public double giveDisc(double total) {
		if(total>200) {
			return total-25;
		}
		else {
			return total;
		}
	}
	
	
}
class Restaurant extends User implements Common{
	
	protected int discount;

	
	private HashMap<String,FoodItem> foodItems2;
	private HashSet<FoodItem> items;
	private HashMap<Integer,FoodItem> foodItems;
	
	private int nOrders;
	
	public Restaurant(String n,String l) {
		
		super(n,l);
		
		//this.name=name;
		foodItems2= new HashMap<String,FoodItem>();
		foodItems=new HashMap<Integer,FoodItem>();
		discount=0;
		nOrders=0;
	}
	
	public void addItem(FoodItem food) {
		
		foodItems2.put(food.getName(), food);
		foodItems.put(food.getID(), food);
	
	}
	public HashMap<Integer,FoodItem> getItems(){
		return foodItems;
	}
	
	public String getName() {
		return name;
	}
	public double getRewards() {
		return rewards;
	}
	
	
	
	public void setRewards(double r) {
		
		rewards+=r;
	}
	public void giveDiscount(int d) {
		
		this.discount=0;
		return;
	}
	
	@Override
	public double giveDisc(double total) {
		return total-(total*this.discount)/100;
	}
	
	
	public int giveRewards(double bill) {
		
		return 5* (int)(bill/(double)100);
	}
	
	public void addOrder() {
		
		nOrders++;
		
	}
	
	@Override
	public void displayRewards() {
		System.out.println("Reward Points: "+rewards);
	}
	
	public void addFoodItem(Restaurant r)throws IOException{
		
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Enter food item details");
		System.out.println("Food name:");
		String name=sc.nextLine();
		System.out.println("item price:");
		int price=Reader.nextInt();
		System.out.println("item quantity:");
		int quantity=Reader.nextInt();
		System.out.println("item category");
		String category=sc.nextLine();
		System.out.println("offer:");
		int offer=Reader.nextInt();
		FoodItem food=new FoodItem(name,price,0,quantity,offer,category);
		
		r.addItem(food);
		
		//printDetails(food);
		
		System.out.println(food.getID()+" "+name+" "+price+" "+quantity+" "+offer+"% off "+category);
		
		
	}
	
	@Override 
	public void printDetails() {
		
		
		System.out.println(name+", "+location+", "+nOrders+ " orders taken");
	}
	
	public void editFoodItem(Restaurant r)throws IOException{
		System.out.println("Choose item by code");
		HashMap<Integer,FoodItem> items=r.getItems();
		for(int ID: items.keySet()) {
			System.out.print(ID+" ");
			FoodItem f=items.get(ID);
			System.out.print(r.getName()+" - "+f.getName()+" "+f.getPrice()+" "+f.getQuan()+" "+f.getDisc()+"% off "+f.getCat());
			System.out.println();
			
			
		}
		int ID=Reader.nextInt();
		System.out.println("Choose an attribute to edit:");
		System.out.println("1) Name");
		System.out.println("2) Price");
		System.out.println("3) Quantity");
		System.out.println("4) Category");
		System.out.println("5) offer");
		int changeNO=Reader.nextInt();
		FoodItem toBeChanged=items.get(ID);
		if(changeNO==1) {
			System.out.print("Enter the new name - ");
			
			toBeChanged.setName(Reader.next());
			System.out.println();
			
		}
		else if(changeNO==2) {
			System.out.print("Enter the new price - ");
			
			toBeChanged.setPrice(Reader.nextInt());
			System.out.println();
		}
		else if(changeNO==3) {
			System.out.print("Enter the new quantity - ");
			toBeChanged.setQuan(Reader.nextInt());
			System.out.println();
		}
		else if(changeNO==4) {
			System.out.print("Enter the new category - ");
			toBeChanged.setCat(Reader.next());
			System.out.println();
		}
		else if(changeNO==5) {
			System.out.print("Enter the new offer - ");
			toBeChanged.setOffer(Reader.nextInt());
			System.out.println();
		}
		System.out.println(ID+" "+this.getName()+" "+toBeChanged.getName()+" "+toBeChanged.getPrice()+" "+toBeChanged.getQuan()+" "+toBeChanged.getDisc()+"% off "+ toBeChanged.getCat());
		System.out.println();
		
	}

	
	
	
	
	
	
	
}

class FastFoodR extends Restaurant{
	
	public FastFoodR(String n,String l) {
		super(n,l);
	}
	
	@Override
	public void giveDiscount(int d) {
		
		
		this.discount=d;
	}
	
	@Override
	public int giveRewards(double bill) {
		
		return 10* (int)(bill/(double)150);
	}
	
	@Override
	public String getName() {
		return name+" (Fast Food)";
	}
	
	

	
	
	
	
}
class AuthenticR extends Restaurant{
	
	
	
	public AuthenticR(String n,String l) {
		super(n,l);
	}
	
	@Override
	public void giveDiscount(int d) {
		this.discount=d;
	}
	
	@Override
	public double giveDisc(double total) {
		
		
		total=total-(total*this.discount)/100;
		if(total>100) {
			total-=50;
		}
		return total;
	}
	
	@Override
	public int giveRewards(double bill) {
		
		return 25* (int)(bill/(double)200);
	}
	
	@Override
	public String getName() {
		return name+" (Authentic)";
	}
	
	
}

class FoodItem{
	
	final private int ID;
	
	private String name;
	private int price;
	
	private int quantity;
	private int discount;
	private String category;
	
	private static int counter=1;

	
	
	public FoodItem(String n,int p,int i,int q,int d,String c) {
		
		name=n;
		price=p;
		if(i==0) {
		ID=counter++;}
		else {
			ID=i;
		}
		quantity=q;
		discount=d;
		category=c;
		
	}
	public String getName() {
		return name;
	}
	public int getID() {
		return ID;
	}
	public int getPrice() {
		return price;
	}
	public int getQuan() {
		return quantity;
	}
	public int getDisc() {
		return discount;
	}
	public String getCat() {
		return category;
	}
	public void setName(String jj) {
		name=jj;
		
	}
	public void setPrice(int p) {
		price=p;
	}
	public void setQuan(int p) {
		quantity=p;
	}
	public void setOffer(int p) {
		discount=p;
	}
	public void setCat(String jj) {
		category=jj;
		
	}
}

public class Zotato {
	
	
	
	private static HashMap<String,Restaurant> restaurants=new HashMap<String,Restaurant>();
	
	private static HashMap<Integer,Restaurant> restaurants2=new HashMap<Integer,Restaurant>();
	
	private static HashMap<Integer,Customer> customers=new HashMap<Integer,Customer>();
	
	private int idGen=0;
	
	static private double balance;
	
	static private int deliverC;
	
	
	

	
	
	public void offerTotalBill(Restaurant r)throws IOException {
		
		System.out.println("Enter offer on total bill value - ");
		int offer=Reader.nextInt();
		
	}
	
	public void checkUserDetails()throws IOException{
		
		for(int e: customers.keySet()) {
			System.out.println(e+". "+customers.get(e).getName());
		}
		int n=Reader.nextInt();
		
		customers.get(n).printDetails();
		
	}
	
	static public void companyDet() {
		
		System.out.println("Total Company balance - INR "+balance+"/-");
		System.out.println("Total Delivery Charges Collected - INR "+deliverC+"/-");
	}
	
	static public void setBal(double x) {
		balance+=x;
	}
	
	static public void setDelc(int x) {
		deliverC+=x;
	}
	

	public static void main(String[] args)throws IOException {
		// TODO Auto-generated method stub
		
		Reader.init(System.in);
		
		
		Customer c1=new EliteC("Ram","Delhi");
		Customer c2=new EliteC("Sam","Pune");	  //POLYMORPHISM
		Customer c3=new SpecialC("Tim","Delhi");  //POLYMORPHISM
		Customer c4=new Customer("Kim","Amritsar");
		Customer c5=new Customer("Jim","Lucknow");
		
		Restaurant r1=new AuthenticR("Shah","Delhi");  //POLYMORPHISM
		Restaurant r2=new Restaurant("Ravi's","Pune");
		Restaurant r3=new AuthenticR("The Chinese","Lucknow"); //POLYMORPHISM
		Restaurant r4=new FastFoodR("Wang's","Amritsar");  //POLYMORPHISM
		Restaurant r5=new Restaurant("Paradise","Ludhiana");
		
		
		customers.put(1,c1);
		customers.put(2,c2);
		customers.put(3,c3);
		customers.put(4,c4);
		customers.put(5,c5);
		
		restaurants2.put(1,r1);
		restaurants2.put(2,r2);
		restaurants2.put(3,r3);
		restaurants2.put(4,r4);
		restaurants2.put(5,r5);
		
		
		int c=0;
		while(c!=5) {
			
			
			
			System.out.println("Welcome to Zotato:");
			System.out.println("   1) Enter as Restaurant Owner");
			System.out.println("   2) Enter as Customer");
			System.out.println("   3) Check User Details");
			System.out.println("   4) Company Account details");
			System.out.println("   5) Exit");
			
			
			c=Reader.nextInt();
			
			if(c==5) {
				break;
			}
			
			else if(c==1) {
				
				System.out.println("Choose Restaurant");
				for(int e: restaurants2.keySet()) {
					
					System.out.println("   "+e+") "+restaurants2.get(e).getName());
				}
				
				Restaurant chosen=restaurants2.get(Reader.nextInt());
				
				int x=0;
				
				while(x!=5) {
					
					System.out.println("Welcome " + chosen.getName());
					System.out.println("   1) Add item");
					System.out.println("   2) Edit item");
					System.out.println("   3) Print rewards");
					System.out.println("   4) Discount on bill value");
					System.out.println("   5) Exit");
					
					x=Reader.nextInt();
					
					if(x==5) {
						break;
					}
					
					else if(x==1) {
						chosen.addFoodItem(chosen);
					}
					else if(x==2) {
						chosen.editFoodItem(chosen);
					}
					else if(x==3) {
						
						chosen.displayRewards();
					}
					else if(x==4) {
						System.out.print("Enter offer on total bill value - ");
						
						chosen.giveDiscount(Reader.nextInt());
						
						System.out.println();
					}
					
					
				
				}
				
				
				
				
				
				
			}
			else if(c==2) {
				
				for(int e: customers.keySet()) {
					System.out.println("    "+e+") "+customers.get(e).getName());
				}
				
				Customer chosen= customers.get(Reader.nextInt());
				
				int x=0;
				
				while(x!=5) {
					
					System.out.println("Welcome "+chosen.getName());
					System.out.println("Customer Menu");
					System.out.println("1) Select Restaurant");
					System.out.println("2) Checkout cart");
					System.out.println("3) Reward won");
					System.out.println("4) print the recent orders");
					System.out.println("5) Exit");
					
					x=Reader.nextInt();
					
					if(x==5) {
						break;
					}
					else if(x==1) {
						
						System.out.println("Choose Restaurant");
						for(int e: restaurants2.keySet()) {
							
							System.out.println("   "+e+") "+restaurants2.get(e).getName());
						}
						
						
						
						chosen.selectRestaurant(chosen, restaurants2);
					}
					else if(x==2) {
						chosen.checkOut(chosen);
					}
					else if(x==3) {
						
						
						chosen.displayRewards();
					}
					else if(x==4) {
						
						chosen.displayRecentOrders();
						
					}
					
				}
				
			}
			
			else if(c==3) {
				
				System.out.println("1) Cutomer List");
				System.out.println("2) Restaurant List");
				
				int x=Reader.nextInt();
				if(x==1) {
					
					for(int e: customers.keySet()) {
						System.out.println("    "+e+") "+customers.get(e).getName());
					}
					
					Customer chosen = customers.get(Reader.nextInt());
					chosen.printDetails();
					
				}
				

				else if(x==2) {
					
					for(int e: restaurants2.keySet()) {
						
						System.out.println("   "+e+") "+restaurants2.get(e).getName());
					}
					
					Restaurant chosen=restaurants2.get(Reader.nextInt());
					chosen.printDetails();
					
				}
				
				
			}
			
			else if(c==4) {
				
				companyDet();
				
			}
			
			
			
			
			
			
			
			
		}
		
		
		

	}

}
