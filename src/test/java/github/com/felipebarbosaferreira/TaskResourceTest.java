package github.com.felipebarbosaferreira;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import github.com.felipebarbosaferreira.task.Task;
import github.com.felipebarbosaferreira.task.TaskService;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class TaskResourceTest {

	@InjectMock
	TaskService taskService;

	public void setup() {
		Task taskMock1 = taskMock("Task Mock primeira");
		Task taskMock2 = taskMock("Task Mock segunda");
		Task taskMock3 = taskMock("Task Mock terceira");
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(taskMock1);
		tasks.add(taskMock2);
		tasks.add(taskMock3);

		Mockito.when(taskService.getAll()).thenReturn(tasks);
		Mockito.when(taskService.getById("5ec5b421e7e9d736feac78fd")).thenReturn(taskMock1);
		Mockito.when(taskService.getAllByState(Boolean.TRUE))
				.thenReturn(tasks.stream().filter(it -> it.getDone()).collect(Collectors.toList()));
		Mockito.when(taskService.getAllByState(Boolean.FALSE))
				.thenReturn(tasks);
		QuarkusMock.installMockForType(taskService, TaskService.class);
	}

	@Test
	public void testGetEndpointEmpty() {
		given().when().get("/task").then().statusCode(200).body(is("[]"));
		given().when().get("/task/isDone/FALSE").then().statusCode(200).body(is("[]"));
	}

	@Test
	public void testGetEndpoint() {
		setup();
		given().when().get("/task").then().statusCode(200).assertThat().body(containsString("Task Mock primeira"),
				containsString("Task Mock segunda"), containsString("Task Mock terceira"));

		given().when().get("/task/5ec5b421e7e9d736feac78fd").then().statusCode(200).assertThat()
				.body(containsString("Task Mock primeira"));
		
		given().when().get("/task/isDone/FALSE").then().statusCode(200).body(containsString("Task Mock primeira"));
		
		given().when().get("/task/isDone/TRUE").then().statusCode(200).body(is("[]"));
	}

	@Test
	public void testSaveEndpoint() throws ParseException {
		given().body(getMockSaveTaskTest()).contentType(MediaType.APPLICATION_JSON).when().post("/task").then()
				.statusCode(204);
		Mockito.verify(taskService).saveTask(Mockito.any(Task.class));
	}

	@Test
	public void testUpdateEndpoint() throws ParseException {
		given().body(getMockUpdateTaskTest()).contentType(MediaType.APPLICATION_JSON).when().put("/task").then()
				.statusCode(204);
		Mockito.verify(taskService).updateTask(Mockito.any(Task.class));
	}

	@Test
	public void testDeleteEndpoint() throws ParseException {
		given().when().delete("/task/5ec5b421e7e9d736feac78fd").then().statusCode(204);
		Mockito.verify(taskService).deleteTask("5ec5b421e7e9d736feac78fd");
	}

	private Task taskMock(String nameTask) {
		return new Task("02:15:B2:00:00:00", 2, nameTask, "Descricao TaskResourceTest " + nameTask, new Date());
	}

	private String getMockSaveTaskTest() {
		return "{\n" + "		    \"created\": null,\n"
				+ "		    \"description\": \"Quarkus teste para salvar task\",\n" + "		    \"done\": null,\n"
				+ "		    \"id\": null,\n" + "		    \"macaddress\": \"72:C3:C1:B9:7E:4E\",\n"
				+ "		    \"title\": \"Quarkus salvar task\",\n" + "		    \"type\": 3,\n"
				+ "		    \"when\": \"2020-07-18T00:02:00Z[UTC]\"\n" + "		  }";
	}

	private String getMockUpdateTaskTest() {
		return "{\n" + "		    \"created\": \"2020-06-13T22:09:47.791Z[UTC]\",\n"
				+ "		    \"description\": \"Quarkus teste para salvar task finalizado\",\n"
				+ "		    \"done\": true,\n" + "		    \"id\": null,\n"
				+ "		    \"macaddress\": \"72:C3:C1:B9:7E:4E\",\n"
				+ "		    \"title\": \"Quarkus update task\",\n" + "		    \"type\": 3,\n"
				+ "		    \"when\": \"2020-07-18T00:02:00Z[UTC]\"\n" + "		  }";
	}
}