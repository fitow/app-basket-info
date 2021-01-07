package com.fitow2512.basketinfo.services.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeUtils {

	public static ZoneId getZoneId() {
		return ZoneId.of("Europe/Madrid");		
	}
	
	public static ZonedDateTime getTimeStamp() {
		return ZonedDateTime.of(LocalDateTime.now(), getZoneId());		
	}
}
