package com.example.Madjobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var companyRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var comList: ArrayList<ComanyModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        companyRecyclerView = findViewById(R.id.rvCom)
        companyRecyclerView.layoutManager = LinearLayoutManager(this)
        companyRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        comList = arrayListOf<ComanyModel>()

        getCompanies()

    }

    private fun getCompanies() {

        companyRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("company")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(ComanyModel::class.java)
                        comList.add(empData!!)
                    }
                    val mAdapter = ComAdapter(comList)
                    companyRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : ComAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@FetchingActivity, CompanyDetailsActivity::class.java)
                            intent.putExtra("id", comList[position].comId)
                            intent.putExtra("name", comList[position].name)
                            intent.putExtra("country", comList[position].country)
                            intent.putExtra("province", comList[position].province)
                            intent.putExtra("district", comList[position].distric)
                            intent.putExtra("address", comList[position].address)
                            intent.putExtra("type", comList[position].type)
                            intent.putExtra("mobile", comList[position].mobile)
                            intent.putExtra("regDate", comList[position].regDate)
                            intent.putExtra("regNumber", comList[position].regNumber)
                            startActivity(intent)
                        }

                    })

                    companyRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}