package tsxdk.query;


import tsxdk.query.model.Query;
import static tsxdk.query.model.LibQuery.*;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public class QueryFactory {
    public Query login(String name, String pw) {
        return new Query(String.format(LOGIN.get(), name, pw));
    }

    public Query servercreate(String serverName) {
        return new Query(String.format(SERVERCREATE.get(), serverName));
    }

    public Query channelcreate(String channelName, String password) {
        return new Query(String.format(CHANNELCREATE.get(), channelName, password));
    }

    public Query serverprocessstop() {
        return new Query(SERVERPROCESSSTOP.get());
    }

    public Query serverinfo() {
        return new Query(SERVERLIST.get());
    }

    public Query stopvirtualserver(int serverId) {
        return new Query(String.format(SERVERSTOP.get(), serverId));
    }

    public Query deletevirtualserver(int serverId) {
        return new Query(String.format(SERVERDELETE.get(), serverId));
    }

    public Query channellist() {
        return new Query(String.format(CHANNELLIST.get()));
    }

    public Query use(int sid) {
        return new Query(String.format(USE.get(), sid));
    }

    public Query serverstart(int sid) {
        return new Query(String.format(SERVERSTART.get(), sid));
    }

    public Query privilegekeydelete(String token) {
        return new Query(String.format(PRIVILEGEKEYDELETE.get(), token));
    }

    public Query channelinfo(int cid) {
        return new Query(String.format(CHANNELINFO.get(), cid));
    }

    public Query channeldelete(int cid) {
        return new Query(String.format(CHANNELDELETE.get(), cid));
    }

    public Query logout() {
        return new Query(String.format(LOGOUT.get()));
    }
}