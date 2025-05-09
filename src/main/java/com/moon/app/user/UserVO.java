package com.moon.app.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserVO implements UserDetails, OAuth2User {
	
	//
	
	@NotBlank(groups = JoinGroup.class)
	private String username;
	@Size(min = 8, max = 16)
	@NotBlank(groups = JoinGroup.class)
	private String password;
	private String passwordCheck;
	@NotBlank(groups = {UpdateGroup.class, JoinGroup.class})
	private String name;
	@NotBlank(groups = {UpdateGroup.class, JoinGroup.class})
	private String phone;
//	@Range(min = 0, max = 150)
//	private int age;
	@Email(groups = {UpdateGroup.class, JoinGroup.class})
	private String email;
	@Past(groups = {UpdateGroup.class, JoinGroup.class})
	private Date birth;
	private String fileName;
	private String oriName;
	
	private boolean status;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	
	//비번이 틀린 경우
	//BadCredentialsException: 자격 증명에 실패하였습니다.

	private List<RoleVO> list;
	
	/** oau2user **/
	private Map<String, Object> attributes;
	
	private String accessToken;
	
	private String sns;
	
	/** oau2user **/

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//ROLE_NAME을 리턴
		List<GrantedAuthority> ar = new ArrayList<>();
		
		for (RoleVO roleVO:this.list) {
			GrantedAuthority g = new SimpleGrantedAuthority(roleVO.getRoleName());
				ar.add(g);
		}
		return ar;
	}

	

	
	
	
	
	
}
