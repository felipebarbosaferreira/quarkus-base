package github.com.felipebarbosaferreira.task;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((done == null) ? 0 : done.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((macaddress == null) ? 0 : macaddress.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((when == null) ? 0 : when.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (done == null) {
			if (other.done != null)
				return false;
		} else if (!done.equals(other.done))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (macaddress == null) {
			if (other.macaddress != null)
				return false;
		} else if (!macaddress.equals(other.macaddress))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (when == null) {
			if (other.when != null)
				return false;
		} else if (!when.equals(other.when))
			return false;
		return true;
	}
}