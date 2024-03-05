package com.example.thingsboard2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.thingsboard2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accessToken = "YBpE7k4SWS60qjpV5BOh"
        val accessToken2 = "iVOEYloqAccdjTDcdEzo"
        val apiService = ApiServiceBuilder.buildService(ApiService::class.java)
        val handler = Handler(Looper.getMainLooper())

        var attributes2 = true
        var attributes12 = true
        val clientKeys = "attribute1,attribute2,attribute3"

        binding.button.setOnClickListener {
            handler.postDelayed(object : Runnable {
                override fun run() {
                    val attributesData = AttributesData("value${(1..10).random()}", attributes2, (1..100).random().toDouble())
                    val attributesData2 = AttributesData("value${(1..10).random()}", attributes12, (1..100).random().toDouble())
                    postAttributes(apiService, accessToken, attributesData)
                    postAttributes2(apiService, accessToken2, attributesData2)
                    getAttributes(apiService, accessToken, clientKeys)
                    getAttributes2(apiService, accessToken2, clientKeys)
                    handler.postDelayed(this, 1000)
                    attributes2 = !attributes2
                    attributes12 = !attributes12
                }
            }, 0)
            postAttributes(apiService, accessToken, AttributesData("value1", true, 1.0))
            postAttributes2(apiService, accessToken2, AttributesData("value2", false, 2.0))
            getAttributes(apiService, accessToken, clientKeys)
            getAttributes2(apiService, accessToken2, clientKeys)
        }
    }

    private fun postAttributes(apiService: ApiService, accessToken: String, attributesData: AttributesData) {
        val call = apiService.postAttributes(
            "${ApiService.BASE_URL}$accessToken/attributes",
            "application/json",
            attributesData
        )
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Request successful","${response.body()}")
                } else {
                    Log.d("Request failed","${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Request failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun postAttributes2(apiService: ApiService, accessToken: String, attributesData: AttributesData) {
        val call = apiService.postAttributes(
            "${ApiService.BASE_URL}$accessToken/attributes",
            "application/json",
            attributesData
        )
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Request successful","${response.body()}")
                } else {
                    Log.d("Request failed","${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Request failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAttributes(apiService: ApiService, accessToken: String, clientKeys: String) {
        val call = apiService.getAttributes(
            "${ApiService.BASE_URL}$accessToken/attributes",
            "application/json",
            clientKeys
        )
        call.enqueue(object : Callback<ClientAttributesResponse> {
            override fun onResponse(call: Call<ClientAttributesResponse>, response: Response<ClientAttributesResponse>) {
                if (response.isSuccessful) {
                    val clientAttributes = response.body()
                    binding.attribute1.text = clientAttributes?.client?.attribute1.toString()
                    binding.attribute2.text = clientAttributes?.client?.attribute2.toString()
                    binding.attribute3.text = clientAttributes?.client?.attribute3.toString()
                } else {
                    Log.d("Request failed","${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Request failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ClientAttributesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getAttributes2(apiService: ApiService, accessToken: String, clientKeys: String) {
        val call = apiService.getAttributes(
            "${ApiService.BASE_URL}$accessToken/attributes",
            "application/json",
            clientKeys
        )
        call.enqueue(object : Callback<ClientAttributesResponse> {
            override fun onResponse(call: Call<ClientAttributesResponse>, response: Response<ClientAttributesResponse>) {
                if (response.isSuccessful) {
                    val clientAttributes = response.body()
                    binding.attribute11.text = clientAttributes?.client?.attribute1.toString()
                    binding.attribute12.text = clientAttributes?.client?.attribute2.toString()
                    binding.attribute13.text = clientAttributes?.client?.attribute3.toString()
                } else {
                    Log.d("Request failed","${response.errorBody()?.string()}")
                    Toast.makeText(this@MainActivity, "Request failed: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ClientAttributesResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
