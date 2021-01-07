package com.fitow2512.basketinfo.services.dtos;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Team implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer position;
	
	private String name;
	
	private Integer wins;
	
	private Integer losses;
}
