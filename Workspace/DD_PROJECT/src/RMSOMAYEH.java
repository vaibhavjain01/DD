import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.net.*;

public class RMSOMAYEH{
	
	
private static final long serialVersionUID = 1L;
public static String userId;	
	

	
public static Map<String, HashMap <String , List<String>> > DVLDateRecord = Collections.synchronizedMap(new HashMap(1000));
public static Map <String , List<String>> DVLRoomRecord = Collections.synchronizedMap(new HashMap(1000));

				
public static Map<String, HashMap <String , List<String>> > KKLDateRecord = Collections.synchronizedMap(new HashMap(1000));
public static Map <String , List<String>> KKLRoomRecord = Collections.synchronizedMap(new HashMap(1000));

	
public static Map<String, HashMap <String , List<String>>> WSTDateRecord = Collections.synchronizedMap(new HashMap(1000));
public static Map <String , List<String>> WSTRoomRecord = Collections.synchronizedMap(new HashMap(1000));


		

	 public boolean DVLCheckBeforeBook(String dateKey, String roomKey, String timeKey)
	 {
		 boolean EmtyRecord = true;
				  
		if(DVLDateRecord.containsKey(dateKey)){ 
			logFile("DVL", "Date exists");
				   
		    DVLRoomRecord =DVLDateRecord.get(dateKey);
		    	    
		        if (DVLRoomRecord == null) {
		        	logFile("DVL", "No Room in this Date");		        	 	        	
		        }
		        else if (DVLRoomRecord != null){
		        	
		        	logFile("DVL", "There is some room in this Date");
		        	boolean TimeExists = true; 
		        	
		        	
		        	if (   DVLDateRecord.get(dateKey).containsKey(roomKey)      ){
		        	logFile("DVL", "This room : " + roomKey + " : already exists");	
		        		       
		        	List<String> TimeSlotsInRoom = new ArrayList<String>();
		        	TimeSlotsInRoom = DVLDateRecord.get(dateKey).get(roomKey);
		        	
		        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
		        		
		        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
		        			logFile("DVL", "This room : " + roomKey + " This time :" + timeKey  + "  exists");		    				
		    				TimeExists = true; 
		    				EmtyRecord = false;
		    				break;
		    				
		        		}
		        		
		        		TimeExists = false; 
		        	}
		        	
		        	if (TimeExists == false){
		        	   
		        		    logFile("DVL", "timeslot  is not exist in this day and room : " + timeKey);
		      		        	      	        
		        	  }
		        	
		        	else if (TimeExists == true){
		        		logFile("DVL", "can not create");		        			
		        	}
			   }//room exist
		        	
		        	else {
		        		logFile("DVL", "room not Exist");
		        	    
		        	}
		        	
			   }        	

			   } 
			   
			   else {//not contain date
				   logFile("DVL", "Date not Exist");
				  
				    }
		        
			   return EmtyRecord;      
		        
		    }
	 
	 
	 
	 public boolean KKLCheckBeforeBook(String dateKey, String roomKey, String timeKey)
	 {
		 boolean EmtyRecord = true;
				  
		if(KKLDateRecord.containsKey(dateKey)){
				   
			logFile("KKL", "Date exists");
				   
		    KKLRoomRecord =KKLDateRecord.get(dateKey);
		    	    
		        if (DVLRoomRecord == null) {
		        	logFile("KKL", "No Room in this Date");		        	 	        	
		        }
		        else if (KKLRoomRecord != null){
		        	
		        	logFile("KKL", "There is some room in this Date");
		        	boolean TimeExists = true; 
		        	
		        	//if (DVLDateOuterRecord.get(key1).equals(key2)){
		        	if (   KKLDateRecord.get(dateKey).containsKey(roomKey)      ){
		        	logFile("KKL", "This room : " + roomKey + " : already exists");	
		        		       
		        	List<String> TimeSlotsInRoom = new ArrayList<String>();
		        	TimeSlotsInRoom = KKLDateRecord.get(dateKey).get(roomKey);
		        	
		        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
		        		
		        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
		        			logFile("KKL", "This room : " + roomKey + " This time :" + timeKey  + "  exists");		    				
		    				TimeExists = true; 
		    				EmtyRecord = false;
		    				break;
		    				
		        		}
		        		
		        		TimeExists = false; 
		        	}
		        	
		        	if (TimeExists == false){
		        	   
		        		    logFile("KKL", "timeslot  is not exist in this day and room : " + timeKey);
		      		        	      	        
		        	  }
		        	
		        	else if (TimeExists == true){
		        		logFile("KKL", "can not create");		        			
		        	}
			   }//room exist
		        	
		        	else {
		        		logFile("KKL", "room not Exist");
		        	    
		        	}
		        	
			   }        	

			   } 
			   
			   else {//not contain date
				   logFile("KKL", "Date not Exist");
				  
				    }
		        
			   return EmtyRecord;      
		        
		    }
	 	 

	 public boolean WSTCheckBeforeBook(String dateKey, String roomKey, String timeKey)
	 {
		 boolean EmtyRecord = true;
				  
		if(WSTDateRecord.containsKey(dateKey)){
				   
			logFile("WST", "Date exists");
				   
		    WSTRoomRecord =WSTDateRecord.get(dateKey);
		    	    
		        if (WSTRoomRecord == null) {
		        	logFile("WST", "No Room in this Date");		        	 	        	
		        }
		        else if (WSTRoomRecord != null){
		        	
		        	logFile("WST", "There is some room in this Date");
		        	boolean TimeExists = true; 
		        	
		        	//if (DVLDateOuterRecord.get(key1).equals(key2)){
		        	if (   WSTDateRecord.get(dateKey).containsKey(roomKey)      ){
		        	logFile("WST", "This room : " + roomKey + " : already exists");	
		        		       
		        	List<String> TimeSlotsInRoom = new ArrayList<String>();
		        	TimeSlotsInRoom = WSTDateRecord.get(dateKey).get(roomKey);
		        	
		        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
		        		
		        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
		        			logFile("WST", "This room : " + roomKey + " This time :" + timeKey  + "  exists");		    				
		    				TimeExists = true; 
		    				EmtyRecord = false;
		    				break;
		    				
		        		}
		        		
		        		TimeExists = false; 
		        	}
		        	
		        	if (TimeExists == false){
		        	   
		        		    logFile("DVL", "timeslot  is not exist in this day and room : " + timeKey);
		      		        	      	        
		        	  }
		        	
		        	else if (TimeExists == true){
		        		logFile("WST", "can not create");		        			
		        	}
			   }//room exist
		        	
		        	else {
		        		logFile("WST", "room not Exist");
		        	    
		        	}
		        	
			   }        	

			   } 
			   
			   else {//not contain date
				   logFile("WST", "Date not Exist");
				  
				    }
		        
			   return EmtyRecord;      
		        
		    }
	 
	 	 

	 public boolean DVLCheckBeforeDelete(String dateKey, String roomKey, String timeKey)
	 {
		 boolean EmtyRecord = true;
				  
			   if(DVLDateRecord.containsKey(dateKey)){
				   
				   logFile("DVL", "Date exists");
				   
		    DVLRoomRecord =DVLDateRecord.get(dateKey);
		    
		    
		        if (DVLRoomRecord == null) {
		        	logFile("DVL", "No Room in this Date");
		        	 
		        	
		        }
		        else if (DVLRoomRecord != null){
		        	
		        	logFile("DVL", "There is some room in this Date");
		        	boolean TimeExists = true; 
		        	
		        	//if (DVLDateOuterRecord.get(key1).equals(key2)){
		        	if (   DVLDateRecord.get(dateKey).containsKey(roomKey)      ){
		        	logFile("DVL", "This room : " + roomKey + " : already exists");	
		        		       
		        	List<String> TimeSlotsInRoom = new ArrayList<String>();
		        	TimeSlotsInRoom = DVLDateRecord.get(dateKey).get(roomKey);
		        	
		        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
		        		
		        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
		        			logFile("DVL", "This room : " + roomKey + " This time :" + timeKey  + "  exists");		    				
		    				TimeExists = true; 
		    				EmtyRecord = false;
		    				break;
		    				
		        		}
		        		
		        		TimeExists = false; 
		        	}
		        	
		        	if (TimeExists == false){
		        	   
		        		    logFile("DVL", "timeslot  is not exist in this day and room : " + timeKey);
		      		         	        
		        	  }
		        	
		        	else if (TimeExists == true){
		        		logFile("DVL", "can not create");		        			
		        	}
			   }//room exist
		        	
		        	else {
		        		logFile("DVL", "room not Exist");
		        	    
		        	}
		        	
			   }        	

			   } 
			   
			   else {//not contain date
				   logFile("DVL", "Date not Exist");
				  
				    }
		        
			   return EmtyRecord;      
		        
		    }
	 
	 
	 
	 public boolean KKLCheckBeforeDelete(String dateKey, String roomKey, String timeKey)
	 {
		 boolean EmtyRecord = true;
		  
		   if(KKLDateRecord.containsKey(dateKey)){
			   
			   logFile("KKL", "Date exists");
			   
	    KKLRoomRecord =KKLDateRecord.get(dateKey);
	    
	    
	        if (KKLRoomRecord == null) {
	        	logFile("KKL", "No Room in this Date");
	        	 
	        	
	        }
	        else if (KKLRoomRecord != null){
	        	
	        	logFile("KKL", "There is some room in this Date");
	        	boolean TimeExists = true; 
	        	
	        	//if (DVLDateOuterRecord.get(key1).equals(key2)){
	        	if (   KKLDateRecord.get(dateKey).containsKey(roomKey)      ){
	        	logFile("KKL", "This room : " + roomKey + " : already exists");	
	        		       
	        	List<String> TimeSlotsInRoom = new ArrayList<String>();
	        	TimeSlotsInRoom = KKLDateRecord.get(dateKey).get(roomKey);
	        	
	        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
	        		
	        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
	        			logFile("KKL", "This room : " + roomKey + " This time :" + timeKey  + "  exists");		    				
	    				TimeExists = true; 
	    				EmtyRecord = false;
	    				break;
	    				
	        		}
	        		
	        		TimeExists = false; 
	        	}
	        	
	        	if (TimeExists == false){
	        	   
	        		    logFile("KKL", "timeslot  is not exist in this day and room : " + timeKey);
	      		         	        
	        	  }
	        	
	        	else if (TimeExists == true){
	        		logFile("KKL", "can not create");		        			
	        	}
		   }//room exist
	        	
	        	else {
	        		logFile("KKL", "room not Exist");
	        	    
	        	}
	        	
		   }        	

		   } 
		   
		   else {//not contain date
			   logFile("KKL", "Date not Exist");
			  
			    }
	        
		   return EmtyRecord;      
	        
		        
		    }
	 
	 
	 
	 
	 public boolean WSTCheckBeforeDelete(String dateKey, String roomKey, String timeKey)
	 {
		 boolean EmtyRecord = true;
		  
		   if(WSTDateRecord.containsKey(dateKey)){
			   
			   logFile("WST", "Date exists");
			   
	    WSTRoomRecord =WSTDateRecord.get(dateKey);
	    
	    
	        if (WSTRoomRecord == null) {
	        	logFile("WST", "No Room in this Date");
	        	 
	        	
	        }
	        else if (WSTRoomRecord != null){
	        	
	        	logFile("WST", "There is some room in this Date");
	        	boolean TimeExists = true; 
	        	
	        	//if (DVLDateOuterRecord.get(key1).equals(key2)){
	        	if (   WSTDateRecord.get(dateKey).containsKey(roomKey)      ){
	        	logFile("WST", "This room : " + roomKey + " : already exists");	
	        		       
	        	List<String> TimeSlotsInRoom = new ArrayList<String>();
	        	TimeSlotsInRoom = WSTDateRecord.get(dateKey).get(roomKey);
	        	
	        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
	        		
	        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
	        			logFile("WST", "This room : " + roomKey + " This time :" + timeKey  + "  exists");		    				
	    				TimeExists = true; 
	    				EmtyRecord = false;
	    				break;
	    				
	        		}
	        		
	        		TimeExists = false; 
	        	}
	        	
	        	if (TimeExists == false){
	        	   
	        		    logFile("WST", "timeslot  is not exist in this day and room : " + timeKey);
	      		         	        
	        	  }
	        	
	        	else if (TimeExists == true){
	        		logFile("WST", "can not create");		        			
	        	}
		   }//room exist
	        	
	        	else {
	        		logFile("WST", "room not Exist");
	        	    
	        	}
	        	
		   }        	

		   } 
		   
		   else {//not contain date
			   logFile("WST", "Date not Exist");
			  
			    }
	        
		   return EmtyRecord;      
	        
		        
		    }
	 	 
	
	   public void addElement(String dateKey, String roomKey, String timeKey) {
		  
		   if(DVLDateRecord.containsKey(dateKey)){
			   
			   logFile("DVL", "Date exists");
			   
	    DVLRoomRecord =DVLDateRecord.get(dateKey);
	    
	    
	        if (DVLRoomRecord == null) {
	        	logFile("DVL", "No Room in this Date");
	        	 
	        	DVLRoomRecord = new HashMap<String ,List<String>>();
	        	 List<String> TimeSlots = new ArrayList<String>();
	        	TimeSlots.add(timeKey);//And so on..
	        	DVLRoomRecord.put(roomKey, TimeSlots);
	        	DVLDateRecord.put(dateKey,(HashMap<String, List<String>>) DVLRoomRecord);
	        	
	        }
	        else if (DVLRoomRecord != null){
	        	
	        	logFile("DVL", "There is some room in this Date");
	        	boolean TimeExists = true; 
	        	logFile("DVL", "roomkey" + roomKey);
	        	
	        	if (   DVLDateRecord.get(dateKey).containsKey(roomKey)      ){
	        	logFile("DVL", "This room : " + roomKey + " : already exists");	
	        	
	        	List<String> TimeSlotsInRoom = new ArrayList<String>();
	        	TimeSlotsInRoom = DVLDateRecord.get(dateKey).get(roomKey);
	        	
	        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
	        		
	        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
	        			logFile("DVL", "This room : " + roomKey + " This time :" + timeKey  + ": already exists");
	    				logFile("DVL", "can not create");
	    				TimeExists = true; 
	    				break;
	    				
	        		}
	        		
	        		TimeExists = false; 
	        	}
	        	
	        	if (TimeExists == false){
	        	   
	        		    logFile("DVL", "timeslot  is not exist in this day and room : " + timeKey);
	      		       
	        		    TimeSlotsInRoom.add(timeKey);
	      		       
	      		  
	      		        DVLRoomRecord.put(roomKey, TimeSlotsInRoom);
	      		        
	      	        	DVLDateRecord.put(dateKey, (HashMap<String, List<String>>) DVLRoomRecord);	      	        
	        	  }
	        	
	        	else if (TimeExists == false){
	        		logFile("DVL", "can not create");		        			
	        	}
		   }//room exist
	        	
	        	else {
	        		logFile("DVL", "in room is not");
	        	    //DVLRoomRecord = new HashMap<String ,List<String>>();
	        	    List<String> TimeSlots = new ArrayList<String>();
	        	    TimeSlots.add(timeKey);
		        	DVLRoomRecord.put(roomKey, TimeSlots);
		        	DVLDateRecord.put(dateKey,(HashMap<String, List<String>>) DVLRoomRecord);
	        	}
	        	
		   }        	

		   } 
		   
		   else {//not contain date
			   logFile("DVL", "Date not Exist");
			   List<String> TimeSlots = new ArrayList<String>();
			   TimeSlots.add(timeKey);
			   DVLRoomRecord = new HashMap<String ,List<String>>();
	        	DVLRoomRecord.put(roomKey, TimeSlots);
	        	DVLDateRecord.put(dateKey,(HashMap<String, List<String>>) DVLRoomRecord);
			    }
	        		          	        
	    }
	   
	   public void KKLAddElement(String dateKey, String roomKey, String timeKey) {
			  
		   if(KKLDateRecord.containsKey(dateKey)){
			   
			   logFile("KKL", "Date exists");
			   
	    KKLRoomRecord =KKLDateRecord.get(dateKey);
	    
	    
	        if (KKLRoomRecord == null) {
	        	logFile("DVL", "No Room in this Date");
	        	 
	        	KKLRoomRecord = new HashMap<String ,List<String>>();
	        	 List<String> TimeSlots = new ArrayList<String>();
	        	TimeSlots.add(timeKey);//And so on..
	        	KKLRoomRecord.put(roomKey, TimeSlots);
	        	KKLDateRecord.put(dateKey,(HashMap<String, List<String>>) KKLRoomRecord);
	        }
	        else if (DVLRoomRecord != null){
	        	
	        	logFile("KKL", "There is some room in this Date");
	        	boolean TimeExists = true; 
	        	
	        	//if (DVLDateOuterRecord.get(key1).equals(key2)){
	        	if (   KKLDateRecord.get(dateKey).containsKey(roomKey)      ){
	        	logFile("KKL", "This room : " + roomKey + " : already exists");	
	        	
	        	//logFile("DVL", "DVLDateRecord.get(dateKey).get(roomKey) : " + DVLDateRecord.get(dateKey).get(roomKey));
	        	//logFile("DVL", "timeKey : " + timeKey);
	        	
	        	List<String> TimeSlotsInRoom = new ArrayList<String>();
	        	TimeSlotsInRoom = KKLDateRecord.get(dateKey).get(roomKey);
	        	
	        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
	        		
	        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
	        			logFile("KKL", "This room : " + roomKey + " This time :" + timeKey  + ": already exists");
	    				logFile("KKL", "can not create");
	    				TimeExists = true; 
	    				break;
	    				
	        		}
	        		
	        		TimeExists = false; 
	        	}
	        	
	        	if (TimeExists == false){
	        	   
	        		    logFile("KKL", "timeslot  is not exist in this day and room : " + timeKey);
	      		        //List<String> TimeSlots = new ArrayList<String>();
	      		       // String time = DVLDateRecord.get(dateKey).get(roomKey).get(0);
	        		    TimeSlotsInRoom.add(timeKey);
	      		        //TimeSlots.add(time);
	      		       // TimeSlots.add(timeKey);//And so on..
	      		  
	      		        KKLRoomRecord.put(roomKey, TimeSlotsInRoom);
	      		        
	      	        	KKLDateRecord.put(dateKey, (HashMap<String, List<String>>) KKLRoomRecord);	      	        
	        	  }
	        	
	        	else if (TimeExists == false){
	        		logFile("KKL", "can not create");		        			
	        	}
		   }//room exist
	        	
	        	else {
	        	    //KKLRoomRecord = new HashMap<String ,List<String>>();
	        	    List<String> TimeSlots = new ArrayList<String>();
	        	    TimeSlots.add(timeKey);
		        	KKLRoomRecord.put(roomKey, TimeSlots);
		        	KKLDateRecord.put(dateKey,(HashMap<String, List<String>>) KKLRoomRecord);
	        	}
	        	
		   }        	

		   } 
		   
		   else {//not contain date
			   logFile("KKL", "Date not Exist");
			   List<String> TimeSlots = new ArrayList<String>();
			   TimeSlots.add(timeKey);
			   KKLRoomRecord = new HashMap<String ,List<String>>();
	        	KKLRoomRecord.put(roomKey, TimeSlots);
	        	KKLDateRecord.put(dateKey,(HashMap<String, List<String>>) KKLRoomRecord);
			    }
	        		          	        
	    }
		 
   	     	   	   
	   public void WSTAddElement(String dateKey, String roomKey, String timeKey) {
			  
		   if(WSTDateRecord.containsKey(dateKey)){
			   
			   logFile("DVL", "Date exists");
			   
			   WSTRoomRecord =WSTDateRecord.get(dateKey);
	    
	    
	        if (WSTRoomRecord == null) {
	        	logFile("DVL", "No Room in this Date");
	        	 
	        	WSTRoomRecord = new HashMap<String ,List<String>>();
	        	 List<String> TimeSlots = new ArrayList<String>();
	        	TimeSlots.add(timeKey);//And so on..
	        	WSTRoomRecord.put(roomKey, TimeSlots);
	        	WSTDateRecord.put(dateKey,(HashMap<String, List<String>>) WSTRoomRecord);
	        }
	        else if (WSTRoomRecord != null){
	        	
	        	logFile("DVL", "There is some room in this Date");
	        	boolean TimeExists = true; 
	        	
	        	//if (DVLDateOuterRecord.get(key1).equals(key2)){
	        	if (   WSTDateRecord.get(dateKey).containsKey(roomKey)      ){
	        	logFile("WST", "This room : " + roomKey + " : already exists");	
	        		        		        	
	        	List<String> TimeSlotsInRoom = new ArrayList<String>();
	        	TimeSlotsInRoom = WSTDateRecord.get(dateKey).get(roomKey);
	        	
	        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
	        		
	        		if ( TimeSlotsInRoom.get(i).equals(timeKey)){
	        			logFile("WST", "This room : " + roomKey + " This time :" + timeKey  + ": already exists");
	    				logFile("WST", "can not create");
	    				TimeExists = true; 
	    				break;
	    				
	        		}
	        		
	        		TimeExists = false; 
	        	}
	        	
	        	if (TimeExists == false){
	        	   
	        		    logFile("WST", "timeslot  is not exist in this day and room : " + timeKey);
	      		        //List<String> TimeSlots = new ArrayList<String>();
	      		       // String time = DVLDateRecord.get(dateKey).get(roomKey).get(0);
	        		    TimeSlotsInRoom.add(timeKey);
	      		        //TimeSlots.add(time);
	      		       // TimeSlots.add(timeKey);//And so on..
	      		  
	      		        WSTRoomRecord.put(roomKey, TimeSlotsInRoom);
	      		        
	      	        	WSTDateRecord.put(dateKey, (HashMap<String, List<String>>) WSTRoomRecord);	      	        
	        	  }
	        	
	        	else if (TimeExists == false){
	        		logFile("WST", "can not create");		        			
	        	}
		   }//room exist
	        	
	        	else {
	        	    //WSTRoomRecord = new HashMap<String ,List<String>>();
	        	    List<String> TimeSlots = new ArrayList<String>();
	        	    TimeSlots.add(timeKey);
		        	WSTRoomRecord.put(roomKey, TimeSlots);
		        	WSTDateRecord.put(dateKey,(HashMap<String, List<String>>) DVLRoomRecord);
	        	}
	        	
		   }        	

		   } 
		   
		   else {//not contain date
			   logFile("WST", "Date not Exist");
			   List<String> TimeSlots = new ArrayList<String>();
			   TimeSlots.add(timeKey);
			   WSTRoomRecord = new HashMap<String ,List<String>>();
	        	WSTRoomRecord.put(roomKey, TimeSlots);
	        	WSTDateRecord.put(dateKey,(HashMap<String, List<String>>) WSTRoomRecord);
			    }	        	          
	        
	    }
		 

		 
			 
			 public void DVLDeleteElement(String key1, String key2, String value) {
				 
				 int delRecordIndex = 0;
				 
				   if(DVLDateRecord.containsKey(key1))
					   DVLRoomRecord =DVLDateRecord.get(key1);
			        	if (DVLRoomRecord ==null) {
			        		System.out.println(" Date is Empty!!");
			        	}
			        	else {
			        					        		
			        		if(DVLRoomRecord.containsKey(key2)){
			        			
			        			
			        			List<String> TimeSlotsInRoom = new ArrayList<String>();
					        	TimeSlotsInRoom = DVLDateRecord.get(key1).get(key2);
					        	
					        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
					        		
					        		if ( TimeSlotsInRoom.get(i).equals(value)){					        			
					    				delRecordIndex = i;
					    				break;					    				
					        		}					        		
					        		//TimeExists = false; 
					        	}
			        			
			        			System.out.println("delnom" + delRecordIndex);
			        			logFile("DVL", "delnom" + delRecordIndex);
			        			TimeSlotsInRoom.remove(delRecordIndex);			        			
			  			        			
			        			//DVLDateRecord.remove(key1,DVLRoomRecord);
			        		}
			        		else
			        			System.out.println(" Room number is Empty!!");
			        	
			        		}
			 }
			 
			 
 public void KKLDeleteElement(String key1, String key2, String value) {
				 
	 int delRecordIndex = 0;
	 
	   if(KKLDateRecord.containsKey(key1))
		   KKLRoomRecord =KKLDateRecord.get(key1);
      	if (KKLRoomRecord ==null) {
      		System.out.println(" Date is Empty!!");
      	}
      	else {
      					        		
      		if(KKLRoomRecord.containsKey(key2)){
      			
      			
      			List<String> TimeSlotsInRoom = new ArrayList<String>();
		        	TimeSlotsInRoom = KKLDateRecord.get(key1).get(key2);
		        	
		        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
		        		
		        		if ( TimeSlotsInRoom.get(i).equals(value)){					        			
		    				delRecordIndex = i;
		    				break;					    				
		        		}					        		
		        		//TimeExists = false; 
		        	}
      			
      			System.out.println("delnom" + delRecordIndex);
      			logFile("KKL", "delnom" + delRecordIndex);
      			TimeSlotsInRoom.remove(delRecordIndex);			        			
			        			
      			//DVLDateRecord.remove(key1,DVLRoomRecord);
      		}
      		else
      			System.out.println(" Room number is Empty!!");
      	
      		}
			 }
			 
 public void WSTDeleteElement(String key1, String key2, String value) {
	 
	 int delRecordIndex = 0;
	 
	   if(WSTDateRecord.containsKey(key1))
		   WSTRoomRecord =WSTDateRecord.get(key1);
      	if (WSTRoomRecord ==null) {
      		System.out.println(" Date is Empty!!");
      	}
      	else {
      					        		
      		if(WSTRoomRecord.containsKey(key2)){
      			
      			
      			List<String> TimeSlotsInRoom = new ArrayList<String>();
		        	TimeSlotsInRoom = WSTDateRecord.get(key1).get(key2);
		        	
		        	for (int i = 0; i< TimeSlotsInRoom.size(); i++) {	
		        		
		        		if ( TimeSlotsInRoom.get(i).equals(value)){					        			
		    				delRecordIndex = i;
		    				break;					    				
		        		}					        		
		        		//TimeExists = false; 
		        	}
      			
      			System.out.println("delnom" + delRecordIndex);
      			logFile("WST", "delnom" + delRecordIndex);
      			TimeSlotsInRoom.remove(delRecordIndex);			        			
			        			
      			//DVLDateRecord.remove(key1,DVLRoomRecord);
      		}
      		else
      			System.out.println(" Room number is Empty!!");
      	
      		}
 }
			 
			 
			 		   	   
			 public boolean DVLCheckElement(String key1, String key2, String value) {
				 boolean EmtyRecord = true;
				 System.out.println("in show element function");
				   if(DVLDateRecord.containsKey(key1)){
					   DVLRoomRecord =DVLDateRecord.get(key1);
				   
			        	if (DVLRoomRecord == null) {
			        		System.out.println(" Date is Empty!!");
			        		logFile("DVL","Date is Empty for deleting");
			        	    EmtyRecord = true;
			        	}
			        	else if (DVLRoomRecord != null){
			        					        					        	
			        		if( (DVLRoomRecord.containsKey(key2)) )
			        				//&& (DVLDateinnerRecord.get(key2) == value )) 
			        		{
			        						        			
			        			logFile("DVL","xvalue " + value);
			        			if (DVLRoomRecord.get(key2).equals(value))	{
			        				
			        				//logFile("DVL","yyyyy");
			        				EmtyRecord = false;
			        			}
			        			else{ 
			        				EmtyRecord = true;
			        				logFile("DVL","time slot is Empty for this date and room number so can not delete");
			        			}
			        				
			        		}
			        	}	
			        					        	
				   }
				   else 
					   EmtyRecord = true;
			        			
				   return EmtyRecord;
			 }
	
	/* Generates the id of Room record*/
	private String generateId(String id){
		int length;
		String number = "";
		length = 5 - id.length();
		for(int i = 0; i < length; i++) {
			number = number + "0";
		}
		number = number + id;
		return number;
	}
		
	/* logs the activities of servers*/
	
	//public static void logFile(String fileName, String Operation, String userId) throws SecurityException{
	public static void logFile(String fileName, String Operation, String userId) throws SecurityException{
		fileName= fileName +"ServerLog.txt";	
		String LogDate;
		File log = new File(fileName);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		LogDate = dateFormat.format(date);
		try{
			if(!log.exists()){
		    }
			log.setWritable(true);
		    FileWriter fileWriter = new FileWriter(log, true);

		    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		    //bufferedWriter.write("In " +  LogDate + " "  +  Operation + "By" + userId );
		    bufferedWriter.write("In " +  LogDate + " "  +  Operation + "  By  " + userId  );
		    bufferedWriter.newLine();
		    bufferedWriter.close();
		} catch(IOException e) {
		    System.out.println("COULD NOT LOG!!");
		}
		}
	
	
	//public String createRoom (String room_Number, String date,String  list_Of_Time_Slots){
public boolean createRoom(String userId, String campusName,String roomNumber,String date, String list_Of_Time_Slots){
											
		
		String RoomRecordId = "";
		
		switch (campusName) {
			case "DVL":{ 
				synchronized(DVLDateRecord)
				{
					//RoomRecordId = "RR" + generateId(String.valueOf(DVLDateOuterRecord.size()+1));	
					System.out.println("Before Room created");
					
					String[] TimeSlots = list_Of_Time_Slots.split(",");
					
					for(int i = 0; i < TimeSlots.length; i++){
						addElement(date, roomNumber, TimeSlots[i]);	
						logFile("DVL","Room Created :" + DVLDateRecord.values(), userId);
					}
					
					System.out.println("After Room Created");
					logFile("DVL","After Room Created  date records :" + DVLDateRecord.values(),userId);
					//logFile("DVL","After Room Created room records :" + DVLRoomRecord.values());
					System.out.println(DVLDateRecord.values());
					
				}
			} break;
			case "KKL": { //
				synchronized(KKLDateRecord)
				{
					//RoomRecordId = "RR" + generateId(String.valueOf(DVLDateOuterRecord.size()+1));	
					System.out.println("Before Room created");
					
					String[] TimeSlots = list_Of_Time_Slots.split(",");
					
					for(int i = 0; i < TimeSlots.length; i++){
						KKLAddElement(date, roomNumber, TimeSlots[i]);	
						logFile("DVL","Room Created :" + DVLDateRecord.values(),userId);
					}
					
					System.out.println("After Room Created");
					logFile("KKL","After Room Created  date records :" + KKLDateRecord.values(),userId);
					//logFile("DVL","After Room Created room records :" + DVLRoomRecord.values());
					System.out.println(DVLDateRecord.values());
				}
				
			} break;
			
			case "WST": { 
				synchronized(WSTDateRecord)
				{						
					System.out.println("Before Room created");
					
					String[] TimeSlots = list_Of_Time_Slots.split(",");
					
					for(int i = 0; i < TimeSlots.length; i++){
						WSTAddElement(date, roomNumber, TimeSlots[i]);	
						logFile("WST","Room Created :" + WSTDateRecord.values(),userId);
					}
					
					System.out.println("After Room Created");
					logFile("WST","After Room Created  date records :" + WSTDateRecord.values(),userId);				
					System.out.println(DVLDateRecord.values());
				}
			} break;

			default:{

			} break;
		}
		return true; //return value
	}		

public Integer calculateAvailableTimeSlots (String campus,String dateKey) {
	
	Integer Counts = 0;
	String timeKey = "";
	String roomKey = "";

	 if(DVLDateRecord.containsKey(dateKey)){
		   
		   logFile("DVL", "Date exists", userId);
		   
		   DVLRoomRecord =DVLDateRecord.get(dateKey);
  
  
      if (DVLRoomRecord == null) {
      	logFile("DVL", "No Room in this Date",userId);
      	Counts = 0;     
      }
      else if (DVLRoomRecord != null){
      	
      	logFile("DVL", "There is some room in this Date",userId);
      	boolean TimeExists = true;      	     	     	           	     	
      	List<String> roomTokens = new ArrayList<String>();
      	int roomCounts =0;
      	String Rooms;
      	String[] tokens;
      	int totalCounts = 0 ;
      	Rooms = DVLDateRecord.get(dateKey).keySet().toString();
      	tokens = Rooms.split(",");
      	
      	int countRooms = tokens.length;
      	         	
      	Counts = 0;
      	List<String> TimeSlotsInRoom = new ArrayList<String>();
      	for (int i=0 ; i< countRooms; i++){
      		      		     		      		      	
        tokens[i] = tokens[i].toString().trim().replace("]","").replace("[","") ;
                       	      	
      	TimeSlotsInRoom = DVLDateRecord.get(dateKey).get(tokens[i]);
      	
      	Counts = Counts + TimeSlotsInRoom.size();     	     	     	      	      	
      	}
      	logFile(campus,"countttttt" + String.valueOf(Counts),userId);
         	          	
	   }  
      	//Counts = 0;

	   } 
	   
	   else {//not contain date
		   logFile("DVL", "Date not Existtt",userId);		   		   
		   Counts = 0;
		    }	
	 logFile(campus,"count: " + String.valueOf(Counts),userId);
	return Counts;				
	
}


public Integer calculateAvailableTimeSlotsKKL (String campus,String dateKey) {
	
		
		Integer Counts = 0;
		String timeKey = "";
		String roomKey = "";

		 if(KKLDateRecord.containsKey(dateKey)){
			   
			   logFile("KKL", "Date exists",userId);
			   
			   KKLRoomRecord =KKLDateRecord.get(dateKey);
	  
	  
	      if (KKLRoomRecord == null) {
	      	logFile("KKL", "No Room in this Date",userId);
	      	Counts = 0;     
	      }
	      else if (KKLRoomRecord != null){
	      	
	      	logFile("KKL", "There is some room in this Date",userId);
	      	boolean TimeExists = true;      	     	     	           	     	
	      	List<String> roomTokens = new ArrayList<String>();
	      	int roomCounts =0;
	      	String Rooms;
	      	String[] tokens;
	      	int totalCounts = 0 ;
	      	Rooms = KKLDateRecord.get(dateKey).keySet().toString();
	      	tokens = Rooms.split(",");
	      	
	      	int countRooms = tokens.length;
	      	         	
	      	Counts = 0;
	      	List<String> TimeSlotsInRoom = new ArrayList<String>();
	      	for (int i=0 ; i< countRooms; i++){
	      		      		     		      		      	
	        tokens[i] = tokens[i].toString().trim().replace("]","").replace("[","") ;
	                       	      	
	      	TimeSlotsInRoom = KKLDateRecord.get(dateKey).get(tokens[i]);
	      	
	      	Counts = Counts + TimeSlotsInRoom.size();     	     	     	      	      	
	      	}
	      	logFile(campus,"countttttt" + String.valueOf(Counts),userId);
	         	          	
		   }  
	      	//Counts = 0;

		   } 
		   
		   else {//not contain date
			   logFile("KKL", "Date not Existtt",userId);		   		   
			   Counts = 0;
			    }	
		 logFile(campus,"count: " + String.valueOf(Counts),userId);
		return Counts;				
		
	}
	


public Integer calculateAvailableTimeSlotsWST (String campus,String dateKey) {
	
		
		Integer Counts = 0;
		String timeKey = "";
		String roomKey = "";

		 if(WSTDateRecord.containsKey(dateKey)){
			   
			   logFile("KKL", "Date exists",userId);
			   
			   WSTRoomRecord =WSTDateRecord.get(dateKey);
	  
	  
	      if (WSTRoomRecord == null) {
	      	logFile("DVL", "No Room in this Date",userId);
	      	Counts = 0;     
	      }
	      else if (WSTRoomRecord != null){
	      	
	      	logFile("WST", "There is some room in this Date",userId);
	      	boolean TimeExists = true;      	     	     	           	     	
	      	List<String> roomTokens = new ArrayList<String>();
	      	int roomCounts =0;
	      	String Rooms;
	      	String[] tokens;
	      	int totalCounts = 0 ;
	      	Rooms = WSTDateRecord.get(dateKey).keySet().toString();
	      	tokens = Rooms.split(",");
	      	
	      	int countRooms = tokens.length;
	      	         	
	      	Counts = 0;
	      	List<String> TimeSlotsInRoom = new ArrayList<String>();
	      	for (int i=0 ; i< countRooms; i++){
	      		      		     		      		      	
	        tokens[i] = tokens[i].toString().trim().replace("]","").replace("[","") ;
	                       	      	
	      	TimeSlotsInRoom = WSTDateRecord.get(dateKey).get(tokens[i]);
	      	
	      	Counts = Counts + TimeSlotsInRoom.size();     	     	     	      	      	
	      	}
	      	logFile(campus,"countttttt" + String.valueOf(Counts),userId);
	         	          	
		   }  
	      	//Counts = 0;

		   } 
		   
		   else {//not contain date
			   logFile("WST", "Date not Existtt",userId);		   		   
			   Counts = 0;
			    }	
		 logFile(campus,"count: " + String.valueOf(Counts),userId);
		return Counts;				
		
	}
	


public Integer calculateAvailableTimeSlotsByRoom (String campus,String dateKey,String roomKey) {
	
	Integer Counts = 0;
	String timeKey = "";

	 if(DVLDateRecord.containsKey(dateKey)){
		   
		   logFile("DVL", "Date exists",userId);
		   
		   DVLRoomRecord =DVLDateRecord.get(dateKey);
  
  
      if (DVLRoomRecord == null) {
      	logFile("DVL", "No Room in this Date",userId);
      	Counts = 0;     
      }
      else if (DVLRoomRecord != null){
      	
      	logFile("DVL", "There is some room in this Date",userId);
      	boolean TimeExists = true; 
  	    	    	    	     	
      	if (   DVLDateRecord.get(dateKey).containsKey(roomKey )){
      	logFile("DVL", "This room : " + roomKey + " : already exists",userId);	
      	      	      	
      	List<String> TimeSlotsInRoom = new ArrayList<String>();
      	TimeSlotsInRoom = DVLDateRecord.get(dateKey).get(roomKey);
     
      	
      	Counts = TimeSlotsInRoom.size();
      	logFile(campus,"countttttt" + String.valueOf(Counts),userId);
      
	   }//room exist
      	
      	else {
      		logFile("DVL", "This room does not exist in this date ",userId);
	       	Counts = 0;
      	}//containsKey(roomKey)
      	
      	
	   }  
      	//Counts = 0;

	   } 
	   
	   else {//not contain date
		   logFile("DVL", "Date not Exist",userId);		   		   
		   Counts = 0;
		    }	
	 logFile(campus,"count: " + String.valueOf(Counts),userId);
	return Counts;	
	
}
public Integer calculateAvailableTimeSlotsKKLByRoom (String campus,String dateKey,String roomKey) {
	
	Integer Counts = 0;
	String timeKey = "";

	 if(KKLDateRecord.containsKey(dateKey)){
		   
		   logFile("KKL", "Date exists",userId);
		   
		   KKLRoomRecord =KKLDateRecord.get(dateKey);
  
  
      if (KKLRoomRecord == null) {
      	logFile("KKL", "No Room in this Date",userId);
      	Counts = 0;     
      }
      else if (KKLRoomRecord != null){
      	
      	logFile("KKL", "There is some room in this Date",userId);
      	boolean TimeExists = true; 
      	
      	logFile("KKL", "roomkey in calculateAvailableTimeSlotsKKLByRoom " + roomKey,userId);
      	logFile("KKL", "roomkeyyy in calculateAvailableTimeSlotsKKLByRoom " +KKLDateRecord.values() ,userId);
      	
      	
      
      	if (   KKLDateRecord.get(dateKey).containsKey(roomKey)      ){
      	logFile("KKL", "This room : " + roomKey + " : already exists",userId);	
      	      	      	
      	List<String> TimeSlotsInRoom = new ArrayList<String>();
      	TimeSlotsInRoom = KKLDateRecord.get(dateKey).get(roomKey);
      	logFile("KKL","bbbbb" + KKLDateRecord.get(dateKey).get(roomKey),userId);
      	
      	Counts = TimeSlotsInRoom.size();
      	logFile("KKL","countttttt" + String.valueOf(Counts),userId);
      
	   }//room exist
      	
     	else {
      	    
	        	Counts = 0;
     	}//containsKey(roomKey)
      	
      	
	   }  
      	//Counts = 0;

	   } 
	   
	   else {//not contain date
		   logFile("KKL", "Date not Exist",userId);		   		   
		   Counts = 0;
		    }	
	 logFile("KKL","count: " + String.valueOf(Counts),userId);
	return Counts;	
	
}
public Integer calculateAvailableTimeSlotsWSTByRoom (String campus,String dateKey,String roomKey) {
	
	Integer Counts = 0;
	String timeKey = "";

	 if(WSTDateRecord.containsKey(dateKey)){
		   
		 logFile("WST", "Date exists",userId);		   
		 WSTRoomRecord =WSTDateRecord.get(dateKey);
		 if (WSTRoomRecord == null) {
		 logFile("WST", "No Room in this Date",userId);
      	 Counts = 0;     
      }
      else if (WSTRoomRecord != null){
      	
      	logFile("DVL", "There is some room in this Date",userId);
      	boolean TimeExists = true;   	      	
      	if (   WSTDateRecord.get(dateKey).containsKey(roomKey)      ){
      	logFile("DVL", "This room : " + roomKey + " : already exists",userId);	
      	      	      	
      	List<String> TimeSlotsInRoom = new ArrayList<String>();
      	TimeSlotsInRoom = WSTDateRecord.get(dateKey).get(roomKey);
      	
      	Counts = TimeSlotsInRoom.size();
      	logFile(campus,"countttttt" + String.valueOf(Counts),userId);
      
	   }//room exist      	
      	else {    	    
	        	Counts = 0;
      	}//containsKey(roomKey)
      	     	
	   }  
      	//Counts = 0;

	   } 
	   
	   else {//not contain date
		   logFile("WST", "Date not Exist",userId);		   		   
		   Counts = 0;
		    }	
	 logFile(campus,"count: " + String.valueOf(Counts),userId);
	return Counts;	
	
}



public boolean deleteRoom( String userId,String campusName,String roomNumber,String date, String list_Of_Time_Slots){
	
	
	String recordId = "";
	
	switch (campusName) {
		case "DVL":{ 
			synchronized(DVLDateRecord)
			{
													
				String[] TimeSlots = list_Of_Time_Slots.split(",");
				
				for(int i = 0; i < TimeSlots.length; i++){
					
					//boolean result = DVLCheckElement( date, roomNumber, TimeSlots[i]);
					boolean result = DVLCheckBeforeDelete( date, roomNumber, TimeSlots[i]);
					if (result == true)
					{
						System.out.println("There is no record for deleting");	
						logFile("DVL","There is no record for deleting :" + DVLDateRecord.values(),userId);
						//result = false;
					}	
					else if  (result == false){
					DVLDeleteElement(date, roomNumber, TimeSlots[i]);	
					logFile("DVL","Room Deleted :" + DVLDateRecord.values(),userId);
					}
				}
								
				System.out.println(DVLRoomRecord.values());
								
				
			}
		} break;
		case "KKL": { //
			synchronized(KKLDateRecord)
			{
													
				String[] TimeSlots = list_Of_Time_Slots.split(",");
				
				for(int i = 0; i < TimeSlots.length; i++){
					
					
					boolean result = KKLCheckBeforeDelete( date, roomNumber, TimeSlots[i]);
					if (result == true)
					{
						System.out.println("There is no record for deleting");	
						logFile("KKL","There is no record for deleting :" + KKLDateRecord.values(),userId);
						//result = false;
					}	
					else if  (result == false){
					KKLDeleteElement(date, roomNumber, TimeSlots[i]);	
					logFile("KKL","Room Deleted :" + KKLDateRecord.values(),userId);
					}
				}
								
				System.out.println(KKLRoomRecord.values());
			}
			
		} break;
		
		case "WST": { //west mount
			synchronized(WSTDateRecord)
			{
					
			
				String[] TimeSlots = list_Of_Time_Slots.split(",");
				
				for(int i = 0; i < TimeSlots.length; i++){
					
					//boolean result = DVLCheckElement( date, roomNumber, TimeSlots[i]);
					boolean result = WSTCheckBeforeDelete( date, roomNumber, TimeSlots[i]);
					if (result == true)
					{
						System.out.println("There is no record for deleting");	
						logFile("WST","There is no record for deleting :" + WSTDateRecord.values(),userId);
						//result = false;
					}	
					else if  (result == false){
					WSTDeleteElement(date, roomNumber, TimeSlots[i]);	
					logFile("WST","Room Deleted :" + WSTDateRecord.values(),userId);
					}
				}
								
				System.out.println(WSTRoomRecord.values());
			}
		} break;

		default:{

		} break;
	}
	return(true); //return value
}		


	
	public String bookRoom(String userId,String campusName, String roomNumber,String date, String timeSlot){
		
	
		boolean result = true;						
		String recordId = "";
		int CountBook= 0;
		
		switch (campusName) {
			case "DVL":{ 
				synchronized(DVLDateRecord)
				{
											
					result = DVLCheckBeforeBook(date, roomNumber, timeSlot);
					
					if (result == false)
					{
					//recordId = "CR" + generateId(String.valueOf(DVLDateRecord.size()+1));	
						recordId = userId+campusName+date+roomNumber+timeSlot;
					DVLDeleteElement(date, roomNumber, timeSlot);
					logFile("DVL","Room Booked :" + DVLDateRecord.values(),userId);
					//CountBook++;
					
					}
					else 
						logFile("DVL","There is no Room for Booking :" + DVLDateRecord.values(),userId);								
					
				}
			} break;
			case "KKL": { //
				synchronized(KKLDateRecord)
				{
					logFile("KKL", "in kkl bookroommmmmmmm",userId);
					result = KKLCheckBeforeBook(date, roomNumber, timeSlot);					
					if (result == false)
					{	
	
						recordId = campusName+date+roomNumber+timeSlot+userId;
						KKLDeleteElement(date, roomNumber, timeSlot);					
						logFile("KKL","Room Booked :" + KKLDateRecord.values(),userId);					
					}
					else 
						logFile("KKL","There is no Room for Booking :" + KKLDateRecord.values(),userId);					
				}
			} break;
			
			case "WST": { //west mount
				synchronized(WSTDateRecord)
				{
					result = WSTCheckBeforeBook(date, roomNumber, timeSlot);
					
					if (result == false)
					{					
					recordId = campusName+date+roomNumber+timeSlot+userId;
					WSTDeleteElement(date, roomNumber, timeSlot);
					logFile("WST","Room Booked :" + WSTDateRecord.values(),userId);		
					}
					else 
						logFile("WST","There is no Room for Booking :" + WSTDateRecord.values(),userId);
														
				}
			} break;

			default:{

			} break;
		}
		return recordId; //return value
	}	
	
	
	public  String getDate(String id){
		String Date;
		Date = id.substring(3, 11);
		return Date;
	}
	
	public  String getRoom(String id){
		String Room;			
		Room = id.substring(11, 14);
		return Room;
	}
	
	public String getTime(String id){
		String Time;
		 Time= id.substring(14, 16);
		return Time;
	}
	public String getUserId(String id){
		String Time;
		 Time= id.substring(16, 24);
		return Time;
	}
	
	
		
	public boolean cancelBooking (String bookingID){
					
		String DestinationCampus = bookingID.substring(0, 3);
		logFile(DestinationCampus,"bookingID: " + bookingID ,userId);
		
		String Date = getDate(bookingID);		
		logFile(DestinationCampus,"Date in canceling: " + Date,userId);
		
		String Room = getRoom(bookingID);
		logFile(DestinationCampus,"Room  in canceling: " + Room,userId);
		
		String userId = getUserId(bookingID);
		logFile(DestinationCampus,"userId  in canceling: " + userId,userId);
		
		
				
		
		String Time = getTime(bookingID);
		if (DestinationCampus.equals("DVL")){			
		addElement(Date,Room,Time);
		logFile("DVL","bookingRoom canceled :" + DVLDateRecord.values(),userId);
		}
		else if (DestinationCampus.equals("KKL")){
		KKLAddElement(Date,Room,Time);
		logFile("KKL","bookingRoom canceled :" + KKLDateRecord.values(),userId);
		}
		else if (DestinationCampus.equals("WST")){
		WSTAddElement(Date,Room,Time);	
		logFile("WST","bookingRoom canceled :" + WSTDateRecord.values(),userId);
		}
				
		return true;
	}
			
	int DVLserverPort= 3000;
	int KKLserverPort= 4000;
	int WSTserverPort= 5000;
			
	public class DVLUdpServer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			logFile("DVL","IN DVLUdpServer",userId);
			System.out.println("DVL UDP Server Started");
			DatagramSocket aSocket = null;
			try{
				aSocket = new DatagramSocket(DVLserverPort);					
				byte[] buffer = new byte[100];
			
				String message = "";
			
				String Result = "";
	
				while(true){
					String Temp = "";
					
					logFile("DVL","DVL thread is waiting for message",userId);
					
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					aSocket.receive(request);																			
					Temp = new String(request.getData());
					Temp = Temp.trim();
					logFile("DVL", "DVLUDP received:   " + Temp,userId);
					String[] tokens = Temp.split(",");
					if (tokens[0].equals("count")){				
					Integer res = calculateAvailableTimeSlots (tokens[1],tokens[2]);	
					Result = res.toString();
					logFile("DVL", "Result:   " + Result,userId);
					}
					else if  (tokens[0].equals("book")){
						logFile("DVL","in DVL book",userId);
					String res = bookRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);
					logFile("DVL","after DVL book",userId);
					Result = res.toString();
					logFile("DVL", "Result:   " + Result,userId);
					}
					else if  (tokens[0].equals("cancel")){
					Boolean res = cancelBooking(tokens[1]);
					Result = res.toString();
					logFile("DVL", "Result:   " + Result,userId);						
					}	
					
					else if  (tokens[0].equals("create")){
						logFile("DVL", "in createeee",userId);		
						Boolean res = createRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);
						Result = res.toString();
						logFile("DVL", "Result:   " + Result,userId);
						}
					else if  (tokens[0].trim().equals("delete")){
						logFile("DVL", "in deleteeeee",userId);		
						Boolean res = deleteRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);						
						Result = res.toString();						
						logFile("DVL", "Result:   " + Result,userId);		
						}
					
					
					message = Result.toString(); 
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,buffer.length,request.getAddress(),request.getPort());
					aSocket.send(reply);
				
					logFile("DVL",new String(reply.getData()),userId);
				}
			}catch (SocketException e){System.out.println("DVL Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(aSocket != null) aSocket.close();}
		}		
	}
	
	/* KKL UDP server */
	public class KKLUdpServer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			logFile("KKL","In KKLUdpServer",userId);
			System.out.println("KKL UDP Server Started");
			DatagramSocket aSocket1 = null;
			try{
				aSocket1 = new DatagramSocket(KKLserverPort);					
				byte[] buffer = new byte[50];
				
				String message = "";
		
				String Result = "";
						
				while(true){
					String Temp = "";
				
					logFile("KKL","KKL thread is waiting for message",userId);					
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					aSocket1.receive(request);																			
					Temp = new String(request.getData());
					Temp = Temp.trim();
					logFile("KKL", "KKLUDP received:   " + Temp,userId);
					String[] tokens = Temp.split(",");
					if (tokens[0].equals("count")){					
					Integer res = calculateAvailableTimeSlotsKKL (tokens[1],tokens[2]);	
					Result = res.toString();
					logFile("KKL", "Result:   " + Result,userId);
					}
					else if  (tokens[0].equals("book")){
					//logFile("KKL","in KKL book");
					String res = bookRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);				
					Result = res.toString();
					logFile("KKL", "Result:   " + Result,userId);
					}
					else if  (tokens[0].equals("cancel")){
						//logFile("KKL", "KKLCANCEL:   ");	
					Boolean res = cancelBooking(tokens[1]);
					Result = res.toString();
					logFile("KKL", "Result:   " + Result,userId);
						
					}	
					
					else if  (tokens[0].equals("create")){
						Boolean res = createRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);
						Result = res.toString();
						logFile("KKL", "Result:   " + Result,userId);
						}
						else if  (tokens[0].equals("delete")){
						Boolean res = deleteRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);
						Result = res.toString();
						logFile("KKL", "Result:   " + Result,userId);
							
						}										
					message = Result.toString(); 
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,buffer.length,request.getAddress(),request.getPort());
					aSocket1.send(reply);
				
					logFile("KKL",new String(reply.getData()),userId);
				}
			}catch (SocketException e){System.out.println("KKL Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(aSocket1 != null) aSocket1.close();}
		}				
	}
	
	/* WST UDP server */
	public class WSTUdpServer implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			logFile("WST"," in WSTUdpServer",userId);
			System.out.println("WST UDP Server Started");
			DatagramSocket aSocket2 = null;
			try{												
				aSocket2 = new DatagramSocket(WSTserverPort);							
				byte[] buffer = new byte[60];
				String message = "";		
				String Result = "";
				
				while(true){
					String Temp = "";
					logFile("WST","WST thread is waiting for message",userId);
					DatagramPacket request = new DatagramPacket(buffer,buffer.length);
					aSocket2.receive(request);
																			
					Temp = new String(request.getData());
					Temp = Temp.trim();
					logFile("WST", "TempWST received :   " + Temp,userId);
					String[] tokens = Temp.split(",");
				
					if (tokens[0].equals("count")){
					
						Integer res = calculateAvailableTimeSlotsWST (tokens[1],tokens[2]);	
						Result = res.toString();
						logFile("WST", "Result:   " + Result,userId);
						}
						else if  (tokens[0].equals("book")){
						String res = bookRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);
						Result = res.toString();
						logFile("WST", "Result:   " + Result,userId);
						}
						else if  (tokens[0].equals("cancel")){
						Boolean res = cancelBooking(tokens[1]);
						Result = res.toString();
						logFile("WST", "Result:   " + Result,userId);
							
						}
						else if  (tokens[0].equals("create")){
							Boolean res = createRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);
							Result = res.toString();
							logFile("WST", "Result:   " + Result,userId);
							}
							else if  (tokens[0].equals("delete")){
							Boolean res = deleteRoom(tokens[5],tokens[1], tokens[2],tokens[3],tokens[4]);
							Result = res.toString();
							logFile("WST", "Result:   " + Result,userId);
								
							}														
					logFile("WST", "Result:   " + Result.toString(),userId);
					message = Result.toString(); 
					buffer = message.getBytes();
					DatagramPacket reply = new DatagramPacket(buffer,buffer.length,request.getAddress(),request.getPort());
					aSocket2.send(reply);
				
					//logFile("WST reply",message);
				}
			}catch (SocketException e){System.out.println("WST Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(aSocket2 != null) aSocket2.close();}
		}
		
	
		
	}

	
	synchronized public int getDestinationPort(String DestinationCampus){
		
		int DestinationPort = 0 ;
	if (DestinationCampus.equals("DVL"))
		DestinationPort  =  DVLserverPort;
	else if (DestinationCampus.equals("KKL"))
		 DestinationPort = KKLserverPort;
	else if (DestinationCampus.equals("WST"))	
		 DestinationPort = WSTserverPort ;
	
	return DestinationPort;
	}
	
	
	public void runDVLServer() {
		Thread t1 = new Thread(new DVLUdpServer());			
		t1.start();
	}
	
	public void runKKLServer() {
		Thread t2 = new Thread(new KKLUdpServer());				
		t2.start();
	}
	public void runWSTServer() {
		Thread t3 = new Thread(new WSTUdpServer());				
		t3.start();
	}
	
	
	public String UDPOperations(String userId,String operation,String campus,String DestinationCampus, String roomNumber,String dateKey, String timeSlot, String RecordID) {
		
		
		DatagramSocket SlotSocket = null;
		DatagramSocket SlotSocket1 = null;
		DatagramSocket SlotSocket2 = null;
		
		Thread t1 = new Thread(new DVLUdpServer());
		Thread t2 = new Thread(new KKLUdpServer());
		Thread t3 = new Thread(new WSTUdpServer());
	
		
		DVLserverPort = DVLserverPort +50;
		KKLserverPort = KKLserverPort +50; 
		WSTserverPort = WSTserverPort +50;
	
		t1.start();
		t2.start();
		t3.start();
	

		int DestinationPort = 0;
		String message = null;
		
		byte[] buffer = new byte[10];
		
		
											
		switch(campus){
		case "DVL":{
			
			try {
				message = "";
				String msg = "";
				
				
				SlotSocket = new DatagramSocket();
				InetAddress aHost = InetAddress.getByName("localhost");
				if (operation.equals("count")){
					
														
						message += "DVL :"  + (calculateAvailableTimeSlots (campus,dateKey));							
						
						msg = new String("count" + "," + "KKL" + "," +  dateKey );
						
						logFile("DVL","msg request : msg lenght : " + msg + "  :" + msg.length(),userId);
						int length = msg.length();
						
						byte[]m = msg.getBytes();	
						
						DestinationPort = getDestinationPort("KKL");
						DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);				
						SlotSocket.send(request);
						SlotSocket.setSoTimeout(4000);
						//t1.sleep(1000);
						logFile("DVL","msg request : in request : " + new String(request.getData()),userId);
																
						DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
						SlotSocket.receive(reply);				
						message += "  KKL: ";
						message += new String(reply.getData());
						//SlotSocket.close();
						//t1.interrupt();
										
						//SlotSocket = new DatagramSocket();
					    msg = "";
					    msg = "count" + "," + "WST" + "," +  dateKey;
					    m = msg.getBytes();	
					    
					    DestinationPort = getDestinationPort("WST");
						DatagramPacket request1 =new DatagramPacket (m,msg.length(),aHost,DestinationPort);
						SlotSocket.send(request1);
						
						DatagramPacket reply1 =new DatagramPacket (buffer,buffer.length);
						SlotSocket.receive(reply1);
						message += "  WST: ";
						message += new String(reply1.getData());
						//SlotSocket.close();	
						//t2.interrupt();

				}
				else if (operation.equals("book")){
				    msg = "";
				    message = "";					
					//buffer = null;
					
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile ("DVL", "in DVL booking in udpoperation + DestinationPort " + DestinationPort,userId);
					
					
					msg = new String("book" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + ","+ userId );
					logFile ("DVL", "in DVL booking in udpoperation + msg " + msg,userId);
					
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
					SlotSocket.send(request);	
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket.receive(reply);								
					message = new String(reply.getData());
					logFile ("DVL", "in DVL booking in udpoperation + reply " + message,userId);
					//SlotSocket.close();
				}
					
				else if (operation.equals("cancel")	){
					msg = "";
					message = "";					
					//buffer = null;
					
					msg = new String("cancel" + "," +  RecordID +"," + userId);	
					logFile ("DVL", "in DVL canceling in udpoperation + msg " + msg,userId);
					int length = msg.length();					
					byte[]m = msg.getBytes();
					DestinationCampus = RecordID.substring(0, 3);
					
					logFile(DestinationCampus,"DestinationCampus: " + DestinationCampus,userId);
					DestinationPort = getDestinationPort(DestinationCampus);
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);					
					SlotSocket.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket.receive(reply);							
					message = new String(reply.getData());
					//SlotSocket.close();
				}	
				else if (operation.equals("create")	){
				    msg = "";
				    message = "";													
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile ("DVL", "in DVL create in udpoperation + DestinationPort " + DestinationPort,userId);										
					msg = new String("create" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + ","+ userId );
					logFile ("DVL", "in DVL create in udpoperation + msg " + msg,userId);
					
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
					SlotSocket.send(request);	
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket.receive(reply);								
					message = new String(reply.getData());
					logFile ("DVL", "in DVL booking in udpoperation + reply " + message,userId);
					//SlotSocket.close();
										
				}	
				
				else if (operation.equals("delete")	){
				    msg = "";
				    message = "";													
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile ("DVL", "in DVL delete in udpoperation + DestinationPort " + DestinationPort,userId);										
					msg = new String("delete" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + ","+ userId );
					logFile ("DVL", "in DVL delete in udpoperation + msg " + msg,userId);
					
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
					SlotSocket.send(request);	
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket.receive(reply);								
					message = new String(reply.getData());
					logFile ("DVL", "in DVL booking in udpoperation + reply " + message,userId);
					//SlotSocket.close();
										
				}	
				
				
				
				else if (operation.equals("changeRsv")	){
					msg = "";
					message = "";					
					//buffer = null;
					String DestinationCampus2 = "";
					
					msg = new String("cancel" + "," +  RecordID  + "," + userId);	
					logFile ("DVL", "in DVL canceling in udpoperation + msg " + msg,userId);
					int length = msg.length();					
					byte[]m = msg.getBytes();
					DestinationCampus2 = RecordID.substring(0, 3);					
					logFile(DestinationCampus2,"DestinationCampus2: " + DestinationCampus2,userId);
					DestinationPort = getDestinationPort(DestinationCampus2);
					
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);					
					SlotSocket.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket.receive(reply);							
					message = new String(reply.getData());
					logFile ("DVL", "message in change reservation: " + message,userId);
					
					if (message.trim().equals("true")){
						
							logFile ("DVL", "Canceling was done ",userId);
						 	msg = "";
						    message = "";					
							//buffer = null;
						    logFile("DVL","DestinationCampus in change in true : " + DestinationCampus,userId);
							DestinationPort = getDestinationPort(DestinationCampus);
							logFile ("DVL", "in DVL booking in udpoperation + DestinationPort " + DestinationPort,userId);
							
							
							msg = new String("book" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + "," + userId );
							logFile ("DVL", "in DVL booking in udpoperation + msg " + msg,userId);
							
							length = msg.length();					
							m = msg.getBytes();							
							request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
							SlotSocket.send(request);	
							
							reply =new DatagramPacket (buffer,buffer.length);
							SlotSocket.receive(reply);								
							message = new String(reply.getData());
							logFile ("DVL", "in DVL booking in udpoperation + reply " + message,userId);	
						
						
					}

					//SlotSocket.close();
				}		

			}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(SlotSocket != null) SlotSocket.close();}
		}break;
		
		case "KKL":{
			try {
				message = "";
				String msg = "";	
				//buffer = null;
				
				SlotSocket1 = new DatagramSocket();
				InetAddress aHost = InetAddress.getByName("localhost");
				
				if (operation.equals("count")){
					message += "KKL :"  + (calculateAvailableTimeSlotsKKL (campus,dateKey));													
					msg = new String("count" + "," + "DVL" + "," +  dateKey);				
					logFile("KKL","msg request : msg lenght : " + msg + "  :" + msg.length(),userId);
					int length = msg.length();			
					byte[]m = msg.getBytes();
					
					DestinationPort = getDestinationPort("DVL");
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);			
					SlotSocket1.send(request);											
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket1.receive(reply);				
					message += "  DVL: ";
					message += new String(reply.getData());
					//SlotSocket1.close();
					
					//t2.sleep(1000);
					
					
					//SlotSocket1 = new DatagramSocket();
				    msg = "";
				    msg = "count" + "," + "WST" + "," +  dateKey;
				    m = msg.getBytes();	
				    DestinationPort = getDestinationPort("WST");
					DatagramPacket request1 =new DatagramPacket (m,msg.length(),aHost,DestinationPort);
					SlotSocket1.send(request1);				
					DatagramPacket reply1 =new DatagramPacket (buffer,buffer.length);
					SlotSocket1.receive(reply1);
					message += "  WST: ";
					message += new String(reply1.getData());
					//SlotSocket1.close();					
				}
				else if (operation.equals("book")){
					
					msg = new String("book" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot+ "," + userId  );
					logFile("KKL","msg request : msg lenght : " + msg + "  :" + msg.length(),userId);
					DestinationPort = getDestinationPort(DestinationCampus);
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);
					logFile("KKL","msg request after sending :  : " + new String(request.getData()),userId);
					SlotSocket1.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					
					SlotSocket1.receive(reply);								
					message = new String(reply.getData());
					//SlotSocket1.close();
				}
					
				else if (operation.equals("cancel")	){
										
					msg = "";
					message = "";					
					//buffer = null;
					
					msg = new String("cancel" + "," +  RecordID + "," + userId);	
					logFile ("KKL", "in KKL canceling in udpoperation + msg " + msg,userId);
					int length = msg.length();					
					byte[]m = msg.getBytes();
					DestinationCampus = RecordID.substring(0, 3);
					
					logFile(DestinationCampus,"DestinationCampus: " + DestinationCampus,userId);
					DestinationPort = getDestinationPort(DestinationCampus);
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);					
					SlotSocket1.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket1.receive(reply);							
					message = new String(reply.getData());
					//SlotSocket1.close();
				}	
				
				else if (operation.equals("create")	){
				    msg = "";
				    message = "";													
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile ("KKL", "in KKL create in udpoperation + DestinationPort " + DestinationPort,userId);										
					msg = new String("create" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + ","+ userId );
					logFile ("KKL", "in KKL create in udpoperation + msg " + msg,userId);
					
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
					SlotSocket1.send(request);	
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket1.receive(reply);								
					message = new String(reply.getData());
					logFile ("KKL", "in  creating in udpoperation + reply " + message,userId);
					//SlotSocket.close();
										
				}	
				else if (operation.equals("delete")	){
				    msg = "";
				    message = "";													
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile ("KKL", "in KKL delete in udpoperation + DestinationPort " + DestinationPort,userId);										
					msg = new String("delete" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + ","+ userId );
					logFile ("KKL", "in KKL delete in udpoperation + msg " + msg,userId);
					
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
					SlotSocket1.send(request);	
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket1.receive(reply);								
					message = new String(reply.getData());
					logFile ("KKL", "in KKL deleting in udpoperation + reply " + message);
					//SlotSocket.close();
										
				}	
				
				
				else if (operation.equals("changeRsv")	){
					msg = "";
					message = "";
					String DestinationCampus2 = "";
					//buffer = null;
					
					msg = new String("cancel" + "," +  RecordID + "," + userId);	
					logFile ("KKL", "in KKL canceling in udpoperation + msg " + msg);
					int length = msg.length();					
					byte[]m = msg.getBytes();
					DestinationCampus2 = RecordID.substring(0, 3);					
					logFile(DestinationCampus2,"DestinationCampus2: " + DestinationCampus2);
					DestinationPort = getDestinationPort(DestinationCampus2);
					
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);					
					SlotSocket1.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket1.receive(reply);							
					message = new String(reply.getData());
					logFile ("KKL", "message in change reservation: " + message);
					
					if (message.trim().equals("true")){
						
							logFile ("KKL", "Canceling was done ");
						 	msg = "";
						    message = "";					
							//buffer = null;
						    logFile("KKL","DestinationCampus in change in true : " + DestinationCampus);
							DestinationPort = getDestinationPort(DestinationCampus);
							logFile ("KKL", "in KKL booking in udpoperation + DestinationPort " + DestinationPort);
							
							
							msg = new String("book" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + "," + userId );
							logFile ("KKL", "in KKL booking in udpoperation + msg " + msg);
							
							length = msg.length();					
							m = msg.getBytes();							
							request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
							SlotSocket1.send(request);	
							
							reply =new DatagramPacket (buffer,buffer.length);
							SlotSocket1.receive(reply);								
							message = new String(reply.getData());
							logFile ("KKL", "in KKL booking in udpoperation + reply " + message);	
						
						
					}
																				
					//SlotSocket1.close();
				}		
				
				
																										
			}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(SlotSocket1 != null) SlotSocket1.close();}
		}break;
		
		case "WST":{
			try {
				message = "";
				String msg = "";	
				//buffer = null;
				
				SlotSocket2 = new DatagramSocket();
				InetAddress aHost = InetAddress.getByName("localhost");
				if (operation.equals("count")){
					message += "WST :"  + (calculateAvailableTimeSlotsWST (campus,dateKey));											
					msg = new String("count" + "," + "DVL" + "," +  dateKey);
								
					int length = msg.length();				
					byte[]m = msg.getBytes();
					DestinationPort = getDestinationPort("DVL");
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);				
					SlotSocket2.send(request);										
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket2.receive(reply);
					
					message += "  DVL: ";
					message += new String(reply.getData());
					//SlotSocket2.close();
					
					SlotSocket2 = new DatagramSocket();
				    msg = "";
				    msg = "count" + "," + "KKL" + "," +  dateKey;
				    m = msg.getBytes();
				    DestinationPort = getDestinationPort("KKL");
					DatagramPacket request1 =new DatagramPacket (m,msg.length(),aHost,DestinationPort);
					SlotSocket2.send(request1);
					
					DatagramPacket reply1 =new DatagramPacket (buffer,buffer.length);
					SlotSocket2.receive(reply1);
					message += "  KKL: ";
					message += new String(reply1.getData());
					//SlotSocket2.close();
				}//count
				else if (operation.equals("book")){
					msg = new String("book" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot+ "," + userId );
					logFile("WST","msg request : msg lenght : " + msg + "  :" + msg.length());
					DestinationPort = getDestinationPort(DestinationCampus);
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);
					logFile("","msg request after sending :  : " + new String(request.getData()));
					SlotSocket2.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					
					SlotSocket2.receive(reply);								
					message = new String(reply.getData());
					//SlotSocket2.close();
				}
					
				else if (operation.equals("cancel")	){
										
					msg = new String("cancel" + "," +  RecordID + "," + userId );
					int length = msg.length();					
					byte[]m = msg.getBytes();
					DestinationCampus = RecordID.substring(0, 3);
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile(DestinationCampus,"DestinationCampus: " + DestinationCampus);
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);					
					SlotSocket2.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket2.receive(reply);							
					message = new String(reply.getData());
					//SlotSocket2.close();
				}	
				else if (operation.equals("create")	){
				    msg = "";
				    message = "";													
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile ("WST", "in WST create in udpoperation + DestinationPort " + DestinationPort);										
					msg = new String("create" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + ","+ userId );
					logFile ("WST", "in WST create in udpoperation + msg " + msg);
					
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
					SlotSocket2.send(request);	
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket2.receive(reply);								
					message = new String(reply.getData());
					logFile ("WST", "in WST creating in udpoperation + reply " + message);
					//SlotSocket.close();
										
				}	
				
				else if (operation.equals("delete")	){
				    msg = "";
				    message = "";													
					DestinationPort = getDestinationPort(DestinationCampus);
					logFile ("WST", "in WST delete in udpoperation + DestinationPort " + DestinationPort);										
					msg = new String("delete" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + ","+ userId );
					
					logFile ("WST", "in WST delete in udpoperation + msg " + msg);
					
					int length = msg.length();					
					byte[]m = msg.getBytes();							
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
					SlotSocket2.send(request);	
					
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket2.receive(reply);								
					message = new String(reply.getData());
					logFile ("WST", "in WST deleting in udpoperation + reply " + message);
					//SlotSocket.close();
										
				}	
				
				else if (operation.equals("changeRsv")	){
					msg = "";
					message = "";					
					//buffer = null;
					String DestinationCampus2 = "";
					
					msg = new String("cancel" + "," +  RecordID + "," + userId);	
					logFile ("WST", "in WST canceling in udpoperation + msg " + msg);
					int length = msg.length();					
					byte[]m = msg.getBytes();
					DestinationCampus2 = RecordID.substring(0, 3);					
					logFile(DestinationCampus2,"DestinationCampus: " + DestinationCampus2);
					DestinationPort = getDestinationPort(DestinationCampus2);
					
					DatagramPacket request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);					
					SlotSocket2.send(request);															
					DatagramPacket reply =new DatagramPacket (buffer,buffer.length);
					SlotSocket2.receive(reply);							
					message = new String(reply.getData());
					logFile ("WST", "message in change reservation: " + message);
					
					if (message.trim().equals("true")){
						
							logFile ("WST", "Canceling was done ");
						 	msg = "";
						    message = "";					
							//buffer = null;
						    logFile("WST","DestinationCampus in change in true : " + DestinationCampus);
							DestinationPort = getDestinationPort(DestinationCampus);
							logFile ("WST", "in WST booking in udpoperation + DestinationPort " + DestinationPort);
							
							
							msg = new String("book" + "," + DestinationCampus + "," + roomNumber + "," + dateKey + "," + timeSlot + "," + userId );
							logFile ("WST", "in WST booking in udpoperation + msg " + msg);
							
							length = msg.length();					
							m = msg.getBytes();							
							request =new DatagramPacket (m,msg.length(),aHost,DestinationPort);										
							SlotSocket2.send(request);	
							
							reply =new DatagramPacket (buffer,buffer.length);
							SlotSocket2.receive(reply);								
							message = new String(reply.getData());
							logFile ("WST", "in DVL booking in udpoperation + reply " + message);	
						
						
					}
																				
					//SlotSocket2.close();
				}		
				
				
				
				
			}catch (SocketException e){System.out.println("Socket: " + e.getMessage());
			}catch (IOException e) {System.out.println("IO: " + e.getMessage());
			}finally {if(SlotSocket2 != null) SlotSocket2.close();}
		}break;
		
		default:{

		} break;
		}
		
		logFile(campus,message);
		return message;
		
	}

	
}