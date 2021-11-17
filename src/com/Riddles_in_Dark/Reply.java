package com.Riddles_in_Dark;


import java.io.Serializable;

/**
 * This class represents all messages sent from server to client
 */
public class Reply implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nextOperation;
    private Object replyData;
    private String reply;
    private User user = null;

    /**
     * @param response
     * @param user
     * @param replyData
     * @param nextOperation
     */
    public Reply(String response, User user, Object replyData, String nextOperation) {
        super();
        this.reply = response;
        this.replyData = replyData;
        this.nextOperation = nextOperation;
        this.user = user;
    }

    public String getResponse() {
        return this.reply;
    }

    public Object getReplyData() {
        return this.replyData;
    }

    public String nextOperation() { return this.nextOperation; }

    public User getUser() { return this.user; }

    ;

/**
 * {
 *      nextOperation: "join team" | "choose character"
 *      data: ["team1", "team2"] | []
 * }
 */
}
