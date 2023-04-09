package com.github.ivaneod.kotlinjsinspections.quickfixes

import com.github.ivaneod.kotlinjsinspections.KotlinJsInspections
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.util.addAnnotation
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.psi.KtFunction
import java.util.*


/**
 *  External Method Fix
 *
 * @author IvanEOD ( 4/9/2023 at 11:10 AM EST )
 */
class ExternalMethodFix : LocalQuickFix {

    override fun getName(): String = KotlinJsInspections.message("inspection.kotlinjs.external.method.display.name")
    override fun getFamilyName(): String = name
    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
        val method = descriptor.psiElement as? KtFunction ?: return
        val methodName = method.name ?: return
        val newMethodName = methodName.replaceFirstChar { it.lowercase(Locale.getDefault()) }
        method.setName(newMethodName)
        method.addAnnotation(FqName("JsName"), "\"$methodName\"")
    }
}