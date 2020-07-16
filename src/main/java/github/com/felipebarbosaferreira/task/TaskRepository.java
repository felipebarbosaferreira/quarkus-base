package github.com.felipebarbosaferreira.task;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.PanacheMongoRepository;

@ApplicationScoped
public class TaskRepository implements PanacheMongoRepository<Task> {
    
	public Task getById(ObjectId id) {
		return findById(id);
	}
	
    public List<Task> getAll() {
        return listAll();
    }
    
    public List<Task> getAllByState(Boolean isDone) {
        return list("done", isDone);
    }
    
    public void createTask(Task task) {
    	persist(task);
    }
    
    public void updateTask(Task task) {
    	update(task);
    }
    
    public void deleteTask(ObjectId id) {
    	deleteById(id);
    }
}