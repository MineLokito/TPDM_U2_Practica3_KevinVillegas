package mx.edu.ittepic.tpdm_u2_practica3_kevinvillegas

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var numero1 : EditText?=null
    var numero2 : EditText?=null
    var boton : Button?=null
    var boton2 : Button?=null
    var etiqueta : TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numero1 = findViewById(R.id.campo1)
        numero2 = findViewById(R.id.campo2)
        boton = findViewById(R.id.botonGenerar)
        boton2 = findViewById(R.id.botonmostrar)
        etiqueta = findViewById(R.id.campoNumeros)

        boton?.setOnClickListener{
            etiqueta?.setText("")
            GenerarNumeros(campo1?.text.toString(), campo2?.text.toString(),applicationContext).execute()
        }
        boton2?.setOnClickListener{
            val abrirArchivo = BufferedReader(InputStreamReader(openFileInput("primos.txt")))
            var cadena = ""
            cadena = abrirArchivo.readLine()
            etiqueta?.setText(cadena)
        }
    }

    class GenerarNumeros(n: String, m: String, context: Context) : AsyncTask<Void, Void, List<Int>>(){ //Inicia AsyncTask
        var n = n.toInt()
        var m = m.toInt()
        var c = context

        override fun doInBackground(vararg p0: Void?): List<Int>? {
            val gen = List(2000){ Random.nextInt(n,m)} //tamaño de la lista a generar
            println(gen)

            return gen
        }
        override fun onPostExecute(result: List<Int>?) {
            super.onPostExecute(result)
            var cont = 0
            var et=""
            var numerosPrimos="Números primos:  "
            (0..1999).forEach {  //contador de los 2 mil numeros
                cont=0
                et=result?.get(it).toString()
                (1..et.toInt()).forEach {
                    if (et.toInt() % it == 0) {
                        cont++
                    }
                }
                if (cont <= 2 && et.toInt()>1) {
                    numerosPrimos=numerosPrimos + et+ ", "
                } else {

                }
            }
            println(numerosPrimos)
            guardar(numerosPrimos)

        }

        fun guardar(numerosPrimos:String){
            val guardarArchivo = OutputStreamWriter(c.openFileOutput("primos.txt", Activity.MODE_PRIVATE))
            guardarArchivo.write(numerosPrimos)
            guardarArchivo.flush()
            guardarArchivo.close()
            Toast.makeText(c,"Numeros Guardados Correctamente",Toast.LENGTH_LONG).show()
        }
    }


}
