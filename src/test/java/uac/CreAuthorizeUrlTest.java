package uac;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp,src/main/resources")
@ContextHierarchy({ @ContextConfiguration(locations = "classpath:spring/spring-mvc.xml") })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreAuthorizeUrlTest {
	@Autowired
	protected WebApplicationContext wac;
	protected MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void testThirdAuthorizeAPI() throws Exception {
		// o1gEVs8EouIDNZqwflQy-jDFDUu4
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/authorizeurl.do").param("sysCnl", "WAP")
				.param("bindType", "WEIXIN").param("scope", "1").param("methodType", "authorizeLogin"));
		MvcResult mrs = result.andDo(MockMvcResultHandlers.print()).andReturn();
		int resultStr = mrs.getResponse().getStatus();
		String content = mrs.getResponse().getContentAsString();
		System.out.println("content[" + content + "]");
		//
		Assert.assertEquals(200, resultStr);
	}

}
