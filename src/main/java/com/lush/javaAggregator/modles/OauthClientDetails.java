package com.lush.javaAggregator.modles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@Entity
@Data
@Table(name = "oauth_client_details")
public class OauthClientDetails {

  @Column(name = "client_id")
  private String client_id;

  private String resource_ids;

  private String client_secret;

  private String scope;

  private String authorized_grant_types;

  private String web_server_redirect_uri;

  private String authorities;

  private long access_token_validity;

  private long refresh_token_validity;

  private String additional_information;

  private String autoapprove;

}
