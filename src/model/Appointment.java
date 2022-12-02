package model;

import java.time.LocalDateTime;

public class Appointment {
    int id, customer_id, user_id, contact_id;
    String title, description, location, type;
    LocalDateTime start, end;

    Appointment(int id, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customer_id, int user_id, int contact_id)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customer_id = id;
        this.user_id = id;
        this.contact_id = id;
    }

    int getId()
    {
        return id;
    }

    int getCustomerId()
    {
        return customer_id;
    }

    int getUserId()
    {
        return user_id;
    }

    int getContactId()
    {
        return contact_id;
    }

    String getTitle()
    {
        return title;
    }

    String getDescription()
    {
        return description;
    }

    String getLocation()
    {
        return location;
    }

    String getType()
    {
        return type;
    }

    LocalDateTime getStart()
    {
        return start;
    }

    LocalDateTime getEnd()
    {
        return end;
    }
}
