package com.example.programmablevideotwilio.listeners

import android.content.Context
import android.widget.Toast
import com.twilio.video.RemoteDataTrack
import java.nio.ByteBuffer

class RemoteDataTrackListener(private val context: Context): RemoteDataTrack.Listener {
    override fun onMessage(remoteDataTrack: RemoteDataTrack, messageBuffer: ByteBuffer) {
        TODO("Not yet implemented")    }

    override fun onMessage(remoteDataTrack: RemoteDataTrack, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}