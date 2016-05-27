package tsxdk.query.model;

/**
 *  TSxBot2
 *  Coded by rENEG4DE
 *  on 27. of Mai
 *  2016
 *  10:22
 */
public enum LibQuery {
    LOGIN("login client_login_name=%s client_login_password=%s"),
    SERVERLIST("serverlist"),
    SERVERCREATE("servercreate virtualserver_name=%s virtualserver_log_query=1"),
    SERVERSTOP("serverstop sid=%d"),
    SERVERSTART("serverstart sid=%d"),
    SERVERDELETE("serverdelete sid=%d"),
    CHANNELLIST("channellist"),
    CHANNELCREATE("channelcreate channel_name=%s channel_password=%s channel_flag_permanent=1"),
    CHANNELINFO("channelinfo cid=%d"),
    CHANNELDELETE("channeldelete cid=%d force=1"),
    SERVERPROCESSSTOP("serverprocessstop"),
    PRIVILEGEKEYDELETE("privilegekeydelete token=%s"),
    USE("use sid=%d");

    private final String query;

    LibQuery(String query) {
        this.query = query;
    }

    public String get() {
        return query;
    }
}