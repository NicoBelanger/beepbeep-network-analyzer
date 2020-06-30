package beepbeeptests;

import java.util.HashMap;
import java.util.Map;

class CollisionCounter 
{ 

    private static CollisionCounter coll_instance = null; 

    public static Map<String, Integer> collTab; 

    private CollisionCounter() 
    { 
    	collTab = new HashMap<>();
    } 

    public static CollisionCounter getInstance() 
    { 
        if (coll_instance == null) 
        	coll_instance = new CollisionCounter(); 
  
        return coll_instance; 
    } 
    
    public void insertIntoARP(String key)
    {  	
    	if(getValue(key) == null)
    		collTab.put(key, 1);
    	else
    		collTab.put(key, getValue(key) + 1);
    }
    
    public Integer getValue(String key)
    {
    	try {
    		return collTab.get(key);
    	}
    	catch (Exception e)
    	{
    		return null;
    	}
    }
    
    public String toString()
    {
    	return collTab.toString();
    }
} 