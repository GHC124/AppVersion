package com.ghc.appversion.web.controller;

import static com.ghc.appversion.web.Constants.DATE_FORMAT_PATTERN;

import java.beans.PropertyEditorSupport;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public abstract class AbstractController {
	@Autowired
	protected MessageSource messageSource;

	protected String mUploadRootDirectory;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(DateTime.class, new DateTimeEditor());
		// binder.registerCustomEditor(Integer.class, new IntegerEditor());
	}

	@PostConstruct
	public void init() {
		mUploadRootDirectory = messageSource.getMessage(
				"file_upload_directory", new Object[] {}, Locale.US);
	}

	public class DateTimeEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (StringUtils.hasText(text)) {
				DateTime dateTime = org.joda.time.format.DateTimeFormat
						.forPattern(DATE_FORMAT_PATTERN).parseDateTime(text);
				setValue(dateTime);
			} else {
				setValue(null);
			}
		}

		@Override
		public String getAsText() throws IllegalArgumentException {
			String s = "";
			if (getValue() != null) {
				s = org.joda.time.format.DateTimeFormat.forPattern(
						DATE_FORMAT_PATTERN).print((DateTime) getValue());
			}
			return s;
		}
	}

	public class IntegerEditor extends PropertyEditorSupport {

		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			if (StringUtils.hasText(text)) {
				setValue(text);
			} else {
				setValue(null);
			}
		}

		@Override
		public String getAsText() throws IllegalArgumentException {
			String s = "";
			if (getValue() != null && getValue() instanceof Integer) {
				s = ((Integer) getValue()).intValue() + "";
			}
			return s;
		}
	}
}