# BitStorePro
BitStorePro

Use /src/account/BalanceInfo.java to retrieve your balances from multiple accounts accross different exchanges

At this point I have preconfogured four exchanges, but you can add more simply by extending this if statement 

				      if (	exchangeClass.getSimpleName().equals("BittrexExchange") ||
				    		exchangeClass.getSimpleName().equals("GeminiExchange") ||
				    		exchangeClass.getSimpleName().equals("KrakenExchange") ||
				    		exchangeClass.getSimpleName().equals("PoloniexExchange")     		  
				    	 ) {
