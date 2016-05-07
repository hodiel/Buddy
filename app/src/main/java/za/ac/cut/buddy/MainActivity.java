package za.ac.cut.buddy;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainActivity extends Activity implements View.OnClickListener {
    final int CREATE_REQUEST = 1;
    String file_name = "Buddies.txt";
    ImageView ivCall, ivMessage, ivEdit;
    ActionBar actionBar;
    ListView lvBuddies;

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
        lvBuddies = (ListView) findViewById(R.id.lv_buddies);
        actionBar = getActionBar();
        loadBuddy(file_name);
        loadProfile();

        ArrayList<CustomFriend> buddies = new ArrayList();
        buddies.add(new CustomFriend("Amo", 15, "Male"));
        buddies.add(new CustomFriend("Bonang", 15, "Female"));
        buddies.add(new CustomFriend("Celiwe", 15, "Female"));
        buddies.add(new CustomFriend("Dudu", 15, "Female"));
        buddies.add(new CustomFriend("Ema", 15, "Female"));
        buddies.add(new CustomFriend("Filane", 15, "Male"));
        buddies.add(new CustomFriend("Gomolemo", 15, "Male"));
        buddies.add(new CustomFriend("Holi", 15, "Male"));
        buddies.add(new CustomFriend("Izwe", 15, "Male"));
        buddies.add(new CustomFriend("Jomo", 15, "Male"));
        buddies.add(new CustomFriend("Katleho", 15, "Female"));
        buddies.add(new CustomFriend("Mpho", 15, "Male"));
        buddies.add(new CustomFriend("Nomi", 15, "Female"));
        lvBuddies.setAdapter(new CustomFriendAdapter(this, buddies));

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
            case R.id.profile:
                createProfile();
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
                Toast.makeText(getApplicationContext(), "Buddy saved successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Buddy not saved", Toast.LENGTH_LONG).show();
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
                if (buddy_line == null)
                    buddy_line = "No data to read.";
                Toast.makeText(getApplicationContext(), buddy_line, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean saveProfile(Buddy tempBuddy) {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = shared.edit();
        editor.putString("name", tempBuddy.getName());
        editor.putString("surname", tempBuddy.getSurname());
        editor.putString("gender", tempBuddy.getGender());
        editor.putString("picture", tempBuddy.getPicture());
        editor.putString("location", tempBuddy.getLocation());
        editor.putString("phone", tempBuddy.getPhone());
        editor.putString("email", tempBuddy.getEmail());
        editor.putString("group", tempBuddy.getGroup());
        editor.putString("status", tempBuddy.getStatus());
        return editor.commit();
    }

    private void createProfile() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.add_new_buddy, null);
        dlg.setView(layout);
        final EditText etName = (EditText) layout.findViewById(R.id.et_name);
        final EditText etSurName = (EditText) layout.findViewById(R.id.et_surname);
        final EditText etLocation = (EditText) layout.findViewById(R.id.et_location);
        final EditText etPhone = (EditText) layout.findViewById(R.id.et_phone);
        final EditText etEmail = (EditText) layout.findViewById(R.id.et_email);
        final Spinner spGroup = (Spinner) layout.findViewById(R.id.sp_group);
        final Spinner spStatus = (Spinner) layout.findViewById(R.id.sp_status);
        final RadioButton rbMale = (RadioButton) layout.findViewById(R.id.rb_male);
        dlg.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (saveProfile(new Buddy(etName.getText().toString(), etSurName.getText().toString(),
                        (rbMale.isChecked() ? "Male" : "Female"), "Buddy Picture", etLocation.getText().toString(),
                        etPhone.getText().toString(), etEmail.getText().toString(), spGroup.getSelectedItem().toString(),
                        spStatus.getSelectedItem().toString()))) {
                    Toast.makeText(MainActivity.this, "Profile saved Successfully.", Toast.LENGTH_LONG).show();
                    loadProfile();
                } else
                    Toast.makeText(MainActivity.this, "Profile not saved.", Toast.LENGTH_LONG).show();
            }
        });
        dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dlg.show();
    }

    private void loadProfile() {
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        actionBar.setTitle(shared.getString("name", "No user stored"));
    }

    class CustomFriend {
        private String friendName, friendGender;
        private int friendAge;

        public CustomFriend(String name, int age, String gender) {
            friendName = name;
            friendAge = age;
            friendGender = gender;
        }

        public String getFriendName() {
            return friendName;
        }

        public String getFriendGender() {
            return friendGender;
        }

        public int getFriendAge() {
            return friendAge;
        }
    }

    class CustomFriendAdapter extends BaseAdapter {
        Activity context;
        ArrayList<CustomFriend> customFriends;

        public CustomFriendAdapter(Activity context, ArrayList<CustomFriend> customFriends) {
            this.context = context;
            this.customFriends = customFriends;
        }

        @Override
        public int getCount() {
            return customFriends.size();
        }

        @Override
        public CustomFriend getItem(int position) {
            return customFriends.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.custom_friend, null);

            TextView tvName = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tvGender = (TextView) convertView.findViewById(R.id.tv_gender);
            TextView tvAge = (TextView) convertView.findViewById(R.id.tv_age);

            CustomFriend cF = getItem(position);
            tvName.setText("Name: " + cF.getFriendName());
            tvGender.setText("Gender: " + cF.getFriendGender());
            tvAge.setText("Age: " + cF.getFriendAge());

            return convertView;
        }
    }
}