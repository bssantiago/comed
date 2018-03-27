package com.mhc.filters;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

public class PostHttpServletRequestWrapper extends HttpServletRequestWrapper {

	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	public PostHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
		try {
			IOUtils.copy(request.getInputStream(), outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(outputStream.toByteArray())));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		return new ServletInputStream() {

			@Override
			public int readLine(byte[] b, int off, int len) throws IOException {
				return inputStream.read(b, off, len);
			}

			@Override
			public int read() throws IOException {
				return inputStream.read();
			}
		};
	}

	public void setBody(String body) {
		outputStream = new ByteArrayOutputStream();
		try {
			outputStream.write(body.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getBody() {
		return new String(outputStream.toByteArray());
	}

}
