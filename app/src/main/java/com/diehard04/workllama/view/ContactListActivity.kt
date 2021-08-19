package com.diehard04.workllama.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diehard04.workllama.R
import com.diehard04.workllama.base.ContactListViewModelFactory
import com.diehard04.workllama.base.Status
import com.diehard04.workllama.data.model.Content
import com.diehard04.workllama.data.network.ApiHelper
import com.diehard04.workllama.data.network.RetrofitBuilder
import com.diehard04.workllama.view.adapter.ContactListAdapter
import com.diehard04.workllama.viewmodel.ContactListViewModel

class ContactListActivity : AppCompatActivity(), ContactListAdapter.ClickListener {

    private lateinit var rvContactList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private var contentList: ArrayList<Content> = arrayListOf()
    private lateinit var mContactListViewModel: ContactListViewModel
    private lateinit var mContactListAdapter: ContactListAdapter
    private var pageNumber: Int = 1
    private var loading: Boolean = true
    private val REQUEST_CODE = 21

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initNetwork(pageNumber)
    }

    private fun initNetwork(pageNumber: Int) {
        mContactListViewModel.getContactList(pageNumber.toString()).observe(this, Observer {
            it.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        rvContactList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        resource.data?.let { users ->
                            retrieveList(users as List<Content>)
                            loading = true
                        }
                    }
                    Status.ERROR -> {
                        rvContactList.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        it.message?.let { it1 -> Log.d("error ", it1) }
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                        //rvContactList.visibility = View.GONE
                    }
                }

            }
        })
    }

    private fun retrieveList(users: List<Content>) {
        mContactListAdapter.apply {
            if (users.isNotEmpty()) {
                addUsers(users = users)
                notifyDataSetChanged()
            } else {
                Toast.makeText(applicationContext, "No data available", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initView() {
        mContactListViewModel = ViewModelProvider(
            this, ContactListViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ContactListViewModel::class.java)

        rvContactList = findViewById(R.id.rvContactList)
        progressBar = findViewById(R.id.progressBar)
        mContactListAdapter = ContactListAdapter(this, contentList)
        rvContactList.layoutManager = LinearLayoutManager(this)
        rvContactList.addItemDecoration(
            DividerItemDecoration(
                rvContactList.context,
                LinearLayoutManager.HORIZONTAL
            )
        )
        rvContactList.adapter = mContactListAdapter

        rvContactList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount =
                        (rvContactList.layoutManager as LinearLayoutManager).childCount
                    val totalItemCount =
                        (rvContactList.layoutManager as LinearLayoutManager).itemCount
                    val firstVisibleItemPosition =
                        (rvContactList.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (loading) {
                        if (firstVisibleItemPosition + visibleItemCount >= totalItemCount) {
                            pageNumber++
                            loading = false
                            initNetwork(pageNumber)
                        }
                    }
                }
            }
        })
    }



    override fun onClick(itemId: Int?, position: Int, starType: Int) {
        itemId?.let { it ->
            mContactListViewModel.updateIsStart(it.toString(), starType).observe(this, Observer {
                it.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            rvContactList.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            resource.data.let { user ->
                                mContactListAdapter.updateStar(user, position)
                            }
                            //mContactListAdapter.addUsers()
                            mContactListAdapter.notifyItemChanged(position)
                        }
                        Status.ERROR -> {
                            rvContactList.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            it.message?.let { it1 -> Log.d("error ", it1) }
                            Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            progressBar.visibility = View.VISIBLE
                            //rvContactList.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    override fun openActivity(content: Content, position: Int) {
        val intent = Intent(applicationContext, ContactDetailsActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("contactDetails", content)
        intent.putExtras(bundle)
        intent.putExtra("position", position)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                val isStar = data?.extras?.getInt("isStar")
                val position = data?.extras?.getInt("position")
                Log.d("isStar ", "$isStar")
                if (isStar != null && position !=null) {
                        mContactListAdapter.updateStarFromOnActivityResult(isStar, position)
                        mContactListAdapter.notifyItemChanged(position)
                }
            }
        }
    }
}