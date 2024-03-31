package com.example.elastic;

import java.util.function.Function;

public class ControllerBase {
    protected long _startTime,_endTime,_duration;
    protected String _status;
    protected String _value;
    protected long _count;
    private final String _returnValue ="{ \"start\" : %d, \"end\" : %d, \"duration\" : %d, \"count\" : %d,\"status\" :\"%s\", \"value\" : \"%s\" }" ;
    public ControllerBase () {

    }
    public String set(String value) {
        _value = value;
        return _value;
    }

    public String get(){
        return _value;
    }

    protected void _start() {
        _startTime = System.currentTimeMillis();
        _duration = 0;
        _count=0;
        _status ="started";
    }
    protected void _end(String status) {
        _endTime = System.currentTimeMillis();
        _duration = _endTime-_startTime;
        _status = status;
    }

    protected String _toJson() {
        return String.format(_returnValue,_startTime,_endTime,_duration,_count,_status,_value);
    }
}
