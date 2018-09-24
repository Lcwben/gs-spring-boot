package Controller;

import hello.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * Spring Boot Junit测试类（测试请求流程）
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc   //测接口流程时开启mockMvc模拟请求用
@ContextConfiguration(locations = "classpath:config/application.properties") //用于加载配置，不使用此注解无法访问项目中配置文件属性
@SpringBootTest(classes = Application.class) //加载Spring Boot启动类
public class GetPropertiesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void init() {

    }

    @Test
    public void getProperties() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.
                    get("/properties/getProperties")).
                    andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}