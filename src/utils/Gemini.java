package utils;

/**
 * @author labeoVlad
 */

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.gemini.v1.GeminiExchange;

public class Gemini {

  public static Exchange getExchange() {

    ExchangeSpecification exSpec = new ExchangeSpecification(GeminiExchange.class);
    exSpec.setApiKey("xxx");
    exSpec.setSecretKey("yyy");

    return ExchangeFactory.INSTANCE.createExchange(exSpec);
  }

}
