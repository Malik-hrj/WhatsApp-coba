package com.example.whatsappcoba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsappcoba.R
import com.example.whatsappcoba.listener.ContactsClickListener
import com.example.whatsappcoba.util.Contacts
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*


class ContactsAdapter(val contacts: ArrayList<Contacts>) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    private var clickListener: ContactsClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_contact,
            parent, false))

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindItem(contacts[position], clickListener)
    }

    override fun getItemCount() = contacts.size

    class ContactsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        //memasangkan data pada view dalam layout_item
        fun bindItem(contacts: Contacts, listener: ContactsClickListener?) {
            txt_contact_name.text = contacts.name
            txt_contact_number.text = contacts.phone
            itemView.setOnClickListener { //memberikan aksi ketika item kontak diklik  é
                listener?.onContactClicked(contacts.name, contacts.phone)
            }
        }
    }
    fun setOnItemClickListener(listener: ContactsClickListener) {
        clickListener = listener
        notifyDataSetChanged()
    }

}