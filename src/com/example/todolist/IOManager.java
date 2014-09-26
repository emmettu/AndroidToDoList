package com.example.todolist;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

public class IOManager {
	static final String mainFile = "main";
	static final String ArchiveFile = "archive";
	static final String slKey = "studentList"; 
	
	Context context;
	
	static private IOManager IOManager = null;
	
	public static void initManager(Context context) {
		if (IOManager == null) {
			if (context==null) {
				throw new RuntimeException("missing context for IOManager");
			}
			IOManager = new IOManager(context);
		}		
	}
	
	public static IOManager getManager() {
		if (IOManager==null) {
			throw new RuntimeException("Did not initialize Manager");
		}
		return IOManager;
	}
	
	
	public IOManager(Context context) {
		this.context = context;
	}

//	public StudentList loadStudentList() throws ClassNotFoundException, IOException {
//		SharedPreferences settings = context.getSharedPreferences(mainFile, Context.MODE_PRIVATE);
//		String studentListData = settings.getString(slKey, "");
//		if (studentListData.equals("")) {
//			return new StudentList();
//		} else {
//			return studentListFromString(studentListData);
//		}
//	}
//	static public StudentList studentListFromString(String studentListData) throws ClassNotFoundException, IOException {
//		ByteArrayInputStream bi = new ByteArrayInputStream(Base64.decode(studentListData, Base64.DEFAULT));
//		ObjectInputStream oi = new ObjectInputStream(bi);
//		return (StudentList)oi.readObject();	
//	}
//	static public String studentListToString(StudentList sl) throws IOException {
//		ByteArrayOutputStream bo = new ByteArrayOutputStream();
//		ObjectOutputStream oo = new ObjectOutputStream(bo);
//		oo.writeObject(sl);
//		oo.close();
//		byte bytes[] = bo.toByteArray();
//		return Base64.encodeToString(bytes,Base64.DEFAULT);
//	}
//	
//	public void saveStudentList(StudentList sl) throws IOException {
//		SharedPreferences settings = context.getSharedPreferences(mainFile, Context.MODE_PRIVATE);
//		Editor editor = settings.edit();
//		editor.putString(slKey, studentListToString(sl));
//		editor.commit();
//	}
}
