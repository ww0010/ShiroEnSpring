package com.atguigu.shiro.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthorizingRealm{

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		Object object = principal.getPrimaryPrincipal();
		
		System.out.println("++++"+object);
		
		Set<String> roles = new HashSet<>();
		roles.add("user");
		
		if("admin".equals(object)){
			roles.add("admin");
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roles);
		
		
		
		
		
		
		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		UsernamePasswordToken upTaken = (UsernamePasswordToken) token;
		
		String username = upTaken.getUsername();
		
		System.out.println(username);
		
		if("xiaoming".equals(username)){
			throw new UnknownAccountException("用戶不存在: [" + username + "]");
		}
		
		System.out.println(username);
		//以下是从数据库中取出的数据
		Object principal = username;
		
		Object credentials = null;
		if("admin".equals(username)){
			credentials = "038bdaf98f2037b31f1e75b5b4c9b26e";
		}else if("user".equals(username)){
			 credentials = "098d2c478e9c11555ce2823231e02ec1";
		}
		
		ByteSource salt = ByteSource.Util.bytes(username);
		String realmName = getName();
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials,salt ,realmName);
		
		return info;
	}
	
	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "123456";
		int hashIterations = 1024;
		ByteSource salt = ByteSource.Util.bytes("admin");
		
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
		System.out.println(result);
	}

}
