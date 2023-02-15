import io.github.dingyi222666.compose.editor.text.Rope
import kotlin.test.Test

import kotlin.random.Random
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 * @author: dingyi
 * @date: 2023/2/14
 * @description:
 **/

class TextTest {

    private fun generateLargeText(): String {
        val sb = StringBuilder()
        val random = Random(0)
        for (i in 0..3000) {
            sb.append('a')
        }
        return sb.toString()
    }

    @Test
    fun testInsert() {
        println("----------------- Basic Insert -----------------")
        var test = Rope("Hello")
        test = test.insert("World")
        println(test)
        assertEquals("HelloWorld", test.toString())
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testLargeTextInsert() {
        println("----------------- Text Insert -----------------")
        val targetText = generateLargeText()

        var test1 = ImmutableText.valueOf("")

        measureTime {
            for (i in 0..1000) {
                test1 = test1.insert(0, targetText)
            }
        }.also {
            println("immutableText(textInsert): ${it.inWholeMilliseconds} ms")
        }

        var test2 = Rope("")

        measureTime {
            for (i in 0..1000) {
                test2 = test2.insert(0, targetText)
            }
        }.also {
            println("rope(textInsert): ${it.inWholeMilliseconds} ms")
        }

        val test3 = test1.toString()
        val test4 = test2.toString()
        println(" " + test3.length + " " + test4.length)

        assertEquals(test3, test4)
    }


    @OptIn(ExperimentalTime::class)
    @Test
    fun testLargeTextSubSequence() {
        println("----------------- Text SubSequence -----------------")
        val targetText = generateLargeText().repeat(1000)

        val test1 = ImmutableText.valueOf(targetText)

        var result1: CharSequence = ""

        measureTime {
            for (i in 0..1000) {
                result1 = test1.subSequence(0, 10)
            }
        }.also {
            println("immutableText(textSubSequence): ${it.inWholeMilliseconds} ms")
        }

        val test2 = Rope(targetText)

        var result2: CharSequence = ""

        measureTime {
            for (i in 0..1000) {
                result2 = test2.subSequence(0, 10)
            }
        }.also {
            println("rope(textSubSequence): ${it.inWholeMilliseconds} ms")
        }

        assertEquals(result1, result2)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testLargeTextDelete() {
        println("----------------- Text Delete -----------------")
        val targetText = generateLargeText().repeat(1000)

        var test1 = ImmutableText.valueOf(targetText)

        measureTime {
            for (i in 0..1000) {
                test1 = test1.delete(0, 10)
            }
        }.also {
            println("immutableText(textDelete): ${it.inWholeMilliseconds} ms")
        }

        var test2 = Rope(targetText)

        measureTime {
            for (i in 0..1000) {
                test2 = test2.delete(0, 10)
            }
        }.also {
            println("rope(textDelete): ${it.inWholeMilliseconds} ms")
        }

        val test3 = test1.toString()
        val test4 = test2.toString()
        println(" " + test3.length + " " + test4.length)

        assertEquals(test3, test4)
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun testLargeTextGet() {
        println("----------------- Text Get -----------------")
        val targetText = generateLargeText()

        val test1 = ImmutableText.valueOf(targetText)

        val list1 = arrayListOf<Char>()
        measureTime {
            for (i in 0..1000) {
                list1.add(test1.get(i))
            }
        }.also {
            println("immutableText(textGet): ${it.inWholeMilliseconds} ms")
        }

        val test2 = Rope(targetText)
        val list2 = arrayListOf<Char>()

        measureTime {
            for (i in 0..1000) {
                list2.add(test2.get(i))
            }
        }.also {
            println("rope(textGet): ${it.inWholeMilliseconds} ms")
        }

        assertEquals(list1, list2)
    }
}
