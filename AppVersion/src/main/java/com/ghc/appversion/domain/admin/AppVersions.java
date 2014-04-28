package com.ghc.appversion.domain.admin;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.ghc.appversion.web.GlobalVariables;
import com.ghc.appversion.web.util.UploadUtil;

@Entity
@Table(name = "app_versions")
public class AppVersions implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long mId;
	private String mVersion;
	private DateTime mReleaseDate;
	private String mReleaseNote;
	private Long mAppId;
	private String mAppDownloadUrl;
	private Long mAppSize;

	public AppVersions() {

	}

	public AppVersions(Long id, String version, DateTime releaseDate,
			String releaseNote, Long appId, String appDownloadUrl, Long appSize) {
		mId = id;
		mVersion = version;
		mReleaseDate = releaseDate;
		mReleaseNote = releaseNote;
		mAppId = appId;
		mAppDownloadUrl = appDownloadUrl;
		mAppSize = appSize;
	}

	public AppVersions(Integer id, String version, DateTime releaseDate,
			String releaseNote, Integer appId, String appDownloadUrl,
			Integer appSize) {
		this(id.longValue(), version, releaseDate, releaseNote, appId
				.longValue(), appDownloadUrl, appSize.longValue());
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

	@NotEmpty(message = "{validation.version.NotEmpty.message}")
	@Size(min = 1, max = 255, message = "{validation.version.Size.message}")
	@Pattern(regexp = "^(\\d+\\.)*\\d+$", message = "{validation.version.Pattern.message}")
	@Column(name = "version")
	public String getVersion() {
		return mVersion;
	}

	public void setVersion(String version) {
		mVersion = version;
	}

	@NotNull(message = "{validation.releaseDate.NotNull.message}")
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	@Column(name = "release_date")
	public DateTime getReleaseDate() {
		return mReleaseDate;
	}

	public void setReleaseDate(DateTime releaseDate) {
		mReleaseDate = releaseDate;
	}

	@Size(min = 0, max = 4000, message = "{validation.note.Size.message}")
	@Column(name = "release_note")
	public String getReleaseNote() {
		return mReleaseNote;
	}

	public void setReleaseNote(String releaseNote) {
		mReleaseNote = releaseNote;
	}

	@Column(name = "app_id")
	public Long getAppId() {
		return mAppId;
	}

	public void setAppId(Long appId) {
		mAppId = appId;
	}

	@Column(name = "app_download_url")
	public String getAppDownloadUrl() {
		return mAppDownloadUrl;
	}

	public void setAppDownloadUrl(String appDownloadUrl) {
		mAppDownloadUrl = appDownloadUrl;
	}

	@Column(name = "app_size")
	public Long getAppSize() {
		return mAppSize;
	}

	public void setAppSize(Long appSize) {
		mAppSize = appSize;
	}

	@javax.persistence.Transient
	public String getReleaseDateString() {
		String releaseDateString = "";
		if (mReleaseDate != null) {
			String dateFormatPattern = GlobalVariables.getInstance().getDateFormatPattern();
			releaseDateString = org.joda.time.format.DateTimeFormat
					.forPattern(dateFormatPattern)
					.withZone(DateTimeZone.getDefault()).print(mReleaseDate);
		}
		return releaseDateString;
	}

	@javax.persistence.Transient
	public String getAppSizeString() {
		String appSizeString = "";
		if (mAppSize != null) {
			appSizeString = UploadUtil.getFileSize(mAppSize);
		}
		return appSizeString;
	}
}
