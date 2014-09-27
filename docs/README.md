AndroidToDoList
===============

Simple Android todo-list application.

http://www.michaelevans.org/blog/2013/08/14/tutorial-building-an-android-to-do-list-app-using-parse/

Use:

Navigate to different activities with the menu button. Long click on items
to perform operations on them, such as emailing, deleting. Click on items
to set them as completed.


Classes:

todoItem: 
-base class that implements an invididual todo item.
	Variables:
	-status: bool, done or not
	-name: name of task
	methods:
	-constructor: Instantiated with a name. Undone by defualt
	-isDone(): Returns true if done, false if not
	-toString(): Returns name
	-setStatus(): Sets status of task, false for not done, true for done.
