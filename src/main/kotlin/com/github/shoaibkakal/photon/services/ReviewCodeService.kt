package com.github.shoaibkakal.photon.services

import com.github.shoaibkakal.photon.MyBundle
import com.github.shoaibkakal.photon.utils.PhotonPrompt
import com.github.shoaibkakal.photon.utils.getCurrentFileLanguage
import com.github.shoaibkakal.photon.utils.removeSpecificText
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class ROLES {
    user,
    system
}

class ReviewCodeService : AnAction() {
    val coroutine = CoroutineScope(Dispatchers.IO)
    override fun actionPerformed(e: AnActionEvent) {

        val snuffling = "//TODO: Wait, Photon is snuffling🐕‍\n\n"
        val OpenAI_API_KEY = System.getenv(MyBundle.message("openAIAPIKEY"))

        try {
            val editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR)
            if (editor != null) {
                val project = editor.project
                val document = editor.document

                val editorSelection = editor.selectionModel
                val editorSelectedText = editorSelection.selectedText
                val selectionStart = editorSelection.selectionStart
                if (!editorSelectedText.isNullOrEmpty()) {
                    if (editorSelectedText.length > 6000) {
                        Messages.showMessageDialog(MyBundle.message("selectedTextTooLong"), "Error", Messages.getErrorIcon())
                    } else {
                        if (!OpenAI_API_KEY.isNullOrEmpty()) {
                            val openAiService = OpenAiService(OpenAI_API_KEY)

//                        Messages.showMessageDialog(snuffling, "Error", Messages.getErrorIcon())
                            coroutine.launch {
                                WriteCommandAction.runWriteCommandAction(project) {
                                    document.insertString(
                                            selectionStart, snuffling
                                    )
                                }

                                val messages: MutableList<ChatMessage> = ArrayList()
                                val systemMessage = ChatMessage(ChatMessageRole.USER.value(), "${PhotonPrompt.REVIEW_CODE(getCurrentFileLanguage(e))}. Below is the code to review:\n $editorSelectedText")
                                messages.add(systemMessage)
                                val chatCompletionRequest = ChatCompletionRequest
                                        .builder()
                                        .model("gpt-3.5-turbo")
                                        .messages(messages)
                                        .n(1)
                                        //.maxTokens(10)
                                        .logitBias(HashMap())
                                        .build()
                                try {
                                    val resultChoices = openAiService.createChatCompletion(chatCompletionRequest).choices

                                    if (resultChoices.size == 0) {
                                        Messages.showMessageDialog(MyBundle.message("somethingWentWrong"), "Error", Messages.getErrorIcon())
                                        return@launch
                                    }
                                    val photonResponse = "\n/** PHOTON REVIEW \n* ${resultChoices[0].message.content}"


                                    WriteCommandAction.runWriteCommandAction(project) {
                                        removeSpecificText(snuffling, e)
                                        document.insertString(
                                                selectionStart, "$photonResponse\n*/\n"
                                        )
                                    }
                                } catch (exp: Exception) {
                                    withContext(Dispatchers.Main)
                                    {
                                        removeSpecificText(snuffling, e)
                                        Messages.showMessageDialog("$exp", "Exception", Messages.getErrorIcon())
                                    }

                                }
                            }

                            openAiService.shutdownExecutor()
                        } else {
                            removeSpecificText(snuffling, e)
                            Messages.showMessageDialog(MyBundle.message("couldNotFindAPIKEY"), "Error", Messages.getErrorIcon())
                        }
                    }
                }
            }
        } catch (exp: Exception) {
            Messages.showMessageDialog("Exception: $exp", "Error", Messages.getErrorIcon())
//            removeSpecificText(snuffling, e)

        }

    }

}