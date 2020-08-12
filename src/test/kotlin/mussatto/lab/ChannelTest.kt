package mussatto.lab

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.junit.Test

class ChannelTest {

    @Test
    fun `Should publish stuff to channel and process it`(){

        val list = listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")
        val channel = Channel<String>(5)
        // wait for the whole test to finish
        runBlocking {

            coroutineScope{
                val processorJob = createProcessorJob(channel)
                val senderJobs = sendListToChannel(list, channel)
                senderJobs.joinAll()
                channel.close()
                processorJob.join()
            }

        }
    }

    private fun CoroutineScope.createProcessorJob(channel: Channel<String>): Job {
        return launch {
            //process untill channel closed
            for (message in channel) {
                println("PROCESSOR: processing ->$message<-")
                Thread.sleep(500)
            }
            println("OMG, THE CHANNEL IS CLOSED!")
        }
    }

    private fun CoroutineScope.sendListToChannel(list: List<String>, channel: Channel<String>): MutableList<Job> {
        val senderJobs = mutableListOf<Job>()
        list.forEach {
            val job = launch {
                channel.send("Hey processor, process $it")
                println("Sending super duper fast, until the capacity is hit.")
            }
            senderJobs.add(job)

        }
        return senderJobs
    }
}