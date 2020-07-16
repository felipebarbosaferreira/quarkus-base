package github.com.felipebarbosaferreira;

import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import github.com.felipebarbosaferreira.task.Task;
import github.com.felipebarbosaferreira.task.TaskRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class TaskRepositoryTest {
	@InjectMock
	TaskRepository taskRepository;
	
	@Test
	public void testGetById() {
		Assertions.assertEquals(0, taskRepository.count());
		
		Task task = new Task("02:15:B2:00:00:00", 2, "Teste task Quarkus Mockito", "Fazendo o teste do taskRepository", new Date());
		ObjectId objectId = new ObjectId("5ec5b421e7e9d736feac78fd");
		
		Mockito.when(taskRepository.getById(objectId)).thenReturn(task);
		Assertions.assertEquals(taskRepository.getById(objectId), task);
	}

}
