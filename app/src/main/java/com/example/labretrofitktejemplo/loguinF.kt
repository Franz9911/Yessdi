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

class loguinF : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var UserAPI: ClientAPI? = null;
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserAPI = (activity as MainActivity).ConectarAPI()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nombreL: TextView = view.findViewById(R.id.nombreLoguin);
        val contrasenhaL: TextView = view.findViewById(R.id.contrasenhaLoguin);
        val btnInicio: Button =view.findViewById(R.id.buttonLoguin)

        btnInicio.setOnClickListener {
            //Log.i("perfilF","user "+ TxtcontrasenhaL)
            val txtcontrasenha=contrasenhaL.text
            val txtnombre=nombreL.text
            if(txtcontrasenha!=null || txtcontrasenha!="" && txtnombre!=null ||txtnombre!=""){
                Log.i("loguin",""+txtcontrasenha + " " + txtnombre  )
                //eliminarUsuario()
                val usuario =UsersLibreria(1,txtnombre.toString(), "", "", txtcontrasenha.toString());
                Toast.makeText(context, "Bienvenido "+txtnombre,Toast.LENGTH_LONG).show()

                buscarUser(usuario);
            }
            else return@setOnClickListener
        }


    }

    fun buscarUser(us: UsersLibreria) {
        val callAdd: Call<UsersLibreria> = UserAPI!!.loguin(us)
        callAdd.enqueue(object : Callback<UsersLibreria> {
            override fun onResponse(p0: Call<UsersLibreria>, p1: Response<UsersLibreria>) {
                if (p1.isSuccessful) {
                    Log.i("loguin", "te encontramos")
                    Toast.makeText(context,"te encontramos a iniciado tu sesion", Toast.LENGTH_LONG).show()

                    listener?.iniciarSesion(us.nombre,us.contrasenha);
                    Log.i("loguin", "A" + p1.body())
                } else {
                    Log.i("loguin", "Error en la respuesta: ${p1.errorBody()?.string()}")
                }
            }

            override fun onFailure(p0: Call<UsersLibreria>, p1: Throwable) {
                Log.i("loguin", "Se produjo un error al agregar: ${p1.message}")
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_loguin, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            loguinF().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}