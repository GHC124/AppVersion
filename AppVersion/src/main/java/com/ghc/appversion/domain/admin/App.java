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
@Table(name = "apps")
public class App extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mName;
	private String mIconUrl;
	private String mDescription;
	private String mLatestVersion;
	private Long mPlatformId;

	public App() {
		super();
	}

	public App(Integer id, String name, String iconUrl, String description,
			String latestVersion, Integer platformId, Integer version) {
		super(version.longValue());
		mId = id.longValue();
		mName = name;
		mIconUrl = iconUrl;
		mDescription = description;
		mLatestVersion = latestVersion;
		mPlatformId = platformId.longValue();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public Long getId() {
		return mId;
	}

	public void setId(Long id) {
		mId = id;
	}

	@NotEmpty(message = "{validation.name.NotEmpty.message}")
	@Size(min = 1, max = 255, message = "{validation.name.Size.message}")
	@Column(name = "name")
	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	@Column(name = "icon_url")
	public String getIconUrl() {
		return mIconUrl;
	}

	public void setIconUrl(String iconUrl) {
		mIconUrl = iconUrl;
	}

	@Size(min = 0, max = 4000, message = "{validation.description.Size.message}")
	@Column(name = "description")
	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String description) {
		mDescription = description;
	}

	@Column(name = "latest_version")
	public String getLatestVersion() {
		return mLatestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		mLatestVersion = latestVersion;
	}

	@NotNull(message = "{validation.platform.NotNull.message}")
	@Column(name = "platform_id")
	public Long getPlatformId() {
		return mPlatformId;
	}

	public void setPlatformId(Long platformId) {
		mPlatformId = platformId;
	}

}
