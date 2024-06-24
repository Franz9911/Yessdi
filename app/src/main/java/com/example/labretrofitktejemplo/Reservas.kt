package com.example.labretrofitktejemplo

data class Reservas(
    var idReserva: Int,
    var idLibro: Int,
    var idUsuario : Int,
    var fechaReserva:String,
    var estado:String,
    var unidades:String,
    var costo:String,
    var fechaResecion:String,
    var imagenL:String,
    var titulo:String
)
