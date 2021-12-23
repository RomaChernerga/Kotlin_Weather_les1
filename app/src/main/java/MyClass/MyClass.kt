package MyClass

import java.lang.Exception
import kotlin.random.Random

fun main () {

     //val list = listOf<String>()  // пример джереника - шаблонный метод


//    println("Работа с коллекциями".split(" ")  // разделим по словам
//        .map{
//            it.uppercase()
//        }
//        .map {
//            it.reversed() // разворачиваем каждое слово
//        }
//        .filter { it.length > 2 }  // берем только слова, которые больше 2
//        .joinToString("_","[", "]"  // склеиваем через подчеркивание
//        ))

    val list = listOf("one", "two")
    var list2 = mutableListOf("one", "two")
    val map = mapOf<String, Int>("one" to 1,
                                "two" to 2,
                                Pair("three", 3)
    )
//    list2.forEach { i -> println(i) }   //пробежаться по листу2 и вывести значения



//    val list3 = arrayListOf("one", "two")
//    list3.add("three")
//    println(map.values)

    var notNullable: String = "test test testfsdfs"
    var nullable: String? = "test test testfsdfs"

    nullable = if(Random.nextBoolean()) { null } else { "null" }

//    val size = nullable?.length ?:   Использование оператора безопасного вызова
    val size: Int = if (nullable != null) {nullable.length} else { 0 } // альтернативный вариант строчки выше

    val sizeLastWord1: Int = nullable?.split(" ")?.last()?.length ?: 0  // тут нужно использовать ?
    val sizeLastWord2: Int = notNullable.split(" ").last().length     // тут НЕ нужно использовать ?

    // ?    ?.  ?:  !!

    //if (nullable != null) { notNullable = nullable }

    //if(nullable == null) { throw Exception("Null")} else {notNullable = nullable}  АЛЬТЕРНАТИВНЫЙ ВАРИАНТ СТРОЧКИ НИЖЕ
//    notNullable = nullable!!

//    if(nullable != null) { notNullable = nullable } else { notNullable = "null" }

//    notNullable = if(nullable != null) { nullable } else { "null" }
//    notNullable = nullable ?: "null string"  // оператор элвиса, строчка говорит, что мы Null кладем в NotNull, если он не null, в противн случае положим строку

//    println(size)

    val any: Any = "String"
    val string = any as String

    val integer = 1
    val double = integer.toDouble()  // приведение типов

}

