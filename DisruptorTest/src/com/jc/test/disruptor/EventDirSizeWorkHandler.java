/*
 * Copyright 2011 LMAX Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jc.test.disruptor;

import java.io.File;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.util.PaddedLong;

public final class EventDirSizeWorkHandler
    implements WorkHandler<FileSizeEvent>
{
    private final PaddedLong[] counters;
    private final int index;

    public EventDirSizeWorkHandler(final PaddedLong[] counters, final int index)
    {
		this.counters = counters;
        this.index = index;
    }

    @Override
    public void onEvent(final FileSizeEvent event) throws Exception
    {
       System.out.println("onEvent(): " + event.getFilename());
       exploreDir(event.getFilename(), event.getRingBuffer());    	
    }
    
	private void exploreDir(final File file, RingBuffer<FileSizeEvent> ringBuffer) {

		long fileSize = 0;
//		pendingFileVisits.incrementAndGet();

		if (file.isFile()) {
			fileSize = file.length();
		}
		else  {
			final File[] children = file.listFiles();
			if (children != null) {
				for (final File child : children) {
					if (child.isFile()) {
						fileSize += child.length();
					} else {
						// Publish further work to ring buffer
						// exploreDir(child);
						long sequence = ringBuffer.next();
						System.out.println("next ringBuffer sequence: "+sequence);
						ringBuffer.get(sequence).setValue(child, ringBuffer);
						System.out.println("onEvent(): exploring new directory [" + child + "]");
						ringBuffer.publish(sequence);						
					}
				}
			}
		}

		try {
	    	counters[index].set(counters[index].get() + fileSize);
//	    	totalFileSize.getAndAdd(fileSize);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
//		pendingFileVisits.decrementAndGet();
		
	}
}
