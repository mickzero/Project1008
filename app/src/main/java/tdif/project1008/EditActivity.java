package tdif.project1008;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class EditActivity extends AppCompatActivity {

    private EditText txtIDEdit, txtNameEdit, txtEmailEdit,txtBYEdit;
    private RadioGroup rgGenderEdit;
    private Button btnEdit;
    private String txtGenderEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        txtIDEdit = (EditText) findViewById(R.id.txtIDEdit);
        txtNameEdit = (EditText) findViewById(R.id.txtNameEdit);
        txtEmailEdit = (EditText) findViewById(R.id.txtEmailEdit);
        txtBYEdit = (EditText) findViewById(R.id.txtBYEdit);
        rgGenderEdit = (RadioGroup) findViewById(R.id.rgGenderEdit);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        txtIDEdit.setText(getIntent().getExtras().getString("keyID"));
        txtNameEdit.setText(getIntent().getExtras().getString("keyName"));
        if (getIntent().getExtras().getString("keyGender") == "Male"){
            rgGenderEdit.check(R.id.rbMaleEdit);
        }else{
            rgGenderEdit.check(R.id.rbFemaleEdit);
        }
        txtEmailEdit.setText("" + getIntent().getExtras().getInt("keyEmail"));
        txtBYEdit.setText("" + getIntent().getExtras().getInt("keyBY"));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                i.putExtra("keyId", txtIDEdit.getText().toString());
                i.putExtra("keyName", txtNameEdit.getText().toString());
                i.putExtra("keyEmail", txtEmailEdit.getText().toString());
                i.putExtra("keyBY", txtBYEdit.getText().toString());
                finish();
            }
        });
    }
}
