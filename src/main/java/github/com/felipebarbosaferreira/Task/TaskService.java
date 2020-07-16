package github.com.felipebarbosaferreira.Task;

import java.text.ParseException;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.types.ObjectId;

@ApplicationScoped
public class TaskService {
	@Inject
	TaskRepository taskRepository;
	
	public Task getById(String id) {
		return taskRepository.getById(new ObjectId(id));
	}

	public List<Task> getAll() {
		return taskRepository.getAll();
	}

	public List<Task> getAllByState(Boolean isDone) {
		return taskRepository.getAllByState(isDone);
	}

	public void createTask(Task task) throws ParseException {
		taskRepository.createTask(
				new Task(task.getMacaddress(), task.getType(), task.getTitle(), task.getDescription(), task.getWhen()));
	}
	
	public void updateTask(Task task) {
		taskRepository.updateTask(task);
	}
	
	public void deleteTask(String id) {
		taskRepository.deleteTask(new ObjectId(id));
	}
}
