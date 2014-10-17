package com.java7developer.chapter4;

public interface SaferMicroBlogNode {
    public void propagateUpdate(Update upd_, SaferMicroBlogNode backup_);
    public boolean tryConfirmUpdate(SaferMicroBlogNode other_, Update upd_);
	public String getIdent();
}
