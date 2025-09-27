package com.paypal.reward_service.controller;

import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.repository.RewardRepository;
import com.paypal.reward_service.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardController {
    private RewardService rewardService;

    @GetMapping("/user/{userId}")
    public List<Reward> getRewardsByUserId(@PathVariable Long userId) {
        return rewardService.getRewardsByUserId(userId);
    }
}
