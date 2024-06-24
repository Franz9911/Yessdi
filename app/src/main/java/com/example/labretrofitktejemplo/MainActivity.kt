package com.example.labretrofitktejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.appcompat.widget.Toolbar
import com.example.labretrofitktejemplo.DatosSecion.clearUserSession


class MainActivity : AppCompatActivity(),ComunicacionFragment {
    var tiendaF: tiendaF?=null;
    var inicioF: inicioF?=null; //es registro
    var perfilF: perfilF?=null;
    var miBibliotecaF: miBibliotecaF?=null;
    var loguinF:loguinF?=null;//en el menu es inicio de sesion
    lateinit var  retrofit:Retrofit;
    lateinit var ConecxionAPI:ClientAPI;
    var nombreU:String="";
    var contrasenhaU:String="";
    var iduser:String="0";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tiendaF=tiendaF( );
        inicioF= inicioF();
        perfilF= perfilF();
        miBibliotecaF= miBibliotecaF();
        loguinF= loguinF();


        val toolbar = findViewById<Toolbar>(R.id.includeToolbar)
        setSupportActionBar(toolbar)

         retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.7/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

         ConecxionAPI = retrofit.create(ClientAPI::class.java)

        // Verificar si hay una sesión guardada y navegar al fragmento correspondiente
        val (savedName, savedPassword) = DatosSecion.getUserSession(this)
        if (savedName != null && savedPassword != null) {
            //iniciarSesion(savedName, savedPassword)
            Log.i("pancho","as "+savedPassword.toString())
            verContrasenha()
            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, tiendaF!!)
            transaction.commit()
        } else {
            if (savedInstanceState == null) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragmentContainer, inicioF!!)
                transaction.commit()
            }
        }

    }

    override fun cambiarFagmentPerfil() {
        Log.i("reserva","pasamons por el listener")
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.fragmentContainer, perfilF!!)
        transaction.commit()
        Toast.makeText(this,"la reserva fue borrada",Toast.LENGTH_SHORT).show()
    }
    override fun iniciarSesion(nombre: String, contrasenha: String) {
        nombreU=nombre;
        contrasenhaU=contrasenha;

        Toast.makeText(this, "Iniciando sesión para $nombreU", Toast.LENGTH_LONG).show()
        Log.d("pancho","estamos iniciando cesioncon  $nombreU");
        //val perfilF= perfilF!!.newInstance(contrasenhaU);
        DatosSecion.saveUserSession(this, nombre, contrasenha)
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.fragmentContainer, perfilF!!)
        transaction.commit()
    }
    override fun verContrasenha():String{
        val (savedName, savedPassword) = DatosSecion.getUserSession(this)
    return savedPassword.toString();
    }
    override fun verNombre():String{
        val (savedName, savedPassword) = DatosSecion.getUserSession(this)
        return savedName.toString();
    }
    override fun verIdentificador(): String {
        return iduser;
        TODO("Not yet implemented")
    }
    override fun modificarIdentificador(i:String){
        iduser=i;

    }

    override fun borrardatosCession() {
        clearUserSession(this)
        nombreU="";
        contrasenhaU="";
        iduser="";
        val transaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.fragmentContainer, inicioF!!)
        transaction.commit()
        //Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
    }


    /*private fun eliminarReserva() {
        Log.i("reserva","en la fun eliminar user")
        //Log.i("reserva","cadena "+idReserva)
        val callEliminar: Call<Void> = ConecxionAPI!!.eliminarReserva("3" )
        callEliminar.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.i("reserva", "pedido eliminado")
                    //cerrarsecion();
                    //Toast.makeText(context,"Tu cuenta fe eliminada ", Toast.LENGTH_LONG).show();
                } else {
                    Log.e("reserva", "Error al eliminar: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("reserva", "Error en la llamada de eliminación: ${t.message}")
            }
        })
    }*/

    /*fun listar(){
        val listarCall: Call<List<UsersLibreria>> = ConecxionAPI.getPaises2()
        listarCall.enqueue(object : Callback<List<UsersLibreria>> {
            override fun onResponse(call: Call<List<UsersLibreria>>, response: Response<List<UsersLibreria>>) {
                if (response.isSuccessful) {
                    val paisesList: List<UsersLibreria>? = response.body()
                    val paisesIterator: Iterator<UsersLibreria>? = paisesList?.iterator()
                    var usuarios = ""
                    while (paisesIterator?.hasNext() == true) {
                        val nombreU: UsersLibreria? = paisesIterator.next()
                        usuarios += "${nombreU?.nombre}\n"
                    }

                }
                Log.i("pancho","conexion exitosa")
                Log.i ("pancho",""+response)
            }

            override fun onFailure(call: Call<List<UsersLibreria>>, t: Throwable) {
                // Handle failure
                Log.i("TEOTEO","Fallo en la conexion"+t.message+"\n"+t.stackTrace)
                Log.d("TEOTEO", Log.getStackTraceString(t));
                //jsonText.text = Log.getStackTraceString(t)

            }
        })
    }*/




    /*fun modificar(p:Paises){
        //modificar
        val callAdd:Call<Paises> = ConecxionAPI.modificar(p)
        callAdd.enqueue(object : Callback<Paises>{
            override fun onResponse(p0: Call<Paises>, p1: Response<Paises>) {
                if(p1.isSuccessful){
                    Log.i("TEOTEO","Actualizado correctamente")
                }else{
                    Log.i("TEOTEO","no actualizado "+p1.body())
                }


            }

            override fun onFailure(p0: Call<Paises>, p1: Throwable) {
                Log.i("TEOTEO","Se produjo un error en la conexión al API")
            }


        })

    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val infla = menuInflater
        infla.inflate(R.menu.menutoolbar,menu)


        //val item = menu?.findItem(R.id.itemBuscar)

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        if(itemId == R.id.tienda){

            //Toast.makeText(applicationContext,"Hizo clic en el item tienda", Toast.LENGTH_SHORT).show()
            Toast.makeText(this,"Abriendo tienda",Toast.LENGTH_SHORT).show()
            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, tiendaF!!)
            transaction.commit()
        }else if(itemId == R.id.Registro){
            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, inicioF!!)
            transaction.commit()
        }else if (itemId==R.id.perfil){
            Toast.makeText(this,"Abriendo perfil",Toast.LENGTH_SHORT).show()

            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, perfilF!!)
            transaction.commit()
        }else if (itemId==R.id.MiBiblioteca){
            Toast.makeText(this,"Abriendo miBiblio",Toast.LENGTH_SHORT).show()

            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, miBibliotecaF!!)
            transaction.commit()
        }else if (itemId==R.id.login){
            Toast.makeText(this,"Abriendo inicio de sesion",Toast.LENGTH_SHORT).show()

            val transaction = getSupportFragmentManager().beginTransaction()
            transaction.replace(R.id.fragmentContainer, loguinF!!)
            transaction.commit()
        }

        return super.onOptionsItemSelected(item)
    }
    /*
    El propósito principal de provideBookAPI() es permitir que otros componentes (como los Fragments)
    accedan a la instancia de ClientAPI que se ha configurado en MainActivity.
    Esto es útil porque garantiza que todas las llamadas a la API se hagan a través de la misma
    instancia, manteniendo la consistencia y facilitando la gestión de las conexiones.
    * */
    fun ConectarAPI(): ClientAPI {
        return ConecxionAPI
    }

}
