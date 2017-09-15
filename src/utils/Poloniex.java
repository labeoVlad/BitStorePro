package utils;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.poloniex.PoloniexExchange;

/**
 * @author labeoVlad
 */

public class Poloniex {

  public static Exchange getExchange() {

    ExchangeSpecification spec = new ExchangeSpecification(PoloniexExchange.class);
    spec.setApiKey("xxx");
    spec.setSecretKey("yyy");

    return ExchangeFactory.INSTANCE.createExchange(spec);
  }
}
