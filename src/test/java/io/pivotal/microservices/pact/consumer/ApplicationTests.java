package io.pivotal.microservices.pact.consumer;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApplicationTests {

	@Rule
	public PactProviderRuleMk2 providerMock = new PactProviderRuleMk2("Foo_Provider", "localhost", 9090,this);

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

		ParameterizedTypeReference<List<Foo>> responseType = new ParameterizedTypeReference<List<Foo>>() {};
		final List<Foo> response = new RestTemplate().exchange("http://localhost:8080/foos", HttpMethod.GET, null, responseType).getBody();
		final List<Foo> expected = Arrays.asList(new Foo(42), new Foo(100));

		assertEquals(expected,response);
	}

}
