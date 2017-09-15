package utils;
/**
 * @author labeoVlad
 */
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.v1.BittrexExchange;

public class Bittrex {

  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(BittrexExchange.class);
    exSpec.setApiKey("xxx");
    exSpec.setSecretKey("yyy");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
