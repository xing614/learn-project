package demo.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User {
    private Long id;

    private String username;

    private String email;

    private String password;

    private Date createTime;

    private Date lastLoginTime;

    private Long status;//1有效，0禁止登陆
    
    private String salt;

    private String pswdsalt;
    private List<String> roleStrlist;//保存本用户对应的角色
    
    private List<String> perminsStrlist;//保存对应的权限
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPswdsalt() {
		return pswdsalt;
	}

	public void setPswdsalt(String pswdsalt) {
		this.pswdsalt = pswdsalt;
	}

	public void setRoleStrlist(List<String> roleStrlist) {
		// TODO Auto-generated method stub
		this.roleStrlist = roleStrlist;
	}
	
    public List<String> getRoleStrlist() {
        return this.roleStrlist;
    }

	public void setPerminsStrlist(List<String> perminsStrlist) {
		// TODO Auto-generated method stub
		this.perminsStrlist = perminsStrlist;
	}

	public List<String> getPerminsStrlist() {
		// TODO Auto-generated method stub
		return perminsStrlist;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", createTime=" + createTime + ", lastLoginTime=" + lastLoginTime + ", status=" + status
				+ ", roleStrlist=" + roleStrlist + ", perminsStrlist=" + perminsStrlist + "]";
	}
}
