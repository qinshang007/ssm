package serviceTest;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.ssm.model.User;
import com.ssm.service.UserService;

/**
 * UserService 测试类
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})  
public class UserServiceTest {
	
	 private static Logger logger = Logger.getLogger(UserServiceTest.class);
	 
	 @Resource
	 private UserService userService;
	
//	 @Test
	 public void getUserById(){
		 User user = userService.getUserById(1);
		 logger.info(JSON.toJSONString(user)); 
	 }
	 
//	 @Test
	 public void createUser(){
		 User user = new User();
		 user.setUserName("test");
		 user.setPassword("test1234");
		 user.setAge(10);
		 if(userService.createUser(user))
			 System.out.println("创建用户成功");
		 else
			 System.out.println("创建用户失败");
	 }
	 
	 @Test
	 public void updateUser(){
		 User user = new User();
		 user.setId(2);
		 user.setUserName("lpf");
		 user.setPassword("hhaha");
		 user.setAge(25);
		 if(userService.updateUser(user))
			 System.out.println("更改用户成功");
		 else
			 System.out.println("更改用户失败");
	 }
}
