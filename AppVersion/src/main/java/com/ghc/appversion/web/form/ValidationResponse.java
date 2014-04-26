package com.ghc.appversion.web.form;

import java.util.ArrayList;
import java.util.List;

public class ValidationResponse {
	private String mStatus = "FAIL";
	private List<ErrorMessage> mResult = new ArrayList<>();
	private String mExtraData;

	public String getStatus() {
		return mStatus;
	}

	public void setStatus(String status) {
		this.mStatus = status;
	}

	public List<ErrorMessage> getResult() {
		return this.mResult;
	}

	public void setResult(List<ErrorMessage> result) {
		this.mResult = result;
	}

	public String getExtraData() {
		return mExtraData;
	}

	public void setExtraData(String extraData) {
		mExtraData = extraData;
	}
}
