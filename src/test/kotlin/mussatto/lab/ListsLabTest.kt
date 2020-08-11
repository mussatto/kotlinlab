package mussatto.lab

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class ListsLabTest{

    @Test
    fun `Should remove from list`(){
        assertThat(listOf("A","B","C","D","E").filter { it != "B" }).isEqualTo(listOf("A","C","D","E"))

        assertThat(listOf("A","B") - listOf("B")).isEqualTo(listOf("A"))
    }

    @Test
    fun `Should transform into 1 string`(){
        assertThat(listOf("A","B").joinToString(separator = "")).isEqualTo("AB")
    }

    @Test
    fun `Should concatenate two lists`(){
        assertThat(listOf("A","B") + listOf("C","D")).isEqualTo(listOf("A","B","C","D"))
    }

    @Test
    fun `Should sum all items from a list`(){
        assertThat(listOf(1,2,3,4,5).sum()).isEqualTo(15)
    }

    @Test
    fun `Should transform attributes to 1 string`(){
        val listOfObjects = listOf(
            SomeObject("A1", "B1", 1),
            SomeObject("A2", "B2", 2),
                    SomeObject("A3", "B3", 3)
        )

        assertThat(listOfObjects.map { it.a }.joinToString(separator = "")).isEqualTo("A1A2A3")
    }

    @Test
    fun `Should transform object to a list of strings`(){
        val listOfObjects = listOf(
            SomeObject("A1", "B1", 1),
            SomeObject("A2", "B2", 2),
            SomeObject("A3", "B3", 3)
        )

        assertThat(listOfObjects.map { it.a }).isEqualTo(listOf("A1", "A2","A3"))
    }

    @Test
    fun `Should transform object to a list of composed strings`(){
        val listOfObjects = listOf(
            SomeObject("A1", "B1", 1),
            SomeObject("A2", "B2", 2),
            SomeObject("A3", "B3", 3)
        )

        assertThat(listOfObjects.map { it.a + it.b}).isEqualTo(listOf("A1B1", "A2B2","A3B3"))
    }

    @Test
    fun `Should sum all attributes from an object`(){
        val listOfObjects = listOf(
            SomeObject("A1", "B1", 1),
            SomeObject("A2", "B2", 2),
            SomeObject("A3", "B3", 3)
        )

        assertThat(listOfObjects.map{it.c}.sum()).isEqualTo(6)
    }


}

data class SomeObject(val a:String, val b:String, val c:Int)