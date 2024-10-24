package com.example.app_vinilos_g17.data.broker

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RetrofitBroker {
    companion object{
        fun getRequest(onResponse:(resp:String)->Unit, onFailure:(resp:String)->Unit) {
            var r = RetrofitApi.retrofitService.getProperties()
            var p = r.enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        onFailure(t.message!!)
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        onResponse(response.body()!!)
                    }
                })
        }
    }
}