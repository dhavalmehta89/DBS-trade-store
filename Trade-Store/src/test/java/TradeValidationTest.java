import org.junit.Before;
import org.junit.Test;
import model.Trade;
import store.TradeStore;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;


public class TradeValidationTest {

    TradeStore tradeStore ;

    @Before
    public void setUp() throws Exception {
        tradeStore = new TradeStore();

    }

    @Test
    public void insertTradeTest(){

        Trade trade1 = new Trade("T1",1,"CP-1","B1", LocalDate.now().plusDays(1),LocalDate.now(),Boolean.FALSE);
        assertEquals(tradeStore.getTotalTradeCount(),0);

        tradeStore.insertTrade(trade1);

        assertEquals(tradeStore.getTotalTradeCount(),1);

    }

    @Test(expected = IllegalStateException.class)
    public void insertPastMaturityTradeTest(){
        Trade trade1 = new Trade("T1",1,"CP-1","B1", LocalDate.now().plusDays(-1),LocalDate.now(),Boolean.FALSE);

        tradeStore.insertTrade(trade1);

    }

    @Test
    public void updateTradeTest(){

        Trade trade1 = new Trade("T1",1,"CP-1","B1", LocalDate.now().plusDays(1),LocalDate.now(),Boolean.FALSE);
        tradeStore.insertTrade(trade1);

        trade1.setMaturityDate(LocalDate.now().plusDays(2));
        trade1.setCreatedDate(LocalDate.now().minusDays(2));
        trade1.setExpired(Boolean.TRUE);
        trade1.setCounterPartyId("CP-2");
        trade1.setBookId("B2");

        tradeStore.updateTrade(trade1);

        Trade trade2 = tradeStore.getTrade(trade1.getTradeId(),trade1.getVersion());

        assertEquals(trade2.getMaturityDate(),LocalDate.now().plusDays(2));
        assertEquals(trade2.getCreatedDate(),LocalDate.now().minusDays(2));
        assertEquals(trade2.getExpired(),Boolean.TRUE);
        assertEquals(trade2.getCounterPartyId(),"CP-2");
        assertEquals(trade2.getBookId(),"B2");

    }

    @Test(expected = IllegalStateException.class)
    public void updatePastMaturityTradeTest() {

        Trade trade1 = new Trade("T1", 1, "CP-1", "B1", LocalDate.now().plusDays(1), LocalDate.now(), Boolean.FALSE);
        tradeStore.insertTrade(trade1);

        trade1.setMaturityDate(LocalDate.now().plusDays(-2));
        trade1.setCreatedDate(LocalDate.now().minusDays(2));
        trade1.setExpired(Boolean.TRUE);
        trade1.setCounterPartyId("CP-2");
        trade1.setBookId("B2");

        tradeStore.updateTrade(trade1);
    }

    @Test
    public void deleteTradeTest(){

        Trade trade1 = new Trade("T1",1,"CP-1","B1", LocalDate.now().plusDays(1),LocalDate.now(),Boolean.FALSE);
        tradeStore.insertTrade(trade1);
        assertEquals(tradeStore.getTotalTradeCount(),1);
        tradeStore.delete(trade1);
        assertEquals(tradeStore.getTrade(trade1.getTradeId(),trade1.getVersion()),null);

    }


}
