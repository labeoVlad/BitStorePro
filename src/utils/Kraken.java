package utils;

/**
 * @author labeoVlad
 */

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.kraken.KrakenExchange;


public class Kraken {

  private Kraken() {

  }

  public static Exchange createExchange() {
	  

      
      Exchange krakenExchange = ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName());
      krakenExchange.getExchangeSpecification().setApiKey("xxx");
      krakenExchange.getExchangeSpecification().setSecretKey("yyy");
      krakenExchange.getExchangeSpecification().setUserName("setYOURuserNamehere");
      krakenExchange.applySpecification(krakenExchange.getExchangeSpecification());
      return krakenExchange;
    
  }
}
