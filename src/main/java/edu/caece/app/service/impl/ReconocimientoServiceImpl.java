package edu.caece.app.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.springframework.stereotype.Service;

import edu.caece.app.service.ReconocimientoService;

@Service
public class ReconocimientoServiceImpl implements ReconocimientoService {
	
	@Override
	public String reconocer(byte[] imagenCara) {
		try {
			HttpResponse<String> result = HttpClient.newBuilder().build().send(
					HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:8085/reconocedor/matias")).build(), 
					BodyHandlers.ofString());
			return result.body();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
