package com.example.reminder.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.reminder.R;
import com.example.reminder.UI.Fragments.reminderFragment;
import com.example.reminder.adapters.MyreminderRecyclerViewAdapter;
import com.example.reminder.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements reminderFragment.OnListFragmentInteractionListener {

    private ArrayList<String> reminders = new ArrayList<>();

    private Dialog reminderDialog;
    private RecyclerView rRecyclerView;
    private RecyclerView.Adapter aAdapter;
    private RecyclerView.LayoutManager lLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        rRecyclerView = findViewById(R.id.list);
        rRecyclerView.setHasFixedSize(true);

        lLayoutManager = new LinearLayoutManager(this);
        rRecyclerView.setLayoutManager(lLayoutManager);

        DummyContent dummy = new DummyContent();
        aAdapter = new MyreminderRecyclerViewAdapter(dummy.ITEMS, this);
        rRecyclerView.setAdapter(aAdapter);


        reminderDialog = new Dialog(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUp(view);
            }

        });


    }

    public void showPopUp(View view) {
        TextView txtClose;
        reminderDialog.setContentView(R.layout.dialog_custom);
        txtClose = (TextView) reminderDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderDialog.dismiss();
            }
        });
        reminderDialog.show();
    }

    public void addReminder(Dialog reminderDialog){
        Button addBtn;
        addBtn = (Button) reminderDialog.findViewById(R.id.btnAdd);
        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_add) {
            // TODO show a popup to add a reminder
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    View view;
                    view = menuItem.getActionView();
                    showPopUp(view);
                    return true;
                }
            });
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.String item) {
        Log.d("Hey", "Item Clicked!");
        View view = new View(this);
        //TODO show edit popup not add
        showPopUp(view);
    }
}

