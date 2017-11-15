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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText txtProduct, txtDetail, txtPrice;
    private Button btnAdd;
    private ListView listProduct;
    private ArrayList<ProductData> listData = new ArrayList<ProductData>();
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtProduct = (EditText) findViewById(R.id.txtProduct);
        txtDetail = (EditText) findViewById(R.id.txtDetail);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        listProduct = (ListView) findViewById(R.id.listProduct);
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        showList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });

    }

    private void showList() {
        getProduct();
        listProduct.setAdapter(new AdapterListViewData(this,listData));
    }

    private void getProduct() {
        Cursor mCursor = database.query(true, "shoplist", new String[]{"id", "product", "detail", "price"}, null, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();

            listData.clear();
            if (mCursor.getCount() > 0) {
                do {
                    int id = mCursor.getInt(mCursor.getColumnIndex("id"));
                    String product = mCursor.getString(mCursor.getColumnIndex("product"));
                    String detail = mCursor.getString(mCursor.getColumnIndex("detail"));
                    int price = mCursor.getInt(mCursor.getColumnIndex("price"));

                    listData.add(new ProductData(id, product, detail, price));
                } while (mCursor.moveToNext());
            }
        }
    }

    public void editProduct(int id, String product, String detail, int price) {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("product", product);
        values.put("detail", detail);
        values.put("price", price);

        database.update("shoplist", values, "id = ?", new String[]{"" + id});

        showList();
    }

    public void deleteProduct(int id) {
        database.delete("shoplist", "id = " + id, null);
        Toast.makeText(this, "Delete Data Id " + id + " Complete", Toast.LENGTH_SHORT).show();

        showList();
    }

    private void addProduct() {
        //เชคว่าข้อมูลที่ป้อนเปน null มั้ย
        if (txtProduct.length() > 0 && txtDetail.length() > 0 && txtPrice.length() > 0) {
            ContentValues values = new ContentValues();
            values.put("product", txtProduct.getText().toString());
            values.put("detail", txtDetail.getText().toString());
            values.put("price", txtPrice.getText().toString());

            database.insert("shoplist", null, values);

            Toast.makeText(this, "Add Data Complete", Toast.LENGTH_SHORT).show();

            txtProduct.setText("");
            txtDetail.setText("");
            txtPrice.setText("");

            showList();
        } else {
            Toast.makeText(this, "Please Input Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void showEdit(int id, String product, String detail, int price) {
        //ไปหน้า edit ผ่าน intent
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra("keyId", id);
        i.putExtra("keyProduct", product);
        i.putExtra("keyDetail", detail);
        i.putExtra("keyPrice", price);

        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            int id = intent.getExtras().getInt("keyId");
            String productEdit = intent.getExtras().getString("keyProduct");
            String DetailEdit = intent.getExtras().getString("keyDetail");
            int PriceEdit = intent.getExtras().getInt("keyPrice");
            editProduct(id, productEdit, DetailEdit, PriceEdit);
        }
    }
}
