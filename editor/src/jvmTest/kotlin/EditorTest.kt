import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

/**
 * @author: dingyi
 * @date: 2023/2/13
 * @description:
 **/
@OptIn(ExperimentalTestApi::class)
class Scratch {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun test() {
        runBlocking(Dispatchers.Unconfined) {
            compose.awaitIdle()


        }
    }
}