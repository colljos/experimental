package com.books.pcjvm.chapt8;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Actors;
import akka.actor.UntypedActor;

public class SizeCollector extends UntypedActor {
	
	private final List<ActorRef> idleFileProcessors = new ArrayList<ActorRef>();
	private final List<String> toProcessFilenames = new ArrayList<String>();
	private long pendingNumberOfFilesToVisit = 0L;
	private long totalSize = 0L;
	private final long start = System.nanoTime();
	
	@Override
	public void onReceive(final Object message) throws Exception {
		
		if (message instanceof RequestAFile) {
			idleFileProcessors.add(getContext().getSender().get());
			sendAFileToProcess();
		}
		
		if (message instanceof FileToProcess) {
			toProcessFilenames.add(((FileToProcess) message).fileName);
			pendingNumberOfFilesToVisit += 1;
			sendAFileToProcess();
		}
		
		if (message instanceof FileSize) {
			totalSize += ((FileSize) message).size;
			pendingNumberOfFilesToVisit -= 1;
			
			if (pendingNumberOfFilesToVisit == 0) {
				final long end = System.nanoTime();				
				System.out.println("Total Size: " + totalSize);
				System.out.println("Time taken: " + (end-start)/1.0e9);
				Actors.registry().shutdownAll();
			}
		}
		
	}

	private void sendAFileToProcess() {
		if (!toProcessFilenames.isEmpty() && !idleFileProcessors.isEmpty()) {
			idleFileProcessors.remove(0).tell(new FileToProcess(toProcessFilenames.remove(0)));
		}
	}
}
