package com.jc.test.disruptor;

import java.io.File;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;

public class FileSizeEvent {

    private File filename;
    private RingBuffer<FileSizeEvent> ringBuffer;

    public File getFilename()
    {
        return filename;
    }

	public RingBuffer<FileSizeEvent> getRingBuffer() {
		return ringBuffer;
	}

	public void setFilename(final File file)
    {
        this.filename = file;
    }

    public final static EventFactory<FileSizeEvent> EVENT_FACTORY = new EventFactory<FileSizeEvent>()
    {
        public FileSizeEvent newInstance()
        {
            return new FileSizeEvent();
        }
    };

	public void setValue(File file, RingBuffer<FileSizeEvent> ringBuffer) {
		filename = file;
		this.ringBuffer = ringBuffer;
	}

}
