package ch.jaunerc.ttt_client.jersey;

import ch.jaunerc.ttt_client.tictactoe.Board;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

public class RestConnectionHandler {

    private Client client;
    private WebTarget target;
    private Invocation.Builder request;

    public RestConnectionHandler(URI backendUri) {
        client = ClientBuilder.newClient();
        target = client.target(backendUri);
        request = target.request();
    }

    public Board getNextTurn(final Board currentBoard) {
        Response response = request.post(Entity.entity(currentBoard, MediaType.APPLICATION_JSON_TYPE));
        return response.readEntity(Board.class);
    }
}
