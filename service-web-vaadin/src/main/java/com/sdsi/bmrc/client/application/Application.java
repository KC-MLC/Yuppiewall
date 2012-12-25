package com.sdsi.bmrc.client.application;

import java.io.Serializable;

public interface Application<T> extends Serializable {

	public String getName();

	public void initialize();

	public boolean isInitialize();

	public T getApplicationUI();

	public String getTitle();

	public String getID();

	public String getThumbnail();

}