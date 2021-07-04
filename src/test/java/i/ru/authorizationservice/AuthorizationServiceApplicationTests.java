package i.ru.authorizationservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthorizationServiceApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    public static GenericContainer<?> devapp = new GenericContainer<>("devapp:latest")
            .withExposedPorts(8080);

    @Container
    public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp:latest")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> forEntity1 = restTemplate.getForEntity("http://localhost:" + devapp.getMappedPort(8080) + "/authorize?user=Ivan&password=1234", String.class);
        ResponseEntity<String> forEntity2 = restTemplate.getForEntity("http://localhost:" + prodapp.getMappedPort(8081) + "/authorize?user=Pasha&password=7890", String.class);
        System.out.println("devapp " + forEntity1.getBody());
        System.out.println("prodapp " + forEntity2.getBody());
    }
}
