// FILE: 1.kt
package test2

abstract class Introspector {
    abstract inner class SchemaRetriever(val transaction: String) {
        inline fun inSchema(crossinline modifier: (String) -> Unit) =
                { modifier.invoke(transaction) }()
    }
}

// FILE: 2.kt
package test

import test2.*

var result = "fail"

class IntrospectorImpl() : Introspector() {
    inner class SchemaRetriever(transaction: String) : Introspector.SchemaRetriever(transaction) {
        internal fun retrieve() {
            inSchema { schema -> result = schema }
        }
    }
}

fun box(): String {
    IntrospectorImpl().SchemaRetriever("1OK").retrieve()

    return result
}


// SMAP