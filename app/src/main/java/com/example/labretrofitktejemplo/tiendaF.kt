package com.example.labretrofitktejemplo

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [tiendaF.newInstance] factory method to
 * create an instance of this fragment.
 */
class tiendaF : Fragment() {
    private var bookAPI: ClientAPI? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var tiendaAdapter: TiendaAdapter
    private var libroList: List<Libros> = listOf()
    private var listener: ComunicacionFragment? =null;
    var identificador:String=""
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
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bookAPI = (activity as MainActivity).ConectarAPI()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tienda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val txt= view.findViewById<TextView>(R.id.prueva)
        //
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        tiendaAdapter = TiendaAdapter(libroList,bookAPI,identificador )
        recyclerView.adapter = tiendaAdapter


        listarLibro()
        //listar(txt)
    }
    private fun listarLibro() {
        identificador=listener?.verIdentificador()+""
        val listarCall: Call<List<Libros>> = bookAPI!!.getLibro()
        listarCall.enqueue(object : Callback<List<Libros>> {
            override fun onResponse(call: Call<List<Libros>>, response: Response<List<Libros>>) {
                if (response.isSuccessful) {
                    libroList = response.body() ?: listOf()
                    tiendaAdapter = TiendaAdapter(libroList,bookAPI,identificador)
                    recyclerView.adapter = tiendaAdapter
                }
                Log.i("pancho", "conexion exitosa")
                //Log.i("pancho", "" + response)
            }

            override fun onFailure(call: Call<List<Libros>>, t: Throwable) {
                Log.i("TEOTEO", "Fallo en la conexion" + t.message + "\n" + t.stackTrace)
                Log.d("TEOTEO", Log.getStackTraceString(t))
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = tiendaF()

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            tiendaF().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}