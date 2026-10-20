package com.tu.javaproject.service;

import com.tu.javaproject.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricingService {

    private final SeasonRepository seasonRepository;
}
