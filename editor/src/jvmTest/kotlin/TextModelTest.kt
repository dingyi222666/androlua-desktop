import io.github.dingyi222666.compose.editor.text.TextModel
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author: dingyi
 * @date: 2023/2/17
 * @description:
 **/
class TextModelTest {

    @Test
    fun testInsert() {
        val textModel = TextModel()
        textModel.insert("abc\ndfg")
        textModel.insert("hij\nkln")
        assertEquals("abc\ndfghij\nkln", textModel.text.toString())
    }

    @Test
    fun testInsertAndGetLineCount() {
        val textModel = TextModel()
        textModel.insert("abc\ndfg\n")
        textModel.insert("hij\nkln")
        assertEquals(4, textModel.getLineCount())
    }

    @Test
    fun testInsertAndGetLineStartOffset() {
        val textModel = TextModel()
        textModel.insert("abc\ndfg\n")
        textModel.insert("hij\nkln")
        assertEquals(0, textModel.getLineStartOffset(1))
        assertEquals(4, textModel.getLineStartOffset(2))
        assertEquals(8, textModel.getLineStartOffset(3))
        assertEquals(12, textModel.getLineStartOffset(4))
    }

    @Test
    fun testInsertAndGetLineContent() {
        val textModel = TextModel()
        textModel.insert("abc\ndfg\n")
        textModel.insert("hij\nkln")
        assertEquals("abc", textModel.getLineContent(1).toString())
        assertEquals("dfg", textModel.getLineContent(2).toString())
        assertEquals("hij", textModel.getLineContent(3).toString())
        assertEquals("kln", textModel.getLineContent(4).toString())
    }
}