package com.ghc.appversion.web.form;

import java.util.List;

public class ValidationResponse {
	private String mStatus;
	private List<ErrorMessage> mResult;

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
}
