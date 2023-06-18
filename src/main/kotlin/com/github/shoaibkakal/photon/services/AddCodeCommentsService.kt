package com.github.shoaibkakal.photon.services

import com.github.shoaibkakal.photon.MyBundle
import com.github.shoaibkakal.photon.utils.PhotonPrompt
import com.github.shoaibkakal.photon.utils.getCurrentFileLanguage
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


class AddCodeCommentsService : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        Messages.showMessageDialog(MyBundle.message("isUnderDevelopment","Adding comments to code is"), "Under Development", com.github.shoaibkakal.photon.utils.Icons.logo)

    }
}