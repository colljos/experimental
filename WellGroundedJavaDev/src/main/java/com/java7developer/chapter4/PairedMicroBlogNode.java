package com.java7developer.chapter4;

public interface PairedMicroBlogNode {
    public void propagateUpdate(Update upd_, PairedMicroBlogNode backup_);
    public void confirmUpdate(PairedMicroBlogNode other_, Update update_);
	public String getIdent();
}
