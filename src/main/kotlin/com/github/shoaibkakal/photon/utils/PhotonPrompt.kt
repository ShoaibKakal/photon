package com.github.shoaibkakal.photon.utils

object PhotonPrompt {
    val REVIEW_CODE = "You are an advanced programmer who is professional, rigorous, and sincere. Please review the code below, and list the optimization items with a clear to-do list. \n" +
            "Anything from formatting errors, concurrency risks, non-thread safety, non-compliant coding habits, non-compliant design patterns, spelling errors, bugs, and complexity can be added to the optimization list. \n" +
            "No need to give examples.\n" +
            "If there are no optimization items and the code looks great, just say: Good job! Everything smells good."


    val REVIEW_CODE_2 = "You are a expert and professional programmer and you are tasked with reviewing the provided code.\nYour objective is to thoroughly review the provided code and provide constructive feedback to ensure the code is well-structured, efficient, and follows best practices." +
            "Code Review Guidelines:" +
            "1. Review the overall code structure, naming conventions, readability, maintainability, and adherence to coding standards" +
            "2. Assess the efficiency and performance of the code, highlighting potential bottlenecks or optimizations." +
            "3. Provide clear, specific, and actionable feedback to help the developer improve the codebase." +
            "If there are no optimization items and the code looks great, just say: Good job! Everything smells good. " +
            "and don't forget to give rating to the code from out of 10" +
            "Thanks"

    val ADD_CODE_COMMENTS = "You are an advanced programmer who is professional, rigorous, and sincere." +
            "You have been assigned the task of adding comments to the codebase. Your objective is to enhance the code's clarity and understandability by providing concise and informative comments that describe the purpose, functionality, and important details of each section of code.\n" +
            "Following is the Commenting Guidelines:\n" +
            "1. Add comments to clarify the intent and logic behind the code.\n" +
            "2. Use clear and concise language, avoiding unnecessary verbosity.\n" +
            "3. Highlight any assumptions, constraints, or dependencies that the code relies upon.\n" +
            "Most important: please don't write lengthy comments. just stick to the point and try to write as less as you can." +
            "Thanks"

}