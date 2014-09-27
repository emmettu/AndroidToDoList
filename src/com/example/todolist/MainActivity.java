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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	static public ArrayList<todoItem> todoArray = new ArrayList<todoItem>();
	public ArrayList<todoItem> ArchiveList = new ArrayList<todoItem>();
	private ArrayAdapter<todoItem> todoAdapter;
    private ListView listview;
    public ArrayList<todoItem> archiveArray = new ArrayList<todoItem>();
    private static final String MAINFILENAME = "mainfile.sav";
    private static final String ARCHIVEFILENAME = "archivefile.sav";
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
		loadFromFile(MAINFILENAME, todoArray);
		loadFromFile(ARCHIVEFILENAME, archiveArray);
			
		
        //initialize ArrayAdapter
        todoAdapter = new ArrayAdapter<todoItem>(getBaseContext(), android.R.layout.simple_list_item_1, todoArray);
        todoAdapter.notifyDataSetChanged();
        
        listview = (ListView) findViewById(R.id.addTodoListView);
        listview.setAdapter(todoAdapter);        
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
//				// TODO Auto-generated method stub
				todoItem todo = todoAdapter.getItem(position);
				TextView textTodo = (TextView) view.findViewById(view.getId());
				todo.setStatus(!todo.isDone());
				
				if(todo.isDone()){
				      textTodo.setPaintFlags(textTodo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				      //Toast.makeText(getBaseContext(), "Nice job!", Toast.LENGTH_SHORT).show();
				}
				else{
				      textTodo.setPaintFlags(textTodo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
				}
				saveInFile(MAINFILENAME, todoArray);
			}
		});
        listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				AlertDialog.Builder adb = new AlertDialog.Builder(getBaseContext());
//				adb.setMessage("");
				dialogBox(position);
				return false;
			}
		});		
        
    }
    
    public void dialogBox(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int finalPosition = position;
        builder.setTitle("What would you like to do?")
               .setItems(R.array.dialog_box_string, new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                   // The 'which' argument contains the index position
                   // of the selected item
                	   if(which == 0){
                		   //Toast.makeText(getBaseContext(), "Archive", Toast.LENGTH_SHORT).show();
                		   archiveArray.add(todoAdapter.getItem(finalPosition));
                		   saveInFile(ARCHIVEFILENAME, archiveArray);
                		   todoArray.remove(finalPosition);
                		   todoAdapter.notifyDataSetChanged();

                	   }
                	   else if(which == 1){
                		   Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                				  "mailto","", null));
                		emailIntent.putExtra(Intent.EXTRA_SUBJECT, todoAdapter.getItem(finalPosition).toString());
                		startActivity(Intent.createChooser(emailIntent, "Send email..."));
                		   
                	   }
                	   else if(which == 2){
                		   todoArray.remove(finalPosition);
                		   todoAdapter.notifyDataSetChanged();
                		   updateCrossOuts();
                		   saveInFile(MAINFILENAME, todoArray);
						}
                	   
               }
        });
        builder.show();
    }
    
    public void updateCrossOuts() {
    	//View view;
    	TextView textView;
    	if(listview.getCount() == 1) {
    		return;
    	}
    	
    	for(int i = 0; i < listview.getCount(); i++) {
    		textView = (TextView) listview.getChildAt(i);
    		todoItem todo = todoAdapter.getItem(i);
    		
    		if(textView == null) {
    			return;
    		}
    		
    		if(todo.isDone()) {
			      textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			      //textView.setText("Update!");
			      //Toast.makeText(getBaseContext(), todo.toString()+" is done!", Toast.LENGTH_SHORT).show();
			}
			else {
			      textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
			      //textView.setText("Updated");
			      //Toast.makeText(getBaseContext(),  todo.toString()+" not done", Toast.LENGTH_SHORT).show();

			  }
    	}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void todoArchive(MenuItem menu) {
		Intent intent = new Intent(MainActivity.this, ToDoArchive.class);
		startActivity(intent);
	}
    
    public void addToDo(View view) throws IOException {
    	TextView textview = (TextView) findViewById(R.id.addTodoText);
    	String TodoText = textview.getText().toString();
    	if(TodoText.equals("")) {
    		Toast.makeText(getBaseContext(), "Can't add empty to do item.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	//Toast.makeText(getBaseContext(), TodoText, Toast.LENGTH_SHORT).show();
    	todoItem todo = new todoItem(TodoText);
    	textview.setText("");
    	todoArray.add(todo);
    	todoAdapter.notifyDataSetChanged();
    	updateCrossOuts();
    	saveInFile(MAINFILENAME, todoArray);
    }


    public void loadFromFile(String FILENAME, ArrayList<todoItem> todoArray) {
    	if(todoArray == null) {
    		todoArray = new ArrayList<todoItem>();
    	}
    	try {
    		FileInputStream fis = openFileInput(FILENAME);
    		BufferedReader in = new BufferedReader(new InputStreamReader(fis));			
    		//From http://www.javacreed.com/simple-gson-example/
    		Gson gson = new Gson();
    		
    		Type ListType = new TypeToken<ArrayList<todoItem>>() {}.getType();
    		todoArray = gson.fromJson(in, ListType);
    	} catch (FileNotFoundException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }

    public void saveInFile(String FILENAME, ArrayList<todoItem> todoArray) {
    	try {
    		FileOutputStream fos = openFileOutput(FILENAME,0);
    		Gson gson = new Gson();
    		OutputStreamWriter osw = new OutputStreamWriter(fos);
    		gson.toJson(todoArray, osw);
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


