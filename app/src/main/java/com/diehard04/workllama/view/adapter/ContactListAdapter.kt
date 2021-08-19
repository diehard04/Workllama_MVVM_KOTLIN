package com.diehard04.workllama.view.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.diehard04.workllama.R
import com.diehard04.workllama.data.model.Content
import com.diehard04.workllama.utils.AppConstant
import com.squareup.picasso.Picasso

/**
 * Created by DieHard_04 on 13-08-2021.
 */
class ContactListAdapter(private var context: Context, private val users: ArrayList<Content>) : RecyclerView.Adapter<ContactListAdapter.DataViewHolder>() {

    private  var clickListener: ClickListener = context as ClickListener

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewAvatar:ImageView = itemView.findViewById(R.id.imageViewAvatar)
        var textViewUserName:AppCompatTextView = itemView.findViewById(R.id.textViewUserName)
        var textViewUserEmail:AppCompatTextView = itemView.findViewById(R.id.textViewUserEmail)
        var isStarred:ImageView = itemView.findViewById(R.id.isStarred)

        fun bind(user: Content, context: Context, clickListener: ClickListener, position: Int) {
            Picasso.with(context).load(AppConstant.BASE_THUMBNAIL_PRE + user.getThumbnail()).fit().centerCrop().into(imageViewAvatar)
            textViewUserName.text = user.getName()
            textViewUserEmail.text = user.getEmail()
            if (user.getIsStarred() ==0) {
                Picasso.with(context).load(R.drawable.ic_not_added).into(isStarred)
            } else {
                Picasso.with(context).load(R.drawable.ic_added).into(isStarred)
            }

            isStarred.setOnClickListener{
                if (user.getIsStarred() == 0) {
                    clickListener.onClick(user.getId(), position, 0)
//                    Picasso.with(context).load(R.drawable.ic_added).into(isStarred)
//                    user.setIsStarred(1)
                } else{
                    clickListener.onClick(user.getId(), position, 1)
//                    Picasso.with(context).load(R.drawable.ic_not_added).into(isStarred)
//                    user.setIsStarred(0)
                }

            }
            itemView.setOnClickListener {
               clickListener.openActivity(user, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_content_list, parent, false))

    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position], context, clickListener, position)
    }

    fun addUsers(users: List<Content>) {
        this.users.apply {
            addAll(users)
        }

    }

    fun updateStar(content: Content?, position: Int) {
        Log.d("updateStar ", "${content?.getIsStarred()}  $position" )
        users[position].setIsStarred(content?.getIsStarred())

    }

    fun updateStarFromOnActivityResult(isStart:Int, position: Int ) {
        Log.d("updateStarFrom ", "$isStart  $position" )
        users[position].setIsStarred(isStart)
    }
    public interface ClickListener {
        fun onClick(itemId:Int?, position: Int, starType:Int)
        fun openActivity(content: Content, position: Int)
    }
}