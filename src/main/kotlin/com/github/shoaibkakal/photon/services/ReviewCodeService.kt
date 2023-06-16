package com.github.shoaibkakal.photon.services

import com.github.shoaibkakal.photon.utils.PhotonPrompt
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.theokanning.openai.completion.chat.ChatCompletionChunk
import com.theokanning.openai.completion.chat.ChatCompletionRequest
import com.theokanning.openai.completion.chat.ChatMessage
import com.theokanning.openai.completion.chat.ChatMessageRole
import com.theokanning.openai.service.OpenAiService
import io.reactivex.functions.Consumer

enum class ROLES {
    user,
    system
}

class ReviewCodeService : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {

        var snuffling = "//TODO: Photon is snuffling 🐕"
        val OpenAI_API_KEY = "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"
        //        String token = System.getenv("OPENAI_API_KEY");
        val openAiService = OpenAiService(OpenAI_API_KEY)

        val editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR)
        if (editor != null) {
            val project = editor.project
            val document= editor.document

            val editorSelection = editor.selectionModel
            val editorSelectedText = editorSelection.selectedText
            if (!editorSelectedText.isNullOrEmpty()) {
                if (editorSelectedText.length > 6000) {
                    Messages.showMessageDialog("Selected text is too long.", "Error", Messages.getErrorIcon())
                } else {
                    if (!OpenAI_API_KEY.isNullOrEmpty()) {

//                        Messages.showMessageDialog(snuffling, "Error", Messages.getErrorIcon())

                        WriteCommandAction.runWriteCommandAction(project) {
                            document.insertString(
                                    editorSelection.selectionStart, snuffling
                            )
                        }

                        val messages: MutableList<ChatMessage> = ArrayList()
                        val systemMessage = ChatMessage(ChatMessageRole.USER.value(), "${PhotonPrompt.REVIEW_CODE_2}. Below is the code to review:\n $editorSelectedText")
                        messages.add(systemMessage)
                        val chatCompletionRequest = ChatCompletionRequest
                                .builder()
                                .model("gpt-3.5-turbo")
                                .messages(messages)
                                .n(1)
                                //.maxTokens(10)
                                .logitBias(HashMap())
                                .build()

                        val resultChoices = openAiService.createChatCompletion(chatCompletionRequest).choices

                        if (resultChoices.size==0) {
                            Messages.showMessageDialog("Something went wrong on our side!", "Error", Messages.getErrorIcon())
                            return
                        }
                        var photonResponse = "\n/** PHOTON REVIEW \n* ${resultChoices[0].message.content}"

                        snuffling = "DONE";
//                        photonResponse = photonResponse.replace(".", "\n*")

                        WriteCommandAction.runWriteCommandAction(project) {
                            document.insertString(
                                    editorSelection.selectionStart, "$photonResponse\n*/\n"
                            )
                        }




//                        openAiService.streamChatCompletion(chatCompletionRequest)
//                                .doOnError(Consumer<Throwable> { obj: Throwable -> obj.printStackTrace() })
//                                .blockingForEach(Consumer<ChatCompletionChunk> { chatCompletionChunk ->
//                                    if (chatCompletionChunk.choices[0] == null) {
//                                        return@Consumer;
//                                    }
//                                    val word = chatCompletionChunk.choices[0].message.content ?: return@Consumer
//
//                                    val project: Project? = editor.project
//                                    val document: Document = editor.document
//
//                                    WriteCommandAction.runWriteCommandAction(project) {
//                                        document.insertString(
//                                                editorSelection.selectionStart, word
//                                        )
//                                    }
//
//                                })

                        openAiService.shutdownExecutor()


                    } else {
                        Messages.showMessageDialog("Please add API Key", "Error", Messages.getErrorIcon())
                    }
                }
            }
        }
    }
}