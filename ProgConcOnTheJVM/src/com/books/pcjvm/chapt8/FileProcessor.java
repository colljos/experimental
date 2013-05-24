package com.books.pcjvm.chapt8;

import java.io.File;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class FileProcessor extends UntypedActor {

	private final ActorRef sizeCollector;

	public FileProcessor(final ActorRef theSizeCollector) {
		this.sizeCollector = theSizeCollector;
	}
	
	@Override
	public void preStart() {
		registerToGetFile();
	}
	
	private void registerToGetFile() {
		//sizeCollector.sendOneWay(new RequestAFile(), getContext());
		sizeCollector.tell(new RequestAFile(), getContext());
	}

	@Override
	public void onReceive(Object message) throws Exception {
		FileToProcess fileToProcess = (FileToProcess) message;
		final File file = new File(fileToProcess.fileName);
		long size = 0L;
		if (file.isFile()) {
			size = file.length();
		}
		else {
			File[] children = file.listFiles();
			if (children != null) {
				for (File child : children) {
					if (child.isFile()) {
						size += child.length();						
					}
					else {
						//sizeCollector.sendOneWay(new FileToProcess(child.getPath()));
						sizeCollector.tell(new FileToProcess(child.getPath()));
					}
				}
			}
		}
		
		// sizeCollector.sendOneWay(new FileSize(size));
		sizeCollector.tell(new FileSize(size));
		registerToGetFile();
	}

}
