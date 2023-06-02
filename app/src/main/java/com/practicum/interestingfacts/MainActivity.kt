package com.practicum.interestingfacts

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

const val FACTS_PREFERENCES = "facts_preferences"
const val FACTS_LIST_KEY = "key_for_facts_list"
const val NEW_FACT_KEY = "key_for_new_fact"

class MainActivity : AppCompatActivity() {

    private val adapter = FactAdapter()

    // метод дессириализует массив объектов Fact (в Shared Preference они хранятся в виде json строки)
    private fun createFactsListFromJson(json: String): Array<Fact> {
        return Gson().fromJson(json, Array<Fact>::class.java)
    }

    // метод серриализует массив объектов Fact (переводит в формат json)
    private fun createJsonFromFactsList(facts: Array<Fact>): String {
        return Gson().toJson(facts)
    }

    // метод серриализует экземпляр класса Fact
    private fun createJsonFromFact(fact: Fact): String {
        return Gson().toJson(fact)
    }

    // метод дессериализует экземпляр класса Fact
    private fun createFactFromJson(json: String): Fact {
        return Gson().fromJson(json, Fact::class.java)
    }

    private lateinit var saveButton: Button
    private lateinit var editFact: EditText
    private lateinit var editSourse: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveButton = findViewById(R.id.saveButton)
        editFact = findViewById(R.id.editTextFact)
        editSourse = findViewById(R.id.editTextSource)
        recyclerView = findViewById(R.id.rvFacts)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val sharedPreferences = getSharedPreferences(FACTS_PREFERENCES, MODE_PRIVATE)

        val facts = sharedPreferences.getString(FACTS_LIST_KEY, null) // считывание массива из Shared Preference (если хранилище пустое, ставим null)
        if (facts != null) {
            adapter.facts.addAll(createFactsListFromJson(facts)) //дессиарилизованные заметки передаются в адаптер
        }

        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == NEW_FACT_KEY) {
                val fact = sharedPreferences?.getString(NEW_FACT_KEY, null)
                if (fact != null) {
                    adapter.facts.add(0, createFactFromJson(fact))
                    adapter.notifyItemInserted(0)
                }
            }
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)

        saveButton.setOnClickListener {
            val fact = Fact(editFact.text.toString(), editSourse.text.toString())
            sharedPreferences.edit()
                .putString(NEW_FACT_KEY, createJsonFromFact(fact))
                .apply()
        }


//
//        saveButton.setOnClickListener {
//            val fact = Fact(editFact.text.toString(), editSourse.text.toString())
//            sharedPreference.edit()
//                .putString(NEW_FACT_KEY, createJsonFromFact(fact))
//                .apply()
//
//            if (editFact.text.toString().isNotEmpty() and editSourse.text.toString().isNotEmpty()) {
//
//            }
//        }
    }

    // в жизненном цикле onStop() происходит сохранение данных в Shared Preference
    override fun onStop() {
        super.onStop()

        val sharedPreferences = getSharedPreferences(FACTS_PREFERENCES, MODE_PRIVATE)
        sharedPreferences.edit()
            .putString(FACTS_LIST_KEY, createJsonFromFactsList(adapter.facts.toTypedArray())) // отдает данные adapter, а createJsonFromFactsList() их серриализует
            .apply()
    }
}