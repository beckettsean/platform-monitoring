package io.pivotal.platformMonitoring.kpiValidator;

import com.jamonapi.MonitorFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.pivotal.platformMonitoring.kpiValidator.Validator.KPI_FILE_NAME;

public class ValidatorTest {
    private Validator validator;

    @Before
    public void setUp() throws Exception {
        System.setProperty("RUN_TIME_MINUTES", "0.1");
        System.setProperty("POLL_INTERVAL_SECONDS", "2");
        validator = new Validator();
    }

    @Test
    public void itExitsWithZeroIfNoMissingKPIs() throws Exception {
        receivedMetrics();
        validator.run();
    }

//    @Test
//    public void itExitsWithOneIfMissingKPIs() throws Exception{
//        try {
//            validator.run();
//        } catch( RuntimeException e) {
//            assert(e.getMessage()).equals(Validator.MISSING_KPIS);
//        }
//    }

    private static void receivedMetrics() throws IOException {
        Files.lines(Paths.get(KPI_FILE_NAME)).forEach(kpi -> MonitorFactory.add(kpi, "hits", 1.0));
    }

}
