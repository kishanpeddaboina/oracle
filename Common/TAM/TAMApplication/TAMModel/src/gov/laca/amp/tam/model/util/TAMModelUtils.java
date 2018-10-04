package gov.laca.amp.tam.model.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TAMModelUtils {
    public TAMModelUtils() {
        super();
    }
    
    public static String deriveAssignedToRole(String assignedByRole){
        String derivedAssignedToRole="";
            switch(assignedByRole.charAt(0)){
            case 'S' :
                derivedAssignedToRole = "Appraiser"; 
                break;
            case 'P' :
                 derivedAssignedToRole = "Supervisor"; 
                break;
            case 'C' :
                 derivedAssignedToRole = "Principal Appraiser"; 
                break;
            default:
                System.out.println("==> Invalid Assinged ByRole:"+assignedByRole);
                break;
                
            }   
            return derivedAssignedToRole;
    }
    
    public static HashMap<String,String> TAM_TO_LDAP_DISTRICTS_MAP = 
        new  HashMap<String,String>()
        {{
//                  put("North District Office","North");
//                  put("South District Office","South");
//                  put("East District Office","East");
//                  put("West District Office","West");
//                  put("Hall of Administration","HOA");
//                  put("Lancaster Office","HOA");
//                  put("Unknown","HOA");
                  
                  put("North","North");
                  put("South","South");
                  put("East","East");
                  put("West","West");
                  put("HOA","HOA");
                  put("Lancaster Office","HOA");
                  put("Unknown","HOA");                  
              
              }};

    public static HashMap<String,String> LDAP_TO_TAM_DISTRICTS_MAP = 
        new  HashMap<String,String>()
        {{
    //                  put("North","North District Office");
    //                  put("South","South District Office");
    //                  put("East","East District Office");
    //                  put("West","West District Office");
   //                   put("HOA","Hall of Administration,Lancaster Office,Unknown");          
                  
                  put("North","North");
                  put("South","South");
                  put("East","East");
                  put("West","West");                 
                  put("HOA","HOA");                  
              
              }};
    
    
    public static ArrayList<String> ALL_TAM_DISTRICTS_LIST = 
    new ArrayList<String>(
        //Arrays.asList( "Hall of Administration","Lancaster Office","Unknown","East District Office", "West District Office", "North District Office" ,"South District Office" ));
        Arrays.asList( "HOA","East", "West", "North" ,"South" ));  
    
    
    public static HashMap<String,String> LDAP_IMMEDIATE_LOW_LEVEL_ROLE_MAP = 
        new  HashMap<String,String>()
        {{
                  put("Chief Appraiser","Principal Appraiser");
                  put("Principal Appraiser","Supervisor");
                  put("Supervisor","Appraiser");
          
              
              }};
           
}
