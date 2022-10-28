package com.example.updatinglanguagedynamically

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.fasterxml.jackson.databind.ObjectMapper
import dev.b3nedikt.restring.Restring
import dev.b3nedikt.reword.Reword.reword
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.HashMap

class MainActivity : BaseActivity() {

    lateinit var btnEnglish: AppCompatButton
    lateinit var btnHindi: AppCompatButton
    lateinit var btnPunjabi: AppCompatButton
    lateinit var englishLocale: Locale
    lateinit var hindiLocale: Locale
    lateinit var punjabiLocale: Locale
    var englishStringsMap: HashMap<String, String> = HashMap()
    var hindiStringsMap: HashMap<String, String> = HashMap()
    var punjabiStringsMap: HashMap<String, String> = HashMap()

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnEnglish = findViewById(R.id.btn_english)
        btnHindi = findViewById(R.id.btn_hindi)
        btnPunjabi = findViewById(R.id.btn_punjabi)

        configureLocales()
        updateStringsHashmap()

        btnEnglish.setOnClickListener {
            updateAppLanguage("en")
        }

        btnHindi.setOnClickListener {
            updateAppLanguage("hi")
        }

        btnPunjabi.setOnClickListener {
            updateAppLanguage("pun")
        }
    }

    private fun configureLocales() {
        englishLocale = Locale("en")
        hindiLocale = Locale("hi")
        punjabiLocale = Locale("pun")
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun updateStringsHashmap() {
        getLocalStringJsonHashmap("en").forEach {
            englishStringsMap[it.key] = it.value
        }
        getLocalStringJsonHashmap("hi").forEach {
            hindiStringsMap[it.key] = it.value
        }
        getLocalStringJsonHashmap("pun").forEach {
            punjabiStringsMap[it.key] = it.value
        }
    }

    private fun updateAppLanguage(language: String) {
        when (language) {
            "en" -> {
                Restring.putStrings(englishLocale, englishStringsMap)
                Restring.locale = englishLocale
            }
            "hi" -> {
                Restring.putStrings(hindiLocale, hindiStringsMap)
                Restring.locale = hindiLocale
            }
            else -> {
                Restring.putStrings(punjabiLocale, punjabiStringsMap)
                Restring.locale = punjabiLocale
            }
        }
        updateView()
    }

    private fun updateView() {
        val rootView: View = window.decorView.findViewById(android.R.id.content)
        reword(rootView)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun getLocalStringJsonHashmap(language: String): HashMap<String, String> {
        val listTypeJson: HashMap<String, String> = HashMap()
        try {
            applicationContext.assets.open("$language.json").use { inputStream ->
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                val jsonString = String(buffer, StandardCharsets.UTF_8)
                JsonHelper().getFlattenedHashmapFromJsonForLocalization(
                    "",
                    ObjectMapper().readTree(jsonString),
                    listTypeJson
                )
            }
        } catch (exception: IOException) {
        }
        return listTypeJson
    }

}