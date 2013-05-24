package com.jc.test.disruptor;

import java.io.File;

import com.lmax.disruptor.EventFactory;

public class ValueEvent {

	private long valueLong;

	public void setValue(long valueLong) {
		this.valueLong = valueLong;	
	}
	
    public long getValue()
    {
        return valueLong;
    }

    public final static EventFactory<ValueEvent> EVENT_FACTORY = new EventFactory<ValueEvent>()
    {
        public ValueEvent newInstance()
        {
            return new ValueEvent();
        }
    };
    

	
}
