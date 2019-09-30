package com.example.management;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Random;

public class NewTask extends AppCompatActivity {

    EditText name;
    EditText    email;
    EditText phone;
    EditText address;
    TextView currentdate;
    Spinner orderspinner;
    Button btnorder;
    ImageButton btndate;
    Integer counter=new Random().nextInt();
    DatabaseReference databaseReference;
    TextView date;
    int year,month,day;

    EditText titledoes, descdoes, datedoes;

Integer doesnum=new Random().nextInt();
    Button btnSaveTask, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);



        name=findViewById(R.id.editTextName);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.Address);
        orderspinner=findViewById(R.id.spinnerOrder);

//        date=findViewById(R.id.date);

//        btndate=(ImageButton) findViewById(R.id.datebtn);
//
//
//
//
//        btndate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Calendar calendar=Calendar.getInstance();
//                year=calendar.get(Calendar.YEAR);
//                month=calendar.get(Calendar.MONTH);
//                day=calendar.get(Calendar.DAY_OF_MONTH);
//
//
//                final DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
//                        new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                                date.setText(day+"/"+month+"/"+year);
//                            }
//                        }, year, month, day);
//
//                datePickerDialog.show();
//            }
//        });
////        String currentdate=date.getText().toString();

        // firebase ke fileld date ky through bn rahi hajn




//        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd ");
//
//        String date = format.format(new Date());
//
//        long d = System.currentTimeMillis();
//
//        String datee = format.format(new Date(d));


        Calendar calendar=Calendar.getInstance();
        String currentdate=DateFormat.getDateInstance().format(calendar.getTime());

        databaseReference= FirebaseDatabase.getInstance().getReference("Order").child(currentdate);


        btnorder=(Button)findViewById(R.id.btnSaveTask);

        btnorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();

            }

        });
    }


    private void addOrder(){
        String personname=name.getText().toString().trim();
        String personemail=email.getText().toString().trim();

        String personphone=phone.getText().toString().trim();

        int personphone1=Integer.parseInt(personphone);
        String personaddress=address.getText().toString().trim();


        String spinnerordered=orderspinner.getSelectedItem().toString();

//if(TextUtils.isEmpty(personname)&&TextUtils.isEmpty(personphone)&&TextUtils.isEmpty(personemail)&&TextUtils.isEmpty(personaddress)&&TextUtils.isEmpty(spinnerordered))
//{
//    Toast.makeText(this, "not added", Toast.LENGTH_LONG).show();
//}
//        if(personname.matches("")&&personemail.matches("")&&personphone.matches("")&&personaddress.matches("")&&spinnerordered.matches("Coffe")){
//
//            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//
//        }

        if(!TextUtils.isEmpty(personname)&&!TextUtils.isEmpty(personphone)&&!TextUtils.isEmpty(personemail)&&!TextUtils.isEmpty(personaddress)&&!TextUtils.isEmpty(spinnerordered)){

            String id=databaseReference.push().getKey();

            Detail detail=new Detail(id,personname,personemail,personphone,personaddress,spinnerordered);



            databaseReference.child(id).setValue(detail);

            Toast.makeText(this, "Order added", Toast.LENGTH_LONG).show();

            name.setText("");
            email.setText("");
            address.setText("");
            phone.setText("");
            Intent i= new Intent (NewTask.this,MainActivity.class);
            startActivity(i);



        }
        else
        {            Toast.makeText(this, "Order not added", Toast.LENGTH_LONG).show();
        }


    }
    private void showdate(){

    }


}

