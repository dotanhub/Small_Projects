import java.net.InetAddress;

/**
 * Created by Dotan Huberman on 28/06/2017.
 */
public class RegisteredClient {
    private InetAddress host;
    private int port;

    /**
     * constructor
     * @param host host address
     * @param port port
     */
    public RegisteredClient(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public boolean equals(Object obj) {
        RegisteredClient reg = (RegisteredClient)obj;
        return reg.host.equals(this.host) && reg.port==this.port;
    }

    public InetAddress getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
