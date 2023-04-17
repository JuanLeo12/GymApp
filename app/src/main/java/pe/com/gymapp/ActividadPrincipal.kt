package pe.com.gymapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import pe.com.gymapp.databinding.ActivityMainBinding
import pe.com.gymapp.utilidad.Util

class ActividadPrincipal : AppCompatActivity() {

    private val objutilidad= Util()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id=item.itemId

        return when (id) {
            R.id.jmiInicio ->{
                val frag=FragmentoInicio()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiEmpleado ->{
                val frag=FragmentoEmpleado()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiRegistroCliente ->{
                val frag=FragmentoCliente()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiSeguimientoFisico ->{
                val frag=FragmentoSeguimientoFisico()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiIncidencia ->{
                val frag=FragmentoIncidencia()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiRol ->{
                val frag=FragmentoRol()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiMembresia ->{
                val frag=FragmentoMembresia()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiProducto ->{
                val frag=FragmentoProducto()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiRegistroMaquina ->{
                val frag=FragmentoMaquina()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiDiasMantenimiento ->{
                val frag=FragmentoMantenimiento()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiProveedor ->{
                val frag=FragmentoProveedor()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiComprarProducto ->{
                val frag=FragmentoCompraProducto()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiComprarMaquina ->{
                val frag=FragmentoCompraMaquina()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiVenta ->{
                val frag=FragmentoVenta()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiAsistenciaEmpleado ->{
                val frag=FragmentoAsistenciaEmpleado()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiAsistenciaCliente ->{
                val frag=FragmentoAsistenciaCliente()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }

            R.id.jmiBuscarCliente ->{
                val fbuscarcliente=FragmentoBuscarCliente()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,fbuscarcliente).commit()
                true
            }

            R.id.jmiUsuario ->{
                val frag=FragmentoUsuario()
                supportFragmentManager.beginTransaction().replace(R.id.contenedor,frag).commit()
                true
            }
            R.id.jmiCerrarSesion->{
                val formulario= Intent(this,ActividadIngreso::class.java)
                startActivity(formulario)
                this.finish()
                true
            }
            R.id.jmiSalir->{
                objutilidad.SalirSistema(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}