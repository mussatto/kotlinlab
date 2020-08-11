package mussatto.lab

import kotlinx.coroutines.async
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.*
import kotlin.collections.ArrayList
import kotlin.system.measureTimeMillis

class ListFindTest {

//    @Test
    fun `Should find item in list`() {

        val list = ArrayList<Long>()
        val r = Random()
        for (i in 1..10000000) {
            list += r.nextLong()
        }

        val randomIndex = r.nextInt(list.size)
        var indexFound: FindResponse = FindResponse(0, 0)
        val valueToFind = list[randomIndex]
        val elapsed = measureTimeMillis {
            indexFound = list.findItSequential(valueToFind)
        }
        assertThat(randomIndex).isEqualTo(indexFound.index)

        println("This test took $elapsed milliseconds to find $valueToFind, index=$randomIndex, steps=${indexFound.steps}")
    }

//    @Test
    fun `Should find item in list parallel`() {

        val list = ArrayList<Long>()
        val r = Random()
        for (i in 1..10000000) {
            list += r.nextLong()
        }

        val randomIndex = r.nextInt(list.size)
        var indexFound: FindResponse = FindResponse(0, 0)
        val valueToFind = list[randomIndex]
        val elapsed = measureTimeMillis {
            indexFound = list.findItParallel(valueToFind)
        }
        assertThat(randomIndex).isEqualTo(indexFound.index)

        println("This test took $elapsed milliseconds to find $valueToFind, index=$randomIndex, steps=${indexFound.steps}")
    }

    private fun ArrayList<Long>.findItSequential(value: Long): FindResponse {
        var steps = 0
        for (i in 0 until this.size) {
            steps++
            if (value == this[i]) {
                return FindResponse(i, steps)
            }
        }
        return FindResponse(-1, steps)
    }

    private fun ArrayList<Long>.findItParallel(value: Long): FindResponse {
        val me = this
        var found = -1

        val context = newFixedThreadPoolContext(4, "CustomPoolContext")

        runBlocking {
            me.mapIndexed{ index, it ->
                async(context) {
                    if (it == value) {
                        found = index
                    }
                }
            }
        }

        return FindResponse(found, -1)
    }
}

data class FindResponse(val index: Int, val steps: Int)