package com.atguigu.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ShiroHandler {
	

	@RequestMapping("/shiroLogin")
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password){
		System.out.println("wwwwwwwwww");
		Subject currentUser = SecurityUtils.getSubject();
		
		// 检验用户是否已经被认证. 即是否已经登录. 
        if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为一个 UsernamePasswordToken
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
            	// 指定登录. 即执行认证. 
                currentUser.login(token);
            } 
            // 登录时所有异常的父类. 
            catch (AuthenticationException ae) {
            	System.out.println("登錄失敗:" + ae.getMessage());
            	return "redirect:/login.jsp";
            }
        }
		
		return "redirect:/success.jsp";
	}
}
