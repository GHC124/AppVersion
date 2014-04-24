/**
 * DataGrid.java
 *
 *	
 */
package com.ghc.appversion.web.form.admin;

import java.util.List;

/**
 * 
 */
public class DataGrid<E> {
	private int mTotalPages;
	private int mCurrentPage;
	private long mTotalRecords;
	private List<E> mData;
	
	public int getTotalPages() {
		return mTotalPages;
	}
	public void setTotalPages(int totalPages) {
		mTotalPages = totalPages;
	}
	public int getCurrentPage() {
		return mCurrentPage;
	}
	public void setCurrentPage(int currentPage) {
		mCurrentPage = currentPage;
	}
	public long getTotalRecords() {
		return mTotalRecords;
	}
	public void setTotalRecords(long totalRecords) {
		mTotalRecords = totalRecords;
	}
	public List<E> getData() {
		return mData;
	}
	public void setData(List<E> data) {
		mData = data;
	}
}
