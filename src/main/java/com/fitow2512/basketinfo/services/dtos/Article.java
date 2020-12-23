package com.fitow2512.basketinfo.services.dtos;

import java.io.Serializable;
import java.time.ZonedDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String title;
	
	private String url;
	
	private String content;
	
	private String date;
	
	private ZonedDateTime zdtDate;
}
