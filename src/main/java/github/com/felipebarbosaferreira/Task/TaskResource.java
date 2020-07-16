package github.com.felipebarbosaferreira.Task;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/task")
public class TaskResource {
    @Inject
    TaskService taskService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAll(){
        return taskService.getAll();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Task getById(@PathParam("id") String id){
        return taskService.getById(id);
    }

    @GET
    @Path("isDone={isDone}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Task> getAllByState(@PathParam("isDone") Boolean isDone){
        return taskService.getAllByState(isDone);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Example format object<br>" +
    		"{<br>" + 
    		"&emsp; \"description\": \"Quarkus nova tarefa\",<br>" + 
    		"&emsp; \"macaddress\": \"02:15:B2:00:00:00\",<br>" + 
    		"&emsp; \"title\": \"Quarkus teste de nova tarefa\",<br>" + 
    		"&emsp; \"type\": 0,<br>" + 
    		"&emsp; \"when\": \"2020-07-16T21:10:13.123-03:00\"<br>" + 
    		"}")
    // TODO - Make a bean to input
    public void createTask(Task task) throws ParseException{
        taskService.createTask(task);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateTask(Task task){
        taskService.updateTask(task);
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteTask(@PathParam("id") String id){
        taskService.deleteTask(id);
    }
}