package eazy.firstapplication;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText= (EditText) findViewById(R.id.eddata);
        btn= (Button) findViewById(R.id.clickbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
MyData myData=new MyData();
                ContentValues cv=new ContentValues();
                cv.put(MyData.NAME,editText.getText().toString());
                Uri uri=getContentResolver().insert(MyData.CONTENT_URI,cv);
            }
        });

       /* Intent i2 = new Intent();
        i2.setClassName("eazy.secondapplication", "eazy.secondapplication.MainActivity");
        i2.putExtra("Id", "100");
        startActivity(i2);*/
    }
}
