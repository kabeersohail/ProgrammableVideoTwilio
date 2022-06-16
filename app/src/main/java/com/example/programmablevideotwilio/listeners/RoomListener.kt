package com.example.programmablevideotwilio.listeners

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.programmablevideotwilio.utils.TAG
import com.twilio.video.*

class RoomListener(private val context: Context, private val localDataTrack: LocalDataTrack) : Room.Listener {
    override fun onConnected(room: Room) {
        Log.d(TAG, "Connected to ${room.name}")
        Toast.makeText(context, "Connected to ${room.name}", Toast.LENGTH_SHORT).show()

        // After connecting to a room, we want to publish our local data track
        val localParticipant = room.localParticipant
        localParticipant?.publishTrack(localDataTrack)

    }

    override fun onConnectFailure(room: Room, twilioException: TwilioException) {
        Toast.makeText(context, "Failed to join room: ${room.name}", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Failed to join room: ${room.name}")
    }

    override fun onReconnecting(room: Room, twilioException: TwilioException) {
        Toast.makeText(context, "Re-Connecting to ${room.name}", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Re-Connecting to ${room.name}")
    }

    override fun onReconnected(room: Room) {
        Toast.makeText(context, "Re-Connected to ${room.name}", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Re-Connected to ${room.name}")
    }

    override fun onDisconnected(room: Room, twilioException: TwilioException?) {
        Toast.makeText(context, "Room disconnected: ${room.name}", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Room disconnected: ${room.name}")
    }

    override fun onParticipantConnected(room: Room, remoteParticipant: RemoteParticipant) {
        Toast.makeText(context, "${remoteParticipant.identity} has joined the room", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "${remoteParticipant.identity} has joined the room")

        remoteParticipant.setListener(RemoteParticipantListener(context))

    }

    override fun onParticipantDisconnected(room: Room, remoteParticipant: RemoteParticipant) {
        Toast.makeText(context, "${remoteParticipant.identity} has left the room", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "${remoteParticipant.identity} has left the room")
    }

    override fun onRecordingStarted(room: Room) {
        Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Recording started")
    }

    override fun onRecordingStopped(room: Room) {
        Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "Recording stopped")
    }
}