package store;

import model.Trade;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TradeStore {

    public TradeStore() {
      this.registerExpiryUpdateService();
    }

    private Map <String, Trade> store = new HashMap<>();

    public List<Trade> findTrades(String tradeId){

        return this.store.values().stream().filter(trade -> trade.getTradeId().equals(tradeId)).collect(Collectors.toList());
    }

    public Trade getTrade(String tradeId, Integer version) {
        return this.store.get(tradeId+version);
    }

    public void insertTrade(Trade trade){

        this.validateTrade(trade);
        store.put(trade.getTradeId()+trade.getVersion(),trade);

    }

    public void updateTrade(Trade trade){

        this.validateTrade(trade);

        if(store.containsKey(trade.getTradeId()+trade.getVersion())){
            store.put(trade.getTradeId()+trade.getVersion(),trade);
        }else{
            throw new RuntimeException("Trade not found for given TradeId and Version");
        }

    }

    public Boolean delete(Trade trade){
        return this.store.remove(trade.getTradeId()+trade.getVersion())!=null;
    }

    private void registerExpiryUpdateService(){

        LocalDate today = LocalDate.now();
        LocalDateTime tomorrow = today.plusDays(1).atStartOfDay();

        Duration duration = Duration.between(LocalDateTime.now(),tomorrow);

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            this.store.values().stream()
                    .forEach(trade -> {
                        if(trade.getMaturityDate()!=null &&
                                trade.getMaturityDate().isBefore(LocalDate.now())){
                            trade.setExpired(Boolean.TRUE);
                        }
                    });

        }, duration.getSeconds(), TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }

    public int getTotalTradeCount(){
        return this.store.size();
    }

    public void validateTrade(Trade trade){

        List<Trade> trades = findTrades(trade.getTradeId());

        OptionalInt optionalInt = trades.stream().mapToInt(trade1-> trade1.getVersion()).max();

        if(optionalInt.isPresent() && optionalInt.getAsInt()> trade.getVersion()){
            throw new IllegalStateException("Trade with higher version is already available");

        }
        if (trade.getMaturityDate() != null && trade.getMaturityDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Maturity date is null or date is passed");
        }

    }

}
