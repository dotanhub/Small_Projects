a Program which runs a library (with low time complexity), implemented with hash tables and linked list:
each subscriber has the folowing characteristics:
last name, ID, codes of books (in the form: "XX1234")
each subscriber has maximum 10 books.

input options:
rent a book - "*name* *ID* *book code* +"
return a book - "*name* *ID* *book code* -"
new subscriber - "+ *name* *ID*"
delete subscriber - "- *name* *ID*"

input queries:
which books has a subscriber: "? *ID*"
which subscriber has the book: "? *book code*"
who are all the subscribers that currently have the most books: "? !"

a full explanation of the algorithm and complexity on "documentation.docx" file (in hebrew)
