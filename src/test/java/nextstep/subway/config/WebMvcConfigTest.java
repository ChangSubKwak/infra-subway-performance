package nextstep.subway.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.CacheControl;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebMvcConfigTest {

    String STATIC_PATH_BEFORE_CONFIG = "/resources/js/main.js";

    @Autowired
    private WebTestClient client;

    @Test
    void 정적_리소스를_요청한다() {
        EntityExchangeResult<String> response = client.get()
                .uri(STATIC_PATH_BEFORE_CONFIG)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().cacheControl(CacheControl.noCache().cachePrivate())
                .expectBody(String.class)
                .returnResult();

        String eTag = response.getResponseHeaders().getETag();

        assertThat(eTag).isNotNull();
    }
}
