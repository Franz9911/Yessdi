package com.example.labretrofitktejemplo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class inicioF : Fragment() {

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

    // TODO: Rename and change types of parameters
    private var UserAPI: ClientAPI? = null;
    private var bookAPI: ClientAPI? = null;


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookAPI = (activity as MainActivity).ConectarAPI()
        UserAPI = bookAPI
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val texto:TextView=view.findViewById(R.id.textView2)
        val TxtNombre: EditText = view.findViewById(R.id.nombre)
        val TxtApellidop: EditText = view.findViewById(R.id.apellidop)
        val TxtApellidom: EditText = view.findViewById(R.id.apellidom)
        val TxtContrasenha: EditText = view.findViewById(R.id.contrasenha)
        val guardar: Button = view.findViewById(R.id.button)

        // Configura el click listener para el botón
        guardar.setOnClickListener {
            // Captura los datos del formulario
            val nombre = TxtNombre.text.toString()
            val apellidop = TxtApellidop.text.toString()
            val apellidom = TxtApellidom.text.toString()
            val contrasenha = TxtContrasenha.text.toString()

            val usuario =UsersLibreria(1,nombre, apellidop, apellidom, contrasenha);
            agregarUser(usuario);
        }
    }

    fun agregarUser(us: UsersLibreria) {
        val callAdd: Call<UsersLibreria> = UserAPI!!.agregar(us)
        callAdd.enqueue(object : Callback<UsersLibreria> {
            override fun onResponse(p0: Call<UsersLibreria>, p1: Response<UsersLibreria>) {
                if (p1.isSuccessful) {
                    Log.i("pancho", "Agregado correctamente")
                    Toast.makeText(context,"Tus datos han sido registrados",Toast.LENGTH_LONG).show()

                    listener?.iniciarSesion(us.nombre,us.contrasenha);
                    Log.i("pancho", "A" + p1.body())
                } else {
                    Log.i("pancho", "Error en la respuesta: ${p1.errorBody()?.string()}")
                }
            }

            override fun onFailure(p0: Call<UsersLibreria>, p1: Throwable) {
                Log.i("pE", "Se produjo un error al agregar: ${p1.message}")
            }
        })
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            inicioF().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}