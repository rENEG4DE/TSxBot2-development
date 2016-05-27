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
public class ErrorResponseWrapper extends QueryResponseDecorator {
    private final Logger log = LoggerFactory.getLogger(ErrorResponseWrapper.class);
    private final int id;
    private final String message;

    public ErrorResponseWrapper(SingleEntityResponseDecorator resultSet) {
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
        return "ErrorResponseWrapper" + "{" +
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
