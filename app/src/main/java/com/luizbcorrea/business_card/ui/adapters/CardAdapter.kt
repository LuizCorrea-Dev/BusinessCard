package com.luizbcorrea.business_card.ui.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.luizbcorrea.business_card.database.models.Card
import com.luizbcorrea.business_card.databinding.ItemCardBinding
import kotlinx.android.synthetic.main.activity_add_card.*


// implementando o recycler view adapter com viewHolder
class CardAdapter : ListAdapter<Card, CardAdapter.ViewHolder>(DiffCallback()){

    //Create functions empty type unit with param Task
    var listenerEdit: (Card) -> Unit = {}
    var listenerDelete: (Card) -> Unit = {}

    // este método é chamado todas as vezes que for criar um item da RV
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    // suporte do layout para o RV ( popula as informaões)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    // implementação do ViewHolder
    inner class ViewHolder (
        private val binding: ItemCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Card) {
            binding.tvName.text = item.name
            binding.tvCompany.text = item.company
            binding.tvPhone.text = item.phone
            binding.tvEmail.text = item.email
            binding.tvColor.text = item.colorSelected
            binding.tvColorTxt.text = item.colorTxt

            // cor do card
            binding.viewCard.setCardBackgroundColor(
                Color.parseColor(item.colorSelected)
            )

            // cor do texto
            binding.tvName.setTextColor(Color.parseColor(item.colorTxt))
            binding.tvCompany.setTextColor(Color.parseColor(item.colorTxt))
            binding.tvPhone.setTextColor(Color.parseColor(item.colorTxt))
            binding.tvEmail.setTextColor(Color.parseColor(item.colorTxt))

            binding.btnDeleteCard.setOnClickListener {
                listenerDelete(item)
            }

            binding.btnEditCard.setOnClickListener {
                listenerEdit(item)
            }



        }
    }


    // Usado para adicionar regras de validação dos itens, que podemos personalizar

    class DiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(
            oldItem: Card,
            newItem: Card)
        : Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Card, newItem: Card)
        : Boolean {
            return oldItem.id == newItem.id
        }

    }
}
