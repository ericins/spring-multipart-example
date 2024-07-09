package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
class Controller {

	@PutMapping("/putobject/{id}")
	public CompletableFuture<ResponseEntity<Void>> putObject(@PathVariable String id, @RequestBody MultipartFile file)
			throws IOException {
		System.out.println("PUT " + id + " named " + file.getOriginalFilename());
		CompletableFuture<String> response = doWhatever(file.getInputStream());
		return response.thenApplyAsync(r -> ResponseEntity.ok().build());
	}

	private static CompletableFuture<String> doWhatever(InputStream inputStream) {
		return CompletableFuture.supplyAsync(() -> {
					try {
						Thread.sleep(5000);
						return inputStream.toString();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
		);
	}
}