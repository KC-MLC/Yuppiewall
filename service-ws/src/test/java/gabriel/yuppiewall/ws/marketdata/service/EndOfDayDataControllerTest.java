package gabriel.yuppiewall.ws.marketdata.service;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.server.MockMvc;

public class EndOfDayDataControllerTest {

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = standaloneSetup(new EndOfDayDataController()).build();
	}

	@Test
	public void json() throws Exception {
		this.mockMvc.perform(post("/endofday/").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isAccepted());
	}
}
