package com.scb.ivr.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scb.ivr.model.rkalees.CardDetails;

@RestController
@RequestMapping("/cardLostUpdate")
public class CardLostController {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	private String setCardLost(@RequestBody CardDetails cardDetails) {
		String response = "";
		try {
			String cardNum = cardDetails.getCardNum();
			String relID = cardDetails.getRelID();
			if (cardNum != null && cardNum.length() == 16) {
				if (relID != null && !(relID.isEmpty())) {
					response = new String(Files.readAllBytes(
							Paths.get("D:/Subbu/host/BH/DummyResponse/RKALEES/setCardLost/" + cardNum + ".json")));
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		} catch (Exception e) {
			System.out.println();
		}
		return response;
	}
}
