package com.lush.javaAggregator.modles;

import java.util.ArrayList;
import java.util.List;

public class Podcast {

  private long id;

  /**
   * User's name.
   */
  private String name;

  /**
   * User's id.
   */
  private long user_id;

  /**
   * Pod cast's description.
   */
  private String description;

  /**
   * Created timestamp.
   */
  private String created_at;

  /**
   * Updated timestamp.
   */
  private String updated_at;

  /**
   * Audio Files by podcasts id.
   */

  private List<Audio> audio_files = new ArrayList<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getUser_id() {
    return user_id;
  }

  public void setUser_id(long user_id) {
    this.user_id = user_id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }

  public List<Audio> getAudio_files() {
    return audio_files;
  }

  public void setAudio_files(List<Audio> audio_files) {
    this.audio_files = audio_files;
  }
}
