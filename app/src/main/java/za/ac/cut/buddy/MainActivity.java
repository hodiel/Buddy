package za.ac.cut.buddy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    final int CREATE_REQUEST = 1;
    String file_name = "Buddies.txt";
    ImageView ivCall, ivMessage, ivEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivCall = (ImageView) findViewById(R.id.iv_call);
        ivCall.setOnClickListener(this);
        ivMessage = (ImageView) findViewById(R.id.iv_message);
        ivMessage.setOnClickListener(this);
        ivEdit = (ImageView) findViewById(R.id.iv_edit);
        ivEdit.setOnClickListener(this);
        loadBuddy(file_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_call:
                Intent i = new Intent(MainActivity.this, AddNewBuddy.class);
                startActivity(i);
                break;
            case R.id.iv_message:
                Toast.makeText(MainActivity.this, "Message button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_edit:
                Toast.makeText(MainActivity.this, "Edit button clicked", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void setActionBar(Toolbar toolbar) {
        super.setActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_new:
                startActivityForResult(new Intent(this, AddNewBuddy.class), CREATE_REQUEST);
                return true;
            case R.id.view_buddy:
                Toast.makeText(this, "Viewing buddy...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.search_buddy:
                Toast.makeText(this, "Searhing buddies...", Toast.LENGTH_LONG).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CREATE_REQUEST) {
            Buddy newBuddy = new Buddy(data.getStringExtra("name"), data.getStringExtra("surname"),
                    data.getStringExtra("gender"), data.getStringExtra("picture"),
                    data.getStringExtra("location"), data.getStringExtra("phone"),
                    data.getStringExtra("email"), data.getStringExtra("group"),
                    data.getStringExtra("status"));

            if (saveBuddy(newBuddy, file_name)) {
                Toast.makeText(this, "Buddy saved successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Buddy not saved", Toast.LENGTH_LONG).show();
            }

        }
    }

    /* Attempts to save Buddy information to a file.**/
    private boolean saveBuddy(Buddy newBuddy, String file_name) {
        boolean save = true;
        FileOutputStream out_stream = null;
        try {
            out_stream = openFileOutput(file_name, Context.MODE_PRIVATE);
            out_stream.write(newBuddy.toString().getBytes());
            out_stream.write("\n".getBytes());

        } catch (IOException e) {
            save = false;
            e.printStackTrace();
        } finally {
            /* Make sure the out_stream is closed
             * even if an exception is thrown in
             * the above code.**/
            try {
                out_stream.close();
            } catch (IOException e) { //handle possible IOException from out_stream.close()
                e.printStackTrace();
            }
        }
        return save;
    }

    /* Attempts to load Buddy information from a file if it exists.**/
    private void loadBuddy(String file_name) {
        try {
            String buddy_line;
            File file = getApplicationContext().getFileStreamPath(file_name);
            if (file.exists()) {
                BufferedReader in_read = new BufferedReader(
                        new InputStreamReader(openFileInput(file_name)));
                buddy_line = in_read.readLine();
                if (buddy_line == null) {
                    buddy_line = "No data to read.";
                }
                Toast.makeText(this, buddy_line, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}