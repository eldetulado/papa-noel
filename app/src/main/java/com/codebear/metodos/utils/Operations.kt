package com.codebear.metodos.utils

object Operations {

    fun calculateTime(
        rawStartMin: String,
        rawStartSec: String,
        rawEndMin: String,
        rawEndSec: String,
    ): Double {
        val startMin = rawStartMin.toDouble()
        val startSec = rawStartSec.toDouble()

        val endMin = rawEndMin.toDouble()
        val endSec = rawEndSec.toDouble()

        return if ((startMin > endMin) || (startSec > endSec)) {
            -1.0
        } else {
            val operation = (((endMin - startMin) * 60) + (endSec - startSec)) * 3
            String.format("%.2f", operation).toDouble()
        }


    }

}