package uac;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.mlx.uac.dict.ConstEC;
import com.mlx.uac.sign.Sign;
import com.mlx.uac.utils.ApplicationProperties;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp,src/main/resources")
@ContextHierarchy({ @ContextConfiguration(locations = "classpath:spring/spring-mvc.xml") })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserAPITest {

	private static Logger logger = LoggerFactory.getLogger(UserAPITest.class);
	final String PRE = "req.";
	 
	@Autowired
	protected WebApplicationContext wac;

	protected MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}


	@Test
	public void testGetUser() throws Exception {

		TreeMap<String, Object> map = new TreeMap<String, Object>();
		
		map.put("userId", 3);
		map.put("methodType", "getUser");
		
		this.send(map, "/user/getUser");
	}
	
	@Test
	public void testCheckUser() throws Exception {
		
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		
		map.put("userName", "13825051111");
		map.put("methodType", "checkUser");
		
		this.send(map, "/user/checkUser");
	}
	
	@Test
	public void testAuthenticationUser() throws Exception {
		
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		
		map.put("userName", "13825051111");
		map.put("password", "123456");
		map.put("methodType", "authenticationUser");
		
		this.send(map, "/user/authenticationUser");
	}
	
	@Test
	public void testRegisterUser() throws Exception {
		
		TreeMap<String, Object> map = new TreeMap<String, Object>();
		
		map.put("userName", "13825051111");
		map.put("password", "123456");
		map.put("tokenId", "");
		map.put("source", "MLX");
		map.put("methodType", "registerUser");
		
		this.send(map, "/user/registerUser");
	}
	
	private void send (TreeMap<String, Object> mapParam, String url) throws Exception {
		
		StringBuffer plain = new StringBuffer("");
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(url);
		Iterator it = mapParam.keySet().iterator();
		String key = null;
		while (it.hasNext()) {
			key = (String) it.next();
			if (null != mapParam.get(key) && !"".equals(mapParam.get(key))) {
				plain.append(mapParam.get(key));
				builder.param(key, mapParam.get(key) + "");
			}
		}
		System.out.println("plain=" + plain.toString());
		
		String sign = Sign.sign(plain.toString() + ApplicationProperties.getMessage(ConstEC.MLX_PRIVATEKEY));
		mapParam.put("sign", sign);
		
		builder.param("sign", sign);
		
		ResultActions result = mockMvc.perform(builder);

		MvcResult mrs = result.andDo(MockMvcResultHandlers.print()).andReturn();
		int resultStr = mrs.getResponse().getStatus();

		String content = mrs.getResponse().getContentAsString();

		System.out.println("content[" + content + "]");

		Assert.assertEquals(200, resultStr);
	}

}
