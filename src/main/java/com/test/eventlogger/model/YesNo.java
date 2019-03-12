package com.test.eventlogger.model;

public enum YesNo {

	Y,N;

	public static YesNo getEnumValue( Boolean value ) {
		return value ?  YesNo.Y : YesNo.N; 
	}
}
