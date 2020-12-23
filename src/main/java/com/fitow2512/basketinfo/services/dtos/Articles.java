package com.fitow2512.basketinfo.services.dtos;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Articles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private ZonedDateTime timeStamp;
	
	private Integer numArticles;
	
	private List<Article> articles;
}
