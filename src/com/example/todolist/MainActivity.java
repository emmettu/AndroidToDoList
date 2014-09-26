package com.example.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private ArrayList<todoItem> todoArray = new ArrayList<todoItem>();
    private ArrayAdapter<todoItem> todoAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize ArrayAdapter
        todoAdapter = new ArrayAdapter<todoItem>(getBaseContext(), android.R.layout.simple_list_item_1, todoArray);

        ListView listview = (ListView) findViewById(R.id.addTodoListView);
        listview.setAdapter(todoAdapter);
        
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				todoItem todo = todoAdapter.getItem(position);
				TextView textTodo = (TextView) view.findViewById(view.getId());
				todo.setStatus(!todo.isDone());
				
				if(todo.isDone()){
				      textTodo.setPaintFlags(textTodo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				      Toast.makeText(getBaseContext(), "Nice job!", Toast.LENGTH_SHORT).show();
				}
				else{
				      textTodo.setPaintFlags(textTodo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
				  }
			}
		});
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
    
    public void addToDo(View view) {
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
    }
}
