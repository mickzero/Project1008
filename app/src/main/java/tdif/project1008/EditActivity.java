package tdif.project1008;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    private EditText txtProductEdit, txtDetailEdit, txtPriceEdit;
    private Button btnEdit;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        txtProductEdit = (EditText) findViewById(R.id.txtProductEdit);
        txtDetailEdit = (EditText) findViewById(R.id.txtDetailEdit);
        txtPriceEdit = (EditText) findViewById(R.id.txtPriceEdit);
        btnEdit = (Button) findViewById(R.id.btnEdit);

        this.id = getIntent().getExtras().getInt("keyId");
        txtProductEdit.setText(getIntent().getExtras().getString("keyProduct"));
        txtDetailEdit.setText(getIntent().getExtras().getString("keyDetail"));
        txtPriceEdit.setText("" + getIntent().getExtras().getInt("keyPrice"));
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                i.putExtra("keyId", id);
                i.putExtra("keyProduct", txtProductEdit.getText().toString());
                i.putExtra("keyDetail", txtDetailEdit.getText().toString());
                i.putExtra("keyPrice", Integer.parseInt(txtPriceEdit.getText().toString()));
                finish();
            }
        });
    }
}
