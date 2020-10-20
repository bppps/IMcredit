package com.example.user.controller.user;

import com.example.common.vo.ResponseVO;
import com.example.user.bl.user.UserService;
import com.example.user.data.user.UserMapper;
import com.example.user.po.User;
import com.example.user.vo.UserForm;
import com.example.user.vo.UserVO;
import org.junit.Assert;
import com.example.common.constant.AesKey;
import com.example.common.utils.AesUtil;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserController Tester.
 *
 * @author <Authors name>
 * @since <pre>10/12/2020</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    UserController userController;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;

    private User user;


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setEmail("C1@qq.com");
        user.setPassword("123456");
        user.setUserType(1);
        user.setAvatarUrl("https://pic4");
        user.setPhoneNumber("123456789");
        user.setUsername("客户一号");
    }

    /**
     *
     * Method: login(@RequestBody UserForm userForm)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testLogin() throws Exception {

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        assert userController.register(userVO).getSuccess();

        UserForm userForm = new UserForm();
        userForm.setEmail(user.getEmail());
        userForm.setPassword(user.getPassword());
        ResponseVO result = userController.login(userForm);
        assert result.getSuccess();

        UserVO user1 = (UserVO) result.getContent();
        Assert.assertEquals(user1.getEmail(), user.getEmail());
        Assert.assertEquals(user1.getPassword(), user.getPassword());
        Assert.assertEquals(user1.getUsername(), user.getUsername());

    }

    /**
     *
     * Method: register(@RequestBody UserVO userVO)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testRegister() throws Exception {

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        assert userController.register(userVO).getSuccess();   // register

        User user1 = userMapper.getUserByEmail(user.getEmail());
        user.setId(user1.getId());
        Assert.assertEquals(user.getUserType(), user1.getUserType());
        Assert.assertEquals(user.getPhoneNumber(), user1.getPhoneNumber());

        Assert.assertEquals(userController.register(userVO).getSuccess(),false);

    }

    /**
     *
     * Method: updateUser(@RequestBody UserVO userVO)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testUpdateUser() throws Exception {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        assert userController.register(userVO).getSuccess();

        userVO.setUserType(0);
        Assert.assertEquals(userController.updateUser(userVO).getSuccess(), true);

    }

    /**
     *
     * Method: deleteUser(@PathVariable Integer userId)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testDeleteUser() throws Exception {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);

        assert userController.register(userVO).getSuccess();
        User user1 = userMapper.getUserByEmail(userVO.getEmail());

        Assert.assertEquals(userController.deleteUser(user1.getId()).getSuccess(), true);
    }

    /**
     *
     * Method: getUserInfo(@PathVariable int id)
     *
     */
    @Test
    @Transactional
    @Rollback
    public void testGetUserInfo() throws Exception {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        assert userController.register(userVO).getSuccess();

        User user1 = userMapper.getUserByEmail(userVO.getEmail());

        ResponseVO result = userController.getUserInfo(user1.getId());
        UserVO userVO1 = (UserVO) result.getContent();

        Assert.assertEquals(userVO1.getAvatarUrl(), "https://pic4");
        Assert.assertEquals(userVO1.getEmail(), "C1@qq.com");
        Assert.assertEquals(userVO1.getPassword(), "123456");
        Assert.assertEquals(userVO1.getUserType(), "Client");
        Assert.assertEquals(userVO1.getPhoneNumber(), "123456789");
        Assert.assertEquals(userVO1.getUsername(), "客户一号");

    }
}