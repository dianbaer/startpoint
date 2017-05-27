package action;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import config.CommonConfigUCenter;
import dao.dao.base.TokenMapper;
import dao.model.base.Token;
import dao.model.base.TokenCriteria;
import log.LogManager;
import mbatis.MybatisManager;
import tool.StringUtil;
import util.IdUtil;

public class TokenAction {
	public static Token getTokenByUserId(String userId) {
		if (StringUtil.stringIsNull(userId)) {
			return null;
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			TokenMapper tokenMapper = sqlSession.getMapper(TokenMapper.class);
			TokenCriteria tokenCriteria = new TokenCriteria();
			TokenCriteria.Criteria criteria = tokenCriteria.createCriteria();
			criteria.andUserIdEqualTo(userId);
			List<Token> tokenList = tokenMapper.selectByExample(tokenCriteria);
			if (tokenList == null || tokenList.size() == 0) {
				return null;
			}
			return tokenList.get(0);
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("获取token异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static Token getTokenById(String tokenId) {
		if (StringUtil.stringIsNull(tokenId)) {
			return null;
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			TokenMapper tokenMapper = sqlSession.getMapper(TokenMapper.class);
			Token token = tokenMapper.selectByPrimaryKey(tokenId);
			return token;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("获取token异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static Token createToken(String userId) {
		if (StringUtil.stringIsNull(userId)) {
			return null;
		}
		Token token = new Token();
		Date date = new Date();
		token.setTokenCreateTime(date);
		token.setTokenUpdateTime(date);
		Date expireTime = new Date(date.getTime() + CommonConfigUCenter.TOKEN_EXPIRE_TIME);
		token.setTokenExpireTime(expireTime);
		token.setUserId(userId);
		token.setTokenId(IdUtil.getUuid());

		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			TokenMapper tokenMapper = sqlSession.getMapper(TokenMapper.class);
			int result = tokenMapper.insert(token);
			if (result == 0) {
				LogManager.mariadbLog.warn("创建token失败");
				return null;
			}
			sqlSession.commit();
			return token;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("创建token异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static Token updateToken(String tokenId) {
		if (StringUtil.stringIsNull(tokenId)) {
			return null;
		}
		Token token = getTokenById(tokenId);
		if (token == null) {
			return null;
		}
		token = new Token();
		Date date = new Date();
		token.setTokenId(tokenId);
		token.setTokenUpdateTime(date);
		Date expireTime = new Date(date.getTime() + CommonConfigUCenter.TOKEN_EXPIRE_TIME);
		token.setTokenExpireTime(expireTime);

		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			TokenMapper tokenMapper = sqlSession.getMapper(TokenMapper.class);
			int result = tokenMapper.updateByPrimaryKeySelective(token);
			if (result == 0) {
				LogManager.mariadbLog.warn("更新token失败");
				return null;
			}
			sqlSession.commit();
			return token;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("更新token异常", e);
			return null;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	public static boolean deleteToken(String tokenId) {
		if (StringUtil.stringIsNull(tokenId)) {
			return false;
		}
		Token token = getTokenById(tokenId);
		if (token == null) {
			return false;
		}
		SqlSession sqlSession = null;
		try {
			sqlSession = MybatisManager.getSqlSession();
			TokenMapper tokenMapper = sqlSession.getMapper(TokenMapper.class);
			int result = tokenMapper.deleteByPrimaryKey(tokenId);
			if (result == 0) {
				LogManager.mariadbLog.warn("删除token失败");
				return false;
			}
			sqlSession.commit();
			return true;
		} catch (Exception e) {
			if (sqlSession != null) {
				sqlSession.rollback();
			}
			LogManager.mariadbLog.error("删除token异常", e);
			return false;
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}
