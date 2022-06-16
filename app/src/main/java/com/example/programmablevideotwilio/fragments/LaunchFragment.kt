package com.example.programmablevideotwilio.fragments

import android.app.Activity
import android.content.Context
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.programmablevideotwilio.R
import com.example.programmablevideotwilio.databinding.FragmentLaunchBinding
import com.example.programmablevideotwilio.listeners.LocalParticipantListener
import com.example.programmablevideotwilio.listeners.RoomListener
import com.example.programmablevideotwilio.managers.ScreenCaptureManager
import com.example.programmablevideotwilio.utils.Constants.ACCESS_TOKEN_ACER
import com.example.programmablevideotwilio.utils.Constants.ACCESS_TOKEN_TECHNO
import com.example.programmablevideotwilio.utils.RoomConnectionRequest
import com.example.programmablevideotwilio.utils.TAG
import com.example.programmablevideotwilio.viewmodels.LaunchViewModel
import com.twilio.video.*
import java.nio.ByteBuffer


class LaunchFragment : Fragment() {

    private lateinit var screenCaptureManager: ScreenCaptureManager
    private lateinit var binding: FragmentLaunchBinding
    private var name = "Salman"
    private var screenCapture: ScreenCapturer? = null
    private lateinit var localVideoView: VideoView
    private var screenVideoTrack: LocalVideoTrack? = null
    private var remoteDataTrack: RemoteDataTrack? = null
    private var localDataTrack: LocalDataTrack? = null

    lateinit var room: Room
    lateinit var connectOptions: ConnectOptions


    private val viewModel: LaunchViewModel by lazy {
        ViewModelProvider(this).get(LaunchViewModel::class.java)
    }

    private val remoteParticipantListener: RemoteParticipant.Listener = object : RemoteParticipant.Listener{
        override fun onAudioTrackPublished(
            remoteParticipant: RemoteParticipant,
            remoteAudioTrackPublication: RemoteAudioTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onAudioTrackUnpublished(
            remoteParticipant: RemoteParticipant,
            remoteAudioTrackPublication: RemoteAudioTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onAudioTrackSubscribed(
            remoteParticipant: RemoteParticipant,
            remoteAudioTrackPublication: RemoteAudioTrackPublication,
            remoteAudioTrack: RemoteAudioTrack
        ) {
            TODO("Not yet implemented")
        }

        override fun onAudioTrackSubscriptionFailed(
            remoteParticipant: RemoteParticipant,
            remoteAudioTrackPublication: RemoteAudioTrackPublication,
            twilioException: TwilioException
        ) {
            TODO("Not yet implemented")
        }

        override fun onAudioTrackUnsubscribed(
            remoteParticipant: RemoteParticipant,
            remoteAudioTrackPublication: RemoteAudioTrackPublication,
            remoteAudioTrack: RemoteAudioTrack
        ) {
            TODO("Not yet implemented")
        }

        override fun onVideoTrackPublished(
            remoteParticipant: RemoteParticipant,
            remoteVideoTrackPublication: RemoteVideoTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onVideoTrackUnpublished(
            remoteParticipant: RemoteParticipant,
            remoteVideoTrackPublication: RemoteVideoTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onVideoTrackSubscribed(
            remoteParticipant: RemoteParticipant,
            remoteVideoTrackPublication: RemoteVideoTrackPublication,
            remoteVideoTrack: RemoteVideoTrack
        ) {
            TODO("Not yet implemented")
        }

        override fun onVideoTrackSubscriptionFailed(
            remoteParticipant: RemoteParticipant,
            remoteVideoTrackPublication: RemoteVideoTrackPublication,
            twilioException: TwilioException
        ) {
            TODO("Not yet implemented")
        }

        override fun onVideoTrackUnsubscribed(
            remoteParticipant: RemoteParticipant,
            remoteVideoTrackPublication: RemoteVideoTrackPublication,
            remoteVideoTrack: RemoteVideoTrack
        ) {
            TODO("Not yet implemented")
        }

        override fun onDataTrackPublished(
            remoteParticipant: RemoteParticipant,
            remoteDataTrackPublication: RemoteDataTrackPublication
        ) {
            remoteDataTrack = remoteDataTrackPublication.remoteDataTrack
            remoteDataTrack?.setListener(remoteDataTrackListener)
        }

        override fun onDataTrackUnpublished(
            remoteParticipant: RemoteParticipant,
            remoteDataTrackPublication: RemoteDataTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onDataTrackSubscribed(
            remoteParticipant: RemoteParticipant,
            remoteDataTrackPublication: RemoteDataTrackPublication,
            remoteDataTrack: RemoteDataTrack
        ) {
            Toast.makeText(requireContext(),"${remoteParticipant.identity} has subscribed",Toast.LENGTH_SHORT).show()
        }

        override fun onDataTrackSubscriptionFailed(
            remoteParticipant: RemoteParticipant,
            remoteDataTrackPublication: RemoteDataTrackPublication,
            twilioException: TwilioException
        ) {
            TODO("Not yet implemented")
        }

        override fun onDataTrackUnsubscribed(
            remoteParticipant: RemoteParticipant,
            remoteDataTrackPublication: RemoteDataTrackPublication,
            remoteDataTrack: RemoteDataTrack
        ) {
            TODO("Not yet implemented")
        }

        override fun onAudioTrackEnabled(
            remoteParticipant: RemoteParticipant,
            remoteAudioTrackPublication: RemoteAudioTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onAudioTrackDisabled(
            remoteParticipant: RemoteParticipant,
            remoteAudioTrackPublication: RemoteAudioTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onVideoTrackEnabled(
            remoteParticipant: RemoteParticipant,
            remoteVideoTrackPublication: RemoteVideoTrackPublication
        ) {
            TODO("Not yet implemented")
        }

        override fun onVideoTrackDisabled(
            remoteParticipant: RemoteParticipant,
            remoteVideoTrackPublication: RemoteVideoTrackPublication
        ) {
            TODO("Not yet implemented")
        }

    }

    private val remoteDataTrackListener: RemoteDataTrack.Listener = object : RemoteDataTrack.Listener{
        override fun onMessage(remoteDataTrack: RemoteDataTrack, messageBuffer: ByteBuffer) {
            TODO("Not yet implemented")
        }

        override fun onMessage(remoteDataTrack: RemoteDataTrack, message: String) {
            Log.d(TAG,message)
            Toast.makeText(requireContext(), message,Toast.LENGTH_SHORT).show()
        }

    }

    private val onScreenCaptureResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            Log.d(TAG, "Triggered screenCapturePermissionResult")

            if (activityResult.resultCode == Activity.RESULT_OK) {

                activityResult.data?.let { intent ->
                    screenCapture = ScreenCapturer(
                        requireContext(),
                        activityResult.resultCode,
                        intent,
                        screenCaptureListener
                    )
                    startScreenCapture()
                }
            }
        }

    private val screenCaptureListener: ScreenCapturer.Listener = object : ScreenCapturer.Listener {
        override fun onScreenCaptureError(errorDescription: String) {
            Log.e(TAG, "Screen capture error: $errorDescription")
            stopScreenCapture()
            Toast.makeText(requireContext(), R.string.screen_capture_error, Toast.LENGTH_LONG)
                .show()
        }

        override fun onFirstFrameAvailable() {
            Log.d(TAG, "First frame from screen capture is available")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLaunchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        localVideoView = binding.localVideo
        screenCaptureManager = ScreenCaptureManager(requireContext())
        observe()
    }

    private fun observe() {
        viewModel.apply {

            observeRoomStatus().observe(viewLifecycleOwner){ request ->
                when(request){
                    true -> {
                        if(::room.isInitialized){
                            Toast.makeText(requireContext(),"${room.state}",Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(),"Not connected to any room yet",Toast.LENGTH_SHORT).show()
                        }
                    }
                    false -> {}
                }

            }

            observeSendMessage().observe(viewLifecycleOwner){
                localDataTrack?.let {
                    it.send("Hello bro")
                }
            }

            observeRoomConnectionRequest().observe(viewLifecycleOwner) { roomConnectionRequest ->
                when (roomConnectionRequest) {
                    RoomConnectionRequest.Connect -> connectToRoom(name)

                    RoomConnectionRequest.Disconnect -> this@LaunchFragment.disconnectFromRoom()
                }
            }

            observeScreenShareRequest().observe(viewLifecycleOwner) { screenShareRequest ->
                when (screenShareRequest) {
                    true -> {

                        if (Build.VERSION.SDK_INT >= 29) screenCaptureManager.startForeground()

                        val mediaProjectionManager: MediaProjectionManager =
                            requireContext().getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager

                        if (screenCapture == null) requestScreenCapturePermission(
                            mediaProjectionManager
                        ) else startScreenCapture()


                    }
                    false -> {
                        if (Build.VERSION.SDK_INT >= 29) screenCaptureManager.endForeground()
                        stopScreenCapture()
                        Toast.makeText(
                            requireContext(),
                            "Screen sharing stopped",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            observePublishDataTrack().observe(viewLifecycleOwner){
                val localParticipant = room.localParticipant
                localParticipant?.let {
                    localDataTrack?.let { localDataTrack ->
                        localParticipant.publishTrack(localDataTrack)
                        localParticipant.setListener(LocalParticipantListener(requireContext()))
                    }
                }

            }

        }
    }

    private fun connectToRoom(name: String) {

        localDataTrack = LocalDataTrack.create(requireContext())

        if(::room.isInitialized){
            when(room.state){
                Room.State.CONNECTING -> Toast.makeText(requireContext(),"Connecting",Toast.LENGTH_SHORT).show()
                Room.State.CONNECTED -> Toast.makeText(requireContext(),"Already connected",Toast.LENGTH_SHORT).show()
                Room.State.RECONNECTING -> Toast.makeText(requireContext(),"Reconnecting",Toast.LENGTH_SHORT).show()
                Room.State.DISCONNECTED -> {
                    localDataTrack?.let { localDataTrack ->
                        room = Video.connect(
                            requireContext(),
                            connectOptions,
                            RoomListener(requireContext(), localDataTrack)
                        )
                    }
                }
            }
        } else {

            localDataTrack?.let { localDataTrack ->
                connectOptions = ConnectOptions.Builder(ACCESS_TOKEN_TECHNO)
                    .roomName(name)
                    .dataTracks(listOf(localDataTrack))
                    .build()
                room = Video.connect(requireContext(), connectOptions, RoomListener(requireContext(), localDataTrack))
            }
        }
    }

    private fun disconnectFromRoom() = when(room.state) {
            Room.State.DISCONNECTED -> Toast.makeText(requireContext(),"Already disconnected",Toast.LENGTH_SHORT).show()
            else -> room.disconnect()
        }

    private fun requestScreenCapturePermission(mediaProjectionManager: MediaProjectionManager) =
        onScreenCaptureResult.launch(mediaProjectionManager.createScreenCaptureIntent())

    private fun startScreenCapture() {

        screenVideoTrack = LocalVideoTrack.create(
            requireContext(),
            true,
            screenCapture ?: kotlin.run {
                Log.e(TAG,"Screen capture is null")
                return
            })

        localVideoView.visibility = View.VISIBLE
        screenVideoTrack?.addSink(localVideoView)

    }

    private fun stopScreenCapture() {
        if (screenVideoTrack != null) {
            screenVideoTrack?.removeSink(localVideoView)
            screenVideoTrack?.release()
            screenVideoTrack = null
            localVideoView.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        if (screenVideoTrack != null) {
            screenVideoTrack?.release()
            screenVideoTrack = null
        }
        if (Build.VERSION.SDK_INT >= 29) {
            screenCaptureManager.unbindService()
        }
        super.onDestroy()
    }

}