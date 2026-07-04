package com.demo.mcqquiz.data.datasource.local

import android.content.Context
import com.demo.mcqquiz.data.model.QuestionDto
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class QuestionLocalDataSourceImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : QuestionLocalDataSource {

    override suspend fun getQuestions(): List<QuestionDto> {

        return try {

            val json = context.assets
                .open("questions.json")
                .bufferedReader()
                .use { it.readText() }

            Json {
                ignoreUnknownKeys = true
            }.decodeFromString(json)

        } catch (exception: IOException) {

            emptyList()

        }
    }
}