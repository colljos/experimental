package com.java7developer.chapter2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Code for listing 2_6 - filter source files by walking the tree
 * 
 * TODO When build 130 is released, this entire example will be redundant
 * 
 * @author Ben Evans and Martijn Verburg
 */
public class Listing_2_6 {

    public static void main(String[] args) throws IOException
    {
        Path startingDir = Paths.get("C:/Projects/workspace");
        Files.walkFileTree(startingDir, new FindJavaVisitor());
    }
    
    private static class FindJavaVisitor extends SimpleFileVisitor<Path>
    {
    	public FindJavaVisitor() {};

        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        	
        	if (file != null && attrs != null)
        	{
        		if (file.getName(0).toString().endsWith(".java"))
        		{
        			System.out.println(file.getName(0).toString());
        		}
        	}
            return FileVisitResult.CONTINUE;
        }
    }
}