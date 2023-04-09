package com.github.ivaneod.kotlinjsinspections.inspections


import com.github.ivaneod.kotlinjsinspections.KotlinJsInspections
import com.github.ivaneod.kotlinjsinspections.quickfixes.ExternalMethodFix
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection
import org.jetbrains.kotlin.idea.project.platform
import org.jetbrains.kotlin.idea.search.usagesSearch.descriptor
import org.jetbrains.kotlin.lexer.KtTokens
import org.jetbrains.kotlin.platform.js.isJs
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.namedFunctionVisitor
import org.jetbrains.kotlin.psi.psiUtil.containingClassOrObject


/**
 *  External Method Inspection
 *
 * @author IvanEOD ( 4/9/2023 at 10:42 AM EST )
 */
class ExternalMethodInspection : AbstractKotlinInspection() {

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = namedFunctionVisitor { function ->
        if (!function.platform.isJs() || !function.isTopLevel) return@namedFunctionVisitor
        val parent = function.containingClassOrObject as? KtClass ?: return@namedFunctionVisitor
        val parentClassDescriptor = parent.descriptor as? ClassDescriptor ?: return@namedFunctionVisitor
        if (parentClassDescriptor.isExternal || function.hasModifier(KtTokens.EXTERNAL_KEYWORD)) {
            if (function.name?.first()?.isUpperCase() == true)
                holder.registerProblem(
                    function,
                    KotlinJsInspections.message("inspection.kotlinjs.external.method.display.name"),
                    ExternalMethodFix()
                )
        }
    }

}