Interactions between MongoDB and MySQL in Grails/Groovy 
=======================================================
**version 1.0.0**  
You can import your json files dynamically into MySQL database
using dynamic domain calls. You can also get all properties
of MongoDB with downloading a json file.


Main functionalities
--------------------
1. Upload *.json file
2. Download *.json file
3. Import to MySQL
4. Export from MongoDB
---
Sample *.Json
----
To import your data into MySQL database, your json file should look
like the below sample:
```
{
	"Book":[
		{
			"author":"F Scott Fitzgerald",
			"title":"The Great Gatsby"
		},
		{
			"author":"J.K Rowling",
			"title":"Harry Potter"
		}
	]
}
```  
Initialize MongoDB
---
In order to save mock data to MongoDB, you can call the provided 
method which is:
[/IMM/file/saveToMongo](http://localhost:8090/IMM/file/saveToMongo)
This allows you to have some data in your MongoDB.  

Usage
---


About
---


###Author
Hamid Farmani

* [github/hamidfarmani](https://github.com/hamidfarmani)
* [hamidfarmani1@gmail.com](emailto:hamidfarmani1@gmail.com?subject=)


###License
Copyright © 2020, NeoVision.am



