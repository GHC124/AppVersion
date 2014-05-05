package com.ghc.appversion.domain.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.ghc.appversion.domain.BaseEntity;

@Entity
@Table(name = "Users")
@NamedQueries({
	@NamedQuery(name="User.findByEmail", query="select u from User u where u.email = :email")
})
public class User extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long mId;
	private String mEmail;
	private String mPassword;
	private String mFirstName;
	private String mLastName;
	private int mIsActive = 1;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		mId = id;
	}
	
	@NotEmpty(message = "{validation.email.NotEmpty.message}")
	@Size(min = 1, max = 255, message = "{validation.email.Size.message}")
	@Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message="{validation.email.Pattern.message}")
	@Column(name = "email")
	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String email) {
		mEmail = email;
	}

	@NotEmpty(message = "{validation.firstname.NotEmpty.message}")
	@Size(min = 3, max = 255, message = "{validation.firstname.Size.message}")
	@Column(name = "first_name")
	public String getFirstName() {
		return mFirstName;
	}

	@NotEmpty(message = "{validation.password.NotEmpty.message}")
	@Size(min = 3, max = 255, message = "{validation.password.Size.message}")
	@Column(name="password")	
	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	public void setFirstName(String firstName) {
		mFirstName = firstName;
	}

	@Size(min = 0, max = 255, message = "{validation.lastname.Size.message}")
	@Column(name = "last_name")
	public String getLastName() {
		return mLastName;
	}

	public void setLastName(String lastName) {
		mLastName = lastName;
	}	

	@NotNull(message="{validation.active.NotNull.message}")
	@Column(name = "is_active")
	public Integer getIsActive() {
		return mIsActive;
	}

	public void setIsActive(Integer isActive) {
		mIsActive = isActive;
	}	
}
