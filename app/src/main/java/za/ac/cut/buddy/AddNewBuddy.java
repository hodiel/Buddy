package za.ac.cut.buddy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by 215110048 on 3/1/2016.
 */
public class AddNewBuddy extends Activity implements View.OnClickListener {
    EditText etName, etSurname, etLocation, etPhone, etEmail;
    RadioButton rbMale, rbFemale;
    Button btnCapture, btnSelect, btnSave, btnCancel;
    Spinner spStatus, spGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_buddy);

        etName = (EditText) findViewById(R.id.et_name);
        etSurname = (EditText) findViewById(R.id.et_surname);
        etLocation = (EditText) findViewById(R.id.et_location);
        etPhone = (EditText) findViewById(R.id.et_phone);
        etEmail = (EditText) findViewById(R.id.et_email);
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);
        btnCapture = (Button) findViewById(R.id.btn_capture);
        btnSelect = (Button) findViewById(R.id.bt_select);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnCancel = (Button) findViewById(R.id.btn_cancel);
        spStatus = (Spinner) findViewById(R.id.sp_status);
        spGroup = (Spinner) findViewById(R.id.sp_group);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (!etName.getText().toString().isEmpty() || !etSurname.getText().toString().isEmpty() ||
                       !etLocation.getText().toString().isEmpty() || !etPhone.getText().toString().isEmpty() ||
                        !etEmail.getText().toString().isEmpty()) {
                    Intent result = new Intent();
                    result.putExtra("name",etName.getText().toString());
                    result.putExtra("surname",etSurname.getText().toString());
                    result.putExtra("gender",(rbMale.isChecked()?"Male":"Female"));
                    result.putExtra("picture","Buddy Picture");
                    result.putExtra("location","Buddy Location");
                    result.putExtra("phone",etPhone.getText().toString());
                    result.putExtra("email",etEmail.getText().toString());
                    result.putExtra("group",spGroup.getSelectedItem().toString());
                    result.putExtra("status",spStatus.getSelectedItem().toString());
                    setResult(RESULT_OK, result);
                    AddNewBuddy.this.finish();
                } else {
                    Toast.makeText(AddNewBuddy.this, "Please fill in all fields.", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_cancel:
                AddNewBuddy.this.finish();
                break;
            default:
                break;
        }
    }
}
