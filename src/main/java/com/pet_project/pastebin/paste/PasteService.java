package com.pet_project.pastebin.paste;


import com.pet_project.pastebin.tools.Hashing;
import com.pet_project.pastebin.tools.PasteStatus;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class PasteService {
    PasteRepository repository;

    PasteService(PasteRepository rep) {
        this.repository = rep;
    }

    public void savePaste(Paste paste) {
        paste.setId(null);
        if (paste.getPassword() != null) {
            try {
                byte[] tmp = Hashing.createHash(paste.getPassword().getBytes());
                byte[] b64encoded = Base64.getEncoder().encode(tmp);
                String hashedEncodedPassword = new String(b64encoded);
                paste.setPassword(hashedEncodedPassword);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        repository.save(paste);
    }

    public Pair<PasteStatus, String> getPasteByHash(String hash, String password) {
        Optional<Paste> opt = repository.findById(hash);
        if (opt.isEmpty())
            return new Pair<>(PasteStatus.NO_SUCH_PASTE, null);
        Paste paste = opt.get();
        if (paste.getPassword() != null) {
            try {
                byte[] tmp = Hashing.createHash(password.getBytes());
                byte[] b64encoded = Base64.getEncoder().encode(tmp);
                String hashedEncodedPassword = new String(b64encoded);
                if (hashedEncodedPassword.equals(paste.getPassword())) {
                    return new Pair<>(PasteStatus.PASTE_FOUND, paste.getText());
                } else {
                    return new Pair<>(PasteStatus.PASSWORD_INCORRECT, null);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        return new Pair<>(PasteStatus.PASTE_FOUND, paste.getText());
    }
}
