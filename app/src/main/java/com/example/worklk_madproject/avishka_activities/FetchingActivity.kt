package com.example.avishkajobmanagement.activities

import com.example.avishkajobmanagement.adapters.EmpAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.avishkajobmanagement.models.JobManagement
import com.example.avishkajobmanagement.R
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var jobRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var jobList: ArrayList<JobManagement>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        jobRecyclerView = findViewById(R.id.rvEmp)
        jobRecyclerView.layoutManager = LinearLayoutManager(this)
        jobRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        jobList = arrayListOf<JobManagement>()

        getJobData()

        supportActionBar?.hide();

    }

    private fun getJobData() {

        jobRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("JobManagement")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                jobList.clear()
                if (snapshot.exists()){
                    for (jobSnap in snapshot.children){
                        val jobData = jobSnap.getValue(JobManagement::class.java)
                        jobList.add(jobData!!)
                    }
                    val mAdapter = EmpAdapter(jobList)
                    jobRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, EmployeeDetailsActivity::class.java)

                            //put extras
                            intent.putExtra("jobId", jobList[position].jobId)
                            intent.putExtra("jobTitle", jobList[position].jobTitle)
                            intent.putExtra("contactNo", jobList[position].contactNo)
                            intent.putExtra("description", jobList[position].description)
                            startActivity(intent)
                        }

                    })

                    jobRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}