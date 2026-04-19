package com.streetsmart.dto;

import java.util.List;

public class FeedResponseDto {

	private List<PostResponseDto> items;
	private FeedCursorDto nextCursor;

	public List<PostResponseDto> getItems() {
		return items;
	}

	public void setItems(List<PostResponseDto> items) {
		this.items = items;
	}

	public FeedCursorDto getNextCursor() {
		return nextCursor;
	}

	public void setNextCursor(FeedCursorDto nextCursor) {
		this.nextCursor = nextCursor;
	}

}
