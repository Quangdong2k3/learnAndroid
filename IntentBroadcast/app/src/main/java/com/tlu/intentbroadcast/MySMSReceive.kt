package com.tlu.intentbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.telephony.SmsMessage.createFromPdu
import android.widget.Toast
import java.util.Objects

class MySMSReceive : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (context != null && intent != null) {
            processReceive(context, intent)
        }
    }

    private fun processReceive(context: Context, intent: Intent) {
        val extras = intent.extras
        var message = ""
        var body = ""
        var address = ""
        if (extras != null) {
            val smsExtra = extras.get("pdus") as Array<*>
            for (i in smsExtra.indices) {
                val sms = createFromPdu(smsExtra[i] as ByteArray)
                body = sms.messageBody
                address = sms.originatingAddress.toString()
                message += "Có 1 tin nhắn từ $address\n$body vừa gởi đến"
            }
            // Hiển thị Toast
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}