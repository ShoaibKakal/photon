package com.github.shoaibkakal.photon.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.shoaibkakal.photon.MyBundle
import com.github.shoaibkakal.photon.services.MyProjectService
import com.github.shoaibkakal.photon.utils.Icons.logo
import com.intellij.util.ui.JBUI
import java.awt.BorderLayout
import java.awt.Desktop
import java.awt.Dimension
import java.awt.Font
import java.net.URI
import java.net.URL
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.border.EmptyBorder


class PhotonChatWindow : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun getContent() = JBPanel<JBPanel<*>>().apply {



            val label = JBLabel(MyBundle.message("chatUnderDevelopment")).apply {
                font = font.deriveFont(Font.BOLD, 22f)
            }
            val imageIcon = ImageIcon(URL("https://www.kindpng.com/picc/m/775-7752343_doge-doge-meme-2020-hd-png-download.png"), "desc")
            val scaledImage = imageIcon.image.getScaledInstance(256, 256, java.awt.Image.SCALE_SMOOTH)
            val scaledImageIcon = ImageIcon(scaledImage)


            val imgLabel = JBLabel().apply {
                icon = scaledImageIcon
                preferredSize = Dimension(256, 256)

            }

            // Set padding between the text and the image
            val paddingSize = 10 // Adjust the padding size as needed
            val padding = JBUI.Borders.empty(paddingSize, 0)
            label.border = padding

            imgLabel.border = JBUI.Borders.empty(0,10,0,0)

            val panel = JPanel(BorderLayout())
            panel.add(imgLabel, BorderLayout.CENTER)
            panel.add(label, BorderLayout.NORTH)

            add(panel)

            val buttonPanel = JPanel()
            buttonPanel.add(JButton("For contribution check out Photon on Github").apply {
                addActionListener {
                    val url = "http://www.github.com/shoaibkakal/photon"
                    val uri = URI(url)
                    Desktop.getDesktop().browse(uri)
                }
            })

            val mainPanel = JPanel(BorderLayout())
            mainPanel.add(panel, BorderLayout.NORTH)
            mainPanel.add(buttonPanel, BorderLayout.WEST)

            add(mainPanel)
        }
    }
}
