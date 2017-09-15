package account;

/**
 * @author labeoVlad
 */

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.marketdata.MarketDataService;

import org.reflections.Reflections;

import utils.Gemini;
import utils.Kraken;


public class BalanceInfo {
	
	//Constructors
	
	//Data
	List<BigDecimal> totalBtc = new ArrayList<BigDecimal>();
	List<BigDecimal> totalUsd = new ArrayList<BigDecimal>();

	public static BigDecimal 	BTC_DOLLAR_PRICE	 ;
	
	
	//Methods	
	public BigDecimal getBtcEquivalent(Exchange exch, Currency cur, Wallet wal, MarketDataService mds) throws IOException {
		
		BigDecimal btcEquivalent =  BigDecimal.ZERO;
		
    	if ( wal.getBalance(cur).getCurrency().getSymbol().equals("BTC") ) 
    	{btcEquivalent = wal.getBalance(cur).getTotal()    ; } 
    	
    	else if ( wal.getBalance(cur).getCurrency().getSymbol().equals("$")) 
		{btcEquivalent =  wal.getBalance(cur).getTotal().divide( mds.getTicker(new CurrencyPair(Currency.BTC, cur)).getLast(), BigDecimal.ROUND_HALF_UP )   ; 	}
    	
    	else if ( wal.getBalance(cur).getCurrency().getSymbol().equals("USDT")) 
		{	btcEquivalent =  wal.getBalance(cur).getTotal().divide( mds.getTicker(new CurrencyPair(Currency.BTC, cur)).getLast() ,  BigDecimal.ROUND_HALF_UP); 	}    	
    	
    	else
		{	btcEquivalent = mds.getTicker(new CurrencyPair(cur, Currency.BTC)).getLast().multiply(wal.getBalance(cur).getTotal() ) ; 	}
		
		return btcEquivalent; 
	}
	
	
	public BigDecimal getUsdEquivalent(Exchange exch, Currency cur, Wallet wal, MarketDataService mds) throws IOException {
		
		BigDecimal usdEquivalent =  BigDecimal.ZERO;
		
    	if ( wal.getBalance(cur).getCurrency().getSymbol().equals("$") ) 
    	{usdEquivalent = wal.getBalance(cur).getTotal()    ; } 
    	
    	else if ( wal.getBalance(cur).getCurrency().getSymbol().equals("USDT") ) 
    	{usdEquivalent = wal.getBalance(cur).getTotal()    ; }     // need to introduce USDT/USD rate here. Use Kraken as the only source	
    	
    	else if ( wal.getBalance(cur).getCurrency().getSymbol().equals("BTC")) 
		{usdEquivalent =  wal.getBalance(cur).getTotal().multiply(getBtcUsdPrice(exch,mds) )   ; 	}
    	
    	else
		{	// it gives just an estimate in USD , not exact price, because the price is translated through BTC. 
    		// in fact it will be better to have a direct price in USD for specific currency. 
    		// it will be more accurate evaluation, but not every currency is  traded against dollars. 
    		// instead every currency is traded against Bitcoin, this is why this method was chosen
    		usdEquivalent = mds.getTicker(new CurrencyPair(cur, Currency.BTC)).getLast().multiply(wal.getBalance(cur).getTotal()).multiply(getBtcUsdPrice(exch,mds))  ; 	}
		
		return usdEquivalent.setScale(2, BigDecimal.ROUND_HALF_UP) ; 
	}
	
	
	public BigDecimal getTotalBtc () {
	    BigDecimal totalBitcoin = new BigDecimal(0);
	    for (BigDecimal b : totalBtc) {
	    	totalBitcoin = totalBitcoin.add(b);
	    }
	    return totalBitcoin; 
	}
	
	
	public BigDecimal getTotalUsd () {
	    BigDecimal totalDollar = new BigDecimal(0);
	    for (BigDecimal b : totalUsd) {
	    	totalDollar = totalDollar.add(b);
	    }
	    return totalDollar ;
	}
	
	

	public BigDecimal getBtcUsdPrice (Exchange exch, MarketDataService mds) throws IOException  {
	    BigDecimal BtcUsdPrice = BigDecimal.ZERO;
	    
	    if (exch.getExchangeMetaData().getCurrencyPairs().containsKey(CurrencyPair.BTC_USD))  {
	    BtcUsdPrice = mds.getTicker(CurrencyPair.BTC_USD).getLast(); }
	    
	    else {
	    BtcUsdPrice = BTC_DOLLAR_PRICE;
	    }
	    
	    return BtcUsdPrice; 
	}
	


	
	
	//Main
	public static void main(String[] args) throws IOException  {
				BalanceInfo ballanceInfo = new BalanceInfo(); 		
				String[] keys; 
				
			  	//set a value to compare to
			    BigDecimal zero = BigDecimal.ZERO;
			    
		    	// Some exchanges don't trade BTC/USD pair, therefore we need to create a global definition for BTC_DOLLAR_PRICE. Gemini BTC/USD price will be used  
			    Exchange GeminiExchange = Gemini.getExchange(); 
		        MarketDataService GeminiMarketDataService = GeminiExchange.getMarketDataService() ;	
		        BTC_DOLLAR_PRICE = GeminiMarketDataService.getTicker(CurrencyPair.BTC_USD).getLast();
			    
			    // Find every Exchange
			    Reflections reflections = new Reflections("org.knowm.xchange");

			    for (Class<? extends Exchange> exchangeClass : reflections.getSubTypesOf(Exchange.class)) {
				      if (Modifier.isAbstract(exchangeClass.getModifiers())) {
				       continue;
				      }
				      
				      if (	exchangeClass.getSimpleName().equals("BittrexExchange") ||
				    		exchangeClass.getSimpleName().equals("GeminiExchange") ||
				    		exchangeClass.getSimpleName().equals("KrakenExchange") ||
				    		exchangeClass.getSimpleName().equals("PoloniexExchange")     		  
				    	 ) {
			    
			    
				    	  	System.out.println(); 
						    
						    keys =  utils.MyExchange.getKeys( exchangeClass.getSimpleName() );
						    
						    ExchangeSpecification spec = new ExchangeSpecification(exchangeClass);
						    spec.setApiKey( keys[0]);  spec.setSecretKey( keys[1] );
						    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);
						    AccountService asaccountService = exchange.getAccountService();
						    AccountInfo accountInfo =  asaccountService.getAccountInfo();
						    Wallet wallet =  accountInfo.getWallet(); 			    
						    MarketDataService marketDataService = exchange.getMarketDataService() ;		    
						    Map<Currency, Balance> walletBalance =  wallet.getBalances() ;
					    
					    //print only currencies with positive balance or with negative balance  . Exclude those with zero balances
					    for(Currency key : walletBalance.keySet()) {
					    	if ( wallet.getBalance(key).getTotal().compareTo(zero)  != 0)     { 
					    		System.out.printf("%-6s %-20s %-25s %-25s %-25s",	wallet.getBalance(key).getCurrency(),
					    															wallet.getBalance(key).getCurrency().getDisplayName(),  
					    															wallet.getBalance(key).getTotal() ,
					    															ballanceInfo.getBtcEquivalent(exchange,   key, wallet, marketDataService) ,  
					    															ballanceInfo.getUsdEquivalent(exchange,   key, wallet, marketDataService) 
					    						 );  
					    						
					    		ballanceInfo.totalBtc.add( ballanceInfo.getBtcEquivalent(exchange, key, wallet, marketDataService) ) ; 
					    		ballanceInfo.totalUsd.add( ballanceInfo.getUsdEquivalent(exchange, key, wallet, marketDataService) ); 
					    		
					    		System.out.println(     )   ;   
					    		} //if positive or negative balance found
					    } // for every currency in a wallet
			      } //if required exchange found
			    } //for every exchange from reflections
	
 
			    System.out.println(  );	
			    


				   
			    System.out.printf("%-6s %-20s %-25s %-25s %-25s", "", "",  "" , ballanceInfo.getTotalBtc() , ballanceInfo.getTotalUsd() ) ;  
		} //main
} //ballanceInfo class
