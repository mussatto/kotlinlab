package mussatto.lab

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

data class MyModel(val id:Int, val name:String)

class ListsOperationsTest {

    val myModels = listOf(
            MyModel(1,"Abraham"),
            MyModel(2, "Bruno"),
            MyModel(3, "Carlos"),
            MyModel(4, "Daniel"),
            MyModel(5, "Eduard"),
            MyModel(6, "Fabio"),
            MyModel(7, "Akira"),
            MyModel(8, "Alberto")
    )

    val myModels2 = listOf(
            MyModel(9, "Maria"),
            MyModel(10, "Julia"),
            MyModel(11, "Rebeca")
    )

    val listOfLists = listOf(myModels, myModels2)

    val myModelsMutable = mutableListOf(
            MyModel(1,"Abraham"),
            MyModel(2, "Bruno"),
            MyModel(3, "Carlos"),
            MyModel(4, "Daniel"),
            MyModel(5, "Eduard"),
            MyModel(6, "Fabio"),
            MyModel(7, "Akira"),
            MyModel(8, "Alberto")
    )


    @Test
    fun `Should retrieve only items with names starting with A`(){
        val filtered = myModels.filter { it.name.startsWith("A") }
        assertThat(filtered).hasSize(3)
        assertThat(filtered[0].name).isEqualTo("Abraham")
        println(filtered)
    }

    @Test
    fun `Should retrieve only id from items with names starting with A`(){
        val filtered = myModels.filter { it.name.startsWith("A") }.map { it.id }
        assertThat(filtered).hasSize(3)
        assertThat(filtered[0]).isEqualTo(1)
        println(filtered)
    }

    @Test
    fun `Should filter by index pair`(){
        val filtered = myModels.filterIndexed() { index, myModel -> index % 2 ==0 }.map { it.id }
        assertThat(filtered).hasSize(4)
        assertThat(filtered[0]).isEqualTo(1)
        println(filtered)
    }

    @Test
    fun `Should search by attribute on list`(){
        val filteredIndex1 = myModelsMutable.map { it.name }.binarySearch("Abraham")
        // filter using ID of given model
        val filteredIndex2 = myModelsMutable.binarySearch(MyModel(1,""), compareBy {it.id})
        assertThat(filteredIndex1).isEqualTo(0) // index
        assertThat(filteredIndex2).isEqualTo(0) // compared by id, it matches Carlos
        println(filteredIndex1)
        println(filteredIndex2)

    }

    @Test
    fun `Should transform a list of lists into one big list`(){
        val bigList = listOfLists.flatten()
        assertThat(bigList).hasSize(11)
        println(bigList)
    }
}