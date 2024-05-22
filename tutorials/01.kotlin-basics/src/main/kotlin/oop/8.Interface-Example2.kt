package oop

class Book()

//Interface does not have an implementation
//Interface can be implemented by different classes
interface BookRepository {
    fun getBook(isbn: String) : Book
    fun addBook(book: Book)
}

class FileBookRepository() : BookRepository {
    override fun getBook(isbn: String) : Book {
        return Book()
    }

    override fun addBook(book: Book) {}
}

class DBBookRepository() : BookRepository {
    override fun getBook(isbn: String) : Book {
        return Book()
    }

    override fun addBook(book: Book) {}
}

fun main() {
    val bookRepository: BookRepository
    bookRepository = DBBookRepository()
    // Programming more generic - See polymorphism studied in CMPS 251
    bookRepository.getBook("1234")
}