package com.aya.client_socket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.socket.client.IO
import io.socket.client.Socket
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import com.aya.client_socket.databinding.ActivityMainBinding
import org.json.JSONException

import org.json.JSONObject

import io.socket.emitter.Emitter




class MainActivity : AppCompatActivity() {

    private val mSocket: Socket by lazy {
        IO.socket("http://chat.socket.io")
    }
    private lateinit var binding:ActivityMainBinding

    //https://socket.io/blog/native-socket-io-and-android/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mSocket.connect()

        binding.sendOrder.setOnClickListener {
            attemptSend()
        }
    }

    private fun attemptSend() {
        val message: String = binding.order.getText().toString().trim()
        if (TextUtils.isEmpty(message)) {
            return
        }
        binding.order.setText("")
        mSocket.emit("new message", message)
    }


    override fun onDestroy() {
        super.onDestroy()
        mSocket.disconnect()
    }
}