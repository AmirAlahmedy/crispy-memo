package com.example.reminder.UI;

import android.app.Dialog;
import android.os.Bundle;
import com.example.reminder.R;
import com.example.reminder.Reminder;
import com.example.reminder.UI.Fragments.reminderFragment;
import com.example.reminder.adapters.MyreminderRecyclerViewAdapter;
import com.example.reminder.dummy.DummyContent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.reminder.database.SqliteDatabase;

public class MainActivity extends AppCompatActivity implements reminderFragment.OnListFragmentInteractionListener, Filterable {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<String> reminders = new ArrayList<>();

    private Dialog reminderDialog;
    private RecyclerView rRecyclerView;
    private RecyclerView.Adapter aAdapter;
    private RecyclerView.LayoutManager lLayoutManager;

    private SqliteDatabase mDataBase;

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

        mDataBase = new SqliteDatabase(this);
        ArrayList<Reminder> allReminders = mDataBase.listReminders();


        aAdapter = new MyreminderRecyclerViewAdapter(this, allReminders, this);
        rRecyclerView.setAdapter(aAdapter);

        if(allReminders.size() > 0){
            rRecyclerView.setVisibility(View.VISIBLE);
            aAdapter = new MyreminderRecyclerViewAdapter(this, allReminders, this);
            rRecyclerView.setAdapter(aAdapter);

        }else {
            rRecyclerView.setVisibility(View.GONE);
            Toast.makeText(this, "There are no reminders in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        reminderDialog = new Dialog(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReminderDialog(view);
            }

        });


    }

    public void addReminderDialog(View view) {

        reminderDialog.setContentView(R.layout.dialog_custom);
        final EditText contentField = (EditText)reminderDialog.findViewById(R.id.EditTextName);
        final CheckBox isImportant = (CheckBox)reminderDialog.findViewById(R.id.important);

        Button btn;
        btn = (Button) reminderDialog.findViewById(R.id.btnAdd);
        btn.setText("Add");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String content = contentField.getText().toString();
                final boolean important = isImportant.isChecked();

                if(TextUtils.isEmpty(content)){
                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    Reminder newReminder = new Reminder(content, important);
                    mDataBase.addReminder(newReminder);
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        TextView txtClose;
        txtClose = (TextView) reminderDialog.findViewById(R.id.txtClose);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reminderDialog.dismiss();
                Toast.makeText(MainActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        reminderDialog.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mDataBase != null){
            mDataBase.close();
        }
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
        View view = item.getActionView();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            return true;
        }else if(id == R.id.action_add) {
            addReminderDialog(view);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.String item) {
        Log.d(TAG, "Item Clicked!");
        View view = new View(this);
        // TODO: Show edit popup here not in the adapter
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}

