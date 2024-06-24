package com.example.labretrofitktejemplo

interface ComunicacionFragment {
        fun iniciarSesion(nombre: String, contrasenha: String)
        fun verContrasenha():String;
        fun verNombre():String;
        fun borrardatosCession();
        fun verIdentificador():String;
        fun modificarIdentificador(i:String)
        fun cambiarFagmentPerfil()


}