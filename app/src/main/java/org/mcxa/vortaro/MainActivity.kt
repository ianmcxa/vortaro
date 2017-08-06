package org.mcxa.vortaro

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    // tag for log methods
    val TAG = "Main_Activity"
    var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // set up the action bar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val wordAdapter = WordAdapter(this)
        dbHelper = DatabaseHelper(this)

        word_view.apply{
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        search_text.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    loading_spinner.visibility = View.VISIBLE
                    dbHelper?.search(s.toString(), word_view.adapter as WordAdapter)
                    loading_spinner.visibility = View.GONE
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.about -> {
                val i = Intent(this, AboutActivity::class.java)
                startActivity(i) // brings up the second activity
                return true
            }
        }

        return false
    }

    override fun onDestroy() {
        dbHelper?.close()
        super.onDestroy()
    }
}