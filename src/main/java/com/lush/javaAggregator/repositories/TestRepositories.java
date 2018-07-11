package com.lush.javaAggregator.repositories;

import com.lush.javaAggregator.modles.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepositories extends JpaRepository<OauthClientDetails, String> {

}
