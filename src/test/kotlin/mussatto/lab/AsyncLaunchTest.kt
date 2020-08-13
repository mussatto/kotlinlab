package mussatto.lab

import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class AsyncLaunchTest {

    @Test
    fun `async should return a value`(){
        runBlocking {
            val job = async {
                println("Running on the background!")
                Thread.sleep(1000)
                "this is done!"
            }

            println("Response is ${job.await()}")
        }
    }

    @Test
    fun `launch should not return a value`(){
        runBlocking {
            val job = launch {
                println("Running on the background!")
                Thread.sleep(1000)
//                "this is done!"
            }

            // println("Response is ${job.await()}") -- doesn't work :(
            job.join()
        }
    }
}