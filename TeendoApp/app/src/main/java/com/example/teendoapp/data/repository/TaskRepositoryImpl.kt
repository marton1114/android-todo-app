package com.example.teendoapp.data.repository


import com.example.teendoapp.data.model.Response
import com.example.teendoapp.data.model.Task
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepositoryImpl @Inject constructor (
    private val tasksCollectionReference: CollectionReference
): TaskRepository {
    override fun getTasksByUserId(userId: String): Flow<Response<List<Task>>> = callbackFlow {
        tasksCollectionReference
            .whereEqualTo("userId", userId)
            .addSnapshotListener { snapshot, e ->
                trySend(
                    if (snapshot != null) {
                        Response.Success(snapshot.toObjects(Task::class.java))
                    } else {
                        Response.Failure(e as Exception)
                    }
                )
            }
        awaitClose()
    }

    override suspend fun addTask(task: Task): Flow<Response<Boolean>> = callbackFlow {
        val documentId = tasksCollectionReference.document().id

        val taskToAdd = task.copy(id = documentId)

        tasksCollectionReference
            .document(documentId)
            .set(taskToAdd)
            .addOnSuccessListener {
                trySend(Response.Success(true))
            }
            .addOnFailureListener { e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }

    override suspend fun deleteTaskById(taskId: String): Flow<Response<Boolean>> = callbackFlow {
        tasksCollectionReference
            .document(taskId)
            .delete()
            .addOnSuccessListener {
                trySend(Response.Success(true))
            }
            .addOnFailureListener {e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }

    override suspend fun updateTask(task: Task): Flow<Response<Boolean>> = callbackFlow {
       val updatedDataMap = hashMapOf<String, Any>(
            "titleId" to task.titleId,
            "title" to task.title,
            "description" to task.description,
            "progression" to task.progression,
            "startTime" to task.startTime,
            "endTime" to task.endTime,
            "finishTime" to task.finishTime,
            "finished" to task.finished,
        )

        tasksCollectionReference
            .document(task.id)
            .update(updatedDataMap)
            .addOnSuccessListener {
                trySend(Response.Success(true))
            }
            .addOnFailureListener { e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }
}