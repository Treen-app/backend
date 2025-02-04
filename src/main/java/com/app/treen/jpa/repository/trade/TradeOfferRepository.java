package com.app.treen.jpa.repository.trade;

import com.app.treen.trade.entity.TradeOffer;
import com.app.treen.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeOfferRepository extends JpaRepository<TradeOffer, Long> {

    List<TradeOffer> findAllByBuyer(User user);
    List<TradeOffer> findAllBySeller(User user);
}
