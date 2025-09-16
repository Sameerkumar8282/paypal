package com.paypal.reward_service.service.impl;

import com.paypal.reward_service.entity.Reward;
import com.paypal.reward_service.repository.RewardRepository;
import com.paypal.reward_service.service.RewardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private RewardRepository rewardRepository;
    @Override
    public Reward sendReward(Reward reward) {
        reward.setSentAt(LocalDateTime.now());
        return rewardRepository.save(reward);
    }

    @Override
    public List<Reward> getRewardsByUserId(Long UserId) {
        return rewardRepository.findByUserId(UserId);
    }
}
