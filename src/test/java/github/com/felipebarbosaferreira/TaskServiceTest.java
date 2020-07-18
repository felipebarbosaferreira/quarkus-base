package github.com.felipebarbosaferreira;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import github.com.felipebarbosaferreira.task.Task;
import github.com.felipebarbosaferreira.task.TaskRepository;
import github.com.felipebarbosaferreira.task.TaskService;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class TaskServiceTest {
	@InjectMock
	TaskRepository taskRepository;

	@Inject
	TaskService taskService;

	public void setup() {
		Task taskMock1 = taskMock("Task Mock primeira");
		Task taskMock2 = taskMock("Task Mock segunda");
		Task taskMock3 = taskMock("Task Mock terceira");
		taskMock3.done = Boolean.TRUE;
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(taskMock1);
		tasks.add(taskMock2);
		tasks.add(taskMock3);

		TaskRepository mock = Mockito.mock(TaskRepository.class);
		Mockito.when(mock.getAll()).thenReturn(tasks);
		Mockito.when(mock.getAllByState(Boolean.TRUE))
				.thenReturn(tasks.stream().filter(it -> it.getDone()).collect(Collectors.toList()));
		Mockito.when(mock.getAllByState(Boolean.FALSE))
				.thenReturn(tasks.stream().filter(it -> !it.getDone()).collect(Collectors.toList()));
		Mockito.when(mock.getById(Mockito.any(ObjectId.class))).thenReturn(taskMock1);
		QuarkusMock.installMockForType(mock, TaskRepository.class);
	}

	@Test
	public void testGetAllEmpty() {
		Assertions.assertTrue(taskService.getAll().isEmpty());
	}

	@Test
	public void testGetWithData() {
		setup();
		Assertions.assertFalse(taskService.getAll().isEmpty());
		Assertions.assertEquals(3, taskService.getAll().size());
		Assertions.assertEquals("Task Mock primeira", taskService.getAll().get(0).getTitle());
		Assertions.assertEquals("Task Mock segunda", taskService.getAll().get(1).getTitle());
		Assertions.assertEquals(taskService.getById("5ec5b421e7e9d736feac78fd").getTitle(),
				taskService.getAll().get(0).getTitle());
		Assertions.assertEquals(1, taskService.getAllByState(Boolean.TRUE).size());
		Assertions.assertEquals(2, taskService.getAllByState(Boolean.FALSE).size());
		Assertions.assertEquals("Task Mock terceira", taskService.getAllByState(Boolean.TRUE).get(0).getTitle());

	}

	@Test
	public void testCreateTask() throws ParseException {
		Task task = taskMock("Teste de criar task");
		Mockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Task taskInput = (Task) invocation.getArguments()[0];
				Assertions.assertEquals(task.getTitle(), taskInput.getTitle());
				return null;
			}
		}).when(taskRepository).saveTask(Mockito.any(Task.class));
		taskService.saveTask(task);
		Mockito.verify(taskRepository).saveTask(Mockito.any(Task.class));
	}

	@Test
	public void testUpdateTask() throws ParseException {
		Task task = taskMock("Teste de update task");
		taskService.updateTask(task);
		Mockito.verify(taskRepository).updateTask(Mockito.any(Task.class));
	}

	@Test
	public void testDeleteTask() throws ParseException {
		taskService.deleteTask("5ec5b421e7e9d736feac78fd");
		Mockito.verify(taskRepository).deleteTask(Mockito.any(ObjectId.class));
	}

	private Task taskMock(String nameTask) {
		return new Task("02:15:B2:00:00:00", 2, nameTask, "Descricao TaskServiceTest " + nameTask, new Date());
	}
}
