package yahoofinance;

import org.junit.Test;
import yahoofinance.mock.MockedServersTest;

import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Map;

import static org.junit.Assert.*;

/**
 *
 * @author Christophe Alexandre
 */
public class SerializeQuoteTest extends MockedServersTest {

    @Test
    public void storeStockTest() throws Exception {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.YEAR, 2016);
        today.set(Calendar.MONTH, 8);
        today.set(Calendar.DATE, 11);
        Calendar from = (Calendar) today.clone();
        from.add(Calendar.YEAR, -1);
        String[] symbols = new String[] {"INTC", "AIR.PA"};
        Map<String, Stock> stocks = YahooFinance.get(symbols, from, today);
        Stock intel = stocks.get("INTC");
        Stock airbus = stocks.get("AIR.PA");
        Utils.storeObject(Paths.get("data", "intel.ser"), intel);
        Utils.storeObject(Paths.get("data", "airbus.ser"), airbus);
        Stock loadedIntel = Utils.loadObject(Paths.get("data", "intel.ser"));
        Stock loadedAirbus = Utils.loadObject(Paths.get("data", "airbus.ser"));

        assertEquals(13, loadedIntel.getHistory().size());
        assertEquals(13, loadedAirbus.getHistory().size());
        assertEquals("INTC", loadedIntel.getHistory().get(3).getSymbol());
        assertEquals("AIR.PA", loadedAirbus.getHistory().get(5).getSymbol());
    }

}
