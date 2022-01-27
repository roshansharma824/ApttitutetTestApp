package com.example.apttitutetest

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlin.math.abs
import kotlinx.android.synthetic.main.activity_question.*

class QuestionsActivity : AppCompatActivity() {
    companion object {
        var allJoined: ArrayList<JoinedFeed> = ArrayList()
        var selectedAnswers: ArrayList<String> = ArrayList()
        var questionNr: Int = 0
        var isCorrect: Int = 0
        var isFailed: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_question)

        val endpoint: String = "https://opentdb.com/api.php?amount=10&difficulty=easy&type=multiple"
        val questions: ArrayList<String> = ArrayList()
        val allanswers: ArrayList<ArrayList<String>> = ArrayList()
        val allcorrectanswers: ArrayList<String> = ArrayList()

        done.visibility = View.GONE

        val httpAsync = endpoint
            .httpGet()
            .responseString { request, response, result ->
                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                    is Result.Success -> {
                        val data: String = result.get()
                        val gson = GsonBuilder().create()

                        val mainData: AllResults = gson.fromJson(data, AllResults::class.java)
                        for ((index, value) in mainData.results.withIndex()) {

                            val mainFeed = mainData.results
                            val question = mainFeed[index].question
                            questions.add(question)

                            val answers = mainFeed[index].incorrect_answers
                            answers.add((0..3).random(), mainFeed[index].correct_answer)
                            allanswers.add(answers)

                            val canswers = mainFeed[index].correct_answer
                            allcorrectanswers.add(canswers)
                        }
                    }
                }
            }

        httpAsync.join()

        allJoined.add(
            JoinedFeed(
                questions = questions,
                answers = allanswers,
                correct_answer = allcorrectanswers
            )
        )

        startQuiz()
    }

    private fun startQuiz() {
        val nextbtn = findViewById<ImageButton>(R.id.next_btn)
        val totalnum: TextView = findViewById<TextView>(R.id.total_num)
        val mainquestion: TextView = findViewById<TextView>(R.id.main_question)
        val donelayout: ConstraintLayout = findViewById(R.id.done)
        val quizlayout: ConstraintLayout = findViewById(R.id.quiz)
        val donepop: ListView = findViewById(R.id.done_pop)

        // Display Number
        var questionNum = questionNr

        // Get current Question
        val currentQuestion = allJoined[0].questions[questionNr]

        // Grab the listview
        val answerListView: ListView = findViewById(R.id.answers_container)

        // Increase the Display number to +1
        questionNum++
        totalnum.text = "$questionNum/${allJoined[0].questions.count()}"

        // Set the first Question
        mainquestion.text = currentQuestion

        // Set the first Question Answers
        var qanswers: ArrayList<String> = allJoined[0].answers[questionNr]
        setAnswers(qanswers)

        answerListView.setOnItemClickListener { parent, view, position, id ->
            val clickedID = id.toInt()
            val correctanswer = allJoined[0].correct_answer[questionNr]
            val selectedanswer = allJoined[0].answers[questionNr][clickedID]
            val answerIsCorrect = selectedanswer == correctanswer

            // Check if answer is correct
            if (answerIsCorrect) {
                isCorrect++
            } else {
                isFailed--
            }

            if (questionNr == allJoined[0].questions.count() - 1 && questionNum === 10) {
                quizlayout.visibility = View.GONE
                donelayout.visibility = View.VISIBLE

                val info: DoneFeed = DoneFeed(
                    qNumbers = "${allJoined[0].questions.count()}",
                    qCorrectAnswer = "${isCorrect}",
                    qNegetive = "${abs(isFailed)}",
                    qAttemppted = "${10}"
                )
                donepop.adapter = DoneAdapter(this, info)
            } else {
                questionNum++
                questionNr++
            }

            totalnum.text = "$questionNum/${allJoined[0].questions.count()}"
            mainquestion.text = allJoined[0].questions[questionNr]

            //update answers
            val newAnswers = allJoined[0].answers[questionNr]
            setAnswers(newAnswers)
        }


    }

    private fun setAnswers(qanswers: ArrayList<String>) {
        val answers: ListView = findViewById(R.id.answers_container)
        for ((index, value) in qanswers.withIndex()) {
            answers.adapter = AnswerAdapter(this, qanswers)
        }
    }

}

