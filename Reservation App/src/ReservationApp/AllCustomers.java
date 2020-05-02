package ReservationApp;
import java.util.ArrayList;

public class AllCustomers {

	// properties
	
	private static ArrayList<Customer> allCustomers; 
	final private int topCustomers = 3; // top kaç istiyosak o
	
	// constructors
	
	public AllCustomers() {
		allCustomers = new ArrayList<Customer>();
	}

	// methods
	
	public void addCustomer( Customer c)
	{
		allCustomers.add(c);	
	}
	
	/*
	private int partition(ArrayList<Customer> customers, int beg, int end)
	{		
		Customer pivot = customers.get(end);
		int i = beg - 1;
		
		for( int j = beg; j < end; j++)
		{
			if(customers.get(j).getPoints() > pivot.getPoints())
			{
				i++;
				
				Customer swapTemp = customers.get(i);
				customers.set(i, customers.get(j));
				customers.set(j, swapTemp);
			}
		}
		
		Customer swapTemp = customers.get(i + 1);
		customers.set(i + 1, customers.get(end));
		customers.set(end, swapTemp);
		
		return i + 1;
	}
	
	private void quickSort(ArrayList<Customer> customers, int beg, int end)
	{
		if(beg < end)
		{
			int partitionIndex = partition( customers, beg, end);
			
			quickSort(customers, beg, partitionIndex - 1);
			quickSort(customers, partitionIndex + 1, end);
		}
	}
	
	public void sortCustomers()
	{
		quickSort(allCustomers, 0, allCustomers.size() - 1);
	}
	*/
	
	public Customer[] getBestCustomers()
	{
		Customer[] bestCustomers = new Customer[topCustomers];
		
		for( int i = 0; i < bestCustomers.length; i++)
		{
			bestCustomers[i] = allCustomers.get(i);
			// will be deleted
			System.out.println(bestCustomers[i].getUserName() + "  " + bestCustomers[i].getPoints());
		}
		
		return bestCustomers;
	}
	
	
	public static boolean rankCustomer(Customer c)
	{
		if(allCustomers.size() > 1)
		{
			for ( int i = allCustomers.indexOf(c) - 1; i >= 0; i--)
			{
				if (c.getPoints() < allCustomers.get(i).getPoints())
				{
					allCustomers.remove(c);
					allCustomers.add(i+1, c);
					return false;
				}
			}
			
			// eðer fordan çýkabildiyse demek ki en yüksek puana sahip, c'yi index = 0 a atamak lazým
			allCustomers.remove(c);
			allCustomers.add(0, c);
			return false;
		}
		else
		{
			// do nothing
			return false;
		}
	}
}

