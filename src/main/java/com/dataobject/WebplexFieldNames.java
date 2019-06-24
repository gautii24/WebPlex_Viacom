package com.dataobject;

public class WebplexFieldNames {

	public static final String branchNameConstant = "Branch Name";
	public static final String folderNameConstant = "Folder Name";
	public static final String siteConstant = "Site";
	public static final String contextConstant = "Context";
	public static final String subcontextConstant = "Subcontext";
	public static final String fileConstant = "File";
	
	String branchName;
	String folderName;
	String site;
	String subcontext;
	String context;
	String file;

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	
	public String getSiteName() {
		return site;
	}

	public void setSiteName(String site) {
		this.site = site;
	}
	
	public String getSubcontext() {
		return subcontext;
	}

	public void setSubcontext(String subcontext) {
		this.subcontext = subcontext;
	}
	
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}
