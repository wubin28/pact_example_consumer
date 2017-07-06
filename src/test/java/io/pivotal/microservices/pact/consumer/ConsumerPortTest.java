package io.pivotal.microservices.pact.consumer;

import au.com.dius.pact.consumer.*;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.PactFragment;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;


public class ConsumerPortTest {

    @Rule
    public PactProviderRuleMk2 rule = new PactProviderRuleMk2("Foo_Provider", "localhost", 8080, this);



    @Pact(provider="Foo_Provider", consumer="Foo_Consumer")
    public RequestResponsePact createFragment(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json;charset=UTF-8");

        return builder.given("Foo_State")
                .uponReceiving("a request for Foos")
                    .path("/foos")
                    .method("GET")
                .willRespondWith()
                    .headers(headers)
                    .status(200)
                    .body("[{\"value\":42}, {\"value\":100}]")
                .toPact();
    }

    @Test
    @PactVerification("Foo_Provider")//可以有多个 provider, 这里指定当前 mock 哪个
    public void runTest() {
        assertEquals(new ConsumerPort("http://localhost:8080").foos(), Arrays.asList(new Foo(42), new Foo(100)));
    }
}
