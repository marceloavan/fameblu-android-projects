package org.asselvi.marceloavancini_01.component;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class GenericListAdapter<T> extends BaseAdapter {
	
	private List<T> list;
	private Context context;

	public GenericListAdapter(Context context, List<T> list) {
		this.context = context;
		this.list = list;
	}
	
	public GenericListAdapter(Context context) {
		this.context = context;
		this.list = new ArrayList<T>();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public T getItem(int position) {
		return list.get(position);
	}
	
	public void addOrReplaceItem(T item) {
		int index = list.indexOf(item);
		if (index > -1) {
			list.set(index, item);
			return;
		}
		list.add(item);
	}
	
	public void addItem(T item) {
		list.add(item);
	}
	
	public void removeItem(T item) {
		list.remove(item);
	}
	
	@Override
	public long getItemId(int position) {
		return list.get(position).hashCode();
	}
	
	protected Context getContext() {
		return context;
	}

	@Override
	public abstract View getView(int position, View convertView, ViewGroup parent);
}
