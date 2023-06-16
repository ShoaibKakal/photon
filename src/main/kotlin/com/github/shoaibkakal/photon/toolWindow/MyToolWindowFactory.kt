package com.github.shoaibkakal.photon.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.shoaibkakal.photon.MyBundle
import com.github.shoaibkakal.photon.services.MyProjectService
import javax.swing.JButton
import javax.swing.JOptionPane


class MyToolWindowFactory : ToolWindowFactory {

    init {
        JOptionPane.showMessageDialog(null, "Hello This is alert box", "My Alert", JOptionPane.INFORMATION_MESSAGE)
        val input = JOptionPane.showInputDialog(null, "Enter your name:")

        if (input != null) {
            // User clicked OK or entered a value
            println("You entered: $input")
        } else {
            // User clicked Cancel or closed the dialog
            println("Input canceled")
        }
//        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val label = JBLabel(MyBundle.message("randomLabel", "?"))

            add(label)
            add(JButton(MyBundle.message("shuffle")).apply {
                addActionListener {
                    label.text = MyBundle.message("randomLabel", service.getRandomNumber())
                }
            })
        }
    }
}
