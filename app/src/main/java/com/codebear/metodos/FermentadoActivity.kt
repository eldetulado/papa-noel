package com.codebear.metodos

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.codebear.metodos.databinding.ActivityMezcladoBinding
import com.codebear.metodos.utils.DataStep

import com.codebear.metodos.utils.Operations
import com.codebear.metodos.utils.PrefsUtil
import com.codebear.metodos.utils.Step

class FermentadoActivity : AppCompatActivity() {

    private val data = mapOf(
        Pair("MAQUINA", "Mezcladora de masa MV50"),
        Pair("TIPO DE OPERACIÓN", "Semiautomático"),
        Pair("CAPACIDAD DE LA OPERACIÓN", "2 bandejas de 5 unidades"),
        Pair("CAPACIDAD DE LA MAQUINA", "3 bandejas de 5 unidades"),
        Pair("MERMAS", "0,000%"),
        Pair("DEFECTUSOS", "0,625%"),
        Pair("SET-UP", "2,00 min/lote"),
        Pair("DISTANCIA A OPERACIÓN POSTERIOR", "6,20 m"),
    )

    private lateinit var binding: ActivityMezcladoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMezcladoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Operación de fermentado"

        loadViews()

        loadPrefs()

        binding.btnCalculate.setOnClickListener {
            val rawStartMin = binding.etStartMin.text.toString().replace(",", ".")
            val rawStartSec = binding.etStartSec.text.toString().replace(",", ".")

            val rawEndMin = binding.etEndMin.text.toString().replace(",", ".")
            val rawEndSec = binding.etEndSec.text.toString().replace(",", ".")

            if (rawStartMin.isNotEmpty() && rawStartSec.isNotEmpty() && rawEndMin.isNotEmpty() && rawEndSec.isNotEmpty()) {

                val solution =
                    Operations.calculateTime(rawStartMin, rawStartSec, rawEndMin, rawEndSec)

                if (solution == -1.0) {
                    Toast.makeText(this, "Los datos son incorrectos", Toast.LENGTH_SHORT).show()
                } else {

                    PrefsUtil.saveData(
                        this,
                        Step.FERMENTACION,
                        DataStep(rawStartMin, rawStartSec, rawEndMin, rawEndSec, solution)
                    )

                    binding.tvResult.text = "El tiempo de ciclo es: $solution s"
                }

            } else {
                Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, HorneadoActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnPrevious.setOnClickListener {
            val intent = Intent(this, MoldeadoActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadViews() {
        data.keys.forEach {
            val llh = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER_VERTICAL
            }

            val tv1 = TextView(this, null, 0, R.style.Text_Body1).apply {
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f)
                gravity = Gravity.CENTER
                text = it
            }
            val tv2 = TextView(this, null, 0, R.style.Text_Light2).apply {
                layoutParams = TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f)
                gravity = Gravity.CENTER
                text = data[it]
            }

            llh.addView(tv1)
            llh.addView(tv2)

            binding.llRoot.addView(llh)
        }
    }

    private fun loadPrefs() {
        val data = PrefsUtil.getData(this, Step.FERMENTACION)
        data?.let {
            binding.etStartMin.setText(data.startMin)
            binding.etStartSec.setText(data.startSec)
            binding.etEndMin.setText(data.endMin)
            binding.etEndSec.setText(data.endSec)
            binding.tvResult.text = "El tiempo de ciclo es: ${data.totalTime} s"
        }
    }
}