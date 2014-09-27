package com.example.todolist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class IOManager {
	static final String mainFile = "main";
	static final String ArchiveFile = "archive";
	static final String mKey = "studentList";
	static final String aKey = "ArchiveList";
	
	Context context;
	
	private IOManager IOManager = null;
	
	public void initManager(Context context) {
		if (IOManager == null) {
			if (context==null) {
				throw new RuntimeException("missing context for IOManager");
			}
			IOManager = new IOManager(context);
		}		
	}
	
	public IOManager getManager() {
		if (IOManager==null) {
			throw new RuntimeException("Did not initialize Manager");
		}
		return IOManager;
	}
	
	
	public IOManager(Context context) {
		this.context = context;
	}

	public ArrayList<todoItem> loadArray() throws ClassNotFoundException, IOException {
		SharedPreferences settings = context.getSharedPreferences(mainFile, Context.MODE_PRIVATE);
		String ArrayData = settings.getString(aKey, "");
		if (ArrayData.equals("")) {
			return new ArrayList<todoItem>();
		} else {
			return ArrayListFromString(ArrayData);
		}
	}
	static public ArrayList<todoItem> ArrayListFromString(String ArrayData) throws ClassNotFoundException, IOException {
		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(ArrayData, Base64.DEFAULT));
		ObjectInputStream oi = new ObjectInputStream(bi);
		return (ArrayList<todoItem>) oi.readObject();	
	}
	static public String ArrayListToString(ArrayList<todoItem> al) throws IOException {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(al);
		oo.close();
		byte bytes[] = bo.toByteArray();
		return Base64.encodeToString(bytes,Base64.DEFAULT);
	}
	
	public void saveArrayList(ArrayList<todoItem> al) throws IOException {
		SharedPreferences settings = context.getSharedPreferences(mainFile, Context.MODE_PRIVATE);
		Editor editor = settings.edit();
		editor.putString(mKey, ArrayListToString(al));
		editor.commit();
	}
}
