package com.app.pollbackend.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
public class CustomListCollection<T> {
	private Long totalCount;
	private Collection<T> data;
}
