package tsxdk.query.model.wrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tsxdk.query.model.Query;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public class ErrorResponse extends ResponseWrapper {
    private final Logger log = LoggerFactory.getLogger(ErrorResponse.class);
    private final int id;
    private final String message;

    public ErrorResponse(SingleEntityResponse resultSet) {
        super(resultSet);
        id = Integer.parseInt(resultSet.get("id"));
        message = resultSet.get("msg");
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public boolean isOK() {
        return id == 0;
    }

    @Override
    public String toString() {
        return "ErrorResponse" + "{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public void assignTo(Query qry) {
        log.debug("Assigning error ({}) to ({})", toString(), qry.getQueryString());
        qry.setError(this);
    }
}
