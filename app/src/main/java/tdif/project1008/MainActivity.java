package tdif.project1008;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtID, txtName, txtEmail, txtBY;
    private String txtGender;
    private RadioGroup rgGender;
    private Button btnAdd;

    private ListView listProduct;
    private ArrayList<StData> listData = new ArrayList<StData>();
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtID = (EditText) findViewById(R.id.txtID);
        txtName = (EditText) findViewById(R.id.txtName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtBY = (EditText) findViewById(R.id.txtBY);
        rgGender = (RadioGroup) findViewById(R.id.rgGender);
        if (rgGender.getCheckedRadioButtonId() == R.id.rbFemale) {
            txtGender = "Female";
        } else {
            txtGender = "Male";
        }
        listProduct = (ListView) findViewById(R.id.listProduct);
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        showList();

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

    }

    private void showList() {
        getStudent();
        listProduct.setAdapter(new AdapterListViewData(this, listData));
    }

    private void getStudent() {
        Cursor mCursor = database.query(true, "student", new String[]{"id", "name", "gender", "email", "by"}, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();

            listData.clear();
            if (mCursor.getCount() > 0) {
                do {
                    String id = mCursor.getString(mCursor.getColumnIndex("id"));
                    String name = mCursor.getString(mCursor.getColumnIndex("name"));
                    String gender = mCursor.getString(mCursor.getColumnIndex("gender"));
                    String email = mCursor.getString(mCursor.getColumnIndex("email"));
                    String by = mCursor.getString(mCursor.getColumnIndex("by"));

                    listData.add(new StData(id, name, gender, email, by));
                } while (mCursor.moveToNext());
            }
        }
    }

    public void editProduct(String id, String name, String gender, String email, String by) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("gender", gender);
        values.put("email", email);
        values.put("by", by);

        database.update("student", values, "id = ?", new String[]{"" + id});

        showList();
    }

    public void deleteProduct(String id) {
        database.delete("student", "id = " + id, null);
        Toast.makeText(this, "Delete Data Id " + id + " Complete", Toast.LENGTH_SHORT).show();

        showList();
    }

    private void addProduct() {
        //เชคว่าข้อมูลที่ป้อนเปน null มั้ย
        if (txtID.length() > 0 && txtName.length() > 0 && txtEmail.length() > 0 && txtBY.length() > 0) {
            ContentValues values = new ContentValues();
            values.put("id", txtID.getText().toString());
            values.put("name", txtName.getText().toString());
            values.put("gender", txtGender);
            values.put("email", txtEmail.getText().toString());
            values.put("by", txtBY.getText().toString());

            database.insert("student", null, values);

            Toast.makeText(this, "Add Data Complete", Toast.LENGTH_SHORT).show();


            txtID.setText("");
            txtName.setText("");
            txtGender = "";
            txtEmail.setText("");
            txtBY.setText("");


            showList();
        } else {
            Toast.makeText(this, "Please Input Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void showEdit(String id, String name, String gender, String email, String by) {
        //ไปหน้า edit ผ่าน intent
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra("keyId", id);
        i.putExtra("keyName", name);
        i.putExtra("keyGender", gender);
        i.putExtra("keyEmail", email);
        i.putExtra("keyBY", by);

        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            String id = intent.getExtras().getString("keyId");
            String NameEdit = intent.getExtras().getString("keyName");
            String GenderEdit = intent.getExtras().getString("keyGender");
            String EmailEdit = intent.getExtras().getString("keyEmail");
            String BYEdit = intent.getExtras().getString("keyBY");

            editProduct(id, NameEdit, GenderEdit, EmailEdit,BYEdit);
        }
    }
}
