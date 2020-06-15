package beepbeeptests;

import java.util.HashMap;
import java.util.Map;

class ARPTable 
{ 

    private static ARPTable arp_instance = null; 

    public static Map<String, String> arpTab; 

    private ARPTable() 
    { 
        arpTab = new HashMap<>();
    } 

    public static ARPTable getInstance() 
    { 
        if (arp_instance == null) 
        	arp_instance = new ARPTable(); 
  
        return arp_instance; 
    } 
    
    public void insertIntoARP(String key, String value)
    {  	
    	arpTab.put(key, value);
    }
    
    public String getValue(String key)
    {
    	try {
    		return arpTab.get(key);
    	}
    	catch (Exception e)
    	{
    		return null;
    	}
    }
    
    public String toString()
    {
    	return arpTab.toString();
    }
} 