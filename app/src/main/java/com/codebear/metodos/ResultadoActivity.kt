package com.codebear.metodos

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.codebear.metodos.databinding.ActivityResultadoBinding
import com.codebear.metodos.utils.CustomCSVUtil
import com.codebear.metodos.utils.DataStep
import com.codebear.metodos.utils.PrefsUtil
import com.codebear.metodos.utils.Step

class ResultadoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoBinding

    private var items = mutableListOf<DataStep>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadResults()

        binding.btnGenerateCSV.setOnClickListener {
            val etName = EditText(this)
            etName.hint = "Nombre del archivo"

            val optionDialog = AlertDialog.Builder(this)
                .setTitle("Atencion")
                .setMessage("Ingresar nombre del archivo para despues ser exportado en CSV")
                .setView(etName)
                .setPositiveButton(getString(android.R.string.ok)) { dialog, _ ->
                    dialog.dismiss()
                    var aux = ""

                    for (item in items) aux += "${item.name},${item.totalTime} seg\n"

                    CustomCSVUtil.generateCSVInExternalCache(
                        this,
                        etName.text.toString(),
                        "TIPO DE OPERACION,TIEMPO\n",
                        aux
                    )
                }
                .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            optionDialog.show()
        }

        binding.btnReset.setOnClickListener {
            PrefsUtil.clearPrefs(this)
            val intent = Intent(this, MezcladoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadResults() {
        val mezclado = PrefsUtil.getData(this, Step.MEZCLADO)
        val amasado = PrefsUtil.getData(this, Step.AMASADO)
        val reposo = PrefsUtil.getData(this, Step.REPOSO)
        val modelado = PrefsUtil.getData(this, Step.MODELADO)
        val moldeado = PrefsUtil.getData(this, Step.MOLDEADO)
        val fermentacion = PrefsUtil.getData(this, Step.FERMENTACION)
        val horneado = PrefsUtil.getData(this, Step.HORNEADO)
        val enfriado = PrefsUtil.getData(this, Step.ENFRIADO)
        val envasado = PrefsUtil.getData(this, Step.ENVASADO)



        mezclado?.let {
            binding.tvMezclado.text = "${mezclado.totalTime} s"
            items.add(it)
        }
        amasado?.let {
            binding.tvAmasado.text = "${amasado.totalTime} s"
            items.add(it)
        }
        reposo?.let {
            binding.tvReposo.text = "${reposo.totalTime} s"
            items.add(it)
        }
        modelado?.let {
            binding.tvModelado.text = "${modelado.totalTime} s"
            items.add(it)
        }
        moldeado?.let {
            binding.tvMoldeado.text = "${moldeado.totalTime} s"
            items.add(it)
        }
        fermentacion?.let {
            binding.tvFermentacion.text = "${fermentacion.totalTime} s"
            items.add(it)
        }
        horneado?.let {
            binding.tvHorneado.text = "${horneado.totalTime} s"
            items.add(it)
        }
        enfriado?.let {
            binding.tvEnfriado.text = "${enfriado.totalTime} s"
            items.add(it)
        }
        envasado?.let {
            binding.tvEmpaquetado.text = "${envasado.totalTime} s"
            items.add(it)
        }
    }
}