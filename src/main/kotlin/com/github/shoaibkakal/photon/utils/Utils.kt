package com.github.shoaibkakal.photon.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.fileTypes.FileTypeRegistry
import com.intellij.openapi.project.Project


fun removeSpecificText(searchText: String, e: AnActionEvent) {
    val project: Project = e.project ?: return

    val editor = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.EDITOR) ?: return

    val document = editor.document

    WriteCommandAction.runWriteCommandAction(project) {
        val lineNumber = findLineWithText(document, "//TODO: Wait, Photon is snuffling🐕‍")
        if (lineNumber != -1) {
            val lineStartOffset = document.getLineStartOffset(lineNumber)
            val lineEndOffset = document.getLineEndOffset(lineNumber)
            document.deleteString(lineStartOffset, lineEndOffset)
        }
    }
}

private fun findLineWithText(document: com.intellij.openapi.editor.Document, searchText: String): Int {
    val text = document.text
    val lines = text.split("\n")
    for ((index, line) in lines.withIndex()) {
        if (line.contains(searchText)) {
            return index
        }
    }
    return -1
}

fun getCurrentFileLanguage(e:AnActionEvent):String
{
    val project: Project = e.project ?: return "";

    val file = e.getData(com.intellij.openapi.actionSystem.CommonDataKeys.PSI_FILE)
    if (file != null) {
        val fileType = FileTypeRegistry.getInstance().getFileTypeByFile(file.virtualFile)
        val language = fileType.name ?: ""
        println("Current file language: $language")
        return language
    }
    return ""
}