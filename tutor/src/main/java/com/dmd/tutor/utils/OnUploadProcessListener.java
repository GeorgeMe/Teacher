package com.dmd.tutor.utils;

public interface OnUploadProcessListener {
 
	void onUploadDone(int responseCode, String message);
 
	void onUploadProcess(int uploadSize);
 
	void initUpload(int fileSize);
	
}
