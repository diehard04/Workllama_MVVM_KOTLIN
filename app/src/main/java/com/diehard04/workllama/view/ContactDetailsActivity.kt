package com.diehard04.workllama.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.diehard04.workllama.R
import com.diehard04.workllama.base.ContactListViewModelFactory
import com.diehard04.workllama.base.Status
import com.diehard04.workllama.data.model.Content
import com.diehard04.workllama.data.network.ApiHelper
import com.diehard04.workllama.data.network.RetrofitBuilder
import com.diehard04.workllama.utils.AppConstant
import com.diehard04.workllama.viewmodel.ContactListViewModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class ContactDetailsActivity : AppCompatActivity() {
    private lateinit var ivBack: ImageView
    private lateinit var user: Content
    private lateinit var profileImage: CircleImageView
    private lateinit var tvName: AppCompatTextView
    private lateinit var tvPhone: AppCompatTextView
    private lateinit var tvEmail: AppCompatTextView
    private lateinit var ivIsStar: ImageView
    private lateinit var mContactListViewModel: ContactListViewModel
    private lateinit var progressBar: ProgressBar
    private var isStar: Int? = null
    private var position:Int ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)
        user = intent.getSerializableExtra("contactDetails") as Content
        position = intent.extras?.getInt("position")
        initView()
    }

    private fun initView() {
        progressBar = findViewById(R.id.progressBar)
        mContactListViewModel = ViewModelProvider(
            this, ContactListViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(ContactListViewModel::class.java)
        ivBack = findViewById(R.id.ivBack)
        ivBack.setOnClickListener {
            val intent = Intent()
            intent.putExtra("isStar", isStar)
            intent.putExtra("position", position)
            setResult(RESULT_OK, intent)
            finish()
        }

        profileImage = findViewById(R.id.profileImage)
        tvName = findViewById(R.id.tvName)
        tvPhone = findViewById(R.id.tvPhone)
        tvEmail = findViewById(R.id.tvEmail)
        ivIsStar = findViewById(R.id.tvIsStar)
        val imageUrl = AppConstant.BASE_THUMBNAIL_PRE + user.getThumbnail()
        Picasso.with(this).load(imageUrl).noFade().fit().noPlaceholder().into(profileImage)
        tvName.text = user.getName()
        tvPhone.text = user.getPhone()
        tvEmail.text = user.getEmail()
        if (user.getIsStarred() == 0) {
            Picasso.with(applicationContext).load(R.drawable.ic_not_added).into(ivIsStar)
        } else {
            Picasso.with(applicationContext).load(R.drawable.ic_added).into(ivIsStar)
        }

        ivIsStar.setOnClickListener {
            if (user.getIsStarred() == 0) {
                mContactListViewModel.updateIsStart(userId = user.getId().toString(), 0)
                    .observe(this, Observer {
                        it.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    progressBar.visibility = View.GONE
                                    resource.data.let { user ->
                                        Picasso.with(applicationContext).load(R.drawable.ic_added)
                                            .into(ivIsStar)
                                        isStar = user?.getIsStarred()
                                    }
                                }
                                Status.ERROR -> {
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
            } else {
                mContactListViewModel.updateIsStart(userId = user.getId().toString(), 1)
                    .observe(this, Observer {
                        it.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    progressBar.visibility = View.GONE
                                    resource.data.let { user ->
                                        Picasso.with(applicationContext)
                                            .load(R.drawable.ic_not_added).into(ivIsStar)
                                        isStar = user?.getIsStarred()
                                    }
                                }
                                Status.ERROR -> {
                                    progressBar.visibility = View.GONE
                                    it.message?.let { it1 -> Log.d("error ", it1) }
                                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                                }
                                Status.LOADING -> {
                                    progressBar.visibility = View.VISIBLE
                                }
                            }
                        }
                    })
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("isStar", isStar)
        intent.putExtra("position", position)
        setResult(RESULT_OK, intent )
        finish()
        super.onBackPressed()
    }
}