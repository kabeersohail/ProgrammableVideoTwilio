package com.example.programmablevideotwilio.utils

sealed class RoomConnectionRequest{
    object Connect : RoomConnectionRequest()
    object Disconnect: RoomConnectionRequest()
}
