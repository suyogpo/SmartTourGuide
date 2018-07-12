package com.tomtom.tourathon.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class TourathonFileUtils {
	public void writeToUserFile(final String fileName, final String userJson) throws IOException {
		String dir = this.getClass().getResource("/userInfo").getFile();
        OutputStream os = new FileOutputStream(dir + "/"+fileName);
        final PrintStream printStream = new PrintStream(os);
        printStream.println(userJson);
        printStream.close();
	}

}
