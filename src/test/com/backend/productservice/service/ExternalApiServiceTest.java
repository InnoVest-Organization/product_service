import com.backend.productservice.service.ExternalApiService;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExternalApiServiceTest {

    private static WireMockServer investorServiceMock;
    private static WireMockServer notificationServiceMock;
    private static ExternalApiService externalApiService;

    @BeforeAll
    public static void setup() {
        // Start mock servers
        investorServiceMock = new WireMockServer(5006);
        investorServiceMock.start();
        configureFor("localhost", 5006);

        notificationServiceMock = new WireMockServer(5005);
        notificationServiceMock.start();
        configureFor("localhost", 5005);

        // Initialize service with RestTemplate
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        externalApiService = new ExternalApiService(restTemplate);

        // Mocking Investor Service Response
        investorServiceMock.stubFor(post(urlEqualTo("/api/investors/match"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("[\"investor1@example.com\", \"investor2@example.com\"]")
                        .withStatus(200)));

        // Mocking Notification Service Response
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
    public void testGetInvestorEmails() {
        List<String> emails = externalApiService.getInvestorEmails(1L, List.of("Tech", "AI"), "PREMIUM");

        assertNotNull(emails);
        assertEquals(2, emails.size());
        assertEquals("investor1@example.com", emails.get(0));
    }

    @Test
    public void testSendNotifications() {
        externalApiService.sendNotifications(List.of("investor1@example.com", "investor2@example.com"));

        notificationServiceMock.verify(postRequestedFor(urlEqualTo("/api/notifications")));
    }
}
