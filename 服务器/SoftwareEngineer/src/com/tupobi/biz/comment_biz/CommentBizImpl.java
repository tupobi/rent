package com.tupobi.biz.comment_biz;

import java.sql.SQLException;
import java.util.List;

import com.tupobi.bean.Comment;
import com.tupobi.dao.comment_dao.CommentDaoImpl;
import com.tupobi.dao.comment_dao.ICommentDao;

public class CommentBizImpl implements ICommentBiz {

	private ICommentDao iCommentDao;

	public CommentBizImpl() {
		iCommentDao = new CommentDaoImpl();
	}

	@Override
	public boolean addComment(Comment comment) {
		int res = 0;
		try {
			res = iCommentDao.insertComment(comment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (res == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<Comment> getCommentByHouseName(String houseName) {
		List<Comment> comments = null;
		try {
			comments = iCommentDao.selectCommentsByHouseName(houseName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}

}
