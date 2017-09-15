package utils;


import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.poloniex.PoloniexExchange;

/**
 * @author labeoVlad
 */
public class MyExchange {
	

	
	  public static String[] getKeys(String myExchange) {
		  
		  
		  
		  String ApiKey , SecretKey , UserName; 
		  String[] keys = new String[3]; 
		  
			switch (myExchange) {
			
			case "BittrexExchange":
				//System.out.println("BittrexExchange");	
				ApiKey 		= "xxx";
				SecretKey	= "yyy"; 	
				keys[0] = ApiKey; 
				keys[1] = SecretKey; 
				break;
				
			case "GeminiExchange":
				//System.out.println("GeminiExchange");
				ApiKey 		= "xxx";
				SecretKey	= "yyy"; 	
				keys[0] = ApiKey; 
				keys[1] = SecretKey; 
				break;
				
			case "KrakenExchange":
				//System.out.println("KrakenExchange");
				ApiKey 		= "xxx";
				SecretKey	=  "yyy"; 
				UserName    = "setYOURuserNamehere";
				keys[0] = ApiKey; 
				keys[1] = SecretKey; 
				break;
				
			case "PoloniexExchange":
				//System.out.println("PoloniexExchange");
				ApiKey	= new String("xxx");
				SecretKey	= "yyy"; 		
				keys[0] = ApiKey; 
				keys[1] = SecretKey; 
				break;				
				
				
			default:
				System.out.println("Unknown Exchange " + myExchange + " please implement API keys for it");
			}


		    return keys;
		  }

}
