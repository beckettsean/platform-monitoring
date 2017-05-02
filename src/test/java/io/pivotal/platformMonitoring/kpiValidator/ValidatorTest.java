package io.pivotal.platformMonitoring.kpiValidator;

import org.cloudfoundry.doppler.*;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.doppler.ReactorDopplerClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;

import javax.management.MBeanServer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static io.pivotal.platformMonitoring.kpiValidator.Validator.KPI_FILE_NAME;
import static java.util.stream.Collectors.toList;

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
        validator.run(receivedMetrics());
    }

    @Test
    public void itExitsWithOneIfMissingKPIs() throws Exception{
        try {
            validator.run(new HashSet<String>());;
        } catch( RuntimeException e) {
            assert(e.getMessage()).equals(Validator.MISSING_KPIS);
        }
    }

    private static Set<String> receivedMetrics() throws IOException {
        return new HashSet<>(Files.lines(Paths.get(KPI_FILE_NAME))
            .collect(toList()));
    }

}
