import com.backend.productservice.service.InventionService;
import com.backend.productservice.repository.InventionRepository;
import com.backend.productservice.entity.Invention;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
public class InventionServiceTest {

    @Mock
    private InventionRepository inventionRepository;

    @InjectMocks
    private InventionService inventionService;

    private static WireMockServer investorServiceMock;
    private static WireMockServer notificationServiceMock;

    @BeforeAll
    public static void setup() {
        investorServiceMock = new WireMockServer(5006);
        investorServiceMock.start();
        configureFor("localhost", 5006);

        notificationServiceMock = new WireMockServer(5005);
        notificationServiceMock.start();
        configureFor("localhost", 5005);

        investorServiceMock.stubFor(post(urlEqualTo("/api/investors/match"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("[\"investor1@example.com\", \"investor2@example.com\"]")
                        .withStatus(200)));

        notificationServiceMock.stubFor(post(urlEqualTo("/api/notifications"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("Notifications sent successfully!")));
    }

    @AfterAll
    public static void tearDown() {
        investorServiceMock.stop();
        notificationServiceMock.stop();
    }

    @Test
    public void testUpdateBidTimes() {
        Invention mockInvention = new Invention();
        mockInvention.setInventionId(1L);
        mockInvention.setAoi(List.of("Tech", "AI"));
        mockInvention.setPaymentPackage(Invention.PaymentPackage.PREMIUM);
        mockInvention.setBidStartTime(LocalTime.of(10, 0));
        mockInvention.setBidEndTime(LocalTime.of(18, 0));
        mockInvention.setBidStartDate(LocalDate.of(2025, 3, 20));

        when(inventionRepository.findById(1L)).thenReturn(Optional.of(mockInvention));
        when(inventionRepository.save(any(Invention.class))).thenReturn(mockInvention);

        boolean result = inventionService.updateBidTimes(1L, LocalTime.of(10, 0), LocalTime.of(18, 0),
                LocalDate.of(2025, 3, 20));

        assertTrue(result);
        verify(inventionRepository, times(1)).save(mockInvention);
    }
}
