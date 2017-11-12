package com.grellenort.feedbackservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grellenort.feedbackservice.controller.dto.FeedbackDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedbackServiceApplicationTests {

	private static final String BASE_URL = "/api/category";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	// Fix of LocalDateTime deserialization bug.
	private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}

	@Test
	public void testFilterByName_validName_success() throws Exception {
		final String expectedName = "Stransky";
		final ResultActions ra = this.mockMvc.perform(
				get(BASE_URL)
						.param("name", expectedName)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())

				.andExpect(MockMvcResultMatchers.content()
						.contentType(MediaType.APPLICATION_JSON_UTF8));

		final String content = ra.andReturn().getResponse().getContentAsString();
		final FeedbackDto result = objectMapper.readValue(content, FeedbackDto.class);

		Assert.assertEquals(expectedName, result.getName());
		Assert.assertEquals("I do not know.", result.getDescription());
	}

	@Test
	public void testFilterByName_doesNotExist_notFound() throws Exception {
		final ResultActions ra = this.mockMvc.perform(
				get(BASE_URL)
						.param("name", "xxxxx")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		ra.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testFilterByName_attributeTooLong_error() {

	}

	@Test
	public void testCreate_allAttributesFit_success() {

	}

	@Test
	public void testCreate_nameTooLong_error() {

	}

	@Test
	public void testCreate_descriptionTooLong_error() {

	}

	@Test
	public void testFindAll_success() {
		// insert one item and count that are in db.
	}

	@Test
	public void contextLoads() {
	}

}
