package bettinger.david.taskchainapp.usecase

import bettinger.david.taskchainapp.data.entity.TaskChainEntity
import bettinger.david.taskchainapp.data.entity.UserEntity
import bettinger.david.taskchainapp.data.repository.TaskChainRepository
import bettinger.david.taskchainapp.usecase.scheduler.TrampolineSchedulerProvider
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.*
import org.mockito.Mockito.mock

import org.junit.Assert.*

class GetTaskChainUseCaseTest {

    private var repository = mock(TaskChainRepository::class.java)
    private var schedulerProvider = TrampolineSchedulerProvider()
    private var taskChainEntity = TaskChainEntity(
        1, "12.01.2020", "test",
        "TestChain", "Unit Test", false, 2
    )
    private var userEntity = UserEntity(2, "User", "User")

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `buildUseCaseSingle$app_debug`() {
        //given
        val getTaskChainUseCase = GetTaskChainUseCase(repository, schedulerProvider)
        given(repository.getTaskChain(1)).willReturn(Single.just(taskChainEntity))
        given(repository.getUser(2)).willReturn(Single.just(userEntity))

        //when
        var response: TaskChainEntity? = null
        getTaskChainUseCase.setTaskChainId(1)
        getTaskChainUseCase.execute(
            onSuccess = {
                response = it
            },
            onError = {
                it.printStackTrace()
            }
        )

        //then
        assertNotNull(response)
        assertEquals(response?.createdByUser, userEntity)
    }
}