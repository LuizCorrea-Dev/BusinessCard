package com.luizbcorrea.business_card.ui

import android.app.Activity
import android.graphics.Color
import android.graphics.MaskFilter
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.insert
import android.util.Log
import android.view.View
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import asm.asmtunis.com.mhcolorpicker.dialog.MHColorsDialog
import com.luizbcorrea.business_card.R
import com.luizbcorrea.business_card.database.models.Card
import com.luizbcorrea.business_card.database.models.CardViewModel
import com.luizbcorrea.business_card.databinding.ActivityAddCardBinding
import com.luizbcorrea.todolist.extensions.text
import kotlinx.android.synthetic.main.activity_add_card.*


class AddCardActivity: AppCompatActivity() {


    private lateinit var mCardViewModel: CardViewModel
    private lateinit var binding: ActivityAddCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAddCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mCardViewModel = ViewModelProvider(this).get(CardViewModel::class.java)

        setupListeners()
        val cardId = intent.getLongExtra(CARD_ID, 0)

        if (cardId > 0) {
            binding.btnNewCard.text = getString(R.string.label_button_update_card)
            binding.toolbar.title = getString(R.string.label_button_update_card)
        } else {
            binding.btnNewCard.text = getString(R.string.label_button_create_card)
            binding.toolbar.title = getString(R.string.label_button_create_card)
        }

        setupObservers(cardId)

        binding.tilName.requestFocus()
    }

    private fun setupListeners() {

        binding.btnNewCard.setOnClickListener {

            if (binding.tilName.text.isBlank()) {
                binding.tilName.editText?.error = resources
                    .getString(R.string.label_field_mandatory)
            } else {
                binding.tilName.editText?.error = null

                val card = Card(
                    id = intent.getLongExtra(CARD_ID, 0),
                    name = binding.tilName.editText?.text.toString(),
                    company = binding.tilCompany.editText?.text.toString(),
                    phone = binding.tilPhone.editText?.text.toString(),
                    email = binding.tilEmail.editText?.text.toString(),
                    colorSelected = binding.tilColor.text.toString(),
                    colorTxt = binding.tilColorTxt.text.toString()
                )

                mCardViewModel.insertCard(card)

                setResult(Activity.RESULT_OK)
                finish()

            }
        }

        // cor do card
        binding.btnPickerColor.setOnClickListener {
              MHColorsDialog(this).setColorListener { color, colorHex ->
                  val color_selected = "#"+colorHex.toString().toUpperCase().substring(2)
                  binding.tilColor.text = color_selected
                  binding.imageView.setColorFilter(
                      Color.parseColor(til_color.text))
              }.show()
        }
        if (binding.tilColor.text.isEmpty()) {
            binding.tilColor.text  = "#F6851F"
        } else {
            binding.tilColor.text
        }

        // cor do texto
        binding.tilColorTxt.text  = "#000000"
        binding.imageView2.setColorFilter(
            Color.parseColor("#000000")
        )
        switch1.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.tilColorTxt.text  = "#e3e4e6"
                binding.imageView2.setColorFilter(
                    Color.parseColor("#e3e4e6"))
            } else {
                binding.tilColorTxt.text  = "#000000"
                binding.imageView2.setColorFilter(

                    Color.parseColor("#000000"))
            }

            if (binding.tilColorTxt.text.isEmpty()) {
                binding.tilColorTxt.text = "#000000"
            } else {
                binding.tilColorTxt.text
            }
        }

        binding.btnCancelCard.setOnClickListener(){
            finish()
        }

    }

    private fun setupObservers(cardId: Long) {
        mCardViewModel.getAllData.observe(this, {
            mCardViewModel.checkDatabaseEmpty(it)

            mCardViewModel.findCardById(cardId)?.let { card ->
                binding.tilName.text = card.name
                binding.tilCompany.text = card.company
                binding.tilPhone.text = card.phone
                binding.tilEmail.text = card.email
                binding.tilColor.text = card.colorSelected
                binding.tilColorTxt.text = card.colorTxt
            }
        })
    }


    companion object {
        const val CARD_ID = "card_id"
    }
}
