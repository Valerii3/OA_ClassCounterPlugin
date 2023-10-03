package com.valerii.ov.basic_plugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifierListOwner
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil

class FileAnalyzer(val project: Project) {

    private val psiManager = PsiManager.getInstance(project)

    fun analyzeFile(file: VirtualFile): FileStats {
        val psiFile = psiManager.findFile(file)
        val classes = PsiTreeUtil.findChildrenOfType(psiFile, com.intellij.psi.PsiClass::class.java).size
        val methods = PsiTreeUtil.findChildrenOfType(psiFile, PsiMethod::class.java).size
        return FileStats(classes, methods)
    }

    fun getAllJavaFiles(): List<VirtualFile> {
        return FileTypeIndex.getFiles(com.intellij.ide.highlighter.JavaFileType.INSTANCE, GlobalSearchScope.projectScope(project))
            .sortedBy { it.path }
    }

    fun getVisibility(element: PsiModifierListOwner): String {
        return when {
            element.hasModifierProperty("public") -> "Public"
            element.hasModifierProperty("private") -> "Private"
            element.hasModifierProperty("protected") -> "Protected"
            else -> "Package-Private"
        }
    }

    fun getAnnotations(element: PsiModifierListOwner): String {
        val annotations = element.modifierList?.annotations?.map { it.qualifiedName }?.joinToString(", ")
        return annotations?.ifBlank { "None" } ?: "None"
    }
}