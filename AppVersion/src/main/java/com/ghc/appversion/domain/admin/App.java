/**
 * App.java
 *
 *	
 */
package com.ghc.appversion.domain.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.ghc.appversion.domain.BaseEntity;

/**
 * 
 */
@Entity
@Table(name="apps")
public class App extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mName;
	private String mIcon;
	private String mDescription;
	private String mLastestVersion;
	private Long mPlatformId;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		mId = id;
	}

	@NotEmpty(message = "{validation.name.NotEmpty.message}")
	@Size(min = 1, max = 255, message = "{validation.name.Size.message}")
	@Column(name="name")
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	@NotEmpty(message = "{validation.icon.NotEmpty.message}")
	@Size(min = 1, max = 4000, message = "{validation.icon.Size.message}")
	@Column(name="icon")
	public String getIcon() {
		return mIcon;
	}

	public void setIcon(String icon) {
		mIcon = icon;
	}

	@Size(min = 0, max = 4000, message = "{validation.description.Size.message}")
	@Column(name="description")
	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	@NotEmpty(message = "{validation.lastestVersion.NotEmpty.message}")
	@Size(min = 1, max = 255, message = "{validation.lastestVersion.Size.message}")
	@Column(name="lastest_version")
	public String getLastestVersion() {
		return mLastestVersion;
	}
	
	public void setLastestVersion(String lastestVersion) {
		mLastestVersion = lastestVersion;
	}

	@NotNull(message="{validation.platform.NotNull.message}")
	@Column(name="platform_id")
	public Long getPlatformId() {
		return mPlatformId;
	}

	public void setPlatformId(Long platformId) {
		mPlatformId = platformId;
	}

}
