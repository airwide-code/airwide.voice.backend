//package com.ebridgevas.vas.content;
//
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
//import org.asteriskjava.fastagi.AgiClientChannel;
//import org.asteriskjava.fastagi.AgiClientScript;
//import org.asteriskjava.fastagi.AgiException;
//import org.asteriskjava.fastagi.command.AgiRequestCommand;
//import org.asteriskjava.fastagi.reply.AgiReply;
//import org.asteriskjava.util.SocketConnectionFacade;
//
///**
// * An AgiConnectionHandler is created and run by the AgiServer whenever a new
// * socket connection from an Asterisk Server is received.
// * <p>
// * It reads the request using an AgiReader and runs the AgiScript configured to
// * handle this type of request. Finally it closes the socket connection.
// *
// * @author srt
// * @version $Id: AgiConnectionHandler.java 633 2007-03-25 17:02:18Z srt $
// */
//public class AgiClientConnection {
//
//    /**
//     * The socket connection.
//     */
//    private final SocketConnectionFacade socket;
//
//    /**
//     * Creates a new AGIConnectionHandler to handle the given socket connection.
//     *
//     * @param socket the socket connection to handle.
//     * @param mappingStrategy the strategy to use to determine which script to
//     *          run.
//     */
//    public AgiClientConnection(SocketConnectionFacade socket) {
//        this.socket = socket;
//    }
//
//    protected AgiReader createReader() {
//        return new FastAgiReader(socket);
//    }
//
//    protected AgiWriter createWriter() {
//        return new FastAgiWriter(socket);
//    }
//
//    public Future<?> run(AgiClientScript script, Map<String, String> parameters) {
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        return executor.submit(new Runner(script,parameters));
//    }
//
//    private class Runner implements Runnable {
//
//        private AgiClientScript _script;
//
//        private Map<String, String> _parameters;
//
//        public Runner(AgiClientScript script, Map<String, String> parameters) {
//            _script = script;
//            _parameters = parameters;
//        }
//
//        @Override
//        public void run() {
//            try {
//                AgiReader reader;
//                AgiWriter writer;
//
//                reader = createReader();
//                writer = createWriter();
//
//                AgiRequestCommand command = new AgiRequestCommand();
//                Map<String, String> params = command.getParameters();
//                params.putAll(_parameters);
//                writer.sendCommand(command);
//
//                AgiClientChannel channel = new AgiClientChannelImpl(writer);
//
//                while (!Thread.interrupted()) {
//                    AgiReply reply = reader.readReply();
//                    _script.service(reply, channel);
//                }
//            } catch (AgiException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    socket.close();
//                } catch (IOException e) // NOPMD
//                {
//                    // swallow
//                }
//            }
//        }
//    }
//}
//
//
