# AndroidBasicNanoDegree_Project3
HystoQuiz - An history quiz based on National Geographic History magazine.

This project target is to realize a quiz app on something we know. Since I add a "Historica" (The italian version of National Geographic "History") magazine nearby, I decided to create a sort of commercial app that, with the proper improvements, could be used by National Geographic for advertising purposes.

The main features are:
* For type of question: Completion, True/False, Multiple Choice and Multiple Answer.
* There is not a standard layout. A JSON file is compiled with the desired question list (with the proper syntax). The app reads it when started and dynamically generates the layout.
* A progress bar follows the user below the main layout, so that we knows what's left to do.
* The test can be reset at any time.
* Submitting the test open a dialog box (not a toast as stated in the project rubric. I hope to be forgiven!) with results, commenting them and allowing the user to choose whether to change their answer or starting from scratch. 
* Each question can have either a positive or negative score. Any unanswered question is assigned a zero score. (This may differ a bit from the rubric assigned grading logic, but the rubric one is a bit ambigous to me...)

Brief rules:
* Completions are assigned a score based on the Levenshtein Distance between the correct answer and the given one.
* True/Falses are given a score +1 when the correct choice is selected, -1 otherwise.
* Multiple Choices are given a score +1 when the correct choice is selected, -1 otherwise.
* Multiple Answer are given a score based on the number of correct anwers versus the wrong ones.

If you want to check the answers (or change them :)), you can check the json settings file under app/src/main/assets/.


What could be further improved:
* Answers correction
* Check for test completion when trying to submit.
* More error control (reading from a manually generated JSON file...).
* Improve the completion question string similarity algorithm. A valid one should recognize the meaning more than the form. But this is quite a long step to perform.
* Improve the design, but I would't be able to do it, no matter what :).

**N.B.:** This time I wrote in the code any time I got something from the web and I compiled a text file (under app\src\main\res\Credits.txt) where I kept trace of the resource file origin. Some of them are protected by copyright (especially since I used the NG logo everywhere!) and are viable only for a demo app.  
