package com.zxdy.dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import oracle.sql.TIMESTAMP;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zxdy.util.JsonDateValueProcessor;


@Repository
public class BaseDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);
	private static final String LEVEL="level";
	private static final String KEY="key";
	private static final String START_TIME="startTime";
	private static final String END_TIME="endTime";
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public List<Object> selectNativeSQL(String sql) {
		try {
			return this.getSqlSessionTemplate().selectList("nativeSQL_select",
					sql);
		} catch (Exception e) {
			LOGGER.error("", e);
			return null;
		}
	}

	public JSONArray selectNativeSQLArray(String sql) {
		return JSONArray.fromObject(selectNativeSQL(sql));
	}

	public JSONArray selectNativeSQLArray(String sql, JsonConfig jsonConfig) {
		return JSONArray.fromObject(selectNativeSQL(sql), jsonConfig);
	}

	public int insertNativeSQL(String sql) {
		try {
			return this.getSqlSessionTemplate().insert("nativeSQL_insert", sql);
		} catch (Exception e) {
			LOGGER.error("", e);
			return -1;
		}
	}

	public int updateNativeSQL(String sql) {
		try {
			return this.getSqlSessionTemplate().update("nativeSQL_update", sql);
		} catch (Exception e) {
			LOGGER.error("", e);
			return -1;
		}
	}

	public JSONArray returnData(String namedSQL, Map<String, Object> params) {
		try {
			return JSONArray.fromObject(this.getSqlSessionTemplate()
					.selectList(namedSQL, params));
		} catch (Exception e) {
			LOGGER.error("", e);
			return null;
		}
	}

	public JSONArray returnData(String namedSQL, Map<String, Object> params,
			JsonConfig jsonConfig) {
		try {
			return JSONArray.fromObject(this.getSqlSessionTemplate()
					.selectList(namedSQL, params), jsonConfig);
		} catch (Exception e) {
			LOGGER.error("", e);
			return null;
		}
	}

	public JSONArray queryResult(int level, String key, String startTime,
			String endTime, String namedQuery) {

		Map<String, Object> params = initParam(level, key, startTime, endTime);

		return returnData(namedQuery, params, getJsonConfig());
	}

	public Map<String, Object> initParam(int level, String key,
			String startTime, String endTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(LEVEL, level);
		params.put(KEY, key);
		params.put(START_TIME, startTime);
		params.put(END_TIME, endTime);
		return params;
	}
	
	public Map<String, Object> initDTMFParam(JSONObject param) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(LEVEL, param.getString("level"));
		params.put(KEY, param.getString("key"));
		params.put(START_TIME, param.getString("startTime"));
		params.put(END_TIME, param.getString("endTime"));
		return params;
	}

	public JsonConfig getJsonConfig() {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(TIMESTAMP.class,
				new JsonDateValueProcessor());
		jsonConfig.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor());
		return jsonConfig;
	}
	public boolean insertData(String namedSQL, Map<String, Object> params) {
		try {
			this.getSqlSessionTemplate().insert(namedSQL, params);
		} catch (Exception e) {
			 e.printStackTrace();
			return false;
		}

		return true;
	}
	public boolean deleteData(String namedSQL, Map<String, Object> params) {
		try {
			this.getSqlSessionTemplate().delete(namedSQL, params);
		} catch (Exception e) {
			 e.printStackTrace();
			return false;
		}

		return true;
	}

}
