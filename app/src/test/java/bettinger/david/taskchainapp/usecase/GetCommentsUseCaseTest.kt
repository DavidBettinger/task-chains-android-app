package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.CommentEntity
import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.entity.TaskEntity
import bettinger.david.taskchainapp.data.entity.UserEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.scheduler.TrampolineSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.BDDMockito
import org.mockito.Mockito

class GetCommentsUseCaseTest {

    private var repository = Mockito.mock(TaskChainRepository::class.java)
    private var schedulerProvider = TrampolineSchedulerProvider()

    private var commentEntity = CommentEntity("test", 2, 1, "date")
    private var userEntity = UserEntity(2, "User", "User")

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `buildUseCaseObservable$app_debug`() {
        //given
        val getCommentsUseCase = GetCommentsUseCase(repository, schedulerProvider)
        BDDMockito.given(repository.getCommentsForTask(1)).willReturn(Observable.just(listOf(commentEntity)))
        BDDMockito.given(repository.getUser(2)).willReturn(Single.just(userEntity))

        //when
        var response: List<CommentEntity>? = null
        getCommentsUseCase.setTaskId(1)
        getCommentsUseCase.execute(
            onNext = {
                response = it
            },
            onError = {
                it.printStackTrace()
            }
        )

        //then
        assertNotNull(response)
        assertEquals(response?.get(0)?.createdBy, userEntity)
    }
}