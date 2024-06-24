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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class TiendaAdapter(private val libroList: List<Libros>, bookAPI: ClientAPI?, identificador: String) : RecyclerView.Adapter<TiendaAdapter.UserViewHolder>() {
    private var bookAPI=bookAPI;
    private var identificador=identificador;

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titulo: TextView = itemView.findViewById(R.id.titulo)
        val categoria: TextView = itemView.findViewById(R.id.categoria)
        val estado: TextView= itemView.findViewById(R.id.estado)
        val precio: TextView= itemView.findViewById(R.id.costo)
        val autor: TextView= itemView.findViewById(R.id.autor)
        val imagenL: ImageView=itemView.findViewById(R.id.imageL)
        val botonReservarLibro:Button=itemView.findViewById(R.id.reserva)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_libro, parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentLibro = libroList[position]
        holder.titulo.text = currentLibro.titulo
        holder.categoria.text = "categoria: "+ currentLibro.categoria
        holder.estado.text="estado: "+ currentLibro.estado
        holder.precio.text="costo: "+ currentLibro.costo
        holder.autor.text="autor: "+ currentLibro.autor
        Glide.with(holder.itemView.context)
            .load(
                currentLibro.imagenL,
            )
            .centerCrop()
            .into(holder.imagenL)

        holder.botonReservarLibro?.setOnClickListener {
            val idLibro:String=""+currentLibro.idLibro;
            val precio:String=""+currentLibro.costo

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "ES"))
            sdf.timeZone = TimeZone.getTimeZone("UTC+1")
            val fecha = sdf.format(Date())
            Log.i("tienda", fecha)
            Log.i("tienda",idLibro)
            Log.i("tienda",identificador)
            val reser=Reservas(1, Integer.parseInt(idLibro),
                Integer.parseInt(identificador),fecha,"pendiente","1",
                precio,"","","")
            NuevaReserva(reser)
        }
    }
    fun NuevaReserva(us: Reservas) {
        val callAdd: Call<Reservas> = bookAPI!!.nuevaReserva(us)
        callAdd.enqueue(object : Callback<Reservas> {
            override fun onResponse(p0: Call<Reservas>, p1: Response<Reservas>) {
                if (p1.isSuccessful) {
                    Log.i("pancho", "Agregado correctamente")
                    //Toast.makeText(context,"Tus datos han sido registrados", Toast.LENGTH_LONG).show()

                    Log.i("pancho", "A" + p1.body())
                } else {
                    Log.i("pancho", "Error en la respuesta: ${p1.errorBody()?.string()}")
                }
            }

            override fun onFailure(p0: Call<Reservas>, p1: Throwable) {
                Log.i("pE", "Se produjo un error al agregar: ${p1.message}")
            }
        })
    }

    override fun getItemCount() = libroList.size
}