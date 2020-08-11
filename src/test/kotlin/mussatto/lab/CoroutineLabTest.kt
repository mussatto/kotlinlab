package mussatto.lab

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.time.Instant.now
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

class CoroutineLabTest {

    @Test
    fun `Should run blocking and create one coroutine per item in list with delay`() {
        val list = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        val context = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        runBlocking {
            list.forEach {
                launch(context) {
                    val time = measureTimeMillis {
                        delay(3000) // this frees up the thread to other tasks
                        println("Finished $it - ${now()}")
                    }

                    println("$it took $time milliseconds")
                }
            }
        }
        context.close()
    }

    @Test
    fun `Should create one coroutine per item in list with delay`() {
        val list = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        val context = Executors.newFixedThreadPool(1).asCoroutineDispatcher()

        runBlocking {
            val job = GlobalScope.launch {
                list.forEach {
                    launch(context) {
                        val time = measureTimeMillis {
                            delay(3000) // this frees up the thread to other tasks
                            println("Finished $it - ${now()}")
                        }

                        println("$it took $time milliseconds")
                    }
                }
            }

            context.close()
            job.join()
            println("Exit! But only after everything else finished.")
        }

    }

    @Test
    fun `Should create one coroutine per item in list with delay with suspend`() {
        runBlocking {
            doSomething()
        }
    }

    suspend fun doSomething() {
        val list = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        val context = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        val job = GlobalScope.launch {
            list.forEach {
                launch(context) {
                    val time = measureTimeMillis {
                        delay(3000) // this frees up the thread to other tasks
                        println("Finished $it - ${now()}")
                    }

                    println("$it took $time milliseconds")
                }
            }
        }

        context.close()
        job.join()
        println("Exit! But only after everything else finished.")
    }

    @Test
    fun `Should create one thread per item in list with delay`() {
        val list = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        val context = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        runBlocking {
            list.forEach {
                launch(context) {
                    delay(1000) // this frees up the thread to other tasks
                    println(it)
                }
            }
        }
        context.close()
    }

    @Test
    fun `Should create one coroutine per item in list with sleep`() {
        val list = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        val context = Executors.newFixedThreadPool(2).asCoroutineDispatcher()
        runBlocking {
            list.forEach {
                launch(context) {
                    Thread.sleep(1000) // this 'locks' the thread
                    println(it)
                }
            }
        }
        context.close()
    }

    @Test
    fun `Should query google in parallel`() {
        val wordsToQuery = listOf("kotlin", "coroutine", "banana", "apple", "something", "bol.com", "mussatto.github.io")
        val context = Executors.newFixedThreadPool(7).asCoroutineDispatcher()
        val client = HttpClient()
        runBlocking {
            wordsToQuery.forEach {
                launch(context) {
                    println("Started:${now()}")
                    val resp = client.get<String>("https://www.google.com/search?q=$it")
                    println("Finished:${now()}")
                }
            }
        }
        context.close()
    }
}