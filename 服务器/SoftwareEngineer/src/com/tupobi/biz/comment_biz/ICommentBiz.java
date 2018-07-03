package com.tupobi.biz.comment_biz;

import java.util.List;

import com.tupobi.bean.Comment;

public interface ICommentBiz {
	boolean addComment(Comment comment);
	
	List<Comment> getCommentByHouseName(String houseName);
}
