package com.fanfte.hospital.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fanfte.datatransfer.DataTransfer;


public class DataServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public DataServlet() {
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	public void init() throws ServletException {
		DataTransfer.startTransfer();
	}
	
}
