package github.com.felipebarbosaferreira.Task;

import java.util.Date;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.MongoEntity;

@MongoEntity(collection="tasks")
public class Task {
    public ObjectId id;
    public Boolean done;
    public Date created;
    public String macaddress;
    public Integer type;
    public String title;
    public String description;
    public Date when;
    
	public Task() {
		super();
	}

	public Task(String macaddress, Integer type, String title, String description, Date when) {
		super();
		this.done = Boolean.FALSE;
		this.created = new Date();
		this.macaddress = macaddress;
		this.type = type;
		this.title = title;
		this.description = description;
		this.when = when;
	}

	public ObjectId getId() {
		return id;
	}

	public Boolean getDone() {
		return done;
	}

	public Date getCreated() {
		return created;
	}

	public String getMacaddress() {
		return macaddress;
	}

	public Integer getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getWhen() {
		return when;
	}
}