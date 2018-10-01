//package com.ebridgevas.vas.content;
//
//
//import java.io.IOException;
//
//import org.asteriskjava.fastagi.AgiException;
//import org.asteriskjava.fastagi.AgiOperations;
//import org.onebusaway.probablecalls.TextToSpeechFactory;
//
//public class RemoteSwiftTextToSpeechFactoryImpl implements TextToSpeechFactory {
//
//    static final String AGI_SCRIPT = "agi-ensure-swift-stream-file.agi";
//
//    static final String VAR_NAME = "swift_stream_file";
//
//    private String _commandSeparator = ",";
//
//    public void setCommandSeparator(String commandSeparator) {
//        _commandSeparator = commandSeparator;
//    }
//
//    public char getAudio(AgiOperations opts, String text, String escapeDigits)
//            throws IOException, AgiException {
//
//        String options = AGI_SCRIPT + _commandSeparator
//                + VAR_NAME + _commandSeparator + escape(text);
//        opts.exec("AGI", options);
//        String streamFile = opts.getVariable(VAR_NAME);
//        return opts.streamFile(streamFile, escapeDigits);
//    }
//
//    private String escape(String value) {
//        value = value.replaceAll("\"", "\\\\\"");
//        return value.replaceAll(_commandSeparator, "\\\\"+_commandSeparator);
//    }
//}
