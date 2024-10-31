package com.pet_project.pastebin.paste;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PasteRepository extends MongoRepository<Paste, String> {

}
