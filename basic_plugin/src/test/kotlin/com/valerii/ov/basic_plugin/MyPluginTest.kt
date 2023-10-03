package com.valerii.ov.basic_plugin

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import org.junit.Test


class MyPluginTest : BasePlatformTestCase() {

    @Test
    fun testSingleClassAndMultipleMethods() { // Changed the method name
        // Prepare
        val file = myFixture.configureByText("MyClass.java", """
            public class MyClass {
                public void method1() {}
                public void method2() {}
            }
        """.trimIndent())

        // Execute
        val stats = FileAnalyzer(myFixture.project).analyzeFile(file.virtualFile)

        // Verify
        assertEquals(1, stats.classes)
        assertEquals(2, stats.methods)
    }

    @Test
    fun testMultipleClasses() { // Changed the method name
        // Prepare
        val file = myFixture.configureByText("MyClass.java", """
            public class MyClass {
                
            }
            
            class Second {
                    
            }
            
        """.trimIndent())

        // Execute
        val stats = FileAnalyzer(myFixture.project).analyzeFile(file.virtualFile)

        // Verify
        assertEquals(2, stats.classes)
    }

    @Test
    fun testInnerClass() { // Changed the method name
        // Prepare
        val file = myFixture.configureByText("MyClass.java", """
            public class Outer {
                class Inner {
                    
                }
            }
        """.trimIndent())

        // Execute
        val stats = FileAnalyzer(myFixture.project).analyzeFile(file.virtualFile)

        // Verify
        assertEquals(2, stats.classes)
    }

    @Test
    fun testAnotherFileType() { // Changed the method name
        // Prepare
        val file = myFixture.configureByText("MyClass.kt", """
            class MyClass {
                
            }
        """.trimIndent())

        // Execute
        val stats = FileAnalyzer(myFixture.project).analyzeFile(file.virtualFile)

        // Verify
        assertEquals(0, stats.classes)
    }

}
