package gabriel.yuppiewall.ws.marketdata.service;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;
import org.springframework.util.FileCopyUtils;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
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

		byte[] jsonRequest = FileCopyUtils
				.copyToByteArray(new ClassPathResource("data1.json").getFile());
		this.mockMvc.perform(
				post("/endofday/").accept(MediaType.APPLICATION_JSON).body(
						jsonRequest)).andExpect(status().isAccepted());
	}
}
