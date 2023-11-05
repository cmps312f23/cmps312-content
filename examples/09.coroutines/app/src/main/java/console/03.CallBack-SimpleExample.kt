package coroutines.console

class NetworkLibrary {
    fun networkCall(url: String, callback: (String) -> Unit) {
        Thread.sleep(1000)
        callback("networkCall result")
    }
}

// https://gist.github.com/vitalybe/34a1d94fad036a66f0c964539093018f
fun main() {
    val networkLibrary = NetworkLibrary()
    // make a networkCall and pass a callback function to get the results
    networkLibrary.networkCall(url = "https://restcountries.com/v3/all",
            callback = {  println(it) }
    )
}

