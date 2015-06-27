package org.asselvi.avaliacaofinal02.dao;

import java.util.List;

import org.asselvi.avaliacaofinal02.db.Database;

import android.content.Context;

/**
 * 
 * @author marcelo
 */
public abstract class BaseDAO<T> {
	
	protected Database getConnection(Context context) {
		return Database.getInstance(context);
	}
	
	public abstract String getTableName();
	
	public abstract List<T> findAll(Context context);
	
	public abstract T findOne(Context context, Integer id);
	
	public abstract void insert(Context context, T item);
	
	public abstract void update(Context context, T item);
	
	public abstract void remove(Context context, T item);
}
