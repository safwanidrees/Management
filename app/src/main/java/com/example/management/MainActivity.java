package com.example.management;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DoesAdapter.OnItemClickListener  {

    DatabaseReference reference;
RecyclerView ourdoes;
ArrayList<Detail>list=new ArrayList<Detail>();
DoesAdapter doesAdapter;
ChildEventListener childEventListener;
    View ChildView ;
    ListView listView;
    int year,month,day;
    TextView selectdate;
    public  String selectedDate;
    SwipeRefreshLayout swipeRefreshLayout;

    int RecyclerViewItemPosition ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        android.net.NetworkInfo wifi = cm
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        android.net.NetworkInfo datac = cm
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((wifi != null & datac != null)
                && (wifi.isConnected() | datac.isConnected())) {
            //connection is avlilable

            selectdate=(TextView)findViewById(R.id.todaydate);

            Calendar calendar=Calendar.getInstance();
            String currentdate=DateFormat.getDateInstance().format(calendar.getTime());

            selectdate.setText(currentdate);
            selectedDate =selectdate.getText().toString();


            loadonstart(selectedDate);


            Button btnadd=(Button)findViewById(R.id.btnAddNew);
            btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i= new Intent (MainActivity.this,NewTask.class);
                    startActivity(i);
                }
            });





            selectdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar calendar=Calendar.getInstance();

                    year=calendar.get(Calendar.YEAR);
                    month=calendar.get(Calendar.MONTH);
                    day=calendar.get(Calendar.DAY_OF_MONTH);


                    final DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//                               selectdate.setText(day+"/"+month+"/"+year);
                                    Calendar calander2 = Calendar.getInstance();

                                    calander2.setTimeInMillis(0);

                                    calander2.set(year, month, day, 0, 0, 0);

                                    Date SelectedDate = calander2.getTime();

                                    DateFormat dateformat_US = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
                                    String StringDateformat_US = dateformat_US.format(SelectedDate);

                                    selectdate.setText(StringDateformat_US);

                                    selectedDate=selectdate.getText().toString();

                                    loadorder(selectedDate);

                                }

                            }, year, month, day);


                    datePickerDialog.show();



                    final SwipeRefreshLayout swipeRefreshLayout=findViewById(R.id.refreshorder);
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
//loadonstart();
                            swipeRefreshLayout.setRefreshing(false);
//                    swipeRefreshLayout.setRefreshing(true);
                            getApplicationContext();
                            loadonstart(selectedDate);
                            swipeRefreshLayout.setRefreshing(false);
                        }

                    });
                    Log.w("val","mydateeeeeee"+selectedDate);


                }
            });



            //Recyclerview lany ky lye



        }else{
            //no connection
            Toast toast = Toast.makeText(this, "No Internet Connection",
                    Toast.LENGTH_LONG);
            toast.show();
            ImageView no=(ImageView)findViewById(R.id.internet);
            no.setVisibility(View.VISIBLE);
//            Intent i=new Intent(this,nointernet.class);
//            startActivity(i);


        }



    }

    @Override
    protected void onStart() {
        super.onStart();
//     if(getApplicationContext()!=null){
//        loadorder();
//
//     }


    }

public void loadonstart(String DA){
    Calendar calendar=Calendar.getInstance();
    String currentdate=DateFormat.getDateInstance().format(calendar.getTime());
loadorder(DA);

}

    @Override
    public void onItemClick(int position) {
//        Detail order = list.get(position);
//        showUpdateDeleteDialog(order.getDetailId(), order.getName(),order.getEmail(),order.getPhone(),order.getAddress(), order.getOrderspinner());
//        Intent i= new Intent (MainActivity.this,NewTask.class);
//         startActivity(i);

        Detail order = list.get(position);
Log.w("da","my"+selectedDate);
      showUpdateDeleteDialog(selectedDate,order.getDetailId(), order.getName(),order.getEmail(),order.getPhone(),order.getAddress(),order.getOrderspinner());


    }
    private boolean updateOrder(String updatedate,String DetailId, String Name, String Email, String Phone, String Address, String orderspinner) {
        //getting the specified artist reference
        Calendar calendar=Calendar.getInstance();
        String currentdate=DateFormat.getDateInstance().format(calendar.getTime());

        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Order").child(updatedate).child(DetailId);

        //updating artist
        Detail detail = new Detail(DetailId, Name, Email,Phone,Address,orderspinner);
        dR.setValue(detail);


        Toast.makeText(getApplicationContext(), "Order Updated", Toast.LENGTH_LONG).show();
        return true;
    }
    private void showUpdateDeleteDialog(final String updatesdate, final String DetailId, final String Name, final String Email, final String Phone, final String Address, final String orderspinner) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_update_order, null);
        dialogBuilder.setView(dialogView);

        final EditText updatename = (EditText) dialogView.findViewById(R.id.updateeditTextName);
        updatename.setText(Name);
        final EditText updateemail = (EditText) dialogView.findViewById(R.id.updateemail);
updateemail.setText(Email);
        final EditText updatephone = (EditText) dialogView.findViewById(R.id.updatephone);
updatephone.setText(Phone);
        final EditText updateaddress = (EditText) dialogView.findViewById(R.id.updateAddress);
updateaddress.setText(Address);

        final Spinner spinnerGenre = (Spinner) dialogView.findViewById(R.id.updatespinnerOrder);

//         spinnerGenre.setTextAlignment(Integer.parseInt(orderspinner));
//        spinnerGenre.setSelection(orderspinner);
        
//        spinnerGenre.setSelected(orderspinner);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.btnDel);



        dialogBuilder.setTitle(Name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String finalname = updatename.getText().toString().trim();
                String finalemail = updateemail.getText().toString().trim();
                String finalphone = updatephone.getText().toString().trim();
                String finaladdress = updateaddress.getText().toString().trim();

                String fianlorderspinner = spinnerGenre.getSelectedItem().toString();

                if (!TextUtils.isEmpty(finalname)) {
                    updateOrder(updatesdate,DetailId, finalname, finalemail,finalphone,finaladdress,fianlorderspinner);
                    b.dismiss();

//                    Intent i=new Intent(MainActivity.this,MainActivity.class);
//                    startActivity(i);

                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                 * we will code this method to delete the artist
                 * */
                Calendar calendar=Calendar.getInstance();
                String currentdate=DateFormat.getDateInstance().format(calendar.getTime());
                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Order").child(updatesdate).child(DetailId);

                dR.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            b.dismiss();


                            Toast.makeText(getApplicationContext(), "Order Deleted", Toast.LENGTH_LONG).show();
//                            Intent i=new Intent(MainActivity.this,MainActivity.class);
//                            startActivity(i);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        });
    }

    //Main code to fetech data fromm firebasde

public void loadorder(final String selecteddate){
    //        listView = (ListView) findViewById(R.id.ourdoes);


    ourdoes=findViewById(R.id.ourdoes);

    ourdoes.setLayoutManager(new LinearLayoutManager(this));



    doesAdapter = new DoesAdapter(MainActivity.this, list);
    ourdoes.setAdapter(doesAdapter);

// get data from firebase




    reference= FirebaseDatabase.getInstance().getReference("Order").child(selecteddate);

    reference.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list.clear();
            doesAdapter.notifyDataSetChanged();

            for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
            {
//Mydoes is use because of arraylist name

                Detail p = dataSnapshot1.getValue(Detail.class);
                list.add(p);

            }


//                listAdapteer.notifyDataSetChanged();

//    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//      Detail order = list.get(position);
//
//      showUpdateDeleteDialog(order.getDetailId(), order.getName(),order.getEmail(),order.getPhone(),order.getAddress(),order.getOrderspinner());
//        return true;
//
//    }
//});
            doesAdapter.setOnItemClickListener(MainActivity.this);


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

            Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_SHORT).show();
        }
    });
}
}
