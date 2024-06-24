package com.example.labretrofitktejemplo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [perfilF.newInstance] factory method to
 * create an instance of this fragment.
 */
class perfilF : Fragment() {
    private var contrasenha: String? = null
    private var nombre: String? = null
    private var identificador: Int=0;

    private var listener: ComunicacionFragment? =null;
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ComunicacionFragment) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    private var UserAPI: ClientAPI? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserAPI = (activity as MainActivity).ConectarAPI()
        arguments?.let {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contrasenha=listener?.verContrasenha();
        nombre=listener?.verNombre();


        val txtnombre: TextView = view.findViewById(R.id.nombre);
        val apellidop: TextView = view.findViewById(R.id.apellidop);
        val apellidom: TextView = view.findViewById(R.id.apellidom);
        val btnEliminarCuenta: Button=view.findViewById(R.id.EliminarCuenta)
        val btnCerrarCession: Button=view.findViewById(R.id.CerraSecion)

        obtenerUsuarioPorContrasenha(contrasenha.toString(),nombre.toString(),txtnombre,apellidop,apellidom );

        btnEliminarCuenta.setOnClickListener {
            Log.i("perfilF","user "+ contrasenha+" "+identificador)
            if(identificador!=null || identificador>0){
                Log.i("perfilF","if entramos" )
                eliminarUsuario()
            }
            else Log.i("perfilF","error en el if")
        }
        btnCerrarCession.setOnClickListener {
            cerrarsecion();
        }

    }
    private fun cerrarsecion(){
        listener?.borrardatosCession();
    }
    private fun obtenerUsuarioPorContrasenha(
        contrasenha: String?,nombre:String?, nombreTextView: TextView,
        apellidopTextView: TextView,apellidomTextView: TextView) {
        Log.i("perfilF","entramos en la funcion")
        if (contrasenha != null && nombre!=null) {
            Log.i("perfilF","pasamos el if de null" + contrasenha)
            val usuario =UsersLibreria(1, ""+nombre, "", "",""+contrasenha);

                    val callAdd: Call<UsersLibreria> = UserAPI!!.loguin(usuario)
                    callAdd.enqueue(object : Callback<UsersLibreria> {
                        override fun onResponse(p0: Call<UsersLibreria>, p1: Response<UsersLibreria>) {

                            if (p1.isSuccessful) {
                        val usuario2 = p1.body()
                        Log.i("perfilF","user: "+p1.body())
                        nombreTextView.text = usuario2?.nombre;
                        apellidopTextView.text= usuario2?.apellidop;
                        apellidomTextView.text=usuario2?.apellidom;
                        identificador= Integer.parseInt(usuario2?.iduser.toString());
                        listener?.modificarIdentificador(usuario2?.iduser.toString());
                        Log.i("perfilF", "Usuario obtenido correctamente: ${usuario?.nombre}")
                    } else {
                        Log.i("perfilF", "Error en la respuesta: ${p1.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<UsersLibreria>, t: Throwable) {
                    Log.e("perfilF", "Error al obtener el usuario: ${t.message}")
                }
            })
        } else {
            Log.e("perfilF", "Contraseña no proporcionada")
        }
    }

    private fun eliminarUsuario() {
        Log.i("perfilF","en la fn eliminar user")

        val callEliminar: Call<Void> = UserAPI!!.eliminarUser(identificador)
        callEliminar.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.i("perfilF", "Usuario eliminado correctamente")
                    cerrarsecion();
                    Toast.makeText(context,"Tu cuenta fe eliminada ",Toast.LENGTH_LONG).show();
                } else {
                    Log.e("perfilF", "Error al eliminar usuario: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("perfilF", "Error en la llamada de eliminación: ${t.message}")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }


    companion object {
        private const val ARG_CONTRASENHA = "contrasenha"

        @JvmStatic
        fun newInstance(contrasenha: String) =
            perfilF()?.apply {
                arguments = Bundle().apply {
                    putString(ARG_CONTRASENHA, contrasenha)
                }
            }
    }
}