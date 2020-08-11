package mussatto.lab

import kotlinx.coroutines.delay
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class KotlinElapsedTest {

    @Test
    fun `Should calculate elapsed in millis`(){
        val elapsed = measureTimeMillis {
            Thread.sleep(1000L)
        }
        assertThat(elapsed).isGreaterThan(1000L)
    }

    @ExperimentalTime
    @Test
    fun `Should calculate elapsed in time unit`(){
        val elapsed = measureTime {
            Thread.sleep(1100L)
        }
        assertThat(elapsed.inSeconds).isGreaterThan(1.0)

        println("micro=${elapsed.inMicroseconds}, nano=${elapsed.inNanoseconds}, milli=${elapsed.inMilliseconds}")
    }


}