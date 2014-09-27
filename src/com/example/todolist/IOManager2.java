package com.example.todolist;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class IOManager2 extends Activity {
	private static final String FILENAME = "file.sav";
	
	public void loadFromFile(ArrayList<todoItem> arrayList) {
		if(arrayList == null) {
			arrayList = new ArrayList<todoItem>();
		}
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));			
			//From http://www.javacreed.com/simple-gson-example/
			Gson gson = new Gson();
			Type ListType = new TypeToken<ArrayList<todoItem>>() {}.getType();
			arrayList = gson.fromJson(in, ListType);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveInFile(ArrayList<todoItem> arrayList) {
		try {
			FileOutputStream fos = openFileOutput(FILENAME,0);
			Gson gson = new Gson();
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			gson.toJson(arrayList, osw);
			osw.flush();
			osw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

