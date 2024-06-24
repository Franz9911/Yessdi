package com.example.labretrofitktejemplo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class ReservaAdapter(
    private var reservaList: List<Reservas>,
    reservaAPI: ClientAPI?,
    listener: ComunicacionFragment?
) : RecyclerView.Adapter<ReservaAdapter.UserViewHolder>()  {
    private  var listener2=listener;
    private var reservaAPI2=reservaAPI;
    var idReserva:String="";

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.tituloR)
        val fechainicioR:TextView=itemView.findViewById(R.id.fechainicioR)
        val totalR:TextView=itemView.findViewById(R.id.totalR)
        val estadoR:TextView=itemView.findViewById(R.id.estadoR)
        val unidadesR:TextView=itemView.findViewById(R.id.unidadesR)
        val imagenR:ImageView=itemView.findViewById(R.id.imageLR)
        val BotonCancelar:Button=itemView.findViewById(R.id.EliminarReserva)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_reserva, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentReservas = reservaList[position]
        holder.titulo.text = currentReservas.titulo;
        holder.fechainicioR.text = "fecha "+currentReservas.fechaReserva;
        holder.estadoR.text = currentReservas.estado;
        holder.totalR.text = "total de "+currentReservas.costo+"Bs";
        holder.unidadesR.text = "unidades adquiridas "+currentReservas.unidades;
        //cargar imagen
        Glide.with(holder.itemView.context)
            .load(currentReservas.imagenL
                ,)
            .centerCrop()
            .into(holder.imagenR)
        Log.i("MBadapter","entramos a miB");

        holder.BotonCancelar?.setOnClickListener {
            idReserva=""+currentReservas.idReserva
            Log.i("reserva",""+reservaAPI2)
            eliminarReserva()
        }

    }

    private fun eliminarReserva() {

        Log.i("reserva","en la fun eliminar user")
        Log.i("reserva","cadena "+idReserva)
        val callEliminar: Call<Void> = reservaAPI2!!.eliminarReserva("3" )
        callEliminar.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.i("reserva", "pedido eliminado")
                    listener2?.cambiarFagmentPerfil()
                } else {
                    Log.e("reserva", "Error al eliminar: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("reserva", "Error en la llamada de eliminación: ${t.message}")
            }
        })
    }

    override fun getItemCount() = reservaList.size
}