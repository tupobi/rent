package com.tupobi.dao.comment_dao;

import java.sql.SQLException;
import java.util.List;

import com.tupobi.bean.Comment;

public interface ICommentDao {
	int insertComment(Comment comment) throws SQLException;
	
	List<Comment> selectCommentsByHouseName(String houseName) throws SQLException;
}
