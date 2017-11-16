package ch.jaunerc.ttt_client.jersey;

public class RestRequestExecutor {

    private RestConnectionHandler connectionHandler;

    public RestRequestExecutor(RestConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }
}
