package com.proyecto.volley

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var userText: EditText? = null
    var titleText: EditText? = null
    var bodyText: EditText? = null

    var getButton: Button? = null
    var sendButton: Button? = null
    var updateButton: Button? = null
    var deleteButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        userText = findViewById(R.id.userText)
        titleText = findViewById(R.id.titleText)
        bodyText = findViewById(R.id.bodyText)

        getButton = findViewById(R.id.getButton)
        sendButton = findViewById(R.id.sendButton)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)


        getButton?.setOnClickListener {
            getPost()
        }

        sendButton?.setOnClickListener {
            sendPost()
        }

        updateButton?.setOnClickListener {
            updatePost()
        }

        deleteButton?.setOnClickListener {
            deletePost()
        }

    }

    private fun getPost() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts/1"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    userText?.setText(jsonObject.getString("userId"))
                    titleText?.setText(jsonObject.getString("title"))
                    bodyText?.setText(jsonObject.getString("body"))
                    Toast.makeText(this, "Response: Se Obtuvo el post", Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
            },
            { error ->
                // Handle the error
                Log.e("Volley", "Error: ${error.message}")
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(stringRequest)
    }

    private fun sendPost() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts"
        val stringRequest = object : StringRequest(
            Method.POST, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    userText?.setText(jsonObject.getString("userId"))
                    titleText?.setText(jsonObject.getString("title"))
                    bodyText?.setText(jsonObject.getString("body"))
                } catch (e: JSONException) {
                    throw RuntimeException(e)
                }
                val jsonObject = JSONObject(response)
                Toast.makeText(this, "Response: $response", Toast.LENGTH_LONG).show()
                Log.d("Volley", "Response: $response")
            },
            { error ->
                // Handle the error
                Log.e("Volley", "Error: ${error.message}")
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["userId"] = userText?.text.toString()
                params["title"] = titleText?.text.toString()
                params["body"] = bodyText?.text.toString()
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun updatePost () {
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val stringRequest = object : StringRequest(
            Method.PUT, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.has("userId")) {
                        userText?.setText(jsonObject.getString("userId"))
                    }
                    if (jsonObject.has("title")) {
                        titleText?.setText(jsonObject.getString("title"))
                    }
                    if (jsonObject.has("body")) {
                        bodyText?.setText(jsonObject.getString("body"))
                    }
                    Toast.makeText(this, "Response: $response", Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    Log.e("Volley", "Error: ${e.message}")
                }
                Log.d("Volley", "Response: $response")
            },
            { error ->
                // Handle the error
                Log.e("Volley", "Error: ${error.message}")
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params.put("userId", "1111111111")
                return params
            }
        }
        queue.add(stringRequest)
    }

    private fun deletePost() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://jsonplaceholder.typicode.com/posts/1"
        val stringRequest = StringRequest(
            Request.Method.DELETE, url,
            { response ->
                try {
                    val jsonObject = JSONObject(response)
                    if (jsonObject.has("userId")) {
                        userText?.setText(jsonObject.getString("userId"))
                    }
                    if (jsonObject.has("title")) {
                        titleText?.setText(jsonObject.getString("title"))
                    }
                    if (jsonObject.has("body")) {
                        bodyText?.setText(jsonObject.getString("body"))
                    }
                    Toast.makeText(this, "Response: Se elimino", Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    Log.e("Volley", "Error: ${e.message}")
                }
                Log.d("Volley", "Response: $response")
            },
            { error ->
                // Handle the error
                Log.e("Volley", "Error: ${error.message}")
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(stringRequest)
    }


}
