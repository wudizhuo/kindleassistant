package com.kindleassistant.entity;

/**
 * 进度监听器接口
 */
public interface ProgressListener {
	public void transferred(long transferedBytes);
}
