package com.paste_bin_clone.services;

import com.paste_bin_clone.entities.PasteEntity;
import com.paste_bin_clone.repositories.PasteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpiredPasteCleaner {

    private final PasteRepository pasteRepository;

    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteExpiredPastes() {
        log.info("Starting expired pastes cleanup...");
        List<PasteEntity> expiredPastes = pasteRepository.findAllByDeadTimeBefore(Instant.now());
        if (!expiredPastes.isEmpty()) {
            pasteRepository.deleteAll(expiredPastes);
            log.info("Deleted {} expired pastes", expiredPastes.size());
        } else {
            log.info("No expired pastes found");
        }
    }
}
