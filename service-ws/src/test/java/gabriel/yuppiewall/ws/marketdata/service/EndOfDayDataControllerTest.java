package gabriel.yuppiewall.ws.marketdata.service;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.server.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Test;
import org.springframework.http.MediaType;

public class EndOfDayDataControllerTest {
	@Test
	public void json() throws Exception {

		standaloneSetup(new EndOfDayDataController())
				.build()
				.perform(get("/person/Lee").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().mimeType("application/json;charset=UTF-8"))
				.andExpect(jsonPath("$.name").value("Lee"));
	}
}
