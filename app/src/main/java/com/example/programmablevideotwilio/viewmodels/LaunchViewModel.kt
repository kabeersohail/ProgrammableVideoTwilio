package com.example.programmablevideotwilio.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.programmablevideotwilio.utils.RoomConnectionRequest

class LaunchViewModel: ViewModel() {

    private val _roomConnectionRequest: MutableLiveData<RoomConnectionRequest> = MutableLiveData()
    fun observeRoomConnectionRequest(): LiveData<RoomConnectionRequest> = _roomConnectionRequest

    private val _screenShareRequest: MutableLiveData<Boolean> = MutableLiveData()
    fun observeScreenShareRequest(): LiveData<Boolean> = _screenShareRequest

    private val _publishDataTrack: MutableLiveData<Boolean> = MutableLiveData()
    fun observePublishDataTrack(): LiveData<Boolean> = _publishDataTrack

    private val _roomStatus: MutableLiveData<Boolean> = MutableLiveData()
    fun observeRoomStatus(): LiveData<Boolean> = _roomStatus

    private val _sendMessage: MutableLiveData<Boolean> = MutableLiveData()
    fun observeSendMessage(): LiveData<Boolean> = _sendMessage

    fun connectToRoom() = _roomConnectionRequest.postValue(RoomConnectionRequest.Connect)

    fun disconnectFromRoom() = _roomConnectionRequest.postValue(RoomConnectionRequest.Disconnect)

    fun screenShare() = _screenShareRequest.postValue(true)

    fun publishDataTrack() = _publishDataTrack.postValue(true)

    fun sendMessage() = _sendMessage.postValue(true)

    fun roomConnectionStatus() = _roomStatus.postValue(true)

}