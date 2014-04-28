package com.ghc.appversion.web.controller;

import java.beans.PropertyEditorSupport;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.ghc.appversion.web.GlobalVariables;

public abstract class AbstractController {
	@Autowired
	protected MessageSource messageSource;
	
	protected String mDateFormatPattern;	
	protected String mUploadRootDirectory;	
	protected String mPhotoType;	
	protected String mAndroidType;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, new DateTimeEditor());
	}

	@PostConstruct
	public void init() {
		GlobalVariables.init(messageSource);
		GlobalVariables globalVariables = GlobalVariables.getInstance();
		mDateFormatPattern = globalVariables.getDateFormatPattern();
		mUploadRootDirectory = globalVariables.getUploadRootDirectory();
		mPhotoType = globalVariables.getPhotoType();
		mAndroidType = globalVariables.getAndroidType();
	}

	public class DateTimeEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (StringUtils.hasText(text)) {
				DateTime dateTime = org.joda.time.format.DateTimeFormat
						.forPattern(mDateFormatPattern)
						.withZone(DateTimeZone.getDefault())
						.parseDateTime(text);
				setValue(dateTime);
			} else {
				setValue(null);
			}
		}

		@Override
		public String getAsText() throws IllegalArgumentException {
			String s = "";
			if (getValue() != null) {
				s = org.joda.time.format.DateTimeFormat
						.forPattern(mDateFormatPattern)
						.withZone(DateTimeZone.getDefault())
						.print((DateTime) getValue());
			}
			return s;
		}
	}
}