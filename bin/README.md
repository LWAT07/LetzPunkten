# --------------LëtzPunkten--------------

# Welcome to my project, where I built an app to save the grades of your exams in Luxembourgish shool system.

# What is LëtzPunkten?
LëtzPunkten is an app made in Java with the javax.swing library it was entirely made with Microsofts Visual Studio Code. To save the files I used Googles Gson library

# How does it works?
First you need to create a file with all the information of your subjects and coefficients all the subjects. You can create this . json file directly inside of the app in the left menu. If you aleready have a classFile for your specific class you can import the file with presing "Select Class File", you can now select your .json file. The app automaticiy calculates all of your averages so that you always know where to improve. All of your Data is saved in that same .json file.
You can even configure "Mathe I" and "Mathe II" in higher classes in the Luxembourgish school system. 

# How to install?
Simply open installer an follow the step of teh installation wizard, then go to the programm files folder and open the app.

# What was Done?
Cursor in the create exam and edit exam dialog is directly set to the grade text field
when creating a classfile, you can now directly go to the next label with the enter button
while changing the classfile, examdata was being transfered between files (niw it is fixed)
Allowed to use big class names with propper resizing
Subjectframe is now always fully visible if it is in the top left corner
Scrollpane where exams are saved
It is now guaranteed that the app will launch inside of the bounds of the screen
Classes are sorted in packages 
While creating a class file, subjects can be edited after they have been created.


# To-Do?
 - Dark mode and changing colors

# DeveDeveloped and maintained by
 - Leo Watgen <watgen.leo@gmail.com>

MIT License

Copyright (c) [2024] [Leo Watgen]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions ->

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.