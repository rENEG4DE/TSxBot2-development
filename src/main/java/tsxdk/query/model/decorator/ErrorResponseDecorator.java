package tsxdk.query.model.decorator;

import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Ulli Gerhard on 09.10.2015.
 */
public class ErrorResponseDecorator extends QueryResponseDecorator {
    private final Logger log = LoggerFactory.getLogger(ErrorResponseDecorator.class);
    private final int id;
    private final String message;

    public ErrorResponseDecorator(SingleEntityResponseDecorator resultSet) {
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
        return "ErrorResponseDecorator" + "{" +
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
