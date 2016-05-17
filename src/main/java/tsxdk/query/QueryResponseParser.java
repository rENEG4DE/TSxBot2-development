package tsxdk.query;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.QueryResponse;
import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.QueryResultSet;
import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.decorator.ErrorResponseDecorator;
import com.teamspeak.skymaster.tscontroller.tsmanagement.query.model.decorator.SingleEntityResponseDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Ulli Gerhard on 09.10.2015.
 */
public class QueryResponseParser {
    private static final Logger log = LoggerFactory.getLogger(QueryResponseParser.class);

    public static QueryResponse parse(String response) throws IllegalArgumentException {
        log.debug("Parsing response ({})", response);
        if (!isValidResponse(response)) {
            throw new IllegalArgumentException("Not a valid Teamspeak-response: (" + response + ")");
        }

        final QueryResponse result;

        switch (identifyType(response)) {
            case TYPE_RESULT: {
                result = parseResult(response);
            }
            break;
            case TYPE_ERROR: {
                result = parseError(response);
            }
            break;
            default:
                throw new IllegalArgumentException("Could not parse response: (" + response + ")");
        }

        return result;
    }

    private static ResponseType identifyType(String response) {
        return response.startsWith("error") ? ResponseType.TYPE_ERROR : ResponseType.TYPE_RESULT;
    }

    private static QueryResultSet parseResult(String response) {
        return parseComplexResult(response);
    }

    private static ErrorResponseDecorator parseError(String response) {
        return new ErrorResponseDecorator(new SingleEntityResponseDecorator(parseComplexResult(response)));
    }

    //TODO: Make this look more fancy
    //TODO: Stop abusing Properties
    private static QueryResultSet parseComplexResult(String response) {
        final Table<Integer, String, String> table = HashBasedTable.create();
        final int[] row = {0};

        Arrays.stream(response.split("[|]")).map(s -> {
                    final Properties properties = new Properties();
                    try {
                        properties.load(new StringReader(s.replaceAll("[ ]", "\n")));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return (Map) properties;
                }
        ).forEach(
                map -> {
                    map.forEach((key, val) -> {
                        table.put(row[0], (String) key, (String) val);
                    });
                    row[0]++;
                }
        );

        return new QueryResultSet(table);
    }

    private static boolean isValidResponse(String response) {
        return response != null && !response.isEmpty();
    }

    private enum ResponseType {
        TYPE_RESULT,
        TYPE_ERROR;
    }
}