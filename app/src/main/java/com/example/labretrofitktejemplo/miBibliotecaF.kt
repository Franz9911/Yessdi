package com.example.labretrofitktejemplo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class miBibliotecaF : Fragment() {
    private var reservaAPI:ClientAPI?=null;
    private lateinit var recyclerViewMB: RecyclerView
    private lateinit var reservaAdapter: ReservaAdapter
    private var reservaList: List<Reservas> = listOf()

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
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservaAPI=(activity as MainActivity).ConectarAPI();
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
        return inflater.inflate(R.layout.fragment_mi_biblioteca, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerViewMB = view.findViewById(R.id.RVmiBiblioteca)
        recyclerViewMB.layoutManager = LinearLayoutManager(context)
        reservaAdapter=ReservaAdapter(reservaList,reservaAPI,listener)
        recyclerViewMB.adapter = reservaAdapter
        listarReservas()
    }

    private fun listarReservas() {
        Log.i("reserva","entramos a listar")
        val identificador=listener?.verIdentificador()+""
        Log.i("reserva","identificador: "+identificador)
        val listarCall: Call<List<Reservas>> =reservaAPI!!.getReservasUsuario(identificador)
        listarCall.enqueue(object : Callback<List<Reservas>> {
            override fun onResponse(call: Call<List<Reservas>>, response: Response<List<Reservas>>) {
                if (response.isSuccessful) {
                    reservaList = response.body() ?: listOf()
                    Log.i("reserva",""+ response.body())
                    //debemos pasar como argmento a reservaAPI para conectarnos con ClienteAPI
                    reservaAdapter = ReservaAdapter(reservaList,reservaAPI,listener)
                    recyclerViewMB.adapter = reservaAdapter;
                }
                else {
                    Log.i("reserva",""+response.errorBody())
                }

            }

            override fun onFailure(call: Call<List<Reservas>>, t: Throwable) {
                Log.i("TEOTEO", "Fallo en la conexion" + t.message + "\n" + t.stackTrace)
                Log.d("TEOTEO", Log.getStackTraceString(t))
            }
        })
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            miBibliotecaF().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}