package com.Riddles_in_Dark;


import java.io.Serializable;

/**
 * This class represents all messages sent from server to client
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nextOperation;
    private Object responseInfo;
    private String response;
    private User user = null;

    /**
     * @param response
     * @param user
     * @param replyData
     * @param nextOperation
     */
    public Response(String response, User user, Object replyData, String nextOperation) {
        super();
        this.response = response;
        this.responseInfo = replyData;
        this.nextOperation = nextOperation;
        this.user = user;
    }

    public String getResponse() {
        return this.response;
    }

    public Object getResponseInfo() {
        return this.responseInfo;
    }

    public String nextOperation() { return this.nextOperation; }

    public User getUser() { return this.user; }

    ;


}
