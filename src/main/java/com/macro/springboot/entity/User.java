package com.macro.springboot.entity;

import org.apache.kafka.common.protocol.types.Field;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "[User]")
public class User {
	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "uuid")
	private String uuid;
	@Column(name = "password")
	private String password;
	@Column(name = "comp_code")
	private int compCode;
	@Column(name = "comp_account")
	private String compAccount;
	@Column(name = "telephone")
	private String telephone;
	@Column(name = "login_count")
	private int loginCount;
	@Column(name = "enabled")
	private boolean enabled;
	@Column(name = "del")
	private boolean del;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "update_time")
	private Date updateTime;
	@Transient
	private String other; //跟数据库无关的字段使用@Transient标记或移至VO类。

	public int getId () {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getCompCode(){
		return  compCode;
	}
	public void setCompCode(int compCode){
		this.compCode = compCode;
	}

	public String getCompAccount(){
		return compAccount;
	}
	public void setCompAccount(String compAccount){
		this.compAccount = compAccount;
	}

	public String getTelephone(){
		return telephone;
	}
	public void setTelephone(String telephone){
		this.telephone = telephone;
	}

	public int getLoginCount(){
		return loginCount;
	}
	public void setLoginCount(int loginCount){
		this.loginCount = loginCount;
	}

	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled(boolean enabled){
		this.enabled = enabled;
	}

	public boolean isDel(){
		return del;
	}
	public void setDel(boolean del){
		this.del = del;
	}

	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}

	public Date getUpdateTime(){
		return updateTime;
	}
	public void setUpdateTime(Date updateTime){
		this.updateTime = updateTime;
	}
}
