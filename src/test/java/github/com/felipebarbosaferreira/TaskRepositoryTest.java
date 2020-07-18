package github.com.felipebarbosaferreira;

import java.util.Date;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import github.com.felipebarbosaferreira.task.Task;
import github.com.felipebarbosaferreira.task.TaskRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@QuarkusTestResource(MongoDBLifeCycleControlTest.class)
public class TaskRepositoryTest {
	@Inject
	TaskRepository taskRepository;

	@Test
	public void testTaskRepositoryMocking() {
		Assertions.assertEquals(0, taskRepository.count());
		Assertions.assertTrue(taskRepository.getAll().isEmpty());

		// persist a task
		Task taskMock1 = taskMock("Quarkus Teste primeira tarefa");
		taskRepository.saveTask(taskMock1);
		Assertions.assertFalse(taskRepository.getAll().isEmpty());
		Assertions.assertEquals(1, taskRepository.count());

		// persist another task
		Task taskMock2 = taskMock("Quarkus Teste segunda tarefa");
		taskRepository.saveTask(taskMock2);
		Assertions.assertEquals(2, taskRepository.count());
		
		// find by id
		Assertions.assertEquals(taskMock1, taskRepository.getById(taskMock1.getId()));
		
		// get all
		Assertions.assertEquals(2, taskRepository.getAll().size());
		Assertions.assertEquals(2, taskRepository.getAllByState(Boolean.FALSE).size());
		
		// update task
		Assertions.assertEquals(taskMock2, taskRepository.getAll().get(1));
		Task updateTask2 = taskRepository.getAll().get(1);
		updateTask2.done = true;
		taskRepository.update(updateTask2);
		Assertions.assertEquals(1, taskRepository.getAllByState(Boolean.TRUE).size());
		Assertions.assertEquals(1, taskRepository.getAllByState(Boolean.FALSE).size());
		Assertions.assertEquals(updateTask2, taskRepository.getById(updateTask2.getId()));
		
		// delete task
		taskRepository.deleteTask(taskMock2.getId());
		Assertions.assertEquals(1, taskRepository.getAll().size());
		Assertions.assertEquals(1, taskRepository.getAllByState(Boolean.FALSE).size());
		
		taskRepository.deleteTask(taskMock1.getId());
		Assertions.assertEquals(0, taskRepository.count());
		Assertions.assertTrue(taskRepository.getAll().isEmpty());
	}

	public Task taskMock(String nameTask) {
		return new Task("02:15:B2:00:00:00", 2, nameTask, "Descricao taskRepository " + nameTask, new Date());
	}

}
