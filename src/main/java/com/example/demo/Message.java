package com.example.demo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * A message that can be "sent"/"tweeted" in the BullHorn app.
 *
 * @author amp
 */
@Entity
public class Message {

    /**
     * Message Id, unique for each message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Full text of the message. (unformatted plaintext)
     */
    @NotNull
    private String content;

    /**
     * Date message was posted.
     *
     * (String representation for now; future: store as date field)
     */
    //@NotNull   maybe put this back once we figure out how to prevent the form from checking it until we've had a chance to fill it in!
    private String postedDate;

    /**
     * Username of the user who sent this message (taken from the current logged
     * in user)
     *
     * Future: store the ID from the "User" table. For this small project, we do
     * not have a user table, and hard code some available logins at startup,
     * so we use the user's login name.
     */
    //@NotNull   maybe put this back once we figure out how to prevent the form from checking it until we've had a chance to fill it in!
    private String fromUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
    
    
}
