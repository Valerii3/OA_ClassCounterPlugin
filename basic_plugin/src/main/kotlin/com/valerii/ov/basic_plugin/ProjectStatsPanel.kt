package com.valerii.ov.basic_plugin

import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.DocumentEvent
import com.intellij.openapi.editor.event.DocumentListener
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiMethod
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.ui.table.JBTable
import java.awt.BorderLayout
import javax.swing.*
import javax.swing.table.DefaultTableModel

class ProjectStatsPanel(project: Project) {
    val content: JComponent
    private val fileAnalyzer = FileAnalyzer(project)

    private val fileStatsTableModel = DefaultTableModel(arrayOf("File", "Classes", "Methods"), 0)
    private val detailedInfoTableModel = DefaultTableModel(arrayOf("Type", "Name", "Visibility", "Annotations"), 0)

    private val fileStatsTable = JBTable(fileStatsTableModel)
    private val detailedInfoTable = JBTable(detailedInfoTableModel)

    private val detailedInfoLabel = JLabel("Detailed Information: File is not selected")
    private var selectedFile: String? = null

    init {
        val mainPanel = JPanel(BorderLayout())
        val detailedInfoPanel = JPanel(BorderLayout()).apply {
            add(detailedInfoLabel, BorderLayout.NORTH)
            add(JScrollPane(detailedInfoTable), BorderLayout.CENTER)
        }

        val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, JScrollPane(fileStatsTable), detailedInfoPanel).apply {
            resizeWeight = 0.7
        }

        mainPanel.add(splitPane, BorderLayout.CENTER)
        content = mainPanel

        setupListeners()
        refreshFileStatistics()
    }

    private fun setupListeners() {
        EditorFactory.getInstance().eventMulticaster.addDocumentListener(object : DocumentListener {
            override fun documentChanged(event: DocumentEvent) {
                PsiDocumentManager.getInstance(fileAnalyzer.project).commitDocument(event.document)
                refreshFileStatistics()
                selectedFile?.let { updateDetailedInfo(it) }
            }
        }, fileAnalyzer.project)

        fileStatsTable.selectionModel.addListSelectionListener {
            val selectedRow = fileStatsTable.selectedRow
            if (selectedRow >= 0) {
                selectedFile = fileStatsTableModel.getValueAt(selectedRow, 0) as String
                updateDetailedInfo(selectedFile!!)
            }
        }
    }

    private fun updateDetailedInfo(fileName: String) {
        detailedInfoLabel.text = "Detailed Information for $fileName"
        detailedInfoTableModel.setRowCount(0)
        fileAnalyzer.getAllJavaFiles().firstOrNull { it.name == fileName }?.let {
            val psiFile = PsiManager.getInstance(fileAnalyzer.project).findFile(it)
            PsiTreeUtil.findChildrenOfType(psiFile, com.intellij.psi.PsiClass::class.java).forEach { psiClass ->
                detailedInfoTableModel.addRow(arrayOf("Class", psiClass.name, fileAnalyzer.getVisibility(psiClass), fileAnalyzer.getAnnotations(psiClass)))
                PsiTreeUtil.findChildrenOfType(psiClass, PsiMethod::class.java).forEach { method ->
                    detailedInfoTableModel.addRow(arrayOf("Method", method.name, fileAnalyzer.getVisibility(method), fileAnalyzer.getAnnotations(method)))
                }
            }
        }
    }

    private fun refreshFileStatistics() {
        fileStatsTableModel.setRowCount(0)
        fileAnalyzer.getAllJavaFiles().forEach { file ->
            val stats = fileAnalyzer.analyzeFile(file)
            fileStatsTableModel.addRow(arrayOf(file.name, stats.classes, stats.methods))
        }
    }
}