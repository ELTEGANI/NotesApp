package com.example.notesapp.feature_note.domain.util


sealed class NoteOrder(var orderType: OrderType){
    class Title(orderType: OrderType):NoteOrder(orderType)
    class Date(orderType: OrderType):NoteOrder(orderType)
    class Color(orderType: OrderType):NoteOrder(orderType)
}
