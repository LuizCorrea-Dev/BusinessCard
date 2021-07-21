package com.luizbcorrea.business_card.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.luizbcorrea.business_card.R
import com.luizbcorrea.business_card.database.models.Card
import com.luizbcorrea.business_card.database.models.CardViewModel
import com.luizbcorrea.business_card.databinding.ActivityMainBinding

import com.luizbcorrea.business_card.ui.adapters.CardAdapter

class MainActivity : AppCompatActivity() {

    lateinit var mCardViewModel: CardViewModel

    private lateinit var binding: ActivityMainBinding
    private val adapterCards by lazy { CardAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvCards.adapter = adapterCards


        mCardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        setupListeners()
        visibleLoading()
        setupObservers()
    }

    private fun visibleLoading() {
        binding.includeStateLoading.clLoadingState.visibility = View.VISIBLE
    }

    private fun goneLoading() {
        binding.includeStateLoading.clLoadingState.visibility = View.GONE
    }

    private fun setupObservers() {
        mCardViewModel.getAllData.observe(this, {
            mCardViewModel.checkDatabaseEmpty(it)
            updateList(it)
            goneLoading()
        })
    }

    private fun setupListeners() {
        binding.fabAdd.setOnClickListener {
            startActivityForResult(
                Intent(this, AddCardActivity::class.java), CREATE_NEW_CARD
            )
        }

        adapterCards.listenerEdit = {
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra(AddCardActivity.CARD_ID, it.id)
            startActivityForResult(intent, UPDATE_CARD)
            goneLoading()
        }

        adapterCards.listenerDelete = {
            visibleLoading()
            mCardViewModel.deleteCard(it)
        }

    }

    private fun updateList(list: List<Card>) {
        if (list.isEmpty()) {
            binding.includeState.emptyState.visibility = View.VISIBLE
            binding.tvName.visibility = View.GONE

        } else {
            binding.includeState.emptyState.visibility = View.GONE
            binding.tvName.visibility = View.VISIBLE

        }

        adapterCards.submitList(list)
        adapterCards.notifyDataSetChanged()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //visibleLoading()

        when {
            requestCode == CREATE_NEW_CARD && resultCode == Activity.RESULT_OK -> {
                feedBackTaskActions(resources.getString(R.string.label_new_card_success))
                goneLoading()
            }

            requestCode == UPDATE_CARD && resultCode == Activity.RESULT_OK -> {
                feedBackTaskActions(resources.getString(R.string.label_update_card_success))
                goneLoading()
            }
        }

        updateList(mCardViewModel.getAllData.value as List<Card>)
    }

    //Feedback actions
    private fun feedBackTaskActions(msg: String) {
        val snackbar = Snackbar.make(
            this, binding.tvName,
            msg, Snackbar.LENGTH_LONG
        )
        snackbar.show()
    }
    companion object {
        private const val CREATE_NEW_CARD = 1000
        private const val UPDATE_CARD = 1001
    }
}