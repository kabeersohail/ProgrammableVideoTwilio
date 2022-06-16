package com.example.programmablevideotwilio.listeners

import android.content.Context
import android.widget.Toast
import com.twilio.video.*

class LocalParticipantListener(private val context: Context): LocalParticipant.Listener {
    override fun onAudioTrackPublished(
        localParticipant: LocalParticipant,
        localAudioTrackPublication: LocalAudioTrackPublication
    ) {
        TODO("Not yet implemented")
    }

    override fun onAudioTrackPublicationFailed(
        localParticipant: LocalParticipant,
        localAudioTrack: LocalAudioTrack,
        twilioException: TwilioException
    ) {
        TODO("Not yet implemented")
    }

    override fun onVideoTrackPublished(
        localParticipant: LocalParticipant,
        localVideoTrackPublication: LocalVideoTrackPublication
    ) {
        TODO("Not yet implemented")
    }

    override fun onVideoTrackPublicationFailed(
        localParticipant: LocalParticipant,
        localVideoTrack: LocalVideoTrack,
        twilioException: TwilioException
    ) {
        TODO("Not yet implemented")
    }

    override fun onDataTrackPublished(
        localParticipant: LocalParticipant,
        localDataTrackPublication: LocalDataTrackPublication
    ) {
        // The data track has been published and is ready for use

        Toast.makeText(context, "Data track is published",Toast.LENGTH_SHORT).show()
        val message: String = "Hi, This is Sohail"
        localDataTrackPublication.localDataTrack.send(message)
    }

    override fun onDataTrackPublicationFailed(
        localParticipant: LocalParticipant,
        localDataTrack: LocalDataTrack,
        twilioException: TwilioException
    ) {
        TODO("Not yet implemented")
    }
}