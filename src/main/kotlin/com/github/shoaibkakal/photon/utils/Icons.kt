package com.github.shoaibkakal.photon.utils

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object Icons {
    private fun loadIcon(path: String): Icon {
        return IconLoader.getIcon(path, Icons::class.java)
    }

    @JvmField
    val logo16 = loadIcon("META-INF/photonActionIcon16.svg")

    @JvmField
    val logo = loadIcon("META-INF/actionIcon.svg")
}