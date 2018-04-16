package com.mhc.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResultDTO<T>{
	private int pages;
	private List<T> items;
	
	public SearchResultDTO() {
		this.items = new ArrayList<T>();
		this.pages = 0;
	}
	
	public SearchResultDTO(ArrayList<T> items,int pages) {
		this.items = items;
		this.pages = pages;
	}
	
	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getItems() {
		return this.items;
	}

	public void setItems(List<T> participants) {
		this.items = participants;
	}
}
