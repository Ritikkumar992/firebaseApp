package com.example.flutterappp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.flutterappp.Adapter.StudentAdapter;
import com.example.flutterappp.Model.StudentModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<StudentModel> studentArr = new ArrayList<>();
    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //============== Static Data ===============================//
        /**

        studentArr.add(new StudentModel("Ritik","rk@gmail.com","Btech", "https://th.bing.com/th/id/OIP.Lpx9j83qR_cfQuaPHuvwWQHaHw?rs=1&pid=ImgDetMain"));
        studentArr.add(new StudentModel("Ritik","rk@gmail.com","Btech", "https://th.bing.com/th/id/OIP.Lpx9j83qR_cfQuaPHuvwWQHaHw?rs=1&pid=ImgDetMain"));
        studentArr.add(new StudentModel("Ritik","rk@gmail.com","Btech", "https://th.bing.com/th/id/OIP.Lpx9j83qR_cfQuaPHuvwWQHaHw?rs=1&pid=ImgDetMain"));
        studentArr.add(new StudentModel("Ritik","rk@gmail.com","Btech", "https://th.bing.com/th/id/OIP.Lpx9j83qR_cfQuaPHuvwWQHaHw?rs=1&pid=ImgDetMain"));

        */
        //============================ Fetching Data from API =====================//
        /**
         * Using Retrofit API:
         * Github link: https://github.com/Ritikkumar992/ApiApp/tree/master/ApiExample1
         */

        //======================= Fetching Data from firebase Realtime Database ====================//
        FirebaseRecyclerOptions<StudentModel> options =
                new FirebaseRecyclerOptions.Builder<StudentModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("teachers"), StudentModel.class)
                        .build();


        adapter = new StudentAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    // ================================= Search Icon Implementation==========================//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
//        MenuItem item = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                txtSearch(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                txtSearch(newText);
//                return true;
//            }
//        });
//
//        return true;
//    }

    // ============================= searching from firebase ===================//
    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<StudentModel> options =
                new FirebaseRecyclerOptions.Builder<StudentModel>()
                        .setQuery(FirebaseDatabase.getInstance()
                                .getReference()
                                .child("students").orderByChild("name").startAt(str).endAt(str+"~"),
                                StudentModel.class)
                        .build();

        adapter = new StudentAdapter(options);
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}