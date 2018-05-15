package com.mhc.dao;

import com.mhc.exceptions.dao.DAOUpdateException;
import com.mhc.services.ApplicationContextProvider;

import org.apache.commons.dbcp.DelegatingConnection;
import org.postgresql.PGConnection;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class BaseDAO<T> {
	
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	protected DataSourceTransactionManager transactionManager;
	protected static ApplicationContext beanFactory = ApplicationContextProvider.getApplicationContext();
	 
	protected PGConnection getPostgresConnection() throws Exception{
		DelegatingConnection del =( DelegatingConnection)(jdbcTemplate.getDataSource().getConnection());
		PGConnection con = (PGConnection) del.getInnermostDelegate();
		return con;
	}
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		this.transactionManager = new DataSourceTransactionManager(dataSource);
	}
		
	public class NamedParamHelper{
		public String parameterList;
		public String valueList;
	}
	
	protected NamedParamHelper insertNamedParams(Set<String> nameKeys){
		NamedParamHelper namedParamHelper = new NamedParamHelper();
		String appendStr = ", ";
		
		StringBuffer sqlParamBuff = new StringBuffer("");  
		StringBuffer sqlValueBuff = new StringBuffer("");   
		
		for(String nameProp : nameKeys){
			sqlValueBuff.append(":").append(nameProp).append(appendStr);
			sqlParamBuff.append(nameProp).append(appendStr);
		}
		if(sqlParamBuff.indexOf(appendStr)!=-1){
			sqlParamBuff.delete(sqlParamBuff.lastIndexOf(appendStr), sqlParamBuff.length());
		}
		if(sqlValueBuff.indexOf(appendStr)!=-1){
			sqlValueBuff.delete(sqlValueBuff.lastIndexOf(appendStr), sqlValueBuff.length());
		}
		
		namedParamHelper.parameterList = sqlParamBuff.toString();
		namedParamHelper.valueList = sqlValueBuff.toString();
		
		return namedParamHelper;
	}
	
	protected <K> void namedParamInsertSQL(String insertSQL, String replaceParamToken, String replaceValueToken, List<T> objects, Set<String> nameKeys){
		
		NamedParamHelper namedParamHelper = insertNamedParams(nameKeys);
		
		String sql = insertSQL.replace(replaceParamToken, namedParamHelper.parameterList).replace(replaceValueToken, namedParamHelper.valueList);
		
		SqlParameterSource[] params =
				SqlParameterSourceUtils.createBatch(objects.toArray());
	
		namedParameterJdbcTemplate.batchUpdate(sql, params);
	}
	
	protected String setUpNamedParams(MapSqlParameterSource namedParameters, StringBuffer sql, String appendStr, Map<String, Object> nameProps){
		StringBuffer sqlBuff = new StringBuffer(sql);   
		
		for(Entry<String, Object> entry : nameProps.entrySet()){
			String nameProp = entry.getKey();
			Object entryValue = entry.getValue();
			
			namedParameters.addValue(nameProp, entryValue);
			sqlBuff.append(nameProp).append("= :").append(nameProp).append(appendStr);
		}
		if(sqlBuff.indexOf(appendStr)!=-1){
			sqlBuff.delete(sqlBuff.lastIndexOf(appendStr), sqlBuff.length());
		}
		
		return sqlBuff.toString();
	}
	
	protected int namedParamUpdateSQL(String updateSql, String replacementToken, Map<String, Object> nameProps) {
		try {  
			MapSqlParameterSource namedParameters = new MapSqlParameterSource();
			
			String settersSQL = setUpNamedParams(namedParameters, new StringBuffer(""), ", ", nameProps);
			
			String updateSqlNamed = updateSql.replace(replacementToken, settersSQL);

			int count = namedParameterJdbcTemplate.update(updateSqlNamed, namedParameters);  
			if(count==0){
				throw new DAOUpdateException("Unable to update the table.");
			}
			
			return count;
		}catch(DAOUpdateException due){
    		throw due;
    	}
	}
	
	
}

