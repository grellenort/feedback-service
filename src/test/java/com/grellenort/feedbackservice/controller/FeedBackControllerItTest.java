package com.grellenort.feedbackservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.grellenort.feedbackservice.controller.dto.FeedbackDto;
import com.grellenort.feedbackservice.controller.dto.req.FeedbackCreateReqDto;
import com.grellenort.feedbackservice.model.cat.FeedbackType;
import com.grellenort.feedbackservice.testutils.GeneratorUtils;
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

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FeedBackControllerItTest {

	private static final String BASE_URL = "/api/category";

	private static final String FIND_ALL_URL = BASE_URL + "/find-all";

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	// Fix of LocalDateTime deserialization bug - register modules.
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
		Assert.assertEquals(FeedbackType.NEUTRAL, result.getType());
		Assert.assertNotNull(result.getCreatedAt());
		Assert.assertNotNull(result.getId());
	}

	@Test
	public void testFilterByName_doesNotExist_notFound_error() throws Exception {
		final ResultActions ra = this.mockMvc.perform(
				get(BASE_URL)
						.param("name", "xxxxx")
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		ra.andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testCreate_allAttributesFit_success() throws Exception {
		final FeedbackCreateReqDto reqDto = new FeedbackCreateReqDto("Novak",
				"Short description.", FeedbackType.POSITIVE);

		final ResultActions ra = this.mockMvc.perform(
				post(BASE_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(reqDto))
		).andDo(print());
		ra.andExpect(MockMvcResultMatchers.status().isCreated());
		final String responseContent = ra.andReturn().getResponse().getContentAsString();
		final FeedbackDto responseDto = objectMapper.readValue(responseContent, FeedbackDto.class);

		Assert.assertEquals(reqDto.getName(), responseDto.getName());
		Assert.assertEquals(reqDto.getContent(), responseDto.getDescription());
		Assert.assertEquals(reqDto.getFeedbackType(), responseDto.getType());
		Assert.assertNotNull(responseDto.getId());
		Assert.assertNotNull(responseDto.getCreatedAt());
	}

	@Test
	public void testCreate_nameIsNull_error() throws Exception {
		final FeedbackCreateReqDto reqDto = new FeedbackCreateReqDto(
				null, "Short description.", FeedbackType.POSITIVE);
		testCreateCommonBadRequest(reqDto);
	}

	@Test
	public void testCreate_descriptionIsNull_error() throws Exception {
		final FeedbackCreateReqDto reqDto = new FeedbackCreateReqDto(
				"Petr", null, FeedbackType.POSITIVE);
		testCreateCommonBadRequest(reqDto);
	}

	@Test
	public void testCreate_TypeIsNull_error() throws Exception {
		final FeedbackCreateReqDto reqDto = new FeedbackCreateReqDto(
				"Petr", "Short description", null);
		testCreateCommonBadRequest(reqDto);
	}

	@Test
	public void testCreate_NameTooLong_error() throws Exception {
		final FeedbackCreateReqDto reqDto = new FeedbackCreateReqDto(
				GeneratorUtils.generateLongString(70), "Short description", null);
		testCreateCommonBadRequest(reqDto);
	}

	@Test
	public void testCreate_DescriptionTooLong_error() throws Exception {
		final FeedbackCreateReqDto reqDto = new FeedbackCreateReqDto(
				"Petr", GeneratorUtils.generateLongString(600), FeedbackType.POSITIVE);
		testCreateCommonBadRequest(reqDto);
	}

	public void testCreateCommonBadRequest(final FeedbackCreateReqDto reqDto) throws Exception {


		final ResultActions ra = this.mockMvc.perform(
				post(BASE_URL)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(reqDto))
		).andDo(print());
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testFindAll_success() throws Exception {
		final ResultActions ra = this.mockMvc.perform(
				get(FIND_ALL_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andDo(print());
		ra.andExpect(MockMvcResultMatchers.status().isOk());

		final String content = ra.andReturn().getResponse().getContentAsString();
		final List<FeedbackDto> result = objectMapper.readValue(
				content, new TypeReference<List<FeedbackDto>>() {
				});

		final int actualSize = result.size();
		Assert.assertTrue(actualSize >= 6);

		final String expectedNameContained = "Stransky";
		final FeedbackDto feedbackDto = result.stream()
				.filter(dto -> expectedNameContained.equalsIgnoreCase(dto.getName()))
				.findAny()
				.orElseThrow(() -> new AssertionError("Expected dto not found"));

		Assert.assertEquals("I do not know.", feedbackDto.getDescription());
		Assert.assertEquals(FeedbackType.NEUTRAL, feedbackDto.getType());
		Assert.assertNotNull(feedbackDto.getId());
		Assert.assertNotNull(feedbackDto.getCreatedAt()); // can be tested its more less now.
	}
}
