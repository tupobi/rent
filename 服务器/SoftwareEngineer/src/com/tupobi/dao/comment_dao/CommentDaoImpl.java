package com.tupobi.dao.comment_dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.tupobi.bean.Comment;
import com.tupobi.utils.C3P0Util;

public class CommentDaoImpl implements ICommentDao {

	@Override
	public int insertComment(Comment comment) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		return qr
				.update("insert into comment (userName, houseName, content, date, avatarUrl) values(?, ?, ?, ?, ?)",
						comment.getUserName(), comment.getHouseName(),
						comment.getContent(), comment.getDate(),
						comment.getAvatarUrl());
	}

	@Override
	public List<Comment> selectCommentsByHouseName(String houseName) throws SQLException {
		QueryRunner qr = new QueryRunner(C3P0Util.getDataSource());
		System.out.println("Êý¾Ý¿â²éÑ¯£ºhouseName == " + houseName);
		return qr.query("select * from comment where houseName = ? order by date DESC", new BeanListHandler<Comment>(Comment.class), houseName);
	}
}
